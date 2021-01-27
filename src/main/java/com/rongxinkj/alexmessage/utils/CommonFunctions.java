package com.rongxinkj.alexmessage.utils;

import com.rongxinkj.alexmessage.httpclientpool.HttpConnectionManager;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;

public class CommonFunctions {
    /**
     * 将字节转换为十六进制字符串
     * @param b
     * @return
     */
    public static String byteToHexStr(byte b) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(b >>> 4) & 0x0F];
        tempArr[1] = Digit[b & 0x0F];
        return new String(tempArr);
    }

    /**
     * 创建默认的httpClient，发送get请求
     * @param url
     * @return
     */
    public static String sendGetRequest(String url) {
        //创建默认的httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //返回响应体，httpClient在被调用的方法中使用完成之后被关闭
        return getResponseFromGetRequest(url, httpClient);
    }

    /**
     * 创建默认的httpClient，发送post请求
     * @param url
     * @param jsonData
     * @return
     */
    public static String sendPostRequest(String url, String jsonData) {
        if (jsonData == null || jsonData.isEmpty()) {
            return sendGetRequest(url);
        }

        //创建默认的httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //返回响应体，httpClient在被调用的方法中使用完成之后被关闭
        return getResponseFromPostRequest(url, jsonData, httpClient);
    }

    /**
     * 从连接池中获取httpClient，发送get请求
     * @param url
     * @return
     */
    public static String sendHttpsGetRequest(String url, int timeOut) {
        //从连接池中获取httpClient
        CloseableHttpClient httpClient = HttpConnectionManager.getHttpClient(timeOut);
        //返回响应体，httpClient在被调用的方法中使用完成之后被关闭
        return getResponseFromGetRequest(url, httpClient);
    }

    /**
     * 从连接池中获取httpClient，发送post请求
     * @param url
     * @param jsonData
     * @return
     */
    public static String sendHttpsPostRequest(String url, String jsonData, int timeOut) {
        if (jsonData == null || jsonData.isEmpty()) {
            return sendHttpsGetRequest(url, timeOut);
        }

        //从连接池中获取httpClient
        CloseableHttpClient httpClient = HttpConnectionManager.getHttpClient(timeOut);
        //返回响应体，httpClient在被调用的方法中使用完成之后被关闭
        return getResponseFromPostRequest(url, jsonData, httpClient);
    }

    /**
     * 获取get请求目标的响应体
     * @param url
     * @param httpClient
     * @return
     */
    private static String getResponseFromGetRequest(String url, CloseableHttpClient httpClient) {
        //请求目标的响应体
        String responseBody = null;
        try {
            //创建HttpGet对象
            HttpGet httpGet = new HttpGet(url);
            //执行get请求，并返回一个response
            CloseableHttpResponse response = httpClient.execute(httpGet);
            //获取响应内容的HttpEntity
            HttpEntity entity = response.getEntity();
            responseBody = EntityUtils.toString(entity);
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }

    /**
     * 获取post请求目标的响应体
     * @param url
     * @param jsonData
     * @param httpClient
     * @return
     */
    private static String getResponseFromPostRequest(String url, String jsonData, CloseableHttpClient httpClient) {
        //请求目标的响应体
        String responseBody = null;
        //设置post请求参数
        StringEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
        try {
            //创建HttpPost对象
            HttpPost httpPost = new HttpPost(url);
            //设置post请求的参数
            httpPost.setEntity(stringEntity);
            //执行get请求，并返回一个response
            CloseableHttpResponse response = httpClient.execute(httpPost);
            //获取响应内容的HttpEntity
            HttpEntity entity = response.getEntity();
            responseBody = EntityUtils.toString(entity);
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }
}
