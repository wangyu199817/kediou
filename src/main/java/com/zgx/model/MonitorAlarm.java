package com.zgx.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: WY
 * @create: 2020/11/4
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MonitorAlarm {
    //摄像头编码
    private String serialNum;

    //心跳发送时间
    private String heartSendTime;

    //报警类型
    private  String alarmType;

    //报警时间
    private String alarmTime;
}
