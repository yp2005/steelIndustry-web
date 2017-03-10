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
import com.steelIndustry.dao.AdvertisementDao;
import com.steelIndustry.model.AdRelation;
import com.steelIndustry.model.Settings;
import com.steelIndustry.model.User;
import com.steelIndustry.service.AdRelationService;
import com.steelIndustry.service.AdvertisementService;
import com.steelIndustry.service.SettingsService;
import com.steelIndustry.service.UserService;

@Controller
@RequestMapping("/adRelation")
public class AdRelationController {

    @Resource(name = "adRelationService")
    private AdRelationService adRelationService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "settingsService")
    private SettingsService settingsService;

    @Resource(name = "advertisementService")
    private AdvertisementService advertisementService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addAdRelation(@RequestBody AdRelation adRelation, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                adRelation = adRelationService.saveAdRelation(adRelation);
                if (adRelation != null) {
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

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxResult deleteAdRelation(@RequestBody AdRelation adRelation, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                int isSuccess = adRelationService.deleteAdRelation(adRelation.getPostion(), adRelation.getAdId());
                if (isSuccess == 1) {
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

    @RequestMapping(value = "/getAdInfo", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getAdInfo(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                Settings settings = settingsService.getSettings();
                result.setErroCode(2000);
                Map resultMap = new HashMap();
                resultMap.put("homePage", adRelationService.getPositionAdList("homePage"));
                if (!settings.getListPageAdType().equals("alliance")) {
                    resultMap.put("listPage", adRelationService.getPositionAdList("listPage"));
                } else {
                    String content = advertisementService.getAllianceAd();
                    if (content != null) {
                        resultMap.put("listPage", JSONObject.parse(content));
                    }
                    else {
                        resultMap.put("listPage", content);
                    }
                }
                if (!settings.getDetailPageAdType().equals("alliance")) {
                    resultMap.put("detailPage", adRelationService.getPositionAdList("detailPage"));
                } else {
                    String content = advertisementService.getAllianceAd();
                    if (content != null) {
                        resultMap.put("detailPage", JSONObject.parse(content));
                    }
                    else {
                        resultMap.put("detailPage", content);
                    }
                }
                result.setResult(resultMap);
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
