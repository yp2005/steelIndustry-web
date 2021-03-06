package com.steelIndustry.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.steelIndustry.model.EmploymentDemand;
import com.steelIndustry.model.Project;
import com.steelIndustry.model.User;
import com.steelIndustry.service.CollectionService;
import com.steelIndustry.service.EmploymentDemandService;
import com.steelIndustry.service.UserService;
import com.steelIndustry.util.CommonProperties;

@Controller
@RequestMapping("/employmentDemand")
public class EmploymentDemandController {

    @Resource(name = "employmentDemandService")
    private EmploymentDemandService employmentDemandService;

    @Resource(name = "userService")
    private UserService userService;
    
    @Resource(name = "collectionService")
    private CollectionService collectionService;

    @RequestMapping(value = "/getUserEmploymentDemand", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getUserEmploymentDemand(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            result.setErroCode(2000);
            Map resultMap = new HashMap();
            resultMap.put("employmentDemandList", employmentDemandService.getUserEmploymentDemand(user));
            resultMap.put("imgServer", CommonProperties.getInstance().get("imgServer"));
            result.setResult(resultMap);
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }

    @RequestMapping(value = "/getEmploymentDemandById", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getEmploymentDemandById(int id, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        result.setErroCode(2000);
        EmploymentDemand employmentDemand = employmentDemandService.getEmploymentDemandById(id);
        User user = userService.getUser(request, result);
        if (user != null && user.getId() != employmentDemand.getUserId()) {
            employmentDemand.setIsCollected(collectionService.isCollected(user.getId(), "work", employmentDemand.getId()));
        }
        if (user == null || user.getId() != employmentDemand.getUserId()) {
            employmentDemandService.updateEmploymentDemandBv(employmentDemand.getId());
        }
        result.setErroCode(2000);
        result.setErroMsg(null);
        result.setResult(employmentDemand);
        return result;
    }

    @RequestMapping(value = "/getEmploymentDemandList", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getEmploymentDemandList(@RequestBody Conditions conditions) {
        AjaxResult result = new AjaxResult();
        result.setErroCode(2000);
        result.setResult(result);
        Map resultMap = new HashMap();
        resultMap.put("employmentDemandList", employmentDemandService.getEmploymentDemandList(conditions));
        resultMap.put("imgServer", CommonProperties.getInstance().get("imgServer"));
        result.setResult(resultMap);
        return result;
    }

    @RequestMapping(value = "/saveEmploymentDemand", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult saveEmploymentDemand(@RequestBody EmploymentDemand employmentDemand, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            employmentDemand.setUserId(user.getId());
            int isSuccess = employmentDemandService.saveEmploymentDemand(employmentDemand);
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

    @RequestMapping(value = "/updateEmploymentDemandCt", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateEmploymentDemandCt(int id) {
        AjaxResult result = new AjaxResult();
        int isSuccess = employmentDemandService.updateEmploymentDemandCt(id);
        if (isSuccess == 1) {
            result.setErroCode(2000);
            result.setResult("success");
        } else {
            result.setErroCode(3000);
            result.setErroMsg("fail");
        }
        return result;
    }

    @RequestMapping(value = "/updateEmploymentDemandState", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateEmploymentDemandState(@RequestBody JSONObject updateCd, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            int id = updateCd.getIntValue("id");
            short state = updateCd.getShortValue("state");
            if (user.getIsAdmin() == 1) {
                int isSuccess = employmentDemandService.updateEmploymentDemandState(id, state);
                if (isSuccess == 1) {
                    result.setErroCode(2000);
                    result.setResult("success");
                } else {
                    result.setErroCode(3000);
                    result.setErroMsg("fail");
                }
            } else if (state == 0 || state == 2) {
                EmploymentDemand employmentDemand = employmentDemandService.findOne(id);
                if (employmentDemand.getUserId() == user.getId()) {
                    int isSuccess = employmentDemandService.updateEmploymentDemandState(id, state);
                    if (isSuccess == 1) {
                        result.setErroCode(2000);
                        result.setResult("success");
                    } else {
                        result.setErroCode(3000);
                        result.setErroMsg("fail");
                    }
                } else {
                    result.setErroCode(5000);
                    result.setErroMsg("权限不足！");
                }
            } else {
                result.setErroCode(5000);
                result.setErroMsg("权限不足！");
            }
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }

    @RequestMapping(value = "/deleteEmploymentDemand", method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxResult deleteEmploymentDemand(int id, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            employmentDemandService.delete(id);
            result.setErroCode(2000);
            result.setResult("success");
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }
}
