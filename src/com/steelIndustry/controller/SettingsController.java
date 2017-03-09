package com.steelIndustry.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.steelIndustry.bo.AjaxResult;
import com.steelIndustry.model.Settings;
import com.steelIndustry.model.User;
import com.steelIndustry.service.SettingsService;
import com.steelIndustry.service.UserService;

@Controller
@RequestMapping("/settings")
public class SettingsController {

    @Resource(name = "settingsService")
    private SettingsService settingsService;
    
    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping(value = "/getSettings", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getSettings() {
        AjaxResult result = new AjaxResult();
        result.setErroCode(2000);
        result.setResult(settingsService.getSettings());
        return result;
    }
    
    @RequestMapping(value = "/updateSettings", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateSettings(@RequestBody Settings settings, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                settings.setId(1);
                settings = settingsService.saveSettings(settings);
                if (settings != null && settings.getId() == 1) {
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
}
