package com.super404.websocket.controller.v5;

import com.super404.websocket.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

/**
 * 股票推送controller
 */
@Controller
public class V5StockInfoController {

    @Autowired
    private WebSocketService ws;

    //只进行推送的controller不用加 “@MessageMapping”注解，@MessageMapping是用于客户端发送数据到服务端的路由配置
    //@MessageMapping("/v5/schedule/push_stock")
    @Scheduled(fixedRate = 5000)
    public void stockInfo(){
        ws.sendStockInfo();
    }

}
