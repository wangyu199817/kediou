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
    private String mark;

    //报警类型
    private String alarmType;

    private String alarmDesc;

    private String alarmTime;

    //设备是否离线 1在线 0离线
    private Integer isOnline;

}
