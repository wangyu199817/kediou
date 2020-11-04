package com.zgx.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zgx.service.IKediouDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author: WY
 * @create: 2020/11/2
 **/
@RestController
@Slf4j
public class KediouDeviceController implements IBaseController {

    @Resource
    private IKediouDeviceService kediouDeviceService;

    @Resource
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 服务端接收设备传递心跳传递信息处理
     */
    @PutMapping(value = "/DeviceEndianHeartbeat")
    public void getDeviceEndianHeartbeat() {
        try {
            kediouDeviceService.DeviceEndianHeartbeat();
        } catch (Exception e) {
            log.error("getDeviceEndianHeartbeat error is {}", e);
        }
    }

    /**
     * 服务器接收设备传递的事件消息处理
     *
     * @param EventInfo 事件json文本文件
     * @param file      图片文件
     */
    @PostMapping(value = "/DeviceEndianEvent")
    public void getDeviceEndianEvent(MultipartFile EventInfo, MultipartFile file) {
        try {
            kediouDeviceService.DeviceEndianEvent(EventInfo, file);
        } catch (Exception e) {
            log.error("getDeviceEndianEvent error is {}", e);
        }
    }


}
