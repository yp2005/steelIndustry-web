package com.steelIndustry.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.steelIndustry.bo.AjaxResult;
import com.steelIndustry.bo.Conditions;
import com.steelIndustry.dao.AdRelationDao;
import com.steelIndustry.dao.SettingsDao;
import com.steelIndustry.model.Advertisement;
import com.steelIndustry.model.Settings;
import com.steelIndustry.model.User;
import com.steelIndustry.service.AdRelationService;
import com.steelIndustry.service.AdvertisementService;
import com.steelIndustry.service.SettingsService;
import com.steelIndustry.service.UserService;

@Controller
@RequestMapping("/advertisement")
public class AdvertisementController {

    @Resource(name = "advertisementService")
    private AdvertisementService advertisementService;

    @Resource(name = "adRelationService")
    private AdRelationService adRelationService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "settingsService")
    private SettingsService settingsService;

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
                int isSuccess = advertisementService.deleteAdvertisement(id);
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getAdvertisement(@PathVariable("id") int id, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                result.setErroCode(2000);
                result.setResult(advertisementService.getAdvertisement(id));
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

    @RequestMapping(value = "getPositionAds", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getPositionAds(String position, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        Settings settings = settingsService.getSettings();
        result.setErroCode(2000);
        if (position.equals("homePage")) {
            result.setResult(adRelationService.getPositionAdList(position));
        } else {
            String adType = position.equals("listPage") ? settings.getListPageAdType() : settings.getDetailPageAdType();
            switch (adType) {
            case "alliance":
                String content = advertisementService.getAllianceAd();
                if (content != null) {
                    result.setResult(JSONObject.parse(content));
                } else {
                    result.setResult(content);
                }
                break;
            case "loopImg":
                result.setResult(adRelationService.getPositionAdList(position));
                break;
            case "oneImg":
                List<Advertisement> adList = adRelationService.getPositionAdList(position);
                if (adList != null && adList.size() > 0) {
                    result.setResult(adList.get((int) (Math.random() * adList.size())));
                } else {
                    result.setResult(null);
                }
                break;
            default:
                break;
            }
        }
        return result;
    }

    @RequestMapping(value = "/updateAllianceAd", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateAllianceAd(@RequestBody JSONObject content, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                int isSuccess = advertisementService.updateAllianceAd(JSONObject.toJSONString(content));
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

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getAdvertisement(@RequestBody Conditions conditions, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                result.setErroCode(2000);
                result.setResult(advertisementService.getAdvertisementList(conditions));
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
