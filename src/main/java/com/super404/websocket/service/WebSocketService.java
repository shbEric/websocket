package com.super404.websocket.service;

import com.super404.websocket.model.InMessage;
import com.super404.websocket.model.OutMessage;
import com.super404.websocket.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    /**
     * v5版本，股票信息推送
     */
    public void sendStockInfo() {
        Map<String, String> stockInfo = StockService.getStockInfo();
        String msgTpl = "名称: %s ; 价格: %s元 ; 最高价: %s ; 最低价: %s ; 涨跌幅: %s";

        if (null != stockInfo) {
            String msg = String.format(msgTpl, stockInfo.get("prod_name"), stockInfo.get("last_px"),
                    stockInfo.get("high_px"), stockInfo.get("low_px"), stockInfo.get("px_change"));

            template.convertAndSend("/topic/stock_info",new OutMessage(msg));
        }
    }

    /**
     * 发送在线用户
     * @param onlineUser
     */
    public void sendOnlineUser(Map<String, User> onlineUser) {
        String msg="";
        for (Map.Entry<String, User> entry: onlineUser.entrySet()){
            msg = msg.concat(entry.getValue().getUsername() + " || ");
        }
        template.convertAndSend("/topic/onlineuser", new OutMessage(msg));

    }
}
