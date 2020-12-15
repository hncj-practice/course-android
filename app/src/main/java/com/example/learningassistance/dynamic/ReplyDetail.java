package com.example.learningassistance.dynamic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.learningassistance.R;
import com.example.learningassistance.entity.Comment;
import com.example.learningassistance.utils.MyTransForm;
import com.example.learningassistance.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReplyDetail extends AppCompatActivity {
    @BindView(R.id.reply_send_text) EditText sendText;
    @BindView(R.id.reply_send_button) TextView sendButton;
    @BindView(R.id.reply_reply_layout) LinearLayout replyLayout;

    private List<String> replyList;
    private String commentId;
    private String commentName;
    private List<Comment> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Utils.setTitle(this,intent.getStringExtra("title"));
        commentId = intent.getStringExtra("commentId");
        commentName = intent.getStringExtra("commentName");
        replyList = (List<String>) intent.getExtras().getSerializable("reply");
        commentList = strToComment(replyList);

        sendButton.setOnClickListener(v->{
            /**
             * 发送回复到数据库,同时刷新本机
             */
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
            replyLayout.setVisibility(View.GONE);
        });

        Utils.setRecycler(this,R.id.recycler_reply,new ReplyAdapter(commentList));

    }

    private class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder>{
        private List<Comment> comments;

        public ReplyAdapter(List<Comment> comments) {
            this.comments = comments;
        }

        @NonNull
        @Override
        public ReplyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_comment_reply, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReplyAdapter.ViewHolder holder, int position) {
            Comment comment = comments.get(position);
            Picasso.get().load(comment.getImgUrl()).transform(new MyTransForm.RangleTransForm()).into(holder.avatar);
            holder.name.setText(comment.getUsername());
            holder.time.setText(comment.getTime());
            String content = comment.getContent();
            //如果回复的人不为楼主,则这条回复是回复回复的回复(ps:没故意套娃)
            if (!comment.getReplyName().equals(commentName)){
                content = "回复 " + "<font color='#2F557D'><small>"+comment.getReplyName()+"</small></font>: " + content;
            }
            holder.content.setText(Html.fromHtml(content));

            holder.reply.setOnClickListener(v->{
                /**
                 * 更新被回复者的信息
                 */
                replyLayout.setVisibility(View.VISIBLE);
            });

        }

        @Override
        public int getItemCount() {
            return comments.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView name,content,time,reply;
            public ImageView avatar;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.comment_reply_username);
                content = itemView.findViewById(R.id.comment_reply_content);
                time = itemView.findViewById(R.id.comment_reply_time);
                reply = itemView.findViewById(R.id.comment_reply_reply);
                avatar = itemView.findViewById(R.id.comment_reply_avatar);
            }
        }
    }

    public List<Comment> strToComment(List<String> str){
        List<Comment> comments = new ArrayList<>();
        for (String s : str){
            JSONObject jo = JSON.parseObject(s);
            String commentId = jo.getString("commentId");
            String username = jo.getString("username");
            String imgUrl = jo.getString("imgUrl");
            String content = jo.getString("content");
            String time = jo.getString("time");
            String replyName = jo.getString("replyName");

            Comment c = new Comment(commentId,username,imgUrl,content,time);
            if (replyName != null){
                c.setReplyName(replyName);
            }
            comments.add(c);
        }
        return comments;
    }

    /**
     * 隐藏键盘及输入框
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        View v = getCurrentFocus();
        if (!Utils.isTouchPointInView(replyLayout, x, y)) {
            if (v != null){
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
            replyLayout.setVisibility(View.GONE);
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

}