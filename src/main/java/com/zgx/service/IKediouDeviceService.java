package com.zgx.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: WY
 * @create: 2020/11/4
 **/
public interface IKediouDeviceService {
    void DeviceEndianHeartbeat();

    void DeviceEndianEvent(MultipartFile EventInfo, MultipartFile file);

}
