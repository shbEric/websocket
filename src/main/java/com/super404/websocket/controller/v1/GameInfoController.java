package com.super404.websocket.controller.v1;

import com.super404.websocket.model.InMessage;
import com.super404.websocket.model.OutMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameInfoController {

    @MessageMapping("/v1/chat")
    @SendTo("/topic/game_chat")
    public OutMessage gameInfo(InMessage message){

        return new OutMessage(message.getContent());

    }

}
