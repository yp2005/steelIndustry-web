package com.steelIndustry.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.steelIndustry.bo.AjaxResult;
import com.steelIndustry.bo.Conditions;
import com.steelIndustry.bo.LoginInfo;
import com.steelIndustry.model.User;
import com.steelIndustry.service.UserService;
import com.steelIndustry.util.CommonProperties;
import com.steelIndustry.util.CommonUtil;
import com.steelIndustry.util.GeneratorUtil;
import com.steelIndustry.util.ValidateCode;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Log logger = LogFactory.getLog(CommonProperties.class);

    @Resource(name = "userService")
    private UserService userService;

    private static Map<String, HashMap> validateCodeMap = new HashMap<String, HashMap>();

    private static Map<String, HashMap> mobileNumberValidateCodeMap = new HashMap<String, HashMap>();

    @RequestMapping(value = "/getImage", method = RequestMethod.GET)
    public void getImage(String instanceId, HttpServletResponse response) {
        try {
            if (CommonUtil.isEmpty(instanceId)) {
                return;
            }
            response.setContentType("image/png");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            ValidateCode vCode = new ValidateCode(100, 30, 4, 20);
            HashMap map = (HashMap) validateCodeMap.get(instanceId);
            if (map == null) {
                map = new HashMap<String, String>();
            }
            map.put("code", vCode.getCode());
            map.put("time", System.currentTimeMillis());
            validateCodeMap.put(instanceId, map);
            logger.info(instanceId + "'s validate code is: " + vCode.getCode());
            vCode.write(response.getOutputStream());
        } catch (Exception e) {
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult login(@RequestBody LoginInfo loginInfo, HttpServletRequest request) {
        String instanceId = request.getHeader("instance_id");
        AjaxResult result = new AjaxResult();
        if (CommonUtil.isEmpty(instanceId)) {
            result.setErroCode(7000);
            result.setErroMsg("非法访问！");
        } else if ((System.currentTimeMillis() - (long) (validateCodeMap.get(instanceId).get("time"))) > 2 * 60
                * 1000) {
            result.setErroCode(4002);
            result.setErroMsg("验证码过期，请重新输入！");
        } else if (!loginInfo.getValidateCode().toUpperCase()
                .equals((String) (validateCodeMap.get(instanceId).get("code")))) {
            result.setErroCode(4001);
            result.setErroMsg("验证码错误，请重新输入！");
        } else if (mobileNumberValidateCodeMap.get(loginInfo.getMobileNumber() + "") == null) {
            result.setErroCode(3000);
            result.setErroMsg("未发送手机验证码！");
        } else if ((System.currentTimeMillis()
                - (long) (mobileNumberValidateCodeMap.get(loginInfo.getMobileNumber() + "").get("time"))) > 2 * 60
                        * 1000) {
            result.setErroCode(4004);
            result.setErroMsg("手机验证码过期，请重新发送！");
        } else if (!loginInfo.getMobilePhoneValidateCode()
                .equals((String) (mobileNumberValidateCodeMap.get(loginInfo.getMobileNumber() + "").get("code")))) {
            result.setErroCode(4003);
            result.setErroMsg("手机验证码错误，请重新输入！");
        } else {
            User user = userService.getUser(loginInfo.getMobileNumber());
            if (user == null) {
                user = new User();
                user.setMobileNumber(loginInfo.getMobileNumber());
                user.setInstanceId(instanceId);
                user.setAccessToken(GeneratorUtil.createUUID());
                user.setState((short) 1);
                user = userService.saveUser(user);
                result.setErroCode(2000);
                result.setResult(user);
                validateCodeMap.remove(instanceId);
                mobileNumberValidateCodeMap.remove(loginInfo.getMobileNumber() + "");
            } else if (user.getState() == 0) {
                result.setErroCode(3000);
                result.setErroMsg("您的账号已被禁用！");
            } else {
                user.setInstanceId(instanceId);
                user.setAccessToken(GeneratorUtil.createUUID());
                user = userService.saveUser(user);
                if (CommonUtil.isNotEmpty(user.getAvatar())) {
                    user.setAvatar(CommonProperties.getInstance().get("imgServer") + user.getAvatar());
                }
                result.setErroCode(2000);
                result.setResult(user);
                validateCodeMap.remove(instanceId);
                mobileNumberValidateCodeMap.remove(loginInfo.getMobileNumber() + "");
            }
        }
        return result;
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getUser(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            result.setErroCode(2000);
            result.setResult(user);
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }

    @RequestMapping(value = "/getUserById", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getUserById(int id, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                result.setErroCode(2000);
                result.setResult(userService.findOne(id));
            } else {
                result.setErroCode(5000);
                result.setErroMsg("没有权限");
            }
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }

    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getUserList(@RequestBody Conditions conditions, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                result.setErroCode(2000);
                result.setResult(userService.getUserList(conditions));
            } else {
                result.setErroCode(5000);
                result.setErroMsg("没有权限");
            }
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }

    @RequestMapping(value = "/updateLatestLoginTime", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateLatestLoginTime(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        int isSuccess = userService.updateLatestLoginTime(request);
        if (isSuccess == 1) {
            result.setErroCode(2000);
            result.setResult("success");
        } else {
            result.setErroCode(3000);
            result.setErroMsg("fail");
        }
        return result;
    }

    @RequestMapping(value = "/updateUserState", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateUserState(@RequestBody JSONObject updateCd, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                int isSuccess = userService.updateUserState(updateCd.getIntValue("id"),
                        updateCd.getShortValue("state"));
                if (isSuccess == 1) {
                    result.setErroCode(2000);
                    result.setResult("success");
                } else {
                    result.setErroCode(3000);
                    result.setErroMsg("fail");
                }
            } else {
                result.setErroCode(5000);
                result.setResult("权限不足！");
            }
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }

    @RequestMapping(value = "/updateShareState", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateShareState(@RequestBody JSONObject updateCd, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            int isSuccess = userService.updateShareState(user.getId(), updateCd.getShortValue("shareState"));
            if (isSuccess == 1) {
                result.setErroCode(2000);
                result.setResult("success");
            } else {
                result.setErroCode(3000);
                result.setErroMsg("fail");
            }
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }
    
    @RequestMapping(value = "/updateUserAvatar", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateUserAvatar(String avatar, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            int isSuccess = userService.updateUserAvatar(user.getId(), avatar);
            if (isSuccess == 1) {
                result.setErroCode(2000);
                result.setResult(CommonProperties.getInstance().get("imgServer") + avatar);
            } else {
                result.setErroCode(3000);
                result.setErroMsg("fail");
            }
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }

    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult sendCode(String mobileNumber, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        String instanceId = request.getHeader("instance_id");
        if (CommonUtil.isEmpty(instanceId)) {
            result.setErroCode(7000);
            result.setErroMsg("非法访问！");
        } else if (mobileNumberValidateCodeMap.get(mobileNumber) != null
                && mobileNumberValidateCodeMap.get(mobileNumber).get("sendTimes") != null
                && (int) (mobileNumberValidateCodeMap.get(mobileNumber).get("sendTimes")) >= 3
                && (System.currentTimeMillis()
                        - (long) (mobileNumberValidateCodeMap.get(mobileNumber).get("beginTime"))) < 5 * 60 * 1000) {
            result.setErroCode(3000);
            result.setErroMsg("您发送短信太过频繁，请稍后重试！");
        } else if (mobileNumberValidateCodeMap.get(mobileNumber) != null
                && mobileNumberValidateCodeMap.get(mobileNumber).get("time") != null && (System.currentTimeMillis()
                        - (long) (mobileNumberValidateCodeMap.get(mobileNumber).get("time"))) < 60 * 1000) {
            result.setErroCode(3000);
            result.setErroMsg("短信发送间隔小于60秒！");
        } else if (CommonUtil.isEmpty(mobileNumber)) {
            result.setErroCode(3000);
            result.setErroMsg("电话号码为空！");
        } else {
            Random random = new Random();
            String code = "";
            for (int i = 0; i < 4; i++) {
                code += random.nextInt(10);
            }
            JSONObject re = JSONObject.parseObject(sendMobilePhoneValidateCode(mobileNumber, code));

            if (re != null && re.getJSONObject("alibaba_aliqin_fc_sms_num_send_response") != null
                    && re.getJSONObject("alibaba_aliqin_fc_sms_num_send_response").getJSONObject("result") != null
                    && re.getJSONObject("alibaba_aliqin_fc_sms_num_send_response").getJSONObject("result")
                            .getBooleanValue("success")) {
                HashMap map = (HashMap) mobileNumberValidateCodeMap.get(mobileNumber);
                if (map == null) {
                    map = new HashMap<String, String>();
                }
                map.put("code", code);
                long cTime = System.currentTimeMillis();
                map.put("time", cTime);
                if (map.get("beginTime") == null) {
                    map.put("beginTime", cTime);
                }
                if (map.get("sendTimes") == null || (int) map.get("sendTimes") >= 3) {
                    map.put("sendTimes", 1);
                    map.put("beginTime", cTime);
                } else {
                    map.put("sendTimes", (int) map.get("sendTimes") + 1);
                }
                mobileNumberValidateCodeMap.put(mobileNumber, map);
                result.setErroCode(2000);
                result.setResult("success");
            } else if (re != null && re.getJSONObject("error_response") != null) {
                result.setErroCode(3000);
                result.setErroMsg(re.getJSONObject("error_response").getString("msg"));
            } else {
                result.setErroCode(3000);
                result.setErroMsg("短信发送失败！");
            }
        }
        return result;
    }

    public String sendMobilePhoneValidateCode(String mobileNumber, String validateCode) {
        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest",
                CommonProperties.getInstance().getProperty("appKey"),
                CommonProperties.getInstance().getProperty("appSecret"));
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType("normal");
        req.setSmsFreeSignName(CommonProperties.getInstance().getProperty("signName"));
        req.setSmsParamString("{\"code\":\"" + validateCode + "\"}");
        req.setRecNum(mobileNumber);
        req.setSmsTemplateCode(CommonProperties.getInstance().getProperty("templateCode"));
        AlibabaAliqinFcSmsNumSendResponse rsp;
        try {
            rsp = client.execute(req);
            return rsp.getBody();
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}
