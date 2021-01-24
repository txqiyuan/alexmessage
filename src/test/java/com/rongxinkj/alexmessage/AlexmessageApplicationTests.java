package com.rongxinkj.alexmessage;

import com.rongxinkj.alexmessage.model.TextMessage;
import com.rongxinkj.alexmessage.utils.WeChatUtil;
import com.thoughtworks.xstream.XStream;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AlexmessageApplicationTests {

    /**
     * 测试文本消息封装成XML字符串
     */
    @Test
    public void testMessage() {
        TextMessage tm = new TextMessage("to", "from", "20210123", "你好");
        XStream stream = new XStream();
        //让程序识别@XStreamAlias()注解语法
        stream.processAnnotations(TextMessage.class);
        System.out.println(stream.toXML(tm));
    }

    /**
     * 测试获取AccessToken
     */
    @Test
    public void testGetAccessToken() {
        System.out.println(WeChatUtil.getAccessToken());
        System.out.println(WeChatUtil.getAccessToken());
    }

}
