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

import com.steelIndustry.bo.AjaxResult;
import com.steelIndustry.bo.Conditions;
import com.steelIndustry.model.Store;
import com.steelIndustry.model.User;
import com.steelIndustry.service.StoreService;
import com.steelIndustry.service.UserService;
import com.steelIndustry.util.CommonProperties;

@Controller
@RequestMapping("/store")
public class StoreController {

    @Resource(name = "storeService")
    private StoreService storeService;

    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping(value = "/getStore", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getStore(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            result.setErroCode(2000);
            result.setResult(storeService.getStoreByUserId(user.getId()));
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }
    
    @RequestMapping(value = "/getStoreByUserId", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getStoreByUserId(int userId) {
        AjaxResult result = new AjaxResult();
        result.setErroCode(2000);
        Store store = storeService.getStoreByUserId(userId);
        result.setResult(store);
        storeService.updateStoreBv(store.getId());
        return result;
    }
    
    @RequestMapping(value = "/getStoreList", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getStoreList(@RequestBody Conditions conditions) {
        AjaxResult result = new AjaxResult();
        result.setErroCode(2000);
        result.setResult(result);
        Map resultMap = new HashMap();
        resultMap.put("storeList", storeService.getStoreList(conditions));
        resultMap.put("imgServer", CommonProperties.getInstance().get("imgServer"));
        result.setResult(resultMap);
        return result;
    }

    @RequestMapping(value = "/saveStore", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult saveStore(@RequestBody Store Store, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            int isSuccess = storeService.saveStore(Store);
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
    
    @RequestMapping(value = "/updateStoreCt", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateStoreCt(int id) {
        AjaxResult result = new AjaxResult();
        int isSuccess = storeService.updateStoreCt(id);
        if (isSuccess == 1) {
            result.setErroCode(2000);
            result.setResult("success");
        } else {
            result.setErroCode(3000);
            result.setErroMsg("fail");
        }
        return result;
    }
    
    @RequestMapping(value = "/delStore", method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxResult delStore(int id, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            storeService.delete(id);
            result.setErroCode(2000);
            result.setResult("success");
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }
}
