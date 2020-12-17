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
        map.put("paperid", "1");
        String url = "problem/getproblembypaperid";

        String result = Jsoup.connect("http://123.56.156.212/Interface/" + url)
                .data(map)
                .ignoreContentType(true)
                .timeout(5000)
                .post()
                .body()
                .text();

//        String result = "{\"a\":\"b\",\"c\":\"d\"}";
        JSONObject jsonObject = JSON.parseObject(result);
        String a = jsonObject.getString("data");
//        JSONObject object = jsonObject.parseObject(a);
//        System.out.println(object.getString("sno"));
        JSONArray js = JSONArray.parseArray(a);
        for (Object j : js) {
            System.out.println(j.toString());
            JSONObject jso = jsonObject.parseObject(j.toString());
        }
    }
}