package com.base.backend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.*;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OkHttp3的工具类,封装网络请求的相关工具方法
 */
public class HttpUtils {
    private static final OkHttpClient okHttpClient = new OkHttpClient();

    public static Response execute(Request request) throws IOException {
        return okHttpClient.newCall(request).execute();
    }

    public static void enqueue(Request request, Callback responseCallback) {
        okHttpClient.newCall(request).enqueue(responseCallback);
    }

    public static void enqueue(Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public static String getStringResult(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            String respString = response.body().string();
            return respString;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    /**
     * 获取二维码图片
     *
     * @param requestURL    请求地址
     * @param params        请求参数
     * @param encodeCharset 编码字符集
     * @param decodeCharset 解码字符集
     * @return 远程主机响应正文
     * @throws UnsupportedEncodingException
     */
    public static void getQrCodeImag(String requestURL, Map<String, Object> params, File file, String encodeCharset,
                                            String decodeCharset) throws UnsupportedEncodingException {
        // 0.初始化参数
        requestURL = StringUtils.trimToEmpty(requestURL);
        params = (null == params) ? new HashMap<String, Object>() : params;
        encodeCharset = StringUtils.trimToEmpty(encodeCharset);
        encodeCharset = ("".equals(encodeCharset)) ? "UTF-8" : encodeCharset;
        decodeCharset = StringUtils.trimToEmpty(decodeCharset);
        decodeCharset = ("".equals(decodeCharset)) ? "UTF-8" : decodeCharset;
        // 1.设置POST方法
        // 1.1.创建POST方法
        HttpPost postMethod = new HttpPost(requestURL);
        postMethod.setHeader("Accept", "*/*");
        postMethod.addHeader(HTTP.CONTENT_TYPE, "application/json");
        postMethod.setHeader("Connection", "keep-alive");
        // 1.2..设置POST连接以及回应超时时间
        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH)
                .setSocketTimeout(50000).setConnectTimeout(50000).setConnectionRequestTimeout(50000).build();
        postMethod.setConfig(requestConfig);
        // 2.设置POST请求参数
        ObjectMapper mapper = new ObjectMapper();
        StringEntity se = null;
        try {
            se = new StringEntity(mapper.writeValueAsString(params));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        se.setContentType("application/json");
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "UTF-8"));
        postMethod.setEntity(se);
        // 3.发送请求
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // 将最大连接数增加到200
        cm.setMaxTotal(200);
        // 将每个路由基础的连接增加到20
        cm.setDefaultMaxPerRoute(20);
        // 将目标主机的最大连接数增加到50
        // HttpHost localhost = new HttpHost("www.yeetrack.com", 80);
        // cm.setMaxPerRoute(new HttpRoute(localhost), 50);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        PoolingHttpClientConnectionManager n = new PoolingHttpClientConnectionManager();
        CloseableHttpResponse clientResponse = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            clientResponse = httpClient.execute(postMethod);
            // 3.1.检查返回状态
            StatusLine statusLine = clientResponse.getStatusLine();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            } // END IF
            // 3.2.检查返回实体
            HttpEntity entity = clientResponse.getEntity();
            if (entity == null) {
                throw new ClientProtocolException("远程访问没有数据返回");
            } else if (entity.getContentLength() > 2147483647L) {
                throw new IllegalArgumentException("返回数据量太大，无法进行缓存");
            }
            //String jsonContent = HttpClientUtil.httpEntityToString( entity, decodeCharset );
            inputStream = entity.getContent();

