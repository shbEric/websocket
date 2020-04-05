package com.super404.websocket.service;

import com.super404.websocket.model.InMessage;
import com.super404.websocket.model.OutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * 简单消息模板，用来推送消息
 */
@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    public void sendTopicMessage(String destination,InMessage message) throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            Thread.sleep(500L);
            template.convertAndSend(destination, new OutMessage(message.getContent()+i));
        }

    }

    public void sendChatMessage(InMessage message) {
        template.convertAndSend("/chat/single/"+message.getTo(),
                new OutMessage(message.getFrom()+"发送："+message.getContent()));
    }


    /**
     * 获取系统信息，推送给客户端
     */
    public void sendServerInfo() {
        int processors = Runtime.getRuntime().availableProcessors();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();

        String message = String.format("服务器可用处理器：%s；虚拟机空闲内存大小：%s；最大内存大小：%s",
                processors,freeMemory,maxMemory);
        template.convertAndSend("/topic/server_info", new OutMessage(message));
    }
}
