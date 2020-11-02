package com.zgx.common.util;

import com.alibaba.fastjson.JSONObject;
import com.zgx.entity.Heartbeat;
import org.apache.http.HttpResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class StreamUtil {
    public static String getRequestStringFromJson(HttpServletRequest request) {
        BufferedReader reader = null;
        try {
            StringBuffer sb = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            String inputStr;
            while ((inputStr = reader.readLine()) != null) {
                sb.append(inputStr);
            }
            return sb.toString();
        } catch (IOException e) {
            return null;

        }
    }

    public static String getResponseStringFromJson(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            return printWriter.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
