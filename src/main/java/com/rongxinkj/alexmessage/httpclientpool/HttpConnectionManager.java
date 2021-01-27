package com.rongxinkj.alexmessage.httpclientpool;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * Created by Tiffany on 2019-7-19.
 */
public class HttpConnectionManager {
    //如果要进行https访问，则必须要对SSL证书进行处理，这里选择绕过SSL验证
    private static SSLContext sslContext = null;
    static {
        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
        try {
            sslContext = sslContextBuilder.loadTrustMaterial(null, (certificate, authType) -> true).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //注册http和https访问方式
    private static final Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
            //注册http请求
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
            //注册https请求
            .register("https", new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier()))
            .build();

    //全局连接池对象
    private static final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

    //静态代码块配置连接池信息
    static {
        // 设置最大连接数
        connManager.setMaxTotal(300);
        // 设置每个连接的路由数
        connManager.setDefaultMaxPerRoute(30);
    }

    /**
     * 获取Http客户端连接对象
     * @param timeOut 超时时间
     * @return Http客户端连接对象
     */
    public static CloseableHttpClient getHttpClient(Integer timeOut) {
        // 创建Http请求配置参数
        RequestConfig requestConfig = RequestConfig.custom()
                // 获取连接超时时间
                .setConnectionRequestTimeout(timeOut)
                // 请求超时时间
                .setConnectTimeout(timeOut)
                // 响应超时时间
                .setSocketTimeout(timeOut)
                .build();

        /*
         * 超时重试机制，为了防止超时不生效而设置
         * 如果返回false，不重试连接。返回true，重试连接
         * 这里会根据情况进行判断是否重试
         */
        HttpRequestRetryHandler retry = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 3) {// 如果已经重试了3次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return true;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };

        /*
         * 长连接策略：
         * 对于请求token和语音的host保持连接6分钟，其余暂定3分钟
         */
        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // Honor 'keep-alive' header
                HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch(NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
                HttpHost target = (HttpHost) context.getAttribute(HttpClientContext.HTTP_TARGET_HOST);
                if ("eastasia.api.cognitive.microsoft.com".equalsIgnoreCase(target.getHostName())) {
                    // Keep alive for 5 seconds only
                    return 300 * 1000;
                }else if("eastasia.tts.speech.microsoft.com".equalsIgnoreCase(target.getHostName())){
                    return 300 * 1000;
                }else if("eastasia.stt.speech.microsoft.com".equalsIgnoreCase(target.getHostName())){
                    return 300 * 1000;
                }else if("api-apc.cognitive.microsofttranslator.com".equalsIgnoreCase(target.getHostName())){
                    return 300 * 1000;
                }else {
                    // otherwise keep alive for 30 seconds
                    return 30 * 1000;
                }
            }
        };

        // 创建httpClient
        return HttpClients.custom()
                //配置连接保持时间
                .setKeepAliveStrategy(myStrategy)
                // 把请求相关的超时信息设置到连接客户端
                .setDefaultRequestConfig(requestConfig)
                // 把请求重试设置到连接客户端
                .setRetryHandler(retry)
                // 配置连接池管理对象
                .setConnectionManager(connManager)
                .build();
    }
}

