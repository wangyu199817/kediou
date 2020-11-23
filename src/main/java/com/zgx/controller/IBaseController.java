package com.zgx.controller;

import com.alibaba.fastjson.JSONObject;
import com.zgx.common.ResultEnum;
import com.zgx.model.Result;

import static com.zgx.common.ResultEnum.ERROR_CODE;
import static com.zgx.common.ResultEnum.SUCESS_CODE;


public interface IBaseController {

    /**
     * 返回一个返回码为10000的json
     */
    default JSONObject successJson() {
        JSONObject resultJson = new JSONObject(true);
        resultJson.put("status", SUCESS_CODE.getCode());
        resultJson.put("message", SUCESS_CODE.getMessage());
        return resultJson;
    }

    /**
     * 返回一个返回码为10000的json
     *
     * @param data 返回数据
     */
    default JSONObject successJson(Object data) {
        JSONObject resultJson = new JSONObject(true);
        resultJson.put("status", SUCESS_CODE.getCode());
        resultJson.put("message", SUCESS_CODE.getMessage());
        resultJson.put("data", data);
        return resultJson;
    }

    /**
     * 返回一个返回码为-10000的json
     */
    default JSONObject errorJson() {
        JSONObject resultJson = new JSONObject(true);
        resultJson.put("status", ERROR_CODE.getCode());
        resultJson.put("message", ERROR_CODE.getMessage());
        return resultJson;
    }

    /**
     * 自定义返回错误消息
     */
    default JSONObject errorJson(String msg) {
        JSONObject resultJson = new JSONObject(true);
        resultJson.put("status", ERROR_CODE.getCode());
        resultJson.put("message", msg);
        return resultJson;
    }

    /**
     * 返回错误码和错误信息
     */
    default JSONObject errorJson(ResultEnum resultEnum) {
        JSONObject resultJson = new JSONObject(true);
        resultJson.put("status", resultEnum.getCode());
        resultJson.put("message", resultEnum.getMessage());
        return resultJson;
    }

    /**
     * 返回错误码和错误信息
     */
    default JSONObject returnJson(Result result) {
        JSONObject resultJson = new JSONObject(true);
        resultJson.put("status", result.getCode());
        resultJson.put("message", result.getMsg());
        resultJson.put("data", result.getData());
        return resultJson;
    }

}
