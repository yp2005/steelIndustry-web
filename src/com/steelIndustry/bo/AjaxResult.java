package com.steelIndustry.bo;

public class AjaxResult {
    private Integer erroCode;// 1000：未知错误，
                             // 2000：成功，3000：失败，4000：未登录或者登陆超时，4001：验证码错误，4002：验证码过期，4003：手机验证码错误，4004：手机验证码过期，5000：没有权限，
                             // 6000：其他设备端登录了该账户，被迫下线，
                             // 7000：非法访问，8000：本地时间异常，请校准本地时间
    private String erroMsg;// 错误信息
    private Object result;// ajax请求结果

    public Integer getErroCode() {
        return erroCode;
    }

    public void setErroCode(Integer erroCode) {
        this.erroCode = erroCode;
    }

    public String getErroMsg() {
        return erroMsg;
    }

    public void setErroMsg(String erroMsg) {
        this.erroMsg = erroMsg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
