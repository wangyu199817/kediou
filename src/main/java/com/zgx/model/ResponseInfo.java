package com.zgx.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zgx.tkmybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseInfo extends BaseEntity {

    //[必须]0表示正确.负数代表错误且不能继续,正数表示有异常但可继续工作.
    private Integer ReturnCode;
    //[可选]返回错误信息.
    private String ReturnStr;
    //[可选]是否推送事件信息.默认为true.该配置项不会保存到配置文件.当配置为false时不会推送事件信息及图片等数据
    private Boolean PushEventInfo;
    //[可选]是否推送事件图片.默认为true.该配置项不会保存到配置文件.当配置为false时不会推送图片等数据.
    private Boolean PushEventPic;
    //[可选]是否需要进行配置.默认为false. 如果为true, 设备端会像配置命令地址请求配置命令。
    private Boolean StartCommand;
}
