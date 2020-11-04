package com.zgx.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zgx.tkmybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author: WY
 * @create: 2020/11/3
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Alarm extends BaseEntity {

    private List<RelateEvent> ChannelEventList;
    private List<RelateEvent> DiskEventList;
    private List<RelateEvent> NetworkEventList;
    private List<RelateEvent> AlarmInOutEventList;
    private List<RelateEvent> OtherEvent;


}
