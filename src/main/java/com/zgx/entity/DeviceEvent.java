package com.zgx.entity;

import com.zgx.tkmybatis.BaseEntity;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;


/**
 * @author: WY
 * @create: 2020/11/2
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class DeviceEvent extends BaseEntity {
    //[必须]设备类型,如"IPC","VMS","NVR","IVR".
    private String DevType;
    //[可选]管理域中可唯一标识.
    private String DevName;
    //[必须]设备序列号.
    private String SerialNum;
    //[必须]设备端的系统时间.时间以字符串表示,其格式遵循ISO8601规范. 举例:19961220T003957Z,19961220T083957+08.
    private String LocalTime;
    //[可选]截至当前已经报告的总次数.心跳返回次数报告
    private Integer ReportCount;
    //[必须]通道号(序号从1开始).如果缺失表示通道1.
    private String Channel;
    //[必须]事件类型,如"FaceCapture","FaceCompare","Person",”Alarm”.EventType决定下文的字段和内容.
    //"FaceCapture"表示下文包含"FaceCaptureInfo"字段.
    //"FaceCompare"表示下文包含"FaceCompareInfo"字段.
    //"Person"表示下文包含"PersonInfo"字段.
    //“EBike”表示下文包含”EBikeInfo”字段.
    private String EventType;
    //"OriginW","OriginH", 是检测的原始图像的宽高
    private Integer OriginW;
    private Integer OriginH;

    private List<EBikeInfo> eBikeInfo;


}
