package cn.com.tzy.websocketserver.conf;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
@Slf4j
@Aspect
@Component
public class AopConfig {
    @Pointcut(value = "execution(* cn.com.tzy.websocketserver.controller.*.*(..))")
    private void aopconf(){}
    @Around("aopconf()")
    public Object twiceAsOld(ProceedingJoinPoint joinPoint) {
        Object resultData=null;
        try {
            resultData=(Object)joinPoint.proceed();
        } catch (Throwable e) {
            //TODO 捕获错误异常
            log.error(e.toString());
        }
        return resultData;
    }

    private Object getWrongMessage(ProceedingJoinPoint joinPoint,Throwable e) {
        log.info(joinPoint.getSignature().toString(),e);

//        resultData.setMessage("获取数据错误"+"||||"+e.getMessage().toString());
//        resultData.setCode("500");
        return null;
    }
}
