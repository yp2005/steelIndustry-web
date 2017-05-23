package com.steelIndustry.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.steelIndustry.util.CommonProperties;

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
            Map resultMap = new HashMap();
            resultMap.put("masterCardList", collectionService.getMasterCards(user.getId()));
            resultMap.put("imgServer", CommonProperties.getInstance().get("imgServer"));
            result.setResult(resultMap);
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
            Map resultMap = new HashMap();
            resultMap.put("workList", collectionService.getEmploymentDemands(user.getId()));
            resultMap.put("imgServer", CommonProperties.getInstance().get("imgServer"));
            result.setResult(resultMap);
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
            Map resultMap = new HashMap();
            resultMap.put("projectList", collectionService.getProjects(user.getId()));
            resultMap.put("imgServer", CommonProperties.getInstance().get("imgServer"));
            result.setResult(resultMap);
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }
    
    @RequestMapping(value = "/getDevices", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getDevices(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            result.setErroCode(2000);
            Map resultMap = new HashMap();
            resultMap.put("deviceList", collectionService.getDevices(user.getId()));
            resultMap.put("imgServer", CommonProperties.getInstance().get("imgServer"));
            result.setResult(resultMap);
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }
    
    @RequestMapping(value = "/deleteCollection", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult deleteCollection(String type, int collectId, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            int isSuccess = collectionService.deleteCollection(user.getId(), type, collectId);
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
