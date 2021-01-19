package com.rongxinkj.alexmessage.utils;

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
            StringBuilder sb = new StringBuilder();
            //处理加密结果
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
    public static String getReMessage(Map<String, String> requestMap) {
        String reMessage =
                "<xml>\n" +
                "  <ToUserName><![CDATA["+requestMap.get("FromUserName")+"]]></ToUserName>\n" +
                "  <FromUserName><![CDATA["+requestMap.get("ToUserName")+"]]></FromUserName>\n" +
                "  <CreateTime>"+System.currentTimeMillis()/1000+"</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA[公众号自动回复消息]]></Content>\n" +
                "</xml>";
        return reMessage;
    }
}
