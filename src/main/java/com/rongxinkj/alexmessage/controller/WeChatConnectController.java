package com.rongxinkj.alexmessage.controller;

import com.rongxinkj.alexmessage.utils.WeChatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class WeChatConnectController {

    @RequestMapping("/wechat")
    @ResponseBody
    public String connectWeChat(String signature, String timestamp, String nonce, String echostr, ServletInputStream is, HttpServletRequest request) {
        if ("get".equalsIgnoreCase(request.getMethod())) {
            boolean checked = WeChatUtil.check(timestamp, nonce, signature);
            if(checked) {
                System.out.println("验证成功");
                return echostr;
            } else {
                System.out.println("验证失败");
            }
        } else if("post".equalsIgnoreCase(request.getMethod())) {
            Map<String, String> requestMap = WeChatUtil.parseXmlFromWeChat(is);
            return WeChatUtil.getReMessage(requestMap);
        }
        return null;
    }
}
