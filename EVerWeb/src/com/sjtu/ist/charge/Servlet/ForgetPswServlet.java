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

import com.sjtu.ist.charge.Dto.json.UserDto;
import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.Service.UserService;
import com.sjtu.ist.charge.Util.RedisUtil;
import com.sjtu.ist.charge.Util.RequestUtil;
import com.sjtu.ist.charge.Util.TokenGenrerate;

import redis.clients.jedis.Jedis;


/**
 * 忘记密码 url : /api/account/forgetpsw.servlet method : post
 * 
 * @author lizheming
 * 
 */
public class ForgetPswServlet extends BaseServlet {
    Map<String, String> telephone_codeMap = new HashMap<String, String>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String payloadRequest = RequestUtil.getRequestBody(request);
        try {
            JSONObject jsonObject = new JSONObject(payloadRequest);
            String type = jsonObject.getString("type");
            String telephone = jsonObject.getString("telephone");
            // 如果是获取手机验证码
            if (type.equals("0")) {
                //telephone_codeMap = createMsg(telephone);
            	telephone_codeMap.put(telephone, "111");
            }
            // 验证验证码
            else if (type.equals("1")) {
                String code = jsonObject.getString("code");
                Map<String, Object> dataMap = new HashMap<String, Object>();
                // 验证码正确
                if (code.equals(telephone_codeMap.get(telephone))) {
                    dataMap.put("validation", "correct");
                    response.setStatus(HttpStatus.OK.value());
                    response.getWriter().println(new JSONObject(dataMap));
                } else {
                    dataMap.put("validation", "error");
                    response.setStatus(HttpStatus.NOT_MODIFIED.value());
                    response.getWriter().println(new JSONObject(dataMap));
                }
            }
            // 修改验证码
            else {
                String password = jsonObject.getString("password");
                UserService userService = (UserService) getCtx().getBean("userService");
                if (!userService.updatePswByTel(telephone, password)) {
                    response.setStatus(HttpStatus.NOT_MODIFIED.value());
                } else {
                    User user = userService.getUserByTel(telephone);
                    String username = user.getUsername();
                    System.err.println("username:" + username);
                    Jedis jedis = RedisUtil.getJedis();
                    String token = TokenGenrerate.getToken(username);
                    // jedis.expire(token, 60 * 60 * 24 * 1);
                    jedis.set(token, user.getId() + "");

                    UserDto dto = new UserDto(user);
                    JSONObject jsDto = new JSONObject(dto);
                    Map<String, Object> dataMap = new HashMap<String, Object>();
                    dataMap.put("token", token);
                    dataMap.put("data", jsDto);
                    response.setStatus(HttpStatus.OK.value());
                    response.getWriter().println(new JSONObject(dataMap));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.NOT_MODIFIED.value());
        }
    }
}
