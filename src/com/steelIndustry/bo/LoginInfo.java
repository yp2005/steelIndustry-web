package com.steelIndustry.bo;

public class LoginInfo {
    private long mobileNumber;//手机号码
    private String validateCode;//验证码
    private String mobilePhoneValidateCode;//手机验证码
    private long oldMobileNumber; //旧的手机号，修改手机号时使用

    public long getOldMobileNumber() {
        return oldMobileNumber;
    }

    public void setOldMobileNumber(long oldMobileNumber) {
        this.oldMobileNumber = oldMobileNumber;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getMobilePhoneValidateCode() {
        return mobilePhoneValidateCode;
    }

    public void setMobilePhoneValidateCode(String mobilePhoneValidateCode) {
        this.mobilePhoneValidateCode = mobilePhoneValidateCode;
    }

}
