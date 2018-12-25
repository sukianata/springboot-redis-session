package com.sukianata.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：huangfan
 * @ Date       ：10:10 2018/12/25
 */
public class AccountUtil {

    private static Logger logger= LogManager.getLogger(AccountUtil.class);

    private static final String AUTH_URL="http://localhost:8080/loginController/auth";//该地址可放到数据库中
    /**
     * 该方法用来跳转到Ⅰ期项目里进行登录认证
     * @param request
     */
    public static String postAuth(HttpServletRequest request){
        /**
         * 其中httpSessionStrategy默认使用的是CookieHttpSessionStrategy
         * 所以需要伪造Header中的cookie
         */
        /**
         * 设置cookie 参考代码：
         ====================================start============================================================
         CookieStore cookieStore=new BasicCookieStore();
         Cookie[] cookies=request.getCookies();
         for (Cookie cookie:cookies){
         BasicClientCookie basicClientCookie=new BasicClientCookie(cookie.getName(),cookie.getValue());
         cookieStore.addCookie(basicClientCookie);
         basicClientCookie.setVersion(0);
         basicClientCookie.setDomain("/pms/");   //设置范围
         basicClientCookie.setPath("/");
         }
         CloseableHttpClient client= HttpClients.custom().setDefaultCookieStore(cookieStore).build();
         ====================================end===============================================================
         */

        CloseableHttpClient client= HttpClients.createDefault();
        HttpPost httpPost=new HttpPost(AUTH_URL);
        httpPost.addHeader("Cookie",request.getHeader("Cookie"));
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        list.add(new BasicNameValuePair("sessionId", request.getSession().getId()));  //请求参数
        UrlEncodedFormEntity entity = null;
        String response="";
        try {
            entity = new UrlEncodedFormEntity(list,"UTF-8");
            httpPost.setEntity(entity);
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {


                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
//                        throw new ClientProtocolException("Unexpected response status: " + status);
                        return "FAILED:"+status;
                    }
                }

            };
            response= client.execute(httpPost,responseHandler);
            System.out.println(response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
        return response;
    }
}
