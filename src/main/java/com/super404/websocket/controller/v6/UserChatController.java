package com.super404.websocket.controller.v6;

import com.super404.websocket.model.User;
import com.super404.websocket.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserChatController {

    @Autowired
    private WebSocketService ws;

    //模拟数据库用户的数据
    public static Map<String, String> userMap = new HashMap<>();
    static {
        userMap.put("jack","123");
        userMap.put("mary","456");
        userMap.put("tom","789");
        userMap.put("tim","000");
        userMap.put("小d","666");
    }

    //在线用户存储
    public static Map<String, User> onlineUser = new HashMap<>();
    static {
        onlineUser.put("123", new User("admin","888"));
    }


    /**
     * 用户登录
     * @return
     */
    @PostMapping(value = "login")
    public String userLogin(@RequestParam(value = "username", required = true) String username,
                            @RequestParam(value = "pwd", required = true) String pwd,
                            HttpSession session){
        String password = userMap.get(username);
        if (pwd.equals(password)){
            User user = new User(username,pwd);
            String sessionId = session.getId();
            onlineUser.put(sessionId,user);
            return "redirect:/v6/chat.html";
        } else {
            return "redirect:/v6/error.html";
        }

    }




}
