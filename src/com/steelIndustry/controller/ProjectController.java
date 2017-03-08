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
import com.steelIndustry.model.Project;
import com.steelIndustry.model.User;
import com.steelIndustry.service.ProjectService;
import com.steelIndustry.service.UserService;
import com.steelIndustry.util.CommonProperties;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Resource(name = "projectService")
    private ProjectService projectService;

    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping(value = "/getUserProject", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getUserProject(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            result.setErroCode(2000);
            Map resultMap = new HashMap();
            resultMap.put("projectList", projectService.getUserProject(user));
            resultMap.put("imgServer", CommonProperties.getInstance().get("imgServer"));
            result.setResult(resultMap);
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }
    
    @RequestMapping(value = "/getProjectById", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getProjectById(int id) {
        AjaxResult result = new AjaxResult();
        result.setErroCode(2000);
        Project project = projectService.getProjectById(id);
        result.setResult(project);
        projectService.updateProjectBv(project.getId());
        return result;
    }
    
    @RequestMapping(value = "/getProjectList", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getProjectList(@RequestBody Conditions conditions) {
        AjaxResult result = new AjaxResult();
        result.setErroCode(2000);
        result.setResult(result);
        Map resultMap = new HashMap();
        resultMap.put("projectList", projectService.getProjectList(conditions));
        resultMap.put("imgServer", CommonProperties.getInstance().get("imgServer"));
        result.setResult(resultMap);
        return result;
    }

    @RequestMapping(value = "/saveProject", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult saveProject(@RequestBody Project Project, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            int isSuccess = projectService.saveProject(Project);
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
    
    @RequestMapping(value = "/updateProjectCt", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateProjectCt(int id) {
        AjaxResult result = new AjaxResult();
        int isSuccess = projectService.updateProjectCt(id);
        if (isSuccess == 1) {
            result.setErroCode(2000);
            result.setResult("success");
        } else {
            result.setErroCode(3000);
            result.setErroMsg("fail");
        }
        return result;
    }
    
    @RequestMapping(value = "/updateProjectState", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateProjectState(@RequestBody JSONObject updateCd, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            if (user.getIsAdmin() == 1) {
                int isSuccess = projectService.updateProjectState(updateCd.getIntValue("id"), updateCd.getShortValue("state"));
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
    
    @RequestMapping(value = "/delProject", method = RequestMethod.DELETE)
    @ResponseBody
    public AjaxResult delProject(int id, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            projectService.delete(id);;
            result.setErroCode(2000);
            result.setResult("success");
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }
}
