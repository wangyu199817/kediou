package com.zgx.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zgx.tkmybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author: WY
 * @create: 2020/11/3
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EBkieInfo extends BaseEntity {
    //检测区域号 支持4个区域 范围 0~3
    private Integer RegionId;
    //编号
    private Integer Id;
    private Map<String, Integer> Rgn;
    //目标类型,"Vehicle", "Person", "Others".
    private String ObjType;
    //子类型, Bicycle-自行车, Motorbike-电动车
    private String SubObjType;
}
