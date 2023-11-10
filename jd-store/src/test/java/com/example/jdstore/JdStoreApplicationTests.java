package com.example.jdstore;

import com.store.jdstore.JdStoreApplication;
import com.store.jdstore.dto.OrderDto;
import com.store.jdstore.entity.HanmaOrderDetailEntity;
import com.store.jdstore.service.OrderService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = JdStoreApplication.class)
class JdStoreApplicationTests {

    @Resource
    private OrderService orderService;

    @Test
    void contextLoads() {
        OrderDto orderDto = new OrderDto();

        orderDto.setUserId(1L);
        orderDto.setUserName("aaa");
        orderDto.setUserPhone("12312312341");
        orderDto.setUserAddress("safdsa");
        orderDto.setUserOpenid("asdas");
        orderDto.setShopId(1L);
        HanmaOrderDetailEntity h = new HanmaOrderDetailEntity();
        h.setProductId(1L);
        h.setQuantity(2L);
        List<HanmaOrderDetailEntity> l = new ArrayList<>();
        l.add(h);
        orderDto.setDetailList(l);

        System.out.println(orderService.orderAdd(orderDto));
    }

}
