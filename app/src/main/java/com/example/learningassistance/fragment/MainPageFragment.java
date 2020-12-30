package com.example.learningassistance.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.learningassistance.R;
import com.example.learningassistance.adapter.CourseListAdapter;
import com.example.learningassistance.adapter.MineAdapter;
import com.example.learningassistance.course.CourseActivity;
import com.example.learningassistance.course.Dynamic;
import com.example.learningassistance.entity.CourseList;
import com.example.learningassistance.entity.Option;
import com.example.learningassistance.utils.MyTransForm;
import com.example.learningassistance.utils.RoundRectImageView;
import com.example.learningassistance.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class MainPageFragment extends Fragment {

    private String data;
    private int id;
    private int size;

    public String localPath;
    private List<CourseList> courseLists = new ArrayList<>();

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
        this.size = (size - 60) / 3 - 20;
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

    /**
     * 加载主页内容
     */
    public void loadHomepageFragment(View view){
        List<String> recentCourse = Utils.getRecentCourse();
        showRecent(view,recentCourse);
        /**
         * 这里放置几个最新发布的问答，由于尚未连接数据库，先用自制的问答进行测试
         */
        String content = "这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;";
        List<String> imgs = new ArrayList<>();
        List<Dynamic> dynamicList = new ArrayList<>();
        Dynamic dynamic = new Dynamic("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg","JEmber","This is time",content,imgs);
        dynamicList.add(dynamic);
        imgs = new ArrayList<>();
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5oYmF7IF2.ZWvEfVStWTGjTri7aey9eXBLvnIUPQfRSzXvGpnLzeio0gmSb*Jl7.8xqaU*7NDWvf4PQM3VCyd9E!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5lEsmmjto3JW4djo4LYT3rMK.5xsZoO2GObxU2HNUN7QOeqeI4JwBuV4VcqrCiq7dUuCy31oMwS2YufZchTWOcA!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5lEsmmjto3JW4djo4LYT3rNoiPsTsoZPE0a0CvVGEFbWU1fUb8mMh8iW2OAz9EMrIm*kaTYfHmQ0Qb2CNoYBIDs!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        dynamic = new Dynamic("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg","JEmber","This is time",content,imgs);
        dynamicList.add(dynamic);

        Utils.setRecycler(view,R.id.recycler_home,new DynamicAdapter(dynamicList));

        SwipeRefreshLayout refresh = view.findViewById(R.id.home_refresh);
        refresh.setOnRefreshListener(() -> {
            List<String> recentCourse1 = Utils.getRecentCourse();
            showRecent(view,recentCourse1);
            refresh.setRefreshing(false);
        });
    }

    /**
     * 加载课程碎片
     */
    public void loadMessageFragment(View view){
        Handler handlerS = new Handler(msg -> {
            Map<Integer, String> result = (HashMap) msg.getData().getSerializable("data");
            for(int j = 1; j < 9; j++ ){
                assert result != null;
                if (Objects.equals(result.get(j), "{ \"code\" : 401, \"message\" : \"查询结果为空\" }")){
                    continue;
                }
                JSONObject json = JSON.parseObject(result.get(j));
                String data = json.getString("data");
                JSONArray array = JSON.parseArray(data);
                for (int i = 0; i < array.size(); i++){
                    JSONObject jsonObject = array.getJSONObject(i);
                    CourseList courseList = new CourseList(jsonObject.getString("coverimg"),jsonObject.getString("cname"),jsonObject.getString("tname"));
                    courseList.setCid( jsonObject.getString("cid") );
                    courseLists.add(courseList);
                }
            }
            if (courseLists.size() != 0) {
                Utils.setRecycler(view, R.id.recycler_course, new CourseListAdapter(courseLists));
            } else {
                Toast.makeText(view.getContext(), "未查询到相关信息", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        Handler handlerT = new Handler(msg -> {
            if (msg.what == 1){
                JSONArray array = JSON.parseArray(msg.getData().getString("data"));
                for (int i = 0; i < array.size(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    CourseList courseList = new CourseList(jsonObject.getString("coverimg"),jsonObject.getString("cname"),jsonObject.getString("tname"));
                    courseList.setCid( jsonObject.getString("cid") );
                    courseLists.add(courseList);
                }
                Utils.setRecycler(view,R.id.recycler_course,new CourseListAdapter(courseLists));
            } else {
                Toast.makeText(view.getContext(), "未查询到相关信息", Toast.LENGTH_SHORT).show();
            }
            return false;
        });

        JSONObject jsonObject = JSON.parseObject(data);

        SharedPreferences preferences = view.getContext().getSharedPreferences("loginHistory",MODE_PRIVATE);
        Map<String, String> map = new HashMap<>();
        if (preferences.getString("currentUserType","0").equals("1")){
            map.put("studentid", jsonObject.getString("sno"));
            Utils.getCourseData(map,handlerS);
        } else {
            map.put("condition", jsonObject.getString("tno"));
            map.put("page","1");
            map.put("num","6");
            map.put("type","1");
            Utils.getNetData("course/getcoursebytnoorcoursename",map,handlerT);
        }
    }

    public void loadDynamicFragment(View view){
        FloatingActionButton fab = view.findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent("com.action.DYNAMIC_CREATE");
            v.getContext().startActivity(intent);
        });

        String content = "这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;这是一个动态的正文;";
        List<String> imgs = new ArrayList<>();
        List<Dynamic> dynamicList = new ArrayList<>();
        Dynamic dynamic = new Dynamic("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg","JEmber","This is time",content,imgs);
        dynamicList.add(dynamic);

        imgs = new ArrayList<>();
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5oYmF7IF2.ZWvEfVStWTGjTri7aey9eXBLvnIUPQfRSzXvGpnLzeio0gmSb*Jl7.8xqaU*7NDWvf4PQM3VCyd9E!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5lEsmmjto3JW4djo4LYT3rMK.5xsZoO2GObxU2HNUN7QOeqeI4JwBuV4VcqrCiq7dUuCy31oMwS2YufZchTWOcA!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5lEsmmjto3JW4djo4LYT3rNoiPsTsoZPE0a0CvVGEFbWU1fUb8mMh8iW2OAz9EMrIm*kaTYfHmQ0Qb2CNoYBIDs!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        dynamic = new Dynamic("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg","JEmber","This is time",content,imgs);
        dynamicList.add(dynamic);

        imgs = new ArrayList<>();
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5oYmF7IF2.ZWvEfVStWTGjTri7aey9eXBLvnIUPQfRSzXvGpnLzeio0gmSb*Jl7.8xqaU*7NDWvf4PQM3VCyd9E!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5lEsmmjto3JW4djo4LYT3rMK.5xsZoO2GObxU2HNUN7QOeqeI4JwBuV4VcqrCiq7dUuCy31oMwS2YufZchTWOcA!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5lEsmmjto3JW4djo4LYT3rNoiPsTsoZPE0a0CvVGEFbWU1fUb8mMh8iW2OAz9EMrIm*kaTYfHmQ0Qb2CNoYBIDs!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5oYmF7IF2.ZWvEfVStWTGjTri7aey9eXBLvnIUPQfRSzXvGpnLzeio0gmSb*Jl7.8xqaU*7NDWvf4PQM3VCyd9E!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5lEsmmjto3JW4djo4LYT3rMK.5xsZoO2GObxU2HNUN7QOeqeI4JwBuV4VcqrCiq7dUuCy31oMwS2YufZchTWOcA!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5lEsmmjto3JW4djo4LYT3rNoiPsTsoZPE0a0CvVGEFbWU1fUb8mMh8iW2OAz9EMrIm*kaTYfHmQ0Qb2CNoYBIDs!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5oYmF7IF2.ZWvEfVStWTGjTri7aey9eXBLvnIUPQfRSzXvGpnLzeio0gmSb*Jl7.8xqaU*7NDWvf4PQM3VCyd9E!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5lEsmmjto3JW4djo4LYT3rMK.5xsZoO2GObxU2HNUN7QOeqeI4JwBuV4VcqrCiq7dUuCy31oMwS2YufZchTWOcA!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5lEsmmjto3JW4djo4LYT3rNoiPsTsoZPE0a0CvVGEFbWU1fUb8mMh8iW2OAz9EMrIm*kaTYfHmQ0Qb2CNoYBIDs!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5oYmF7IF2.ZWvEfVStWTGjTri7aey9eXBLvnIUPQfRSzXvGpnLzeio0gmSb*Jl7.8xqaU*7NDWvf4PQM3VCyd9E!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5lEsmmjto3JW4djo4LYT3rMK.5xsZoO2GObxU2HNUN7QOeqeI4JwBuV4VcqrCiq7dUuCy31oMwS2YufZchTWOcA!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        imgs.add("http://a1.qpic.cn/psc?/V1250wVF0hEr1t/ruAMsa53pVQWN7FLK88i5lEsmmjto3JW4djo4LYT3rNoiPsTsoZPE0a0CvVGEFbWU1fUb8mMh8iW2OAz9EMrIm*kaTYfHmQ0Qb2CNoYBIDs!/m&ek=1&kp=1&pt=0&bo=VQhABlUIQAYRECc!&tl=3&vuin=2116161338&tm=1607180400&sce=60-4-3&rf=0-0");
        dynamic = new Dynamic("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg","JEmber","This is time",content,imgs);
        dynamicList.add(dynamic);

        imgs = new ArrayList<>();
        imgs.add("http://wx4.sinaimg.cn/mw690/6a04b428gy1fyrldan1nzg20900a3myd.gif");
        imgs.add("http://wx4.sinaimg.cn/mw690/6a04b428gy1fyrldan1nzg20900a3myd.gif");
        imgs.add("http://wx4.sinaimg.cn/mw690/6a04b428gy1fyrldan1nzg20900a3myd.gif");
        imgs.add("http://wx4.sinaimg.cn/mw690/6a04b428gy1fyrldan1nzg20900a3myd.gif");
        imgs.add("http://wx4.sinaimg.cn/mw690/6a04b428gy1fyrldan1nzg20900a3myd.gif");
        imgs.add("http://wx4.sinaimg.cn/mw690/6a04b428gy1fyrldan1nzg20900a3myd.gif");
        imgs.add("http://wx4.sinaimg.cn/mw690/6a04b428gy1fyrldan1nzg20900a3myd.gif");
        imgs.add("http://wx4.sinaimg.cn/mw690/6a04b428gy1fyrldan1nzg20900a3myd.gif");
        imgs.add("http://wx4.sinaimg.cn/mw690/6a04b428gy1fyrldan1nzg20900a3myd.gif");
        dynamic = new Dynamic("https://tva1.sinaimg.cn/large/466f79e8ly1fw5oh9146uj21gp15qdta.jpg","JEmber","This is time",content,imgs);
        dynamicList.add(dynamic);

        Utils.setRecycler(view,R.id.recycler_dynamic,new DynamicAdapter(dynamicList));

    }

    /**
     * 加载Fragment"我的".
     * @param view "我的"在Activity中的fragment组件
     */
    public void loadMineFragment(View view){
        JSONObject jsonObject = JSON.parseObject(data);
        localPath = jsonObject.getString("localPath");
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

        SwipeRefreshLayout refreshLayout = view.findViewById(R.id.mine_refresh);
        refreshLayout.setOnRefreshListener(() -> {
            RoundRectImageView.setUserAvatar(imageView,localPath,40);
            refreshLayout.setRefreshing(false);
        });
    }

    private void showRecent(View view, List<String> recentCourse){
        LinearLayout l1 = view.findViewById(R.id.home_course_layout1);
        LinearLayout l2 = view.findViewById(R.id.home_course_layout2);
        LinearLayout l3 = view.findViewById(R.id.home_course_layout3);
        LinearLayout l4 = view.findViewById(R.id.home_course_layout4);

        if (recentCourse.get(0).equals("null")){
            l1.setVisibility(View.GONE);
            TextView text = view.findViewById(R.id.home_course_none);
            text.setVisibility(View.VISIBLE);
        } else {
            l1.setVisibility(View.VISIBLE);
            JSONObject j1 = JSON.parseObject(recentCourse.get(0));
            ImageView i1 = view.findViewById(R.id.home_course_avatar1);
            Picasso.get().load(j1.getString("url")).transform(new MyTransForm.RangleTransForm()).into(i1);
            TextView n1 = view.findViewById(R.id.home_course_name1);
            n1.setText(j1.getString("name"));
            l1.setOnClickListener(v -> {
                Intent intent = new Intent("com.action.COURSE_DETAIL_ACTIVITY_START");
                intent.putExtra("courseName",j1.getString("name"));
                intent.putExtra("cid",j1.getString("id"));
                v.getContext().startActivity(intent);
            });
        }

        if (recentCourse.get(1).equals("null")){
            l2.setVisibility(View.GONE);
        } else {
            l2.setVisibility(View.VISIBLE);
            JSONObject j2 = JSON.parseObject(recentCourse.get(1));
            ImageView i2 = view.findViewById(R.id.home_course_avatar2);
            Picasso.get().load(j2.getString("url")).transform(new MyTransForm.RangleTransForm()).into(i2);
            TextView n2 = view.findViewById(R.id.home_course_name2);
            n2.setText(j2.getString("name"));
            l2.setOnClickListener(v -> {
                Intent intent = new Intent("com.action.COURSE_DETAIL_ACTIVITY_START");
                intent.putExtra("courseName",j2.getString("name"));
                intent.putExtra("cid",j2.getString("id"));
                v.getContext().startActivity(intent);
            });
        }

        if (recentCourse.get(2).equals("null")){
            l3.setVisibility(View.GONE);
        } else {
            l3.setVisibility(View.VISIBLE);
            JSONObject j3 = JSON.parseObject(recentCourse.get(2));
            ImageView i3 = view.findViewById(R.id.home_course_avatar3);
            Picasso.get().load(j3.getString("url")).transform(new MyTransForm.RangleTransForm()).into(i3);
            TextView n3 = view.findViewById(R.id.home_course_name3);
            n3.setText(j3.getString("name"));
            l3.setOnClickListener(v -> {
                Intent intent = new Intent("com.action.COURSE_DETAIL_ACTIVITY_START");
                intent.putExtra("courseName",j3.getString("name"));
                intent.putExtra("cid",j3.getString("id"));
                v.getContext().startActivity(intent);
            });
        }

        if (recentCourse.get(3).equals("null")){
            l4.setVisibility(View.GONE);
        } else {
            l4.setVisibility(View.VISIBLE);
            JSONObject j4 = JSON.parseObject(recentCourse.get(3));
            ImageView i4 = view.findViewById(R.id.home_course_avatar4);
            Picasso.get().load(j4.getString("url")).transform(new MyTransForm.RangleTransForm()).into(i4);
            TextView n4 = view.findViewById(R.id.home_course_name4);
            n4.setText(j4.getString("name"));
            l4.setOnClickListener(v -> {
                Intent intent = new Intent("com.action.COURSE_DETAIL_ACTIVITY_START");
                intent.putExtra("courseName",j4.getString("name"));
                intent.putExtra("cid",j4.getString("id"));
                v.getContext().startActivity(intent);
            });
        }
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
        options.add(new Option("退出登录",R.drawable.icon_unload));
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
            Picasso.get().load(dynamic.getAvatar()).transform(new MyTransForm.RangleTransForm()).into(holder.avatar);

            if (dynamic.getImages().size() < 1){
                holder.gridLayout.setVisibility(View.GONE);
            } else {
                //设置图片
                int imageNum = 1;
                for (String url : dynamic.getImages()){

                    ImageView imageView = new ImageView(holder.gridLayout.getContext());
                    Picasso.get().load(url).transform(new MyTransForm.SquareTransForm(size)).into(imageView);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
                    GridLayout.LayoutParams gl = new GridLayout.LayoutParams(layoutParams);
                    gl.rightMargin = 10;
                    gl.leftMargin = 10;
                    gl.bottomMargin = 10;
                    imageView.setLayoutParams(gl);

                    if (imageNum == MAX && dynamic.getImages().size() > 9) {
                        TextView textView = new TextView(holder.view.getContext());
                        textView.setLayoutParams(gl);
                        textView.setBackgroundColor(getResources().getColor(R.color.gray));
                        textView.setText("+" + (dynamic.getImages().size() - 8));
                        textView.setTextSize(size / 6);
                        textView.setGravity(Gravity.CENTER);
                        textView.setTextColor(Color.WHITE);
                        holder.gridLayout.addView(textView);
                        break;
                    }

                    holder.gridLayout.addView(imageView);
                    imageNum += 1;
                }
            }

            holder.view.setOnClickListener(v -> {
                Intent intent = new Intent("com.action.DYNAMIC_DETAIL");
                Bundle bundle = new Bundle();
                bundle.putSerializable("dynamic",dynamic);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return dynamics.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView name,time,content;
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
            }
        }
    }

}
