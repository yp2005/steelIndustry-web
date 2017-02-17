package com.steelIndustry.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.steelIndustry.bo.AjaxResult;
import com.steelIndustry.service.SettingsService;

@Controller
@RequestMapping("/settings")
public class SettingsController {

    @Resource(name = "settingsService")
    private SettingsService settingsService;

    @RequestMapping(value = "/getSettings", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getSettings() {
        AjaxResult result = new AjaxResult();
        result.setErroCode(2000);
        result.setResult(settingsService.getSettings());
        return result;
    }

    @RequestMapping(value = "/updateShareSwitch", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateShareSwitch(short shareSwitch) {
        AjaxResult result = new AjaxResult();
        int isSuccess = settingsService.updateShareSwitch(shareSwitch);
        if (isSuccess == 1) {
            result.setErroCode(2000);
            result.setResult("success");
        } else {
            result.setErroCode(3000);
            result.setErroMsg("fail");
        }

        return result;
    }

    @RequestMapping(value = "/updateIsCheckStore", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateIsCheckStore(short isCheckStore) {
        AjaxResult result = new AjaxResult();
        int isSuccess = settingsService.updateIsCheckStore(isCheckStore);
        if (isSuccess == 1) {
            result.setErroCode(2000);
            result.setResult("success");
        } else {
            result.setErroCode(3000);
            result.setErroMsg("fail");
        }

        return result;
    }

    @RequestMapping(value = "/updateIsCheckProject", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateIsCheckProject(short isCheckProject) {
        AjaxResult result = new AjaxResult();
        int isSuccess = settingsService.updateIsCheckProject(isCheckProject);
        if (isSuccess == 1) {
            result.setErroCode(2000);
            result.setResult("success");
        } else {
            result.setErroCode(3000);
            result.setErroMsg("fail");
        }

        return result;
    }

    @RequestMapping(value = "/updateIsCheckCard", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateIsCheckCard(short isCheckCard) {
        AjaxResult result = new AjaxResult();
        int isSuccess = settingsService.updateIsCheckCard(isCheckCard);
        if (isSuccess == 1) {
            result.setErroCode(2000);
            result.setResult("success");
        } else {
            result.setErroCode(3000);
            result.setErroMsg("fail");
        }

        return result;
    }

    @RequestMapping(value = "/updateIsCheckWork", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateIsCheckWork(short isCheckWork) {
        AjaxResult result = new AjaxResult();
        int isSuccess = settingsService.updateIsCheckWork(isCheckWork);
        if (isSuccess == 1) {
            result.setErroCode(2000);
            result.setResult("success");
        } else {
            result.setErroCode(3000);
            result.setErroMsg("fail");
        }

        return result;
    }

    @RequestMapping(value = "/updateMainPostPoints", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateMainPostPoints(int mainPostPoints) {
        AjaxResult result = new AjaxResult();
        int isSuccess = settingsService.updateMainPostPoints(mainPostPoints);
        if (isSuccess == 1) {
            result.setErroCode(2000);
            result.setResult("success");
        } else {
            result.setErroCode(3000);
            result.setErroMsg("fail");
        }

        return result;
    }

    @RequestMapping(value = "/updateReplyingPoints", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateReplyingPoints(int replyingPoints) {
        AjaxResult result = new AjaxResult();
        int isSuccess = settingsService.updateReplyingPoints(replyingPoints);
        if (isSuccess == 1) {
            result.setErroCode(2000);
            result.setResult("success");
        } else {
            result.setErroCode(3000);
            result.setErroMsg("fail");
        }

        return result;
    }

    @RequestMapping(value = "/updateHomePageAdType", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateHomePageAdType(String homePageAdType) {
        AjaxResult result = new AjaxResult();
        int isSuccess = settingsService.updateHomePageAdType(homePageAdType);
        if (isSuccess == 1) {
            result.setErroCode(2000);
            result.setResult("success");
        } else {
            result.setErroCode(3000);
            result.setErroMsg("fail");
        }

        return result;
    }

    @RequestMapping(value = "/updateListPageAdType", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateListPageAdType(String listPageAdType) {
        AjaxResult result = new AjaxResult();
        int isSuccess = settingsService.updateListPageAdType(listPageAdType);
        if (isSuccess == 1) {
            result.setErroCode(2000);
            result.setResult("success");
        } else {
            result.setErroCode(3000);
            result.setErroMsg("fail");
        }

        return result;
    }

    @RequestMapping(value = "/updateDetailPageAdType", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateDetailPageAdType(String detailPageAdType) {
        AjaxResult result = new AjaxResult();
        int isSuccess = settingsService.updateDetailPageAdType(detailPageAdType);
        if (isSuccess == 1) {
            result.setErroCode(2000);
            result.setResult("success");
        } else {
            result.setErroCode(3000);
            result.setErroMsg("fail");
        }

        return result;
    }
}
