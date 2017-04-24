package com.steelIndustry.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.steelIndustry.bo.AjaxResult;
import com.steelIndustry.bo.Conditions;
import com.steelIndustry.model.RealNameAuthentication;
import com.steelIndustry.model.User;
import com.steelIndustry.service.RealNameAuthenticationService;
import com.steelIndustry.service.UserService;
import com.steelIndustry.util.CommonProperties;

@Controller
@RequestMapping("/realNameAuthentication")
public class RealNameAuthenticationController {

    @Resource(name = "realNameAuthenticationService")
    private RealNameAuthenticationService realNameAuthenticationService;

    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping(value = "/saveRealNameAuthentication", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult saveRealNameAuthentication(@RequestBody RealNameAuthentication realNameAuthentication,
            HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            realNameAuthentication.setUserId(user.getId());
            realNameAuthentication = realNameAuthenticationService.saveRealNameAuthentication(realNameAuthentication);
            if (realNameAuthentication != null) {
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

    @RequestMapping(value = "/updateRealNameAuthenticationState", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateRealNameAuthenticationState(@RequestBody JSONObject updateCd, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                int isSuccess = realNameAuthenticationService.updateRealNameAuthenticationState(updateCd.getIntValue("userId"), updateCd.getShortValue("state"));
                if (isSuccess == 1) {
                    result.setErroCode(2000);
                    result.setResult("success");
                } else {
                    result.setErroCode(3000);
                    result.setErroMsg("fail");
                }
            }
            else {
                result.setErroCode(5000);
                result.setResult("权限不足！");
            }
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }

    @RequestMapping(value = "/getRealNameAuthentication", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getRealNameAuthentication(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            result.setErroCode(2000);
            RealNameAuthentication realNameAuthentication = realNameAuthenticationService.getRealNameAuthentication(user.getId());
            if (realNameAuthentication != null) {
                realNameAuthentication.setImgServer(CommonProperties.getInstance().getProperty("imgServer"));
            }
            result.setResult(realNameAuthentication);
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }

    @RequestMapping(value = "/getRealNameAuthenticationById", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getRealNameAuthenticationById(int id, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                result.setErroCode(2000);
                RealNameAuthentication realNameAuthentication = realNameAuthenticationService.findOne(id);
                if (realNameAuthentication != null) {
                    realNameAuthentication.setImgServer(CommonProperties.getInstance().getProperty("imgServer"));
                }
                result.setResult(realNameAuthentication);
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

    @RequestMapping(value = "/getRealNameAuthenticationList", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getRealNameAuthenticationList(@RequestBody Conditions conditions, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                result.setErroCode(2000);
                result.setResult(realNameAuthenticationService.getRealNameAuthenticationList(conditions));
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

}
