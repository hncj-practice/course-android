package com.example.learningassistance.fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.learningassistance.R;
import com.example.learningassistance.adapter.MineAdapter;
import com.example.learningassistance.course.Dynamic;
import com.example.learningassistance.entity.Option;
import com.example.learningassistance.utils.MyTransForm;
import com.example.learningassistance.utils.RoundRectImageView;
import com.example.learningassistance.utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class MainPageFragment extends Fragment{
    private String data;
    private int id;
    private int size;

    /**
     * 碎片的有参构造,完成数据的传递
     * @param data 要传入碎片的数据
     * @param id 用来判断用户想要显示哪个碎片
     */
    public MainPageFragment(String data, int id) {
        this.data = data;
        this.id = id;
    }

    public MainPageFragment(String data, int id, int size) {
        this.data = data;
        this.id = id;
        this.size = size / 3 - 50;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        switch (id){
            case R.id.menu_homepage:
                view = inflater.inflate(R.layout.fg_homepage, container, false);
                loadHomepageFragment(view);
                break;
            case R.id.menu_message:
                view = inflater.inflate(R.layout.fg_message, container, false);
                loadMessageFragment(view);
                break;
            case R.id.menu_dynamic:
                view = inflater.inflate(R.layout.fg_dynamic, container, false);
                loadDynamicFragment(view);
                break;
            case R.id.menu_my:
                view = inflater.inflate(R.layout.fg_mine, container, false);
                loadMineFragment(view);
                break;
            default:
                break;
        }

        return view;
    }

    public void loadHomepageFragment(View view){
        Toast.makeText(this.getContext(), "正在加载首页", Toast.LENGTH_SHORT).show();
    }

    public void loadMessageFragment(View view){
        Toast.makeText(view.getContext(), "正在加载消息", Toast.LENGTH_SHORT).show();
    }

    public void loadDynamicFragment(View view){
        String content = "这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;";
        List<String> imgs = new ArrayList<>();
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oi8xtynj21040nmqd3.jpg");
        List<Dynamic> dynamicList = new ArrayList<>();
        Dynamic dynamic = new Dynamic("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg","JEmber","This is time",content,"103",imgs);
        dynamicList.add(dynamic);

        imgs = new ArrayList<>();
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oi7d5w5j21hc0u0n1p.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oi7d5w5j21hc0u0n1p.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oi7d5w5j21hc0u0n1p.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oi7d5w5j21hc0u0n1p.jpg");
        dynamic = new Dynamic("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg","JEmber","This is time",content,"103",imgs);
        dynamicList.add(dynamic);

        imgs = new ArrayList<>();
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oi836h6j21b80xeth3.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oi836h6j21b80xeth3.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oi836h6j21b80xeth3.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oi836h6j21b80xeth3.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oi836h6j21b80xeth3.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oi836h6j21b80xeth3.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oi836h6j21b80xeth3.jpg");
        dynamic = new Dynamic("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg","JEmber","This is time",content,"103",imgs);
        dynamicList.add(dynamic);

        imgs = new ArrayList<>();
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg");
        imgs.add("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg");
        dynamic = new Dynamic("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg","JEmber","This is time",content,"103",imgs);
        dynamicList.add(dynamic);

        Utils.setRecycler(view,R.id.recycler_dynamic,new DynamicAdapter(dynamicList));

    }

    /**
     * 加载Fragment"我的".
     * @param view "我的"在Activity中的fragment组件
     */
    public void loadMineFragment(View view){
        JSONObject jsonObject = JSON.parseObject(data);

        String localPath = jsonObject.getString("localPath");
        ImageView imageView = view.findViewById(R.id.mine_avatar);
        TextView username = view.findViewById(R.id.mine_user_name);
        TextView cla = view.findViewById(R.id.mine_user_class);

        RoundRectImageView.setUserAvatar(imageView,localPath,40);
        username.setText(jsonObject.getString("name"));
        cla.setText(jsonObject.getString("cla"));

        List<Option> options = initOption();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_mine);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(manager);
        MineAdapter adapter = new MineAdapter(options,data);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 加载每个选项所需的各种元素
     * @return 返回一个包含所有选项的List集合
     */
    public List<Option> initOption(){
        List<Option> options = new ArrayList<>();
        options.add(new Option("我的信息",R.drawable.icon_mine_info));
        options.add(new Option("我的课程",R.drawable.icon_mine_course));
        options.add(new Option("我的成绩",R.drawable.icon_mine_mark));
        options.add(new Option("发布动态",R.drawable.icon_mine_dynamic));
        options.add(new Option("设置",R.drawable.icon_mine_setting));
        return options;
    }

    private class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.ViewHolder>{
        private static final int MAX = 9;

        private List<Dynamic> dynamics;

        public DynamicAdapter(List<Dynamic> dynamics) {
            this.dynamics = dynamics;
        }

        @NonNull
        @Override
        public DynamicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_dynamic_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DynamicAdapter.ViewHolder holder, int position) {
            Dynamic dynamic = dynamics.get(position);
            holder.name.setText(dynamic.getName());
            holder.content.setText(dynamic.getContent());
            holder.time.setText(dynamic.getTime());
            holder.commentNum.setText(dynamic.getCommentNum());
            Picasso.get().load(dynamic.getAvatar()).transform(new MyTransForm.RangleTransForm(10,50)).into(holder.avatar);


            //设置图片
            int imageNum = 0;
            for (String url : dynamic.getImages()){
                imageNum += 1;
                ImageView imageView = new ImageView(holder.gridLayout.getContext());
                Picasso.get().load(url).transform(new MyTransForm.SquareTransForm(size)).into(imageView);

                holder.gridLayout.addView(imageView);
                if (imageNum == MAX)
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return dynamics.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView name,time,content,commentNum;
            ImageView avatar;
            GridLayout gridLayout;
            View view;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                view = itemView;
                name = itemView.findViewById(R.id.dynamic_user_name);
                time = itemView.findViewById(R.id.dynamic_commit_time);
                content = itemView.findViewById(R.id.dynamic_content_text);
                avatar = itemView.findViewById(R.id.dynamic_user_avatar);
                gridLayout = itemView.findViewById(R.id.dynamic_image_gridlayout);
                commentNum = itemView.findViewById(R.id.dynamic_comment_num);
            }
        }
    }

}
