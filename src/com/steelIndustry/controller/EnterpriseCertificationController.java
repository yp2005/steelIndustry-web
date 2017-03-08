package com.steelIndustry.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.steelIndustry.bo.AjaxResult;
import com.steelIndustry.bo.Conditions;
import com.steelIndustry.model.EnterpriseCertification;
import com.steelIndustry.model.User;
import com.steelIndustry.service.EnterpriseCertificationService;
import com.steelIndustry.service.UserService;

@Controller
@RequestMapping("/enterpriseCertification")
public class EnterpriseCertificationController {

    @Resource(name = "enterpriseCertificationService")
    private EnterpriseCertificationService enterpriseCertificationService;

    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping(value = "/addEnterpriseCertification", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addEnterpriseCertification(@RequestBody EnterpriseCertification enterpriseCertification,
            HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            enterpriseCertification.setUserId(user.getId());
            enterpriseCertification = enterpriseCertificationService.save(enterpriseCertification);
            if (enterpriseCertification != null) {
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

    @RequestMapping(value = "/updateEnterpriseCertificationState", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateEnterpriseCertificationState(short state, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            int isSuccess = enterpriseCertificationService.updateEnterpriseCertificationState(user.getId(), state);
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

    @RequestMapping(value = "/getEnterpriseCertification", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getEnterpriseCertification(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            result.setErroCode(2000);
            result.setResult(enterpriseCertificationService.getEnterpriseCertification(user.getId()));
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }

    @RequestMapping(value = "/getEnterpriseCertificationById", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getEnterpriseCertificationById(int id, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                result.setErroCode(2000);
                result.setResult(enterpriseCertificationService.findOne(id));
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

    @RequestMapping(value = "/getEnterpriseCertificationList", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getEnterpriseCertificationList(@RequestBody Conditions conditions, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                result.setErroCode(2000);
                result.setResult(enterpriseCertificationService.getEnterpriseCertificationList(conditions));
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
