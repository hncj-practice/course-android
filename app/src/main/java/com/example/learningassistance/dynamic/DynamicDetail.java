package com.example.learningassistance.dynamic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningassistance.R;
import com.example.learningassistance.course.Dynamic;
import com.example.learningassistance.entity.Comment;
import com.example.learningassistance.utils.MyTransForm;
import com.example.learningassistance.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DynamicDetail extends AppCompatActivity {
    @BindView(R.id.dynamic_user_avatar) ImageView avatar;
    @BindView(R.id.dynamic_user_name) TextView username;
    @BindView(R.id.dynamic_commit_time) TextView commitTime;
    @BindView(R.id.dynamic_content_text) TextView content;
    @BindView(R.id.dynamic_image_gridlayout) GridLayout gridLayout;
    @BindView(R.id.dynamic_reply_button) TextView replyIcon;

    @BindView(R.id.comment_send_text) EditText sendText;
    @BindView(R.id.comment_send_button) TextView sendButton;
    @BindView(R.id.dynamic_reply_layout) LinearLayout replyLayout;

    private Dynamic dynamic;
    private Point outsize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_detail);
        ButterKnife.bind(this);
        Utils.setTitle(this,"详情");
        Intent intent = getIntent();
        dynamic = (Dynamic) Objects.requireNonNull(intent.getExtras()).getSerializable("dynamic");

        outsize = new Point();
        getWindowManager().getDefaultDisplay().getSize(outsize);

        initComponent();
        Utils.setRecycler(this,R.id.recycler_dynamic_comment,new CommentAdapter(initComment()));

        sendButton.setOnClickListener(v -> {
            /**
             * 发送回复到数据库,同时刷新本机
             */
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
            replyLayout.setVisibility(View.GONE);
        });

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

    /**
     * 加载组件
     */
    public void initComponent(){
        Picasso.get().load(dynamic.getAvatar()).transform(new MyTransForm.RangleTransForm()).into(avatar);
        username.setText(dynamic.getName());
        commitTime.setText(dynamic.getTime());
        content.setText(dynamic.getContent());

        if (dynamic.getImages().size() < 1){
            gridLayout.setVisibility(View.GONE);
        } else {
            for (String s : dynamic.getImages()){
                ImageView imageView = new ImageView(gridLayout.getContext());
                int size = (outsize.x - 60)/ 3 - 20;
                Picasso.get().load(s).transform(new MyTransForm.SquareTransForm(size)).into(imageView);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
                GridLayout.LayoutParams gl = new GridLayout.LayoutParams(layoutParams);
                gl.setMargins(10,10,10,10);
                imageView.setLayoutParams(gl);
                gridLayout.addView(imageView);
            }
        }
    }

    public List<Comment> initComment(){
        String commentId = "123456789";
        List<Comment> comments = new ArrayList<>();
        List<String> reply = new ArrayList<>();
        reply.add("{\"commentId\":'123456789', \"username\":'江道宽', \"imgUrl\":'https://qlogo1.store.qq.com/qzone/846557024/846557024/50?1600155820', \"content\":'就这就这', \"time\":'不知道', \"replyName\":'冯叶展'}");
        reply.add("{\"commentId\":'123456789', \"username\":'江道宽', \"imgUrl\":'https://qlogo1.store.qq.com/qzone/846557024/846557024/50?1600155820', \"content\":'就这就这', \"time\":'不知道', \"replyName\":'江道宽'}");
        reply.add("{\"commentId\":'123456789', \"username\":'江道宽', \"imgUrl\":'https://qlogo1.store.qq.com/qzone/846557024/846557024/50?1600155820', \"content\":'就这就这', \"time\":'不知道', \"replyName\":'吴硕'}");
        Comment comment = new Comment(commentId,"江道宽","https://qlogo1.store.qq.com/qzone/846557024/846557024/50?1600155820","就这就这","不知道");
        comment.setReplyList(reply);
        comments.add(comment);
        comments.add(new Comment(commentId,"江道宽","https://qlogo1.store.qq.com/qzone/846557024/846557024/50?1600155820","就这就这","不知道"));
        comment = new Comment(commentId,"江道宽","https://qlogo1.store.qq.com/qzone/846557024/846557024/50?1600155820","就这就这","不知道");
        comment.setReplyList(reply);
        comments.add(comment);
        comments.add(new Comment(commentId,"江道宽","https://qlogo1.store.qq.com/qzone/846557024/846557024/50?1600155820","就这就这","不知道"));
        comments.add(new Comment(commentId,"江道宽","https://qlogo1.store.qq.com/qzone/846557024/846557024/50?1600155820","就这就这","不知道"));
        comments.add(new Comment(commentId,"江道宽","https://qlogo1.store.qq.com/qzone/846557024/846557024/50?1600155820","就这就这","不知道"));
        return comments;
    }

    public void startReply(View view) {
        replyLayout.setVisibility(View.VISIBLE);
    }

    private class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
        private List<Comment> comments;

        public CommentAdapter(List<Comment> comments) {
            this.comments = comments;
        }

        @NonNull
        @Override
        public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_dynamic_comment, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
            Comment comment = comments.get(position);
            Picasso.get().load(comment.getImgUrl()).transform(new MyTransForm.RangleTransForm()).into(holder.avatar);
            holder.username.setText(comment.getUsername());
            holder.time.setText(comment.getTime());
            holder.content.setText(comment.getContent());

            holder.reply.setOnClickListener(v -> {
                /**
                 * 更新被回复者的信息
                 */
                replyIcon.performClick();
            });

            //如果有评论有回复则显示回复的数量
            if (comment.getReplyList().size() > 0){
                String s = comment.getReplyList().size() + "条回复";
                holder.replyNum.setText(s);
                holder.replyNum.setOnClickListener(v -> {
                    /**
                     * 跳转到评论的回复详情页
                     */
                   Intent intent = new Intent("com.action.DYNAMIC_Reply_Detail");
                   intent.putExtra("commentId",comment.getCommentId());
                   intent.putExtra("commentName",comment.getUsername());
                   intent.putExtra("title",holder.replyNum.getText().toString());
                   Bundle bundle = new Bundle();
                   bundle.putSerializable("reply", (Serializable) comment.getReplyList());
                   intent.putExtras(bundle);
                   v.getContext().startActivity(intent);
                });
            } else {
                holder.replyNum.setVisibility(View.GONE);
            }


        }

        @Override
        public int getItemCount() {
            return comments.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView username,content,time,reply,replyNum;
            public ImageView avatar;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                username = itemView.findViewById(R.id.dynamic_comment_username);
                content = itemView.findViewById(R.id.dynamic_comment_content);
                time = itemView.findViewById(R.id.dynamic_comment_time);
                reply = itemView.findViewById(R.id.dynamic_comment_reply);
                replyNum = itemView.findViewById(R.id.comment_reply_detail);
                avatar = itemView.findViewById(R.id.dynamic_comment_avatar);
            }
        }
    }
}
