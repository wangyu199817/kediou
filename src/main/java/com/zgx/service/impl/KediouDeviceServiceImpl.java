package com.zgx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zgx.model.DeviceEvent;
import com.zgx.util.*;
import com.zgx.model.Heartbeat;
import com.zgx.model.ResponseInfo;
import com.zgx.service.IKediouDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author: WY
 * @create: 2020/11/4
 * 科缔欧Ai摄像头事件控制器类
 **/
@Slf4j
@Transactional
@Service
public class KediouDeviceServiceImpl implements IKediouDeviceService {

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @Value("${picture.url}")
    private String path;

    /**
     * 服务端接收设备传递心跳传递信息处理
     */
    @Override
    public void DeviceEndianHeartbeat() {
        String requestStringFromJson = JsonStreamUtil.getRequestStringFromJson(request);
        JSONObject requestJson = JSONObject.parseObject(requestStringFromJson);
        Heartbeat heartbeat = requestJson.toJavaObject(Heartbeat.class);
        heartbeat.setLocalTime(TimetransUtil.getDateStrFromISO8601Timestamp(heartbeat.getLocalTime()));
        log.info(" heartbeat is {}", heartbeat);
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setReturnCode(0);
        responseInfo.setReturnStr("正确");
        responseInfo.setPushEventInfo(true);
        responseInfo.setPushEventPic(true);
        responseInfo.setStartCommand(false);
        JsonStreamUtil.getResponsePrintWriter(responseInfo, response);
    }

    /**
     * 服务器接收设备传递的事件消息处理
     *
     * @param EventInfo 事件json文本文件
     * @param file      图片文件
     */
    @Override
    public void DeviceEndianEvent(MultipartFile EventInfo, MultipartFile file) {
        String EventInfoStr = JsonStreamUtil.readFiletoString(EventInfo);
        DeviceEvent deviceEvent = JSONObject.parseObject(EventInfoStr, DeviceEvent.class);
        log.info("deviceEvent is {}", deviceEvent);
        if (null != file) {
            String originalFilename = file.getOriginalFilename();
            String suffix = "";
            int beginIndex = originalFilename.lastIndexOf(".");
            if (beginIndex > 0) {
                suffix = originalFilename.substring(beginIndex);
            }
            String filename = UUID.randomUUID().toString()+ suffix;
            File dest = new File(path, filename);
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                log.info("file IOException is {}", e);
            }
        }
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setReturnCode(0);
        JsonStreamUtil.getResponsePrintWriter(responseInfo, response);
    }
}
