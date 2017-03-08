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
import com.steelIndustry.model.MasterCard;
import com.steelIndustry.model.User;
import com.steelIndustry.service.MasterCardService;
import com.steelIndustry.service.UserService;
import com.steelIndustry.util.CommonProperties;

@Controller
@RequestMapping("/masterCard")
public class MasterCardController {

    @Resource(name = "masterCardService")
    private MasterCardService masterCardService;

    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping(value = "/getMasterCard", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getMasterCard(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            result.setErroCode(2000);
            result.setResult(masterCardService.getMasterCardByUserId(user.getId()));
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }
    
    @RequestMapping(value = "/getMasterCardByUserId", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getMasterCardByUserId(int userId) {
        AjaxResult result = new AjaxResult();
        result.setErroCode(2000);
        MasterCard masterCard = masterCardService.getMasterCardByUserId(userId);
        result.setResult(masterCard);
        masterCardService.updateMasterCardBv(masterCard.getId());
        return result;
    }
    
    @RequestMapping(value = "/getMasterCardList", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getMasterCardList(@RequestBody Conditions conditions) {
        AjaxResult result = new AjaxResult();
        result.setErroCode(2000);
        result.setResult(result);
        Map resultMap = new HashMap();
        resultMap.put("masterCardList", masterCardService.getMasterCardList(conditions));
        resultMap.put("imgServer", CommonProperties.getInstance().get("imgServer"));
        result.setResult(resultMap);
        return result;
    }

    @RequestMapping(value = "/saveMasterCard", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult saveMasterCard(@RequestBody MasterCard masterCard, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            int isSuccess = masterCardService.saveMasterCard(masterCard);
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
    
    @RequestMapping(value = "/updateMasterCardCt", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateMasterCardCt(int id) {
        AjaxResult result = new AjaxResult();
        int isSuccess = masterCardService.updateMasterCardCt(id);
        if (isSuccess == 1) {
            result.setErroCode(2000);
            result.setResult("success");
        } else {
            result.setErroCode(3000);
            result.setErroMsg("fail");
        }
        return result;
    }
    
    @RequestMapping(value = "/updateMasterCardWorkState", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateMasterCardWorkState(short isWorking, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            int isSuccess = masterCardService.updateMasterCardWorkState(user.getId(), isWorking);
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
    
    @RequestMapping(value = "/updateMasterCardState", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateMasterCardState(@RequestBody JSONObject updateCd, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                int isSuccess = masterCardService.updateMasterCardState(updateCd.getIntValue("id"), updateCd.getShortValue("state"));
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
    
    @RequestMapping(value = "/delMasterCard", method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxResult delMasterCard(int id, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            masterCardService.delete(id);;
            result.setErroCode(2000);
            result.setResult("success");
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }
}
