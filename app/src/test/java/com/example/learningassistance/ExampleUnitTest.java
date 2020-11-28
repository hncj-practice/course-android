package com.example.learningassistance;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExampleUnitTest {

    @Test
    public void test() throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("courseid","1");
        map.put("page","1");
        map.put("num","6");
        String url = "data/getdatabycourseid";

        String result = Jsoup.connect("http://123.56.156.212/Interface/" + url)
                .data(map)
                .ignoreContentType(true)
                .timeout(5000)
                .post()
                .body()
                .text();
//        System.out.println(result);

        JSONObject jsonObject = JSON.parseObject(result);
        JSONArray jsonArray = JSON.parseArray(jsonObject.getString("data"));
        jsonArray.forEach(System.out::println);
    }
}