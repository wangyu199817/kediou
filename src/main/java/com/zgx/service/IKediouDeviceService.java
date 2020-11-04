package com.zgx.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: WY
 * @create: 2020/11/4
 **/
public interface IKediouDeviceService {
    /**
     * 服务端接收设备传递心跳传递信息处理
     */
    void DeviceEndianHeartbeat();

    /**
     * 服务器接收设备传递的事件消息处理
     *
     * @param EventInfo 事件json文本文件
     * @param file      图片文件
     */
    void DeviceEndianEvent(MultipartFile EventInfo, MultipartFile file);


}
