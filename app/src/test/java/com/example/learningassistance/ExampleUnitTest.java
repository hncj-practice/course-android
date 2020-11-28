package com.example.learningassistance;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <icon_user_avater href="http://d.android.com/tools/testing">Testing documentation</icon_user_avater>
 */
public class ExampleUnitTest {

    @Test
    public void test() throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("courseid","1");
        String url = "topic/gettopicbycid";

        String result = Jsoup.connect("http://123.56.156.212/Interface/" + url)
                .data(data)
                .ignoreContentType(true)
                .timeout(5000)
                .post()
                .body()
                .text();
//        System.out.println(result);

        JSONObject jsonObject = JSON.parseObject(result);
        JSONArray jsonArray = JSON.parseArray(jsonObject.getString("data"));
        jsonArray.forEach(name -> System.out.println(name));
    }

    @Test
    public void tset(){
        Map<String, String> data = new HashMap<>();
        data.put("studentid","081417162");

        Map<Integer, String> result = new HashMap<>();
        try {
            Integer i = 0;
            while (i++ < 8) {
                data.put("semesterid",i.toString());
                String response = Jsoup.connect("http://123.56.156.212/Interface/course/getcoursebysno")
                        .data(data)
                        .ignoreContentType(true)
                        .timeout(5000)
                        .post()
                        .body()
                        .text();
                if (response.isEmpty()){
                    System.out.println("第"+i+"学期没有课");
                    continue;
                }
                result.put(i, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("++++++++++++");
    }

}