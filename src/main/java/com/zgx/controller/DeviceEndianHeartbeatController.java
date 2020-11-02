package com.zgx.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zgx.common.util.DateUtil;
import com.zgx.common.util.StreamUtil;
import com.zgx.entity.DeviceEvent;
import com.zgx.entity.EBikeInfo;
import com.zgx.entity.Heartbeat;
import com.zgx.entity.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author: WY
 * @create: 2020/11/2
 **/
@RestController
@Slf4j
public class DeviceEndianHeartbeatController implements IBaseController {


    @PutMapping(value = "/DeviceEndianHeartbeat")
    public void DeviceEndianHeartbeat(HttpServletRequest request, HttpServletResponse response) {
        String requestStringFromJson = StreamUtil.getRequestStringFromJson(request);
        JSONObject requestJson = JSONObject.parseObject(requestStringFromJson);
        DeviceEvent heartbeat = requestJson.toJavaObject(DeviceEvent.class);
//        heartbeat.setLocalTime(DateUtil.getDateStrFromISO8601Timestamp(heartbeat.getLocalTime()));
        log.info("heartbeat is {}", heartbeat);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            ResponseInfo responseInfo = new ResponseInfo();
            responseInfo.setReturnCode(1);
            responseInfo.setReturnStr("错误");
            responseInfo.setPushEventInfo(true);
            responseInfo.setPushEventPic(true);
            responseInfo.setStartCommand(false);
            printWriter.write(getJson(responseInfo));
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            printWriter.close();
        }
//        ResponseInfo responseInfo=new ResponseInfo();
//        responseInfo.setReturnCode(1);
//        responseInfo.setReturnStr("错误");
//        responseInfo.setPushEventInfo(true);
//        responseInfo.setPushEventPic(true);
//        responseInfo.setStartCommand(false);
//        JSONObject jsonObject =new JSONObject();
//        jsonObject.put("",responseInfo);
//        return ResponseEntity.ok(heartbeat);

    }

    @PostMapping(value = "/DeviceEndianEvent")
    public ResponseEntity DeviceEndianEvent(HttpServletRequest request) {
        String requestStringFromJson = StreamUtil.getRequestStringFromJson(request);
        JSONObject requestJson = JSONObject.parseObject(requestStringFromJson);
//        JSONObject list = requestJson.getJSONObject("List");
        JSONArray virJson = requestJson.getJSONArray("List");
        List<EBikeInfo> eBikeInfoList = virJson.toJavaList(EBikeInfo.class);
        DeviceEvent event = requestJson.toJavaObject(DeviceEvent.class);
        event.setEBikeInfo(eBikeInfoList);
        log.info("event is {}", event);
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setReturnCode(0);
        responseInfo.setReturnStr("错误");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("", responseInfo);
        return ResponseEntity.ok(responseInfo);
    }

    @ResponseBody
    public String getJson(ResponseInfo responseInfo) {
        JSONObject jsonDate = new JSONObject();
        jsonDate.put("responseInfo",responseInfo);
        return jsonDate.toString();
    }

}
