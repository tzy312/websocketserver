package cn.com.tzy.websocketserver;

import cn.com.tzy.websocketserver.util.SpringContextUtil;
import lombok.Data;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
@MapperScan("cn.com.tzy.websocketserver.mapper")
@SpringBootApplication
public class WebsocketserverApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebsocketserverApplication.class, args);
    }
}
