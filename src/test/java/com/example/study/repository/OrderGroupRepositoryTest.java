package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entitiy.OrderGroup;
import com.example.study.model.enumclass.OrderType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class OrderGroupRepositoryTest extends StudyApplicationTests {
    @Autowired
    OrderGroupRepository orderGroupRepository;
    @Autowired
    UserRepository userRepository;



    @Test
    public void create(){
        OrderGroup orderGroup = new OrderGroup();
        orderGroup.setStatus("COMPLETE");
        orderGroup.setOrderType(OrderType.ALL);
        orderGroup.setRevAddress("부천시 오정구");
        orderGroup.setRevName("정준영");
        orderGroup.setPaymentType("CARD");
        orderGroup.setTotalPrice(BigDecimal.valueOf(900000));
        orderGroup.setTotalQuantity(1);
        orderGroup.setOrderAt(LocalDateTime.now().minusDays(2));
        orderGroup.setArrivalDate(LocalDateTime.now());
        orderGroup.setCreatedAt(LocalDateTime.now());
        orderGroup.setCreatedBy("AdminServer");
        orderGroup.setUser(userRepository.getOne(1L));


//        orderGroup.setUserId(1L);

        OrderGroup newOrderGroup = orderGroupRepository.save(orderGroup);
        Assert.assertNotNull(newOrderGroup);
    }

    @Test
    public void read(){

        Optional<OrderGroup> orderGroup = orderGroupRepository.findById(1L);
        System.out.println(orderGroup);



    }

}
