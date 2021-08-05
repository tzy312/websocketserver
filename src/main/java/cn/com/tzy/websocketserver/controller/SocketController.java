package cn.com.tzy.websocketserver.controller;

import cn.com.tzy.websocketserver.mapper.MapperImp;
import cn.com.tzy.websocketserver.server.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class SocketController {
    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    private MapperImp mapperImp;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/webSocket")
    public ModelAndView socket() {
        Map<String, String> map = new HashMap<>();
        map.put("ID","117860");
       // Map<String, Object> user = mapperImp.getUser(map);
       // log.info(user.toString());
        ModelAndView mav=new ModelAndView("/webSocket");
        log.info("boss yang is show");
        return mav;
    }
}
