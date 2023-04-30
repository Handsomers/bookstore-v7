package com.nick.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

public class JsonUtils {
    /**
     * 获取Json请求体的参数，并且返回封装了参数的JavaBean对象
     * @param request
     * @param clazz
     * @return
     */
    public static Object parseJsonToBean(HttpServletRequest request,Class clazz){
        try {
            BufferedReader reader = request.getReader();
            StringBuffer stringBuffer = new StringBuffer("");
            String line = "";
            while ((line=reader.readLine()) != null){
                stringBuffer.append(line);
            }
            //此时拿到的还只是那个Json字符串
            String jsonBody = stringBuffer.toString();
            //我们还需要从Json字符串中解析出每一个key对应的值
            //其实就是将Json字符串转换成JavaBean对象,然后Json字符串中的数据就存储到了JavaBean对象中，然后就能从JavaBean对象中获取数据
            //使用jackson
            return new ObjectMapper().readValue(jsonBody, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 将对象转成Json字符串响应给前端
     * @param response
     * @param object
     */
    public static void writeResult(HttpServletResponse response,Object object){
        try {
            //将对象转成Json字符串
            String jsonStr = new ObjectMapper().writeValueAsString(object);

            //再 将Json字符串响应给前端
            response.getWriter().write(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
