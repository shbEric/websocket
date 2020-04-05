package com.super404.websocket.controller.v3;

import com.super404.websocket.model.InMessage;
import com.super404.websocket.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 *  简单版单人聊天
 */
@Controller
public class V3ChatRoomController {
    @Autowired
    private WebSocketService ws;

    @MessageMapping("/v3/single/chat")
    public void singleChat(InMessage message) {
        ws.sendChatMessage(message);
    }

}
