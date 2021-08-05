package cn.com.tzy.websocketserver.service.impl;

import cn.com.tzy.websocketserver.service.IServiceDeal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class TzyShould implements IServiceDeal {
    @Override
    public String excute(String msg) {
        log.info("TZYBEAN");
        return  null;
    }
}
