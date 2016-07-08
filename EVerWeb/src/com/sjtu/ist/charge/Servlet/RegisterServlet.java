package com.sjtu.ist.charge.Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.Service.UserService;
import com.sjtu.ist.charge.Util.RedisUtil;
import com.sjtu.ist.charge.Util.RequestUtil;
import com.sjtu.ist.charge.Util.TokenGenrerate;

import redis.clients.jedis.Jedis;


/**
 * 用户注册 url:/api/account/register.servlet method : post
 * 
 * @author lizheming,huangshunting
 * 
 */
public class RegisterServlet extends BaseServlet {
    Map<String, String> telephone_code = new HashMap<String, String>();// 手机对应的生成的二维码

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        resp.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String payload = RequestUtil.getRequestBody(req);

        try {
            JSONObject jsonObject = new JSONObject(payload);
            String telephone = jsonObject.getString("telephone");
            String type = jsonObject.getString("type");
            UserService userService = (UserService) getCtx().getBean("userService");
            // 如果是获取验证码
            if ("0".equals(type)) {
                // 当前电话号码已注册
                if (userService.getUserByTel(telephone) != null) {
                    Map<String, Object> dataMap = new HashMap<String, Object>();
                    dataMap.put("data", "telphoneAlreadyRegister");
                    resp.setStatus(HttpStatus.OK.value());
                    resp.getWriter().println(new JSONObject(dataMap));
                } else {
                    // telephone_code = createMsg(telephone);
                    telephone_code.put(telephone, "111");
                }

            } else {
                String password = jsonObject.getString("password");
                String username = jsonObject.getString("username");
                String code = jsonObject.getString("code");
                // 验证码正确
                if (code.equals(telephone_code.get(telephone))) {
                    User user = new User(username, password, telephone, 1, 0, 0.0);
                    userService.register(user);
                    Jedis jedis = RedisUtil.getJedis();
                    String token = TokenGenrerate.getToken(username);
                    jedis.set(token, user.getId() + "");
                    JSONObject resObject = new JSONObject();
                    resObject.put("data", "codeTrue");
                    resp.getWriter().println(resObject);
                    resp.setStatus(HttpStatus.OK.value());
                } else {
                    Map<String, Object> dataMap = new HashMap<String, Object>();
                    dataMap.put("data", "codeFalse");
                    resp.setStatus(HttpStatus.OK.value());
                    resp.getWriter().println(new JSONObject(dataMap));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("error");
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

}
