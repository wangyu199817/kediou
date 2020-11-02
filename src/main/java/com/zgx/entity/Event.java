package com.zgx.entity;

import com.zgx.tkmybatis.BaseEntity;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author: WY
 * @create: 2020/11/2
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class Event extends BaseEntity {
    private String DevType;
    private String DevName;
    private String SerialNum;
    private String Channel;
    private String LocalTime;
    private String EventType;
    private Integer OriginW;
    private Integer OriginH;

}
