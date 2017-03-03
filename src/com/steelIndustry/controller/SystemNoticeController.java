package com.steelIndustry.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.steelIndustry.bo.AjaxResult;
import com.steelIndustry.model.SystemNotice;
import com.steelIndustry.model.User;
import com.steelIndustry.service.SystemNoticeService;
import com.steelIndustry.service.UserService;

@Controller
@RequestMapping("/systemNotice")
public class SystemNoticeController {
    
    @Resource(name = "systemNoticeService")
    private SystemNoticeService systemNoticeService;
    
    @Resource(name = "userService")
    private UserService userService;
    
    @RequestMapping(value = "/getSystemNoticeList", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getSystemNoticeList() {
        AjaxResult result = new AjaxResult();
        result.setErroCode(2000);
        result.setResult(systemNoticeService.getSystemNoticeList());
        return result;
    }
    
    @RequestMapping(value = "/getSystemNotice", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getSystemNotice(int id) {
        AjaxResult result = new AjaxResult();
        result.setErroCode(2000);
        result.setResult(systemNoticeService.getSystemNotice(id));
        return result;
    }
    
    @RequestMapping(value = "/releaseSystemNotice", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult releaseSystemNotice(SystemNotice systemNotice, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                systemNotice = systemNoticeService.releaseSystemNotice(systemNotice);
                if (systemNotice != null) {
                    result.setErroCode(2000);
                    result.setResult("success");
                } else {
                    result.setErroCode(3000);
                    result.setErroMsg("fail");
                }
            }
            else {
                result.setErroCode(5000);
                result.setErroMsg("没有权限");
            }
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }
    
    @RequestMapping(value = "/delSystemNotice", method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxResult delSystemNotice(int id, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                systemNoticeService.delete(id);;
                result.setErroCode(2000);
                result.setResult("success");
            }
            else {
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
