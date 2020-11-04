package com.zgx.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: WY
 * @create: 2020/11/2
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Heartbeat  implements Serializable {
    //[必须]设备类型,如"IPC","VMS","NVR","IVR".
    private String DevType;
    //[可选]管理域中可唯一标识.
    private String DevName;
    //[必须]设备序列号.
    private  String SerialNum;
    //[必须]设备端的系统时间.时间以字符串表示,其格式遵循ISO8601规范. 举例:19961220T003957Z,19961220T083957+08.
    private String LocalTime;
    //[可选]截至当前已经报告的总次数.
    private Integer ReportCount;
}
