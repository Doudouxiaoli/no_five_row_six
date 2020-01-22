package com.wx.no_five_row_six.common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wx.common.spring.mvc.WebUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;

public class SaveBufferImageUtil {

    // 生成的二维码为正方形
    public static String createQRCodeUrl = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=";// POST
    // 生成的二维码为圆形
    public static String getwxacode = "https://api.weixin.qq.com/wxa/getwxacode?access_token=";// POST
    public static String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";// GET

    private final static Logger log = LoggerFactory.getLogger(SaveBufferImageUtil.class);
    private static final String timeout = "60000";
    private static final int MIN_ERROR_REQUEST_SPEND_TIME = 200;
    private RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000)
            .setConnectTimeout(3000)
            .setConnectionRequestTimeout(15000)
            .build();

    private static SaveBufferImageUtil instance = null;

    private SaveBufferImageUtil() {
    }

    public static SaveBufferImageUtil getInstance() {
        if (instance == null) {
            instance = new SaveBufferImageUtil();
        }
        return instance;
    }

    public String sendHttpPost4Json(String fileName, String url, String accessToken) {
        HttpPost httpPost = null;
        try {
            String httpUrl = getwxacode + accessToken;
            httpPost = new HttpPost(httpUrl);// 创建httpPost
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("path", "pages/webView/webView?url=" + URLEncoder.encode(url, "UTF-8"));
            jsonObject.addProperty("width", 280);
            //设置参数
            StringEntity stringEntity = new StringEntity(jsonObject.toString(), "UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost, fileName);
    }

    /**
     * 发送Post请求
     *
     * @param httpPost
     * @return
     */
    private String sendHttpPost(HttpPost httpPost, String fileName) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String savePath = "";
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            if (entity != null) {
                if (entity.getContentType().toString().equals("Content-Type: image/jpeg")) {
                    savePath = savePic(fileName, entity);
                } else if (entity.getContentType().toString().equals("Content-Type: application/json; encoding=utf-8")) {
                    String errorContent = EntityUtils.toString(entity, "UTF-8");
                    JsonObject obj = new JsonParser().parse(errorContent).getAsJsonObject();
                    if (obj.get("errcode").getAsInt() == 42001) {
                        savePath = "error-小程序二维码保存失败，请重新保存,accesstoken失效";
                    } else {
                        savePath = "error-小程序二维码保存失败:" + errorContent;
                    }
                } else {
                    savePath = "error-小程序二维码保存失败:" + EntityUtils.toString(entity, "UTF-8");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return savePath;
    }


    /**
     * 将流 保存为数据数组
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        // 每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        // 使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        // 关闭输入流
        inStream.close();
        // 把outStream里的数据写入内存
        return outStream.toByteArray();
    }
    // 获取文件后缀
//    private String getSuffix(String filename) {
//        String suffix = "";
//        int pos = filename.lastIndexOf('.');
//        if (pos > 0 && pos < filename.length() - 1) {
//            suffix = filename.substring(pos + 1);
//        }
//        return suffix;
//    }

    public String savePic(String fileName, HttpEntity entity) {
        String root = WebUtil.getRealPath(Const.PIC_PATH_SHARECODE);
        //重命名
//        String ext = this.getSuffix(fileName);
//        fileName = System.currentTimeMillis() + "." + ext;
        fileName = System.currentTimeMillis() + "_" + fileName;
        String savePath = root + fileName;
        File target = new File(root);
        if (!target.exists()) {
            target.mkdirs();
        }
        byte[] data = null;
        //new一个文件对象用来保存图片
        File imageFile = new File(savePath);
        //创建输出流
        FileOutputStream outStream = null;
        try {
            data = readInputStream(entity.getContent());
            outStream = new FileOutputStream(imageFile);
            //写入数据
            outStream.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭输出流
                outStream.close();
                return Const.PIC_PATH_SHARECODE + fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 发送 get请求
     *
     * @param httpUrl
     */
    public String sendHttpGet(String httpUrl) {
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        return sendHttpGet(httpGet);
    }

    /**
     * 发送 get请求Https
     *
     * @param httpUrl
     */
    public String sendHttpsGet(String httpUrl) {
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        return sendHttpsGet(httpGet);
    }

    /**
     * 发送Get请求
     *
     * @param httpGet
     * @return
     */
    private String sendHttpGet(HttpGet httpGet) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 发送Get请求Https
     *
     * @param httpGet
     * @return
     */
    private String sendHttpsGet(HttpGet httpGet) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(httpGet.getURI().toString()));
            DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
            httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }
}
