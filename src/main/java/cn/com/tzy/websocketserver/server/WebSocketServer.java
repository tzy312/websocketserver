package cn.com.tzy.websocketserver.server;

import cn.com.tzy.websocketserver.service.IServiceDeal;
import cn.com.tzy.websocketserver.util.SpringContextUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/*
* 核心类
* */
@Slf4j
@Component
@ServerEndpoint("/tzySocket/{sid}")
public class WebSocketServer {
    private static final String HEAD = "HEAD";//消息头
    private static final String BODY = "BODY";//消息体
    private static final String HEAD_IN_PRE = "IN_PRE";//请入码
    private static final String HEAD_IN_END = "IN_END";//请入场景

    @Autowired
    private  ApplicationContext applicationContext;
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineNum = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    //发送消息
    public void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            synchronized (session) {
                log.info("测试todo{}"+message);

                String txt = "你的名字我的姓氏"+message;
                //TODO 获取消息头里面的请求转发名
                Map<String,Object> map = (Map<String, Object>) JSONObject.parseObject(message);
                //请入消息空值处理
                if(MapUtils.isEmpty(map) //消息不为空
                        || MapUtils.isEmpty((Map<String,Object>)map.get(HEAD))//头不为空
                            || MapUtils.isEmpty((Map<String,Object>)map.get(BODY)))//消息体不为空
                {
                    Map<String, Object> hashMap = new HashMap<>();
                    hashMap.put("HEAD",new HashMap<String,Object>().put("MSG","请求消息解析失败"));
                    session.getBasicRemote().sendText(JSON.toJSONString(hashMap));
                }

                ApplicationContext context = SpringContextUtil.getApplicationContext();
                String returnData = "";
                try {
                    //TODO 创建反射类进行消息处理
                    Map<String, Object> head = (Map<String, Object>) map.get(HEAD);
                    String start = (String) head.get(HEAD_IN_PRE);
                    String end = (String) head.get(HEAD_IN_END);
                    String beanCode = start +"_"+ end;
                    Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("bean.properties"));
                    if (StringUtils.isEmpty(properties.getProperty(beanCode))){
                      log.error("交易未配置或交易不存在");
                      session.getBasicRemote().sendText("交易未配置或交易不存在");
                      return;
                    }
                    log.info("实例beanCode:" + beanCode);
                    String beanName = properties.get(beanCode).toString();
                    log.info("实例beanName:" + beanName);
                    IServiceDeal bean = context.getBean(beanName, IServiceDeal.class);
                    returnData = bean.excute("NIHAO");
                } catch (Exception e){
                    log.error("初始化引用上下文失败，请检查！",e);

                }
                log.info(returnData);
                session.getBasicRemote().sendText(txt);
            }
        }

    }
    //给指定用户发送信息
    public void sendInfo(String userName, String message){
        Session session = sessionPools.get(userName);
        try {
            sendMessage(session, message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String userName){
        //用户渠道校验，校验用户是否为准入用户
        //checkUserChnl();
        sessionPools.put(userName, session);
        addOnlineCount();
        System.out.println(userName + "加入webSocket！当前人数为" + onlineNum);
//        try {
//            log.info(userName);
//            sendMessage(session, "欢迎" + userName + "加入连接！");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam(value = "sid") String userName){
        sessionPools.remove(userName);
        subOnlineCount();
        System.out.println(userName + "断开webSocket连接！当前人数为" + onlineNum);
    }

    //收到客户端信息
    @OnMessage
    public void onMessage(String message) throws IOException{
  //      message = "客户端：" + message + ",已收到";
        System.out.println(message);
        for (Session session: sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount(){
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

}
