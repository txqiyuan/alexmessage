package com.rongxinkj.alexmessage.utils;

import com.rongxinkj.alexmessage.model.AccessToken;
import com.rongxinkj.alexmessage.model.TextMessage;
import com.thoughtworks.xstream.XStream;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 验证签名
 */
public class WeChatUtil {
    private static final String TOKEN = "alexmsg";
    private static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static  final String APPID = "wx6a955bfff0c18e68";
    private static  final String APPSECRET = "ee21c14c06f65f2f4baa2b2cdc535e1a";

    public static boolean check(String timestamp, String nonce, String signature) {
        //1）将token、timestamp、nonce三个参数进行字典序排序
        String[] strs = new String[]{TOKEN, timestamp, nonce};
        Arrays.sort(strs);
        //2）将三个参数字符串拼接成一个字符串进行sha1加密
        String str = strs[0] + strs[1] + strs[2];
        String mysig = sha1(str);
        //3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        return signature.equalsIgnoreCase(mysig);
    }

    /**
     * 进行sha1加密
     * @param src
     * @return
     */
    private static String sha1(String src) {
        try {
            //获取一个加密对象
            MessageDigest md = MessageDigest.getInstance("sha1");
            //加密
            byte[] digest = md.digest(src.getBytes());
            //处理加密结果
            StringBuilder sb = new StringBuilder();
            for(byte b : digest) {
                sb.append(CommonFunctions.byteToHexStr(b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析从微信发送过来的xml数据包
     * @param is
     */
    public static Map<String, String> parseXmlFromWeChat(InputStream is) {
        Map<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();
        try {
            //读取输入流，获取文档对象
            Document document = reader.read(is);
            //根据文档对象获取跟节点
            Element root = document.getRootElement();
            //获取跟节点的所有子节点
            List<Element> elements = root.elements();
            for (Element element : elements) {
                map.put(element.getName(), element.getStringValue());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获得xml格式的消息回复文本
     * @param requestMap
     * @return
     */
    public static String getResponse(Map<String, String> requestMap) {
        //根据消息类型获取不同消息的回复
        String msgType = requestMap.get("MsgType");
        switch (msgType) {
            case "text":
                return returnTextMessage(requestMap);
            case "image":
            case "voice":
            case "video":
            case "shortvideo":
            case "location":
            case "link":
            default:
        }
        return null;
    }

    /**
     * 返回文本消息
     * @param requestMap
     * @return
     */
    private static String returnTextMessage(Map<String, String> requestMap) {
        //因为现在是回复消息，所以接收者和发送者双方的角色需要对换。
        String toUserName = requestMap.get("FromUserName");
        String fromUserName = requestMap.get("ToUserName");
        String creatTime = String.valueOf(System.currentTimeMillis()/1000);
        String content = "你干啥？";
        //把要回复的消息封装成一个消息对象
        TextMessage textMessage = new TextMessage(toUserName, fromUserName, creatTime, content);
        return beanToXml(textMessage);
    }

    /**
     * 将消息对象转化为XML字符串
     * @param textMessage
     * @return
     */
    private static String beanToXml(TextMessage textMessage) {
        XStream stream = new XStream();
        //让程序识别@XStreamAlias()注解语法
        stream.processAnnotations(textMessage.getClass());
        return stream.toXML(textMessage);
    }

    /**
     * 返回一个微信的access_token
     * @return
     */
    public static String getAccessToken() {
        String token = null;
        //获得单例的AccessToken对象
        AccessToken accessToken = AccessToken.getInstance();

        //如果accessToken里的token为null或者token过期，则从微信服务器重新获取token，否则直接从accessToken里获取token。
        if(accessToken.getToken()==null || accessToken.isExpired()) {
            //获得正确的请求url
            String url = GET_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
            //获取微信服务器返回的json字符串，并解析。里面包含access_token和expires_in
            String tokenStr = CommonFunctions.sendGetRequest(url);
            JSONObject jsonObject = JSONObject.fromObject(tokenStr);
            token = jsonObject.getString("access_token");
            accessToken.setToken(token);
            accessToken.setExpiresTime(jsonObject.getString("expires_in"));
        } else {
            token = accessToken.getToken();
        }

        return token;
    }

    /**
     * 微信公众号发送模版消息需要设置行业，在这个方法中设置
     */
    public static void setIndustry() {
        String url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=" + getAccessToken();
        String data = "{\"industry_id1\":\"1\",\"industry_id2\":\"4\"}";
        String result = CommonFunctions.sendPostRequest(url, data);
        System.out.println(result);
    }
}
