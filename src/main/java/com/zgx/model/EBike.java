package com.zgx.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zgx.tkmybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EBike extends BaseEntity {
    //"OriginW","OriginH", 是检测的原始图像的宽高
    private Integer OriginW;
    private Integer OriginH;
    List<EBkieInfo> List;

}
