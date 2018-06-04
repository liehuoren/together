package com.zhlzzz.together.websocket;

import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserNotFoundException;
import com.zhlzzz.together.user.UserRepository;
import com.zhlzzz.together.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@ServerEndpoint(value = "/websocket/{openId}/{roomId}")
@Component
public class WebSocketServer {

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * 存放对应的userId为键的CopyOnWriteArraySet<WebSocketServer>集合
     */
    private static Hashtable<String, CopyOnWriteArraySet<WebSocketServer>> webSocketSetTable = new Hashtable<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 连接建立成功调用的方法
     *
     * @param openId  openId
     * @param session session
     */
    @OnOpen
    public void onOpen(@PathParam("openId") String openId, @PathParam("roomId") String roomId, Session session) {
//        User user = userRepository.findByOpenId(openId).orElseThrow(() -> new UserNotFoundException("openId"));

        this.session = session;
        log.debug("uuid为" + openId + "的用户进来了！！！");
        CopyOnWriteArraySet<WebSocketServer> webSocketSet = webSocketSetTable.get(openId);
        if (null == webSocketSet) {
            webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
        }
        webSocketSet.add(this);
        webSocketSetTable.put(openId, webSocketSet);
        addOnlineCount();           //在线数加1
        ChatMessage chatMessage = new ChatMessage(ChatMessage.Type.people, 1L, "欢迎");
        JSONObject json = JSONObject.fromObject(chatMessage);
        String message = json.toString();
        try {
            sendMessageByUserId(openId,message);
        } catch (IOException e) {

        }

        log.debug("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     *
     * @param openId openId
     */
    @OnClose
    public void onClose(@PathParam("openId") String openId) {
        //从set中删除
        CopyOnWriteArraySet<WebSocketServer> webSocketServers = webSocketSetTable.get(openId);
        webSocketServers.remove(this);
        //在线数减1
        subOnlineCount();
        log.debug("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        log.info(message);
    }

    /**
     * 发生错误时产生的动作
     *
     * @param error error
     */
    @OnError
    public void onError(Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 发送信息
     *
     * @param message message
     * @throws IOException IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 对指定的用户发送信息
     *
     * @param userId  userId
     * @param message message
     * @throws IOException IOException
     */
    public static void sendMessageByUserId(String userId, String message) throws IOException {
        Set<WebSocketServer> socketServers = webSocketSetTable.get(userId);
        if (null != socketServers) {
            for (WebSocketServer item : socketServers) {
                try {
                    item.sendMessage(message);
                } catch (IOException e) {
                    continue;
                }
            }
        }
    }

    /**
     * 获取当前在线人数
     *
     * @return int
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 在线人数加一
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * 统计在线人数减一
     */
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
