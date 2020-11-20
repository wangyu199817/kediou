package com.zgx.job;

import com.zgx.common.util.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.zgx.service.impl.KediouDeviceServiceImpl.isOnLineMap;
import static com.zgx.service.impl.KediouDeviceServiceImpl.isSameMap;

@Component
@EnableScheduling
@Slf4j
public class ScheduTask {

    @Value("${operation.server}")
    public  String serverPort;
    Map<String, String> params = new HashMap<>();

    //    @Scheduled(cron = " 0/30 * * * ?")
    @Scheduled(fixedRate = 5000)
    public void taskScheduled() {
        if (!isOnLineMap.isEmpty()) {
            new HashMap<>(isOnLineMap).forEach((k, v) -> {
                if (v.plusMinutes(2).isBefore(LocalDateTime.now())) {
//                if (v.plusSeconds(20).isBefore(LocalDateTime.now())) {
                    params.put("mark",k);
                    params.put("isOnline", String.valueOf(0));
                    OkHttpUtils.doPost(serverPort + "/camera/online", params, null);
                    log.info("摄像头编号："+k+" 接收不到心跳，发送下线通知！");
                    isOnLineMap.remove(k);
                }
            });
        } else {
            log.info("暂无任何离线设备！");
        }
        if(!isSameMap.isEmpty()){
            new HashMap<>(isSameMap).forEach((k,v)->{
                if(v.plusSeconds(20).isBefore(LocalDateTime.now())){
                    isSameMap.remove(k);
                }
            });
        }
    }
}
