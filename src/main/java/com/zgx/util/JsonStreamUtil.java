package com.zgx.util;

import com.alibaba.fastjson.JSONObject;
import com.zgx.model.ResponseInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class JsonStreamUtil {
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

    public static void getResponsePrintWriter(ResponseInfo responseInfo, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.write(getJson(responseInfo));
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
        }
    }
    public static String getJson(ResponseInfo responseInfo) {
        JSONObject jsonDate = new JSONObject();
        jsonDate.put("returnCode", responseInfo.getReturnCode());
        jsonDate.put("returnStr", responseInfo.getReturnStr());
        jsonDate.put("pushEventInfo", responseInfo.getPushEventInfo());
        jsonDate.put("pushEventPic", responseInfo.getPushEventPic());
        jsonDate.put("startCommand", responseInfo.getStartCommand());
        return jsonDate.toString();
    }

    public static String readFiletoString(MultipartFile file){
        InputStream in = null;
        byte b[] = new byte[1024];
        try {
            in = file.getInputStream();
            int len = 0;
            int temp = 0;
            while ((temp = in.read()) != -1) {
                b[len] = (byte) temp;
                len++;
            }
            in.close();
            String EventInfoStr = new String(b, 0, len);
            return EventInfoStr;
        } catch (IOException e) {
            return null;
        }
    }
}
