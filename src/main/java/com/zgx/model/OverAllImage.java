package com.zgx.model;

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
public class OverAllImage extends BaseEntity {
    private  Integer w;
    private Integer h;
    private  String ImgName;
}
