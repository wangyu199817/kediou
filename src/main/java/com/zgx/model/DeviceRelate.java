package com.zgx.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zgx.tkmybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import java.time.LocalDateTime;

import static javax.persistence.TemporalType.DATE;

/**
 * @author: WY
 * @create: 2020/11/4
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceRelate extends BaseEntity {
    //摄像头编码
    private String serialNum;

    //心跳发送时间
    @Temporal(DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime heartSendTime;
}
