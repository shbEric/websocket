package com.super404.websocket.controller.v4;

import com.super404.websocket.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

/**
 * 实时推送服务器的JVM负载，已用内存等消息
 */
@Controller
public class V4ServerInfoController {

    @Autowired
    private WebSocketService ws;

    //被注解@Scheduled标记的方法，是不能有参数，不然会报错
    @MessageMapping("/v4/schedule/push")
    @Scheduled(fixedRate = 3000)
    public void sendServerInfo(){
        ws.sendServerInfo();
    }
}
