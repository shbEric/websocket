package com.super404.websocket.controller.v2;

import com.super404.websocket.model.InMessage;
import com.super404.websocket.model.OutMessage;
import com.super404.websocket.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class V2GameInfoController {

    @Autowired
    private WebSocketService ws;

    @MessageMapping("/v2/chat")
    public void gameInfo(InMessage message) throws InterruptedException {

        ws.sendTopicMessage("/topic/game_rank", message);

    }

}
