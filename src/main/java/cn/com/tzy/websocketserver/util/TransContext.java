package cn.com.tzy.websocketserver.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransContext {
    private static final String HEAD = "HEAD";//消息头
    private static final String BODY = "BODY";//消息体
    private static final String HEAD_IN_PRE = "IN_PRE";//请入码
    private static final String HEAD_IN_END = "IN_END";//请入场景
    private Map<String,Object> context;//消息线
    private Map<String,Object> bodyParam;//消息体
    private Map<String,Object> headParam;//消息头
    /*
    * 创建新的数据线
    * */
    public TransContext(){
        this.context = new ConcurrentHashMap<String,Object>();
        this.bodyParam = new ConcurrentHashMap<String,Object>();
        this.headParam = new ConcurrentHashMap<String,Object>();
        this.context.put(BODY,this.bodyParam);
        this.context.put(HEAD,this.headParam);
    }
    /*
    * 初始化数据线
    * */
    public TransContext(Map<String,Object> map){
        this.bodyParam = map;
        this.context = new ConcurrentHashMap<String,Object>();
        this.headParam = new ConcurrentHashMap<String,Object>();
        this.context.put(BODY,this.bodyParam);
        this.context.put(HEAD,this.headParam);
    }
    /*
    * 获取数据线
    * */
    public Map<String, Object> getAllContext(){
        return this.context;
    }
    /*
    * 新增数据
    * */
    public void setContext(String key,Object value){
        this.bodyParam.put(key,value);
    }
    /*
    * 获取数据
    * */
    public Object getContext(String key){
        return this.bodyParam.get(key);
    }
    /*
    * 获取消息体
    * */
    public Object getBody(){
        return this.bodyParam;
    }
    /*
    * 初始化（重置）消息体
    * */
    private void setBody(Map<String,Object> body){
        this.context.put(BODY,body == null ? new ConcurrentHashMap<String,Object>() : body);
    }
    /*
    * 获取头消息
    * */
    public Object getHead(){
        return this.headParam;
    }
    /*
    * 初始化（重置）消息头
    * */
    public void setHead(Map<String,Object> head){
        this.context.put(HEAD,head == null ? new ConcurrentHashMap<String,Object>() : head);
    }
    /*
    * 请入码
    * */
    public void setHeadpPre(String pre){
        headParam.put(HEAD_IN_PRE,pre);
    }
    /*
    * 场景码
    * */
    public void setHeadpEnd(String end){
        headParam.put(HEAD_IN_END,end);
    }
}
