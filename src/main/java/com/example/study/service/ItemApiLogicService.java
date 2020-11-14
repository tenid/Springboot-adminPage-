package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entitiy.Item;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.Optional;

@Service
public class ItemApiLogicService extends BaseService<ItemApiRequest, ItemApiResponse,Item> {

    @Autowired
    PartnerRepository partnerRepository;


    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {
        ItemApiRequest body = request.getData();

        Item item = Item.builder()
                    .status(body.getStatus())
                    .name(body.getName())
                    .title(body.getTitle())
                    .content(body.getContent())
                    .price(body.getPrice())
                    .brandName(body.getBrandName())
                    .partner(partnerRepository.getOne(body.getPartnerId()))
                    .registeredAt(body.getRegisteredAt())
                    .build();

        Item newItem = baseRepository.save(item);

        return response(newItem);
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {
       Optional<Item> item = baseRepository.findById(id);

       return item.map(findItem ->response(findItem)).orElseGet(()->Header.ERROR("데이터 없음"));

    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {
        ItemApiRequest itemApiRequest = request.getData();
        Optional<Item> item= baseRepository.findById(itemApiRequest.getId());
       return item.map(findItem ->{
            findItem.setTitle(itemApiRequest.getTitle())
                   .setName(itemApiRequest.getName())
                   .setContent(itemApiRequest.getContent())
                   .setStatus(itemApiRequest.getStatus())
                   .setRegisteredAt(itemApiRequest.getRegisteredAt())
                   .setUnregisteredAt(itemApiRequest.getUnregisteredAt())
                   .setPrice(itemApiRequest.getPrice())
                   .setBrandName(itemApiRequest.getBrandName())
                   .setPartner(partnerRepository.getOne(itemApiRequest.getPartnerId()));
            return findItem;
       }).map(findItem->baseRepository.save(findItem)).map(findItem->response(findItem)).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<Item> Item = baseRepository.findById(id);

        return Item.map(findItem -> {
            baseRepository.delete(findItem);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));


    }

    public Header<ItemApiResponse> response(Item item){
        ItemApiResponse itemApiResponse =ItemApiResponse.builder()
                                            .id(item.getId())
                                            .name(item.getName())
                                            .title(item.getTitle())
                                            .price(item.getPrice())
                                            .status(item.getStatus())
                                            .brandName(item.getBrandName())
                                            .content(item.getContent())
                                            .partnerId(item.getPartner().getId())
                                            .registeredAt(item.getRegisteredAt())
                                            .unregisteredAt(item.getUnregisteredAt())
                                            .build();
        return Header.OK(itemApiResponse);
    }

}
