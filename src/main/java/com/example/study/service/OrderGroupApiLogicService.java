package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entitiy.OrderGroup;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.OrderGroupApiRequest;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.repository.OrderGroupRepository;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderGroupApiLogicService extends BaseService<OrderGroupApiRequest, OrderGroupApiResponse,OrderGroup> {


    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<OrderGroupApiResponse> create(Header<OrderGroupApiRequest> request) {
        OrderGroupApiRequest orderGroupApiRequest = request.getData();

        OrderGroup orderGroup = OrderGroup.builder()
                                    .orderAt(orderGroupApiRequest.getOrderAt())
                                    .orderType(orderGroupApiRequest.getOrderType())
                                    .paymentType(orderGroupApiRequest.getPaymentType())
                                    .revName(orderGroupApiRequest.getRevName())
                                    .status(orderGroupApiRequest.getStatus())
                                    .revAddress(orderGroupApiRequest.getRevAddress())
                                    .totalQuantity(orderGroupApiRequest.getTotalQuantity())
                                    .totalPrice(orderGroupApiRequest.getTotalPrice())
                                    .arrivalDate(orderGroupApiRequest.getArrivalDate())
                                    .user(userRepository.getOne(orderGroupApiRequest.getUser_id()))
                                    .build();

        OrderGroup newOrderGroup=baseRepository.save(orderGroup);
        return response(newOrderGroup);
    }

    @Override
    public Header<OrderGroupApiResponse> read(Long id) {

        return baseRepository.findById(id)
                .map(findOrderGroup -> response(findOrderGroup))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<OrderGroupApiResponse> update(Header<OrderGroupApiRequest> request) {

        OrderGroupApiRequest body = request.getData();

        Optional<OrderGroup> orderGroup = baseRepository.findById(body.getId());

        return orderGroup.map(findOrderGroup->{
                findOrderGroup  .setStatus(body.getStatus())
                                .setTotalQuantity(body.getTotalQuantity())
                                .setTotalPrice(body.getTotalPrice())
                                .setArrivalDate(body.getArrivalDate())
                                .setOrderAt(body.getOrderAt())
                                .setPaymentType(body.getPaymentType())
                                .setRevName(body.getRevName())
                                .setRevAddress(body.getRevAddress())
                                .setOrderType(body.getOrderType())
                                .setUser(userRepository.getOne(body.getUser_id()));
                return findOrderGroup;
        }).map(findOrderGroup -> {
            baseRepository.save(findOrderGroup);
            return response(findOrderGroup);
        }).orElseGet(()->Header.ERROR("데이터 없음"));

    }

    @Override
    public Header delete(Long id) {
        Optional<OrderGroup> orderGroup = baseRepository.findById(id);

        return orderGroup.map(orderGroup1 ->{
            baseRepository.delete(orderGroup1);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("데이터 없음"));

    }

    public Header<OrderGroupApiResponse> response(OrderGroup orderGroup){
        OrderGroupApiResponse orderGroupApiResponse = OrderGroupApiResponse.builder()
                                                                .id(orderGroup.getId())
                                                                .arrivalDate(orderGroup.getArrivalDate())
                                                                .orderAt(orderGroup.getOrderAt())
                                                                .orderType(orderGroup.getOrderType())
                                                                .paymentType(orderGroup.getPaymentType())
                                                                .revAddress(orderGroup.getRevAddress())
                                                                .revName(orderGroup.getRevName())
                                                                .status(orderGroup.getStatus())
                                                                .totalPrice(orderGroup.getTotalPrice())
                                                                .totalQuantity(orderGroup.getTotalQuantity())
                                                                .user_id(orderGroup.getUser().getId())
                                                                .build();

        return Header.OK(orderGroupApiResponse);

    }


}
