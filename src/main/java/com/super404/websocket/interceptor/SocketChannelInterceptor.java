package com.super404.websocket.interceptor;

import com.super404.websocket.controller.v6.UserChatController;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * 频道拦截器，类似管道，可以获取消息的一些meta（元）数据
 */
public class SocketChannelInterceptor implements ChannelInterceptor {

    /**
     * 在消息被实际发送到频道之前调用
     *
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("SocketChannelInterceptor->preSend");
        return message;
    }

    /**
     * 发送消息调用后立即调用
     *
     * @param message
     * @param channel
     * @param sent
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        System.out.println("SocketChannelInterceptor->postSend");

        //消息头访问器
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        //避免非stomp消息类型，例如心跳检测
        if (headerAccessor.getCommand() == null) return;
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        System.out.println("SocketChannelInterceptor -> sessionId=" + sessionId);

        switch (headerAccessor.getCommand()){
            case CONNECT:
                connect(sessionId);
                break;
            case DISCONNECT:
                disconnect(sessionId);
                break;
            case SUBSCRIBE:
                break;
            case UNSUBSCRIBE:
                break;
            default:
                break;
        }

    }

    /**
     * 在完成发送之后进行调用，不管是否有异常发生，一般用于资源清理
     *
     * @param message
     * @param channel
     * @param sent
     * @param ex
     */
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        System.out.println("SocketChannelInterceptor->afterSendCompletion");
    }

    //连接成功
    private void connect(String sessionId){
        System.out.println("connect sessionId="+sessionId);
    }


    //断开连接
    private void disconnect(String sessionId){
        System.out.println("disconnect sessionId="+sessionId);
        //用户下线操作
        UserChatController.onlineUser.remove(sessionId);
    }

}
