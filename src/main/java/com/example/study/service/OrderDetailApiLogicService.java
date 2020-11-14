package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entitiy.OrderDetail;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.OrderDetailApiRequest;
import com.example.study.model.network.response.OrderDetaiApilResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.OrderDetailRepository;
import com.example.study.repository.OrderGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDetailApiLogicService extends BaseService<OrderDetailApiRequest, OrderDetaiApilResponse,OrderDetail> {

    @Autowired
    private OrderGroupRepository orderGroupRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Header<OrderDetaiApilResponse> create(Header<OrderDetailApiRequest> request) {
        OrderDetailApiRequest body= request.getData();
        OrderDetail orderDetail = OrderDetail.builder()
                                    .status(body.getStatus())
                                    .arrivalDate(body.getArrivalDate())
                                    .orderGroup(orderGroupRepository.getOne(body.getOrderGroupId()))
                                    .item(itemRepository.getOne(body.getItemID()))
                                    .quantity(body.getQuantity())
                                    .totalPrice(body.getTotalPrice())
                                    .build();
        OrderDetail newOrderDetail1 = baseRepository.save(orderDetail);

        return response(newOrderDetail1);
    }

    @Override
    public Header<OrderDetaiApilResponse> read(Long id) {
        Optional<OrderDetail> orderDetail = baseRepository.findById(id);

        return orderDetail.map(newOrderDetail-> response(newOrderDetail)).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<OrderDetaiApilResponse> update(Header<OrderDetailApiRequest> request) {
        OrderDetailApiRequest body = request.getData();
        Optional<OrderDetail> newOrderDetail =baseRepository.findById(body.getId());

        return newOrderDetail.map(orderDetail -> {

            orderDetail.setStatus(body.getStatus())
                        .setArrivalDate(body.getArrivalDate())
                        .setItem(itemRepository.getOne(body.getItemID()))
                        .setQuantity(body.getQuantity())
                        .setTotalPrice(body.getTotalPrice())
                        .setOrderGroup(orderGroupRepository.getOne(body.getOrderGroupId()));
            baseRepository.save(orderDetail);
            return response(orderDetail);
        }).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<OrderDetail> orderDetail = baseRepository.findById(id);

        return orderDetail.map(newOrderDetail-> {
            baseRepository.delete(newOrderDetail);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    public Header<OrderDetaiApilResponse> response(OrderDetail orderDetail) {

        OrderDetaiApilResponse body = OrderDetaiApilResponse.builder()
                .arrivalDate(orderDetail.getArrivalDate())
                .id(orderDetail.getId())
                .itemID(orderDetail.getId())
                .orderGroupId(orderDetail.getId())
                .status(orderDetail.getStatus())
                .quantity(orderDetail.getQuantity())
                .totalPrice(orderDetail.getTotalPrice())
                .build();
        return Header.OK(body);
    }
}
