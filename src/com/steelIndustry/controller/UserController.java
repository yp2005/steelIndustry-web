package com.steelIndustry.controller;

import java.util.HashMap;
import java.util.Map;

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

import com.steelIndustry.bo.AjaxResult;
import com.steelIndustry.bo.LoginInfo;
import com.steelIndustry.model.User;
import com.steelIndustry.service.UserService;
import com.steelIndustry.util.CommonProperties;
import com.steelIndustry.util.CommonUtil;
import com.steelIndustry.util.GeneratorUtil;
import com.steelIndustry.util.ValidateCode;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Log logger = LogFactory.getLog(CommonProperties.class);

    @Resource(name = "userService")
    private UserService userService;

    private static Map<String,HashMap> validateCodeMap = new HashMap<String,HashMap>();

    @RequestMapping("/getImage")
    public void getImage(String instanceId, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (CommonUtil.isEmpty(instanceId)) {
                return;
            }
            response.setContentType("image/png");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            ValidateCode vCode = new ValidateCode(100, 30, 4, 20);
            HashMap map = (HashMap)validateCodeMap.get(instanceId);
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
        if ((System.currentTimeMillis() - (long)(validateCodeMap.get(instanceId).get("time"))) > 5 * 60 * 1000) {
            result.setErroCode(4002);
            result.setErroMsg("验证码过期，请重新输入！");
        }
        else if (!loginInfo.getValidateCode().toUpperCase().equals((String)(validateCodeMap.get(instanceId).get("code")))) {
            result.setErroCode(4001);
            result.setErroMsg("验证码错误，请重新输入！");
        }
        else if (!loginInfo.getMobilePhoneValidateCode().equals("1234")) {
            result.setErroCode(4003);
            result.setErroMsg("手机验证码错误，请重新输入！");
        }
        else {
            User user = userService.getUser(loginInfo.getMobileNumber());
            if (user == null) {
                user = new User();
                user.setMobileNumber(loginInfo.getMobileNumber());
                user.setInstanceId(instanceId);
                user.setAccessToken(GeneratorUtil.createUUID());
                user.setState((short) 1);
                user = userService.save(user);
                result.setErroCode(2000);
                result.setResult(user);
            } else if (user.getState() == 0) {
                result.setErroCode(3000);
                result.setErroMsg("您的账号已被禁用！");
            } else {
                user.setInstanceId(instanceId);
                user.setAccessToken(GeneratorUtil.createUUID());
                user = userService.save(user);
                result.setErroCode(2000);
                result.setResult(user);
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
}
