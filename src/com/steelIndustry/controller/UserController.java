package com.steelIndustry.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.steelIndustry.bo.AjaxResult;
import com.steelIndustry.bo.LoginInfo;
import com.steelIndustry.model.User;
import com.steelIndustry.service.UserService;
import com.steelIndustry.util.CommonProperties;
import com.steelIndustry.util.CommonUtil;
import com.steelIndustry.util.GeneratorUtil;
import com.steelIndustry.util.ValidateCode;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Log logger = LogFactory.getLog(CommonProperties.class);

    @Resource(name = "userService")
    private UserService userService;

    private static Map<String, HashMap> validateCodeMap = new HashMap<String, HashMap>();

    private static Map<String, HashMap> mobileNumberValidateCodeMap = new HashMap<String, HashMap>();

    @RequestMapping("/getImage")
    public void getImage(String instanceId, HttpServletResponse response) {
        try {
            if (CommonUtil.isEmpty(instanceId)) {
                return;
            }
            response.setContentType("image/png");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            ValidateCode vCode = new ValidateCode(100, 30, 4, 20);
            HashMap map = (HashMap) validateCodeMap.get(instanceId);
            if (map == null) {
                map = new HashMap<String, String>();
            }
            map.put("code", vCode.getCode());
            map.put("time", System.currentTimeMillis());
            validateCodeMap.put(instanceId, map);
            logger.info(instanceId + "'s validate code is: " + vCode.getCode());
            vCode.write(response.getOutputStream());
        } catch (Exception e) {
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult login(@RequestBody LoginInfo loginInfo, HttpServletRequest request) {
        String instanceId = request.getHeader("instance_id");
        AjaxResult result = new AjaxResult();
        if (CommonUtil.isEmpty(instanceId)) {
            result.setErroCode(7000);
            result.setErroMsg("非法访问！");
        } else if ((System.currentTimeMillis() - (long) (validateCodeMap.get(instanceId).get("time"))) > 2 * 60
                * 1000) {
            result.setErroCode(4002);
            result.setErroMsg("验证码过期，请重新输入！");
        } else if (!loginInfo.getValidateCode().toUpperCase()
                .equals((String) (validateCodeMap.get(instanceId).get("code")))) {
            result.setErroCode(4001);
            result.setErroMsg("验证码错误，请重新输入！");
        } else if ((System.currentTimeMillis()
                - (long) (mobileNumberValidateCodeMap.get(loginInfo.getMobileNumber()).get("time"))) > 2 * 60 * 1000) {
            result.setErroCode(4004);
            result.setErroMsg("手机验证码过期，请重新发送！");
        } else if (!loginInfo.getMobilePhoneValidateCode()
                .equals((String) (mobileNumberValidateCodeMap.get(loginInfo.getMobileNumber()).get("code")))) {
            result.setErroCode(4003);
            result.setErroMsg("手机验证码错误，请重新输入！");
        } else {
            User user = userService.getUser(loginInfo.getMobileNumber());
            if (user == null) {
                user = new User();
                user.setMobileNumber(loginInfo.getMobileNumber());
                user.setInstanceId(instanceId);
                user.setAccessToken(GeneratorUtil.createUUID());
                user.setState((short) 1);
                user = userService.save(user);
                result.setErroCode(2000);
                result.setResult(user);
            } else if (user.getState() == 0) {
                result.setErroCode(3000);
                result.setErroMsg("您的账号已被禁用！");
            } else {
                user.setInstanceId(instanceId);
                user.setAccessToken(GeneratorUtil.createUUID());
                user = userService.save(user);
                result.setErroCode(2000);
                result.setResult(user);
                validateCodeMap.remove(instanceId);
                mobileNumberValidateCodeMap.remove(loginInfo.getMobileNumber());
            }
        }
        return result;
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getUser(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User user = userService.getUser(request, result);
        if (user != null) {
            result.setErroCode(2000);
            result.setResult(user);
        } else if (result.getErroCode() == null) {
            result.setErroCode(1000);
            result.setErroMsg("未知错误");
        }
        return result;
    }

    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult sendCode(String mobileNumber, HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        String instanceId = request.getHeader("instance_id");
        if (CommonUtil.isEmpty(instanceId)) {
            result.setErroCode(7000);
            result.setErroMsg("非法访问！");
        } else if (mobileNumberValidateCodeMap.get(mobileNumber) != null
                && mobileNumberValidateCodeMap.get(mobileNumber).get("sendTimes") != null
                && (int) (mobileNumberValidateCodeMap.get(mobileNumber).get("sendTimes")) >= 3
                && (System.currentTimeMillis()
                        - (long) (mobileNumberValidateCodeMap.get(mobileNumber).get("beginTime"))) < 5 * 60 * 1000) {
            result.setErroCode(3000);
            result.setErroMsg("您发送短信太过频繁，请稍后重试！");
        } else if (mobileNumberValidateCodeMap.get(mobileNumber) != null
                && mobileNumberValidateCodeMap.get(mobileNumber).get("time") != null && (System.currentTimeMillis()
                        - (long) (mobileNumberValidateCodeMap.get(mobileNumber).get("time"))) < 60 * 1000) {
            result.setErroCode(3000);
            result.setErroMsg("短信发送间隔小于60秒！");
        } else if (CommonUtil.isEmpty(mobileNumber)) {
            result.setErroCode(3000);
            result.setErroMsg("电话号码为空！");
        } else {
            Random random = new Random();
            String code = "";
            for (int i = 0; i < 4; i++) {
                code += random.nextInt(10);
            }
            String re = sendMobilePhoneValidateCode(code);
            if (re.indexOf("success") != -1) {
                HashMap map = (HashMap) mobileNumberValidateCodeMap.get(mobileNumber);
                if (map == null) {
                    map = new HashMap<String, String>();
                }
                map.put("code", code);
                long cTime = System.currentTimeMillis();
                map.put("time", cTime);
                if (map.get("beginTime") == null) {
                    map.put("beginTime", cTime);
                }
                if (map.get("sendTimes") == null || (int) map.get("sendTimes") >= 3) {
                    map.put("sendTimes", 1);
                    map.put("beginTime", cTime);
                } else {
                    map.put("sendTimes", (int) map.get("sendTimes") + 1);
                }
                mobileNumberValidateCodeMap.put(mobileNumber, map);
                result.setErroCode(2000);
                result.setResult(re);
            } else {
                result.setErroCode(3000);
                result.setErroMsg(re);
            }
        }
        return result;
    }

    public String sendMobilePhoneValidateCode(String validateCode) {
        try {
            // 连接超时及读取超时设置
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); // 连接超时：30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时：30秒
            // 新建一个StringBuffer链接
            StringBuffer buffer = new StringBuffer();
            // String encode = "GBK";
            // //页面编码和短信内容编码为GBK。重要说明：如提交短信后收到乱码，请将GBK改为UTF-8测试。如本程序页面为编码格式为：ASCII/GB2312/GBK则该处为GBK。如本页面编码为UTF-8或需要支持繁体，阿拉伯文等Unicode，请将此处写为：UTF-8
            String encode = "UTF-8";

            String username = "jxdd"; // 用户名
            String mobile = "18710095921"; // 手机号,只发一个号码：13800000001。发多个号码：13800000001,13800000002,...N
            String apikey = "5560a615f585359a949df330d62d3317"; // apikey秘钥（请登录
                                                                // http://m.5c.com.cn
                                                                // 短信平台-->账号管理-->我的信息
                                                                // 中复制apikey）
            String content = "您好，您的登录验证码是：" + validateCode + "【钢】"; // 要发送的短信内容，特别注意：签名必须设置，网页验证码应用需要加添加【图形识别码】。
            String password_md5 = GeneratorUtil.md5("asdf12345"); // 密码
            String contentUrlEncode = URLEncoder.encode(content, encode); // 对短信内容做Urlencode编码操作。注意：如
            // 把发送链接存入buffer中，如连接超时，可能是您服务器不支持域名解析，请将下面连接中的：【m.5c.com.cn】修改为IP：【115.28.23.78】
            buffer.append("http://m.5c.com.cn/api/send/index.php?username=" + username + "&password_md5=" + password_md5
                    + "&mobile=" + mobile + "&apikey=" + apikey + "&content=" + contentUrlEncode + "&encode=" + encode);
            // 把buffer链接存入新建的URL中
            URL url = new URL(buffer.toString());
            // 打开URL链接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 使用POST方式发送
            connection.setRequestMethod("POST");
            // 使用长链接方式
            connection.setRequestProperty("Connection", "Keep-Alive");
            // 发送短信内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            // 获取返回值
            String result = reader.readLine();
            // 输出result内容，查看返回值，成功为success，错误为error
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