            outputStream = new FileOutputStream( file );
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read( buf, 0, 1024 )) != -1) {
                outputStream.write( buf, 0, len );
            }
            outputStream.flush();
        } catch (ConnectTimeoutException cte) {
            System.out.println("与[" + requestURL + "]连接超时,自动返回空字符串");
        } catch (SocketTimeoutException ste) {
            System.out.println("与[" + requestURL + "]读取超时,自动返回空字符串");
        } catch (HttpResponseException e) {
            System.out.println(
                    "" + requestURL + "访问失败,状态码为" + e.getStatusCode() + ",原因为" + e.getMessage() + ",堆栈信息如下:");
        } catch (IllegalArgumentException e) {
            System.out.println("" + requestURL + "访问失败,原因为" + e.getMessage() + ",堆栈信息如下:");
        } catch (IOException e) {
            System.out.println("" + requestURL + "访问失败,原因为" + e.getMessage() + ",堆栈信息如下:");
        } catch (Exception e) {
            System.out.println("与[" + requestURL + "]通信过程中发生异常,堆栈信息如下:");
            e.printStackTrace();
        } finally {
            if (null != clientResponse) {
                try {
                    clientResponse.close();
                } catch (IOException e) {
                    System.out.println("关闭与[" + requestURL + "]的通信时发生异常,堆栈信息如下:");
                    e.printStackTrace();
                }
            } // END IF
        }
    }

    /**
     * 获取token
     *
     * @param requestURL    请求地址
     * @param params 请求参数
     * @param encodeCharset 编码字符集
     * @param decodeCharset 解码字符集
     * @return 远程主机响应正文
     */
    public static String getAccessToken(String requestURL, Map<String, Object> params, String encodeCharset,
                                        String decodeCharset) {
        // 0.初始化参数
        String accessToken = null;
        requestURL = StringUtils.trimToEmpty(requestURL);
        params = (null == params) ? new HashMap<String, Object>() : params;
        encodeCharset = StringUtils.trimToEmpty(encodeCharset);
        encodeCharset = ("".equals(encodeCharset)) ? "UTF-8" : encodeCharset;
        decodeCharset = StringUtils.trimToEmpty(decodeCharset);
        decodeCharset = ("".equals(decodeCharset)) ? "UTF-8" : decodeCharset;
        // 1.设置POST方法
        // 1.1.创建POST方法
        HttpPost postMethod = new HttpPost(requestURL);
        postMethod.setHeader("Accept", "*/*");
        // postMethod.setHeader( "Content-Type",
        // "application/x-www-form-urlencoded; charset=UTF-8" );
        postMethod.setHeader("Connection", "keep-alive");
        // 1.2..设置POST连接以及回应超时时间
        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH)
                .setSocketTimeout(50000).setConnectTimeout(50000).setConnectionRequestTimeout(50000).build();
        postMethod.setConfig(requestConfig);
        // 2.设置POST请求参数
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        // 3.发送请求
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // 将最大连接数增加到200
        cm.setMaxTotal(200);
        // 将每个路由基础的连接增加到20
        cm.setDefaultMaxPerRoute(20);
        // 将目标主机的最大连接数增加到50
        // HttpHost localhost = new HttpHost("www.yeetrack.com", 80);
        // cm.setMaxPerRoute(new HttpRoute(localhost), 50);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        PoolingHttpClientConnectionManager n = new PoolingHttpClientConnectionManager();
        CloseableHttpResponse clientResponse = null;
        try {
            HttpEntity formEntity = new UrlEncodedFormEntity(formParams, encodeCharset);
            postMethod.setEntity(formEntity);
            clientResponse = httpClient.execute(postMethod);
            // 3.1.检查返回状态
            StatusLine statusLine = clientResponse.getStatusLine();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            } // END IF
            // 3.2.检查返回实体
            HttpEntity entity = clientResponse.getEntity();
            if (entity == null) {
                throw new ClientProtocolException("远程访问没有数据返回");
            } else if (entity.getContentLength() > 2147483647L) {
                throw new IllegalArgumentException("返回数据量太大，无法进行缓存");
            }
            // 3.3.解析实体内容
            String jsonContent = HttpUtils.httpEntityToString(entity, decodeCharset);
            // 返回数据示例:jsonContent={"access_token":"61yolkrTnbCPA3Xb1KqpVf7wtr4WyntenDH5Avdx3gNx_UDoXDsxJ9721unpRELrqsar4wGEP5gsM0p-JZRDmM7vTsKzbcUwKORSV_J4LOwNKWaAGAWTF","expires_in":7200}
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> map = objectMapper.readValue(jsonContent, Map.class);
            accessToken = map.get("access_token");
        } catch (ConnectTimeoutException cte) {
            System.out.println("与[" + requestURL + "]连接超时,自动返回空字符串");
        } catch (SocketTimeoutException ste) {
            System.out.println("与[" + requestURL + "]读取超时,自动返回空字符串");
        } catch (HttpResponseException e) {
            System.out.println(
                    "" + requestURL + "访问失败,状态码为" + e.getStatusCode() + ",原因为" + e.getMessage() + ",堆栈信息如下:");
        } catch (IllegalArgumentException e) {
            System.out.println("" + requestURL + "访问失败,原因为" + e.getMessage() + ",堆栈信息如下:");
        } catch (IOException e) {
            System.out.println("" + requestURL + "访问失败,原因为" + e.getMessage() + ",堆栈信息如下:");
        } catch (Exception e) {
            System.out.println("与[" + requestURL + "]通信过程中发生异常,堆栈信息如下:");
            e.printStackTrace();
        } finally {
            if (null != clientResponse) {
                try {
                    clientResponse.close();
                } catch (IOException e) {
                    System.out.println("关闭与[" + requestURL + "]的通信时发生异常,堆栈信息如下:");
                    e.printStackTrace();
                }
            } // END IF
        }
        return accessToken;
    }

    /**
     * 将httpEntity内容转为字符串
     *
     * @param entity        httpEntity实体
     * @param decodeCharset 指定解码字符
     * @return
     * @throws IOException
     */
    public static String httpEntityToString(HttpEntity entity, String decodeCharset)
            throws IllegalStateException, IOException {
        decodeCharset = StringUtils.trimToEmpty(decodeCharset);
        decodeCharset = ("".equals(decodeCharset)) ? "UTF-8" : decodeCharset;
        StringBuffer content = new StringBuffer();
        Reader reader = null;
        InputStream instream = null;
        try {
            instream = entity.getContent();
            if (null == instream) {
                content = null;
            } else {
                // 1.选择解码字符集
                ContentType contentType = ContentType.getOrDefault(entity);
                Charset charset = ("".equals(decodeCharset)) ? null : Charset.forName(decodeCharset);
                charset = (null == charset) ? ((null == contentType) ? null : contentType.getCharset()) : charset;
                charset = (null == charset) ? HTTP.DEF_CONTENT_CHARSET : charset;
                // 2.读取内容
                reader = new InputStreamReader(instream, charset);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            }
        } catch (IllegalStateException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } // END IF
        }

        return content.toString();
    }

}
