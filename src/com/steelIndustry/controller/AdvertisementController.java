package com.steelIndustry.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.steelIndustry.bo.AjaxResult;
import com.steelIndustry.model.Advertisement;
import com.steelIndustry.model.User;
import com.steelIndustry.service.AdvertisementService;
import com.steelIndustry.service.UserService;

@Controller
@RequestMapping("/advertisement")
public class AdvertisementController {

    @Resource(name = "advertisementService")
    private AdvertisementService advertisementService;

    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addAdvertisement(@RequestBody Advertisement advertisement, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                advertisement = advertisementService.saveAdvertisement(advertisement);
                if (advertisement != null) {
                    result.setErroCode(2000);
                    result.setResult("success");
                } else {
                    result.setErroCode(3000);
                    result.setErroMsg("fail");
                }
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
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxResult deleteAdvertisement(@PathVariable("id") int id, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                advertisementService.delete(id);
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

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public AjaxResult updateAdvertisement(@RequestBody Advertisement advertisement, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                int id = advertisement.getId();
                advertisement = advertisementService.saveAdvertisement(advertisement);
                if (advertisement != null && advertisement.getId() == id) {
                    result.setErroCode(2000);
                    result.setResult("success");
                } else {
                    advertisementService.delete(advertisement);
                    result.setErroCode(3000);
                    result.setErroMsg("fail");
                }
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
