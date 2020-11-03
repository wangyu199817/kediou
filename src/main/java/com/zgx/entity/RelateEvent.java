package com.zgx.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zgx.tkmybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: WY
 * @create: 2020/11/3
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelateEvent extends BaseEntity {
    private Integer DeviceNo;
    private Integer ChannelNo;
    private Integer StreamNo;
    private Integer RegionNo;
    private Integer MajorType;
    private Integer MinorType;
    private Integer State;
    private String StartTime;
     private String StopTIme;
}
