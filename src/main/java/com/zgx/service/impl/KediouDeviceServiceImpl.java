package com.zgx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zgx.common.util.JsonStreamUtil;
import com.zgx.common.util.OkHttpUtils;
import com.zgx.common.util.TimetransUtil;
import com.zgx.model.DeviceEvent;
import com.zgx.model.Heartbeat;
import com.zgx.model.ResponseInfo;
import com.zgx.service.IKediouDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

    @Resource
    private RestTemplate restTemplate;


    @Value("${picture.url}")
    private String path;

    @Value("${operation.server}")
    private String serverPort;

    /**
     * 服务端接收设备传递心跳传递信息处理
     */
    @Override
    public void DeviceEndianHeartbeat() {
        String requestStringFromJson = JsonStreamUtil.getRequestStringFromJson(request);
        JSONObject requestJson = JSONObject.parseObject(requestStringFromJson);
        Heartbeat heartbeat = requestJson.toJavaObject(Heartbeat.class);
        heartbeat.setLocalTime(TimetransUtil.getDateStrFromISO8601Timestamp(heartbeat.getLocalTime()));
        Map<String, String> params = new HashMap<>();
        params.put("serialNum", heartbeat.getSerialNum());
        params.put("heartSendTime", heartbeat.getLocalTime());
        OkHttpUtils.doPost(serverPort + "/camera/online", params, null);
        log.info(" heartbeat is {}", heartbeat);
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setReturnCode(0);
        responseInfo.setReturnStr("正确");
        responseInfo.setPushEventInfo(true);
        responseInfo.setPushEventPic(true);
        responseInfo.setStartCommand(false);
        JsonStreamUtil.getResponsePrintWriter(responseInfo, response);
    }

    @Override
    public void DeviceEndianEvent(MultipartFile EventInfo, MultipartFile alarmPicture) {
        String EventInfoStr = JsonStreamUtil.readFiletoString(EventInfo);
//        log.info("EventInfoStr is {}", EventInfoStr);
        DeviceEvent deviceEvent = JSONObject.parseObject(EventInfoStr, DeviceEvent.class);
        deviceEvent.setLocalTime(TimetransUtil.getDateStrFromISO8601Timestamp(deviceEvent.getLocalTime()));
        log.info("DeviceEndianEvent deviceEvent is {}", deviceEvent);
        if (null != alarmPicture) {
            String originalFilename = alarmPicture.getOriginalFilename();
            String suffix = "";
            int beginIndex = originalFilename.lastIndexOf(".");
            if (beginIndex > 0) {
                suffix = originalFilename.substring(beginIndex);
            }
            String filename = UUID.randomUUID().toString() + suffix;
            File dest = new File(path, filename);
            try {
                alarmPicture.transferTo(dest);
            } catch (IOException e) {
                log.info("file IOException is {}", e);
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("serialNum", deviceEvent.getSerialNum());
        map.put("alarmType", deviceEvent.getEventType());
        map.put("alarmTime", deviceEvent.getLocalTime());
        OkHttpUtils.doPostFile(serverPort + "/camera/alarm", null, map, "alarmPicture", alarmPicture);
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setReturnCode(0);
        JsonStreamUtil.getResponsePrintWriter(responseInfo, response);

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(popHeaders(deviceEvent, file), headers);
//        restTemplate.postForObject(serverPort + "/camera/alarm", entity, Object.class);

    }

//    private MultiValuedMap<String, String> popHeaders(DeviceEvent deviceEvent, MultipartFile file) {
//        MultiValueMap<String, String> map = new LinkedMultiValueMap();
//        map.add("serialNum", deviceEvent.getSerialNum());
//        map.add("alarmType", deviceEvent.getEventType());
//        map.add("alarmTime", deviceEvent.getLocalTime());
//        if (file != null) {
//            map.add("alarmPicture", file);
//        }
//        log.info("map is {}", map);
//        return (MultiValuedMap<String, String>) map;
//    }

}
