package com.sukianata.controller;

import com.alibaba.fastjson.JSONObject;
import com.sukianata.constant.AuthConstant;
import com.sukianata.constant.ResponseContentType;
import com.sukianata.util.AccountUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ Author     ：huangfan
 * @ Date       ：14:29 2018/12/24
 * @ Description：login
 */
@Controller
public class LoginController {

    private static Logger logger = LogManager.getLogger(LoginController.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/")
    public String index(Model model, HttpServletResponse response) {
        return "index";
    }

    /**
     * 接收跨域认证请求
     *
     * @param request
     */
    @RequestMapping("/auth")
    public void auth(HttpServletRequest request, HttpServletResponse response) {
        /**
         * 获取参数
         * 第一个参数必须为动作类型，默认为login
         */
        String sessionId = request.getParameter("sessionId");
        HttpSession session = request.getSession();
        /**
         * 此时无法仍无法获取到session的属性值,需要请求从浏览器那边发过来才可以获取到
         */
        String newSessionId = session.getId();
        System.out.println("sessionId from springboot：" + newSessionId);
        System.out.println("sessionId from springmvc:" + sessionId);

        /**
         * I 期系统 ---A系统
         * II 期系统---B 系统
         * A系统登录后，进入B系统的两种方式
         *      1.从A中直接点开进入B，此时B无需登录
         *      2.A已经登录，从浏览器直接输入B的地址，B是否还需要登录？
         *      3. A,B都登录后，假如A退出，B是否需要退出？反之亦有同样疑问。
         * 目前实现的方案是是基于session共享的方案,
         *  （该方案是A,B共享一个Session,若一个退出则session会删除，另一个也需要重新登录）
         *  目前满足A系统登录后，B免登录进入，但若疑问2的答案不是否(即A登录后，输入B的网址，B仍需重新登录，则该方案不适用
         *         若疑问3的答案是若要保证一个退出另一个不退出，则该方案也不适用
         *   单点登录或session共享
         */

       /* switch (action){
            case "login":
                doLogin();
                break;
            case "logout":
                doLogout();
                break;
        }*/

        /**
         * 此处根据认证结果来响应认证请求
         */
        String result = "";
        if (newSessionId.equals(sessionId)){
            result= AuthConstant.SUCCESS;
        }else {
            result=AuthConstant.FAILED;
        }
        outResponse(response, result, ResponseContentType.JSON);
    }

    @RequestMapping("/doLogin")
    public String login(HttpServletRequest request, ModelMap map){
        HttpSession session=request.getSession();
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        Assert.notNull(username,"username cannot be null");
        Assert.notNull(password,"password cannot be null");
        if (username.equals("springboot")&&password.equals("springboot")){
            session.setAttribute("username",username);
            session.setAttribute("password",password);
        }
        map.put("username",username);
        String result=AccountUtil.postAuth(request);
        if (result=="failed"){
            logger.error("cannot auth another system ,user may need to relogin");
        }
        return "loginSuccess";
    }

    @RequestMapping("/getSession")
    public String getSession(HttpServletRequest request, ModelMap map) {
        String username = (String) request.getSession().getAttribute("username");
        map.put("username",username);
        System.out.println(">>:" + username);
        return "loginSuccess";
    }


    private void doLogin() {

    }

    private void doLogout() {

    }

    private void outResponse(HttpServletResponse response, Object object,String type) {
        //将实体对象转换为JSON Object转换
        Object responseJSONObject = JSONObject.toJSON(object);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(type);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(responseJSONObject.toString());
            out.flush();
            logger.debug(responseJSONObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
