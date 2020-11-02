package com.zgx.controller;

import com.alibaba.fastjson.JSONObject;
import com.zgx.common.util.DateUtil;
import com.zgx.entity.Heartbeat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @author: WY
 * @create: 2020/11/2
 **/
@RestController
@Slf4j
public class DeviceEndianHeartbeatController implements IBaseController {

    @Resource
    private RestTemplate restTemplate;

//    @Value("${kediou.url}")
//    private String K_URL;

    @PutMapping(value = "/DeviceEndianHeartbeat")
    public ResponseEntity DeviceEndianHeartbeat(HttpServletRequest request, HttpServletResponse response) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        HttpEntity entity = new HttpEntity(heartbeat, headers);
//        restTemplate.put(K_URL+,heartbeat);
//        ResponseEntity<Heartbeat> exchange = restTemplate.exchange(K_URL + "/DeviceEndianHeartbeat", HttpMethod.PUT, entity, Heartbeat.class);
//        return exchange;
//        String heartTime = DateUtil.getDateStrFromISO8601Timestamp(heartbeat.getLocalTime());
//        heartbeat.setLocalTime(heartTime);
        String devType = request.getParameter("DevType");
        String devName = request.getParameter("DevName");
        String serialNum = request.getParameter("SerialNum");
        String localTime = request.getParameter("LocalTime");
        String reportCount = request.getParameter("ReportCount");
        Heartbeat heartbeat=new Heartbeat();
        heartbeat.setDevType(devType);
        heartbeat.setDevName(devName);
        heartbeat.setLocalTime(localTime);
        heartbeat.setSerialNum(serialNum);
//        heartbeat.setReportCount(reportCount);
        log.info("request is {}",request);
//        log.info("response is {}",response);
        log.info("heartbeat is {}",heartbeat);
        return ResponseEntity.ok(heartbeat);
    }

    @PostMapping(value = "/DeviceEndianEvent")
    public ResponseEntity DeviceEndianEvent(@RequestBody Heartbeat heartbeat) {
        return null;
    }


}
