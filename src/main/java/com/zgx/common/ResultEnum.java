package com.zgx.common;

/**
 * 返回值和返回信息
 */
public enum ResultEnum {

    SUCESS_CODE("10000", "请求成功"),
    ERROR_CODE("-10000", "系统异常"),
    E_LOGINERROR("20001", "用户名或密码错误"),
    E_SELECTRROR("20023", "查询失败"),
    E_NOTFOUND("30001", "暂无数据"),
    E_DEVICE_CACHE("20004", "设备缓存增加失败"),
    E_REQUESTERROR("20004", "修改失败"),
    E_YWYDATAERROR("20019", "添加失败"),
    E_EDITERROR("20024", "删除失败"),
    E_IMGNOTFOUND("20002", "图片不能为空"),
    E_IMGFORMATERROR("20003", "图片格式错误"),
    E_BADREQUEST("20400", "请求处理异常，请稍后再试"),
    E_DUPLICATESITENAME("20005", "站点名称重复"),
    E_DEVICE_ALREDAY_EXIST("20006", "设备名称或编码不能与已注册设备相同"),
    E_DEVICE_REGIDIT_FAILED("20016", "设备到运营商平台注册失败"),
    E_DEVICE_ISDELETE("20007", "设备已被删除"),
    E_DEVICE_ISNOTREG("20010", "设备没有被注册"),
    E_DEVICE_ISBINDING("20008", "设备已被绑定"),
    E_DEVICE_ISNOTBINDING("20011", "设备未被绑定"),
    E_DEVICE_ISNOTEXIST("20009", "设备不存在"),
    E_INTERNAL_SERVER_ERROR("20012", "短信请求失败"),
    E_NOT_PHONE("20018", "没有这个用户"),
    E_MESSAGE_SERVER_LIMIT("20014", "您今日短信发送量已达上限"),
    E_PASSWORD("20013", "密码错误"),
    E_HAS_DEVICE_BIND("20015", "设备类型删除失败，请先删除关联设备"),
    E_DEVICE_REGISTERED_FAIL("90001", "运营商平台注册失败"),
    E_USEREXIST("40024", "您关联用户已经存在，请添加其他用户"),
    E_NOTDATA("4001", "暂无配置指令"),
    E_OUTOFNUMBER("4007", "设备数量过多,请重新选择"),
    E_NAMEEXIST("40078", "平台名称或编码已存在，不能重复！"),

    E_DELETE_FAIL_USE("60000", "存在告警规则关联了该告警等级"),
    E_ALARM_DISPATCH_OVER("60001", "该告警已派发派发，等待处理"),
    E_ALARM_PICK_OVER("60002", "该告警已被领取，等待处理"),
    E_ALARM_PROCESS_FAIL("60003", "当前告警信息处理失败"),
    E_ALARM_GROUPNOPEOPLE_FAIL("60004", "改分组下没有处理人员，请添加人员或重新选择分组"),
    E_ALARM_HAVE_NO_AUTHORITY("60005", "抱歉，您没有处理权限！"),
    E_ALARM_HAVE_NO_HANDLEEMP("60006", "该告警区域未配置相应的处理单位，请先配置相应的处理单位及人员！"),
    E_TOP_DEPT("60007", "您已是顶级部门，不能上报，请处理！"),
    E_HAVE_NO_ALARM_GROUP("60008", "该部门未配置告警组，暂不能上报！"),
    E_HAVE_NO_DEPT("60009", "抱歉，您尚未绑定部门信息，请先绑定部门信息！"),

    //新闻相关
    E_NEWS_HAVE_NEWSNAME("61000", "新闻标题已存在！"),

    E_EXAM_BEGING("62000", "考试中或考试已结束不能删除！"),
    E_EXAM_NO_HANDEL("62001", "已过考试时间，不支持任何操作！"),
    E_EXAM_STOP_HANDEL("62002", "考试状态必须为停用状态才能修改！"),
    E_EXAM_NO_USER("62003", "抱歉您没有绑定部门！"),
    E_EXAM_TEST_OVER("62004", "您已提交答案，不能重复提交！"),
    E_EXAM_CHECK_RESULT_ERROR("62005", "检查当天考试情况异常！"),

    E_HAVE_EMP_USING("70000", "该单位下存在用户，请先删除用户！"),
    E_HAVE_ACCOUNT("70001", "该账号已关联！"),

    E_90003("90003", "缺少必填参数");


    private String code;

    private String message;

    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
