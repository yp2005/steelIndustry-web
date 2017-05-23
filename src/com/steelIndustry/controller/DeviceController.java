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
import com.steelIndustry.model.Device;
import com.steelIndustry.model.User;
import com.steelIndustry.service.CollectionService;
import com.steelIndustry.service.DeviceService;
import com.steelIndustry.service.UserService;
import com.steelIndustry.util.CommonProperties;

@Controller
@RequestMapping("/device")
public class DeviceController {

    @Resource(name = "deviceService")
    private DeviceService deviceService;

    @Resource(name = "userService")
    private UserService userService;
    
    @Resource(name = "collectionService")
    private CollectionService collectionService;

    @RequestMapping(value = "/getUserDevice", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getUserDevice(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            result.setErroCode(2000);
            Map resultMap = new HashMap();
            resultMap.put("deviceList", deviceService.getUserDevice(user));
            resultMap.put("imgServer", CommonProperties.getInstance().get("imgServer"));
            result.setResult(resultMap);
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }

    @RequestMapping(value = "/getDeviceById", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getDeviceById(int id, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        result.setErroCode(2000);
        Device device = deviceService.getDeviceById(id);
        if (device != null) {
            User user = userService.getUser(request, result);
            if (user != null && user.getId() != device.getUserId()) {
                device.setIsCollected(collectionService.isCollected(user.getId(), "device", device.getId()));
            }
            if (user == null || user.getId() != device.getUserId()) {
                deviceService.updateDeviceBv(device.getId());
            }
        }
        result.setErroCode(2000);
        result.setErroMsg(null);
        result.setResult(device);
        return result;
    }

    @RequestMapping(value = "/getDeviceList", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getDeviceList(@RequestBody Conditions conditions) {
        AjaxResult result = new AjaxResult();
        result.setErroCode(2000);
        result.setResult(result);
        Map resultMap = new HashMap();
        resultMap.put("deviceList", deviceService.getDeviceList(conditions));
        resultMap.put("imgServer", CommonProperties.getInstance().get("imgServer"));
        result.setResult(resultMap);
        return result;
    }

    @RequestMapping(value = "/saveDevice", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult saveDevice(@RequestBody Device device, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            device.setUserId(user.getId());
            int isSuccess = deviceService.saveDevice(device);
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

    @RequestMapping(value = "/updateDeviceCt", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateDeviceCt(int id) {
        AjaxResult result = new AjaxResult();
        int isSuccess = deviceService.updateDeviceCt(id);
        if (isSuccess == 1) {
            result.setErroCode(2000);
            result.setResult("success");
        } else {
            result.setErroCode(3000);
            result.setErroMsg("fail");
        }
        return result;
    }

    @RequestMapping(value = "/updateDeviceState", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateDeviceState(@RequestBody JSONObject updateCd, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            int id = updateCd.getIntValue("id");
            short state = updateCd.getShortValue("state");
            if (user.getIsAdmin() == 1) {
                int isSuccess = deviceService.updateDeviceState(id, state);
                if (isSuccess == 1) {
                    result.setErroCode(2000);
                    result.setResult("success");
                } else {
                    result.setErroCode(3000);
                    result.setErroMsg("fail");
                }
            } else if (state == 0 || state == 2) {
                Device device = deviceService.findOne(id);
                if (device.getUserId() == user.getId()) {
                    int isSuccess = deviceService.updateDeviceState(id, state);
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

    @RequestMapping(value = "/deleteDevice", method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxResult deleteDevice(int id, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            deviceService.delete(id);
            result.setErroCode(2000);
            result.setResult("success");
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }
}
