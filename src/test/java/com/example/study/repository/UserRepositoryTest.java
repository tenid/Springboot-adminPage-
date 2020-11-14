package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entitiy.User;
import com.example.study.model.enumclass.UserStatus;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public class UserRepositoryTest extends StudyApplicationTests {

    @Autowired
    private  UserRepository userRepository;

    @Test
    public void create(){
        String account = "Test03";
        String password = "Test03";
        String status = "REGISTERED";
        String email = "Test01@gmail.com";
        String phoneNumber = "010-1111-3333";
        LocalDateTime registeredAt=  LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        User user = new User();

        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(UserStatus.REGISTERED);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);
        User newUser = userRepository.save(user);
        Assert.assertNotNull(newUser);


    }


    @Test
    @Transactional
    public void read(){
        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-2222");

        if(user!=null) {
            user.getOrderGroupList().stream().forEach(orderGroup -> {

                System.out.println("---------------------주문묶음---------------------");

                System.out.println("수령인 :" + orderGroup.getRevName());
                System.out.println("수령지 :" + orderGroup.getRevAddress());
                System.out.println("총금액 :" + orderGroup.getTotalPrice());
                System.out.println("총수량 :" + orderGroup.getTotalQuantity());
                System.out.println("---------------------주문상세---------------------");

                orderGroup.getOrderDetailList().stream().forEach(orderDetail -> {
                    System.out.println("파트너사이름 :"+orderDetail.getItem().getPartner().getName());
                    System.out.println("파트너사 카테고리 :"+orderDetail.getItem().getPartner().getCategory().getTitle());
                    System.out.println("주문의 상품 :"+orderDetail.getItem().getName());
                    System.out.println("고객센터 번호 :"+orderDetail.getItem().getPartner().getCallCenter());
                    System.out.println("주문의 상태 :"+orderDetail.getStatus());
                    System.out.println("도착예정일자 :"+orderDetail.getArrivalDate());



                });

            });
        }

        Assert.assertNotNull(user);



    }

}
