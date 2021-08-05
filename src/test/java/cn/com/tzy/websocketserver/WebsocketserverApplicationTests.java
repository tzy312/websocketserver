package cn.com.tzy.websocketserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

@SpringBootTest
class WebsocketserverApplicationTests {

    @Test
    void contextLoads() {
        TransactionManager transactionManagernew = new DataSourceTransactionManager();
    }

    public static void main(String[] args) {
        String url = "nihao/tzy";

        boolean contains = url.contains("\\/");
        boolean contains1 = url.contains("/");
        String[] split = url.split("\\/");
        System.out.println(split);

    }
}
