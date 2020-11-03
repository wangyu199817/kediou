package com.zgx.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zgx.common.util.DateUtil;
import com.zgx.common.util.StreamUtil;
import com.zgx.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author: WY
 * @create: 2020/11/2
 **/
@RestController
@Slf4j
public class DeviceEndianHeartbeatController implements IBaseController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @Resource
    private ServletContext servletContext;

    @Resource
    HttpSession session;

    @PutMapping(value = "/DeviceEndianHeartbeat")
    public void DeviceEndianHeartbeat(HttpServletRequest request_, HttpServletResponse response_) {
        String requestStringFromJson = StreamUtil.getRequestStringFromJson(request);
        log.info("1111requestStringFromJson is {}", requestStringFromJson);
        JSONObject requestJson = JSONObject.parseObject(requestStringFromJson);
        Heartbeat heartbeat = requestJson.toJavaObject(Heartbeat.class);
        heartbeat.setLocalTime(DateUtil.getDateStrFromISO8601Timestamp(heartbeat.getLocalTime()));
        log.info(" Heartbeat heartbeat is {}", heartbeat);
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setReturnCode(0);
        responseInfo.setReturnStr("正确");
        responseInfo.setPushEventInfo(true);
        responseInfo.setPushEventPic(true);
        responseInfo.setStartCommand(false);
        StreamUtil.getResponsePrintWriter(responseInfo, response);
    }

    @PostMapping(value = "/DeviceEndianEvent")
    public void DeviceEndianEvent(MultipartFile EventInfo, MultipartFile file) {
        String EventInfoStr = StreamUtil.readFiletoString(EventInfo);
        DeviceEvent deviceEvent = JSONObject.parseObject(EventInfoStr, DeviceEvent.class);
        log.info("EventInfoStr is {}", deviceEvent);
//                log.info("EventInfoStr is {}",EventInfoStr);
        log.info("----------------------------------------------------");
        if (null!=file) {
            String originalFilename = file.getOriginalFilename();
            String suffix = "";
            int beginIndex = originalFilename.lastIndexOf(".");
            if (beginIndex > 0) {
                suffix = originalFilename.substring(beginIndex);
            }
            String path="d:\\test";
            String filename = UUID.randomUUID().toString() + suffix;
            File dest = new File(path, filename);
            try {
                file.transferTo(dest);
            } catch (IOException e) {
               log.info("error is {}",e);
            }
        }
//            JSONObject requestJson = null;
//            if (requestJson != null) {
//                DeviceEvent event = requestJson.toJavaObject(DeviceEvent.class);
//                if (null != event.getEventType() && event.getEventType().equals("EBike")) {
//                    JSONObject eBikeJson = requestJson.getJSONObject("EBikeInfo");
//                    JSONObject imageJson = requestJson.getJSONObject("OverallImageInfo");
//                    JSONArray virJson = eBikeJson.getJSONArray("List");
//                    List<EBkieInfo> eBkieInfoList = virJson.toJavaList(EBkieInfo.class);
//                    EBike eBike = eBikeJson.toJavaObject(EBike.class);
//                    OverAllImage overAllImage = imageJson.toJavaObject(OverAllImage.class);
//                    eBike.setList(eBkieInfoList);
//                    event.setEBikeInfo(eBike);
//                    event.setOverallImageInfo(overAllImage);
//                    log.info("event is {}", event);
//                }
//                ResponseInfo responseInfo = new ResponseInfo();
//                responseInfo.setReturnCode(0);
//                StreamUtil.getResponsePrintWriter(responseInfo, response);
//            }

    }

}
