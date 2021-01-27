package com.rongxinkj.alexmessage;

import com.rongxinkj.alexmessage.model.AlexMessage;
import com.rongxinkj.alexmessage.model.AlexMessageData;
import com.rongxinkj.alexmessage.model.AlexMessageMetaData;
import com.rongxinkj.alexmessage.model.TextMessage;
import com.rongxinkj.alexmessage.utils.CommonFunctions;
import com.rongxinkj.alexmessage.utils.WeChatUtil;
import com.thoughtworks.xstream.XStream;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

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

    /**
     * 设置行业
     */
    @Test
    public void setIndustry() {
        WeChatUtil.setIndustry();
    }

    /**
     * 获取行业
     */
    @Test
    public void getIndustry() {
        String url = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=" + WeChatUtil.getAccessToken();
        String result = CommonFunctions.sendGetRequest(url);
        System.out.println(result);
    }

    /**
     * 获取行业对应的消息模版
     */
    @Test
    public void getIndustryTemplate() {
        String url = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=" + WeChatUtil.getAccessToken();
        String result = CommonFunctions.sendGetRequest(url);
        System.out.println(result);
    }

    /**
     * 测试以post向指定地址发送Alex消息
     */
    @Test
    public void testSendPostRequest () {
        AlexMessageData data = new AlexMessageData();
        data.setAlexdate(new AlexMessageMetaData(String.valueOf(new Date()), "#157efb"));
        data.setAlexid(new AlexMessageMetaData("0066668803", "#157efb"));
        data.setAlexname(new AlexMessageMetaData("李家岩水情-琉璃村", "#157efb"));
        data.setAlexrain(new AlexMessageMetaData(String.valueOf(33.5), "#FFA500"));
        data.setAlexrainal(new AlexMessageMetaData(String.valueOf(30.0), "#FFA500"));
        data.setAlexwater(new AlexMessageMetaData(String.valueOf(524.0), "#FFA500"));
        data.setAlexwateral(new AlexMessageMetaData(String.valueOf(500.0), "#FFA500"));
        data.setRemark(new AlexMessageMetaData("截止目前李家岩水情-琉璃村站点雨量（水位）值已达到预警值，请注意做好山洪及次生灾害预防准备！！！", "#FF0000"));

        AlexMessage alexMessage = new AlexMessage();
        alexMessage.setTouser("oTaLS5noaL01vbiIXy_vo1kpKYn4");
        alexMessage.setTemplate_id("uIAHANtWocNo3ivxhAREvVdT2rw0BGYi4Ti5cxhOyqo");
        alexMessage.setUrl("http://117.176.184.118:9696/summer");
        alexMessage.setData(data);

        JSONObject j = new JSONObject();
        j.put("L1_LF_1", 23.5);
        j.put("L1_LF_2", 45.3);
        JSONObject o = new JSONObject();
        o.put("12345678", j);
        alexMessage.setJsonObject(o);

        JSONObject jsonObject = JSONObject.fromObject(alexMessage);
        System.out.println(jsonObject);

        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + WeChatUtil.getAccessToken();
        String result = CommonFunctions.sendPostRequest(url, jsonObject.toString());
        System.out.println(result);
    }

    @Test
    public void testAlex() {
        String url = "https://218.6.244.186:15011/api/devices/datapoints?type=1";
        String data = "{\n" +
                " \"deviceId\":\"48416578\",\n" +
                " \"apikey\":\"DD8B54B6C149994294BF3DD1294F8EBC\",\n" +
                " \"data\": {\"48416578\":{\"L3_YL_1\":\"23.5\"}}\n" +
                "}";

        System.out.println(CommonFunctions.sendHttpsPostRequest(url, data));
    }
}
