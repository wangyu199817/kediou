package com.zgx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zgx.common.config.Sha256Encode;
import com.zgx.common.util.JsonStreamUtil;
import com.zgx.common.util.OkHttpUtils;
import com.zgx.common.util.TimetransUtil;
import com.zgx.model.DeviceEvent;
import com.zgx.model.Heartbeat;
import com.zgx.model.ResponseInfo;
import com.zgx.service.IKediouDeviceService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
    public String path;

    @Value("${operation.server}")
    public String serverPort;

    @Value("${key.secretKey}")
    public String secretKey;

    //用于存放设备序列号和设备心跳发送时间，方便后续推送平台时上线和下线可以只调用一次接口
    public static Map<String, LocalDateTime> isOnLineMap = new HashMap<>();

    //用于存放设备告警事件和设备告警发送时间，用于阻止一直报警
    public static Map<String, LocalDateTime> isSameMap = new HashMap<>();


    /**
     * 服务端接收设备传递心跳传递信息处理
     */
    @Override
    public void DeviceEndianHeartbeat() {
//        String requestStringFromJson = JsonStreamUtil.getRequestStringFromJson(request);
//        JSONObject requestJson = JSONObject.parseObject(requestStringFromJson);
//        Heartbeat heartbeat = requestJson.toJavaObject(Heartbeat.class);
//        heartbeat.setLocalTime(TimetransUtil.getLocalDateStrFromISO8601Timestamp(heartbeat.getLocalTime()));
//        log.info("网关接收的heartbeat为 {}", heartbeat);
        //测试用
        Heartbeat heartbeat = new Heartbeat();

        heartbeat.setSerialNum("mark-001");
        heartbeat.setLocalTime("2020-11-20'T'16:45:00.000");

        Map<String, String> params = new HashMap<>();
        //在请求头加"authorization"值进行加密
        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", Sha256Encode.getSHA256(secretKey));
        //判断摄像头的序列号是够存在 Map  isOnLineMap<K,V>中，若存在则说明设备已经上线了，只更新V的时间，不存在则发送上线通知到平台
        if (!isOnLineMap.containsKey(heartbeat.getSerialNum())) {
            params.put("mark", heartbeat.getSerialNum());
            params.put("alarmTimes", heartbeat.getLocalTime());
            params.put("isOnline", String.valueOf(1));
            ResponseBody responseBody = OkHttpUtils.doPost(serverPort + "/camera/online", params, headers);
            isOnLineMap.put(heartbeat.getSerialNum(), LocalDateTime.now());
            log.info("ResponseBody - > {}", OkHttpUtils.resonse2String(responseBody));
            log.info("发送心跳到平台");
        }
        //接收设备心跳后根据设备心跳传来的序列号 即通过K更新V的时间， 以便后续判断设备是否离线
        isOnLineMap.put(heartbeat.getSerialNum(), LocalDateTime.now());

        //返回给摄像头响应值
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
        DeviceEvent deviceEvent = JSONObject.parseObject(EventInfoStr, DeviceEvent.class);
        deviceEvent.setLocalTime(TimetransUtil.getDateStrFromISO8601Timestamp(deviceEvent.getLocalTime()));
        //测试用
//        DeviceEvent deviceEvent=new DeviceEvent();
//            deviceEvent.setLocalTime("2020-11-20 17:59:03");
//            deviceEvent.setEventType("EBike");
//            deviceEvent.setSerialNum("mark-001");
        log.info("网关接收的DeviceEndianEvent deviceEvent is {}", deviceEvent);
        //在请求头加"authorization"值进行加密
        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", Sha256Encode.getSHA256(secretKey));
        Map<String, String> map = new HashMap<>();
        if (!isSameMap.containsKey(deviceEvent.getSerialNum())) {
            if (deviceEvent.getEventType().equals("EBike")) {
                isSameMap.put(deviceEvent.getSerialNum(), LocalDateTime.now());
                map.put("mark", deviceEvent.getSerialNum());
                map.put("alarmType", "eBike");
                map.put("alarmDesc", "警告！电动车违规进入电梯！");
                map.put("alarmTime", deviceEvent.getLocalTime());
                OkHttpUtils.doPostFile(serverPort + "/camera/alarm", headers, map, "alarmPicture", alarmPicture);
                log.info("发送告警到平台");
            }
        }
        isSameMap.put(deviceEvent.getSerialNum(), LocalDateTime.now());
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setReturnCode(0);
        JsonStreamUtil.getResponsePrintWriter(responseInfo, response);
    }
}
