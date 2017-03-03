package com.steelIndustry.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.steelIndustry.bo.AjaxResult;
import com.steelIndustry.model.Collection;
import com.steelIndustry.model.User;
import com.steelIndustry.service.CollectionService;
import com.steelIndustry.service.UserService;

@Controller
@RequestMapping("/collection")
public class CollectionController {

    @Resource(name = "collectionService")
    private CollectionService collectionService;

    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping(value = "/getMasterCards", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getMasterCards(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            result.setErroCode(2000);
            result.setResult(collectionService.getMasterCards(user.getId()));
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }
    
    @RequestMapping(value = "/getEmploymentDemands", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getEmploymentDemands(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            result.setErroCode(2000);
            result.setResult(collectionService.getEmploymentDemands(user.getId()));
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }
    
    @RequestMapping(value = "/getProjects", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getProjects(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            result.setErroCode(2000);
            result.setResult(collectionService.getProjects(user.getId()));
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }
    
    @RequestMapping(value = "/getStores", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getStores(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            result.setErroCode(2000);
            result.setResult(collectionService.getStores(user.getId()));
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }
    
    @RequestMapping(value = "/delCollection", method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxResult delCollection(String type, int collectId, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            int isSuccess = collectionService.delCollection(user.getId(), type, collectId);
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
    
    @RequestMapping(value = "/addCollection", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addCollection(String type, int collectId, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            Collection collection = collectionService.addCollection(user.getId(), type, collectId);
            if (collection != null) {
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
    
}
