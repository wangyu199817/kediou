package com.zgx.tkmybatis;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Temporal;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.TemporalType.DATE;

/**
 * 基础信息
 *
 * @author liuzh
 * @since 2016-01-31 21:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseEntity implements Serializable {

    @Temporal(DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @SerializedName("createTime")
    private LocalDateTime create_time;

    @Temporal(DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modify_time;
}
