package com.zgx.controller;

import com.zgx.common.util.OkHttpUtils;
import com.zgx.model.MonitorAlarm;
import com.zgx.service.IKediouDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: WY
 * @create: 2020/11/2
 **/
@RestController
@Slf4j
public class KediouDeviceController implements IBaseController {


    @Resource
    private IKediouDeviceService kediouDeviceService;

    @Value("${operation.server}")
    public String serverPort;

    @Value("${picture.url}")
    private String path;

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

    @PostMapping("/camera/alarm")
    public void getAlarm(MultipartFile alarmPicture, MonitorAlarm monitorAlarm) {
        log.info("monitorAlarm is {}", monitorAlarm);
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
    }

    @PostMapping("/camera/online")
    public void getHeartBeat(MonitorAlarm monitorAlarm) {
        log.info("getMark is {}", monitorAlarm.getMark());
        log.info("getAlarmTime is {}", monitorAlarm.getAlarmTime());
        log.info("设备是否上线 0-下线，1上线：" + monitorAlarm.getIsOnline());
    }

    //c测试用
    @PostMapping("/camera/alarms")
    public void sendAlarmCamera(MonitorAlarm monitorAlarm, MultipartFile alarmPicture) {
        Map<String, String> map = new HashMap<>();
        if (monitorAlarm.getAlarmType().equals("EBike")) {
            map.put("mark", monitorAlarm.getMark());
            map.put("alarmType", "eBike");
            map.put("alarmDesc", "警告！电动车违规进入电梯！");
            map.put("alarmTime", monitorAlarm.getAlarmTime());
            OkHttpUtils.doPostFile(serverPort + "/camera/alarm", null, map, "alarmPicture", alarmPicture);
        }
    }

}
