package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entitiy.Partner;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.PartnerApiRequest;
import com.example.study.model.network.request.PartnerApiResponse;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.repository.CategoryRepository;
import com.example.study.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerLogicService extends BaseService<PartnerApiRequest, PartnerApiResponse,Partner> {

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Header<PartnerApiResponse> create(Header<PartnerApiRequest> request) {
        PartnerApiRequest body = request.getData();

        Partner partner = Partner.builder()
                                    .address(body.getAddress())
                                    .partnerNumber(body.getPartnerNumber())
                                    .callCenter(body.getCallCenter())
                                    .category(categoryRepository.getOne(body.getCategoryId()))
                                    .name(body.getName())
                                    .businessNumber(body.getBusinessNumber())
                                    .ceoName(body.getCeoName())
                                    .registeredAt(body.getRegisteredAt())
                                    .unregisteredAt(body.getUnregisteredAt())
                                    .status(body.getStatus())
                                    .build();
        Partner newPartner = partnerRepository.save(partner);

        return response(newPartner);
    }

    @Override
    public Header<PartnerApiResponse> read(Long id) {

        Optional<Partner> partner = partnerRepository.findById(id);

        return partner.map(newPartner->response(newPartner)).orElseGet(()->Header.ERROR("데이터 없음"));

    }

    @Override
    public Header<PartnerApiResponse> update(Header<PartnerApiRequest> request) {
        PartnerApiRequest body = request.getData();
        Optional<Partner> partner = partnerRepository.findById(body.getId());

        return partner.map(newPartner -> {
            newPartner.setCeoName(body.getCeoName())
                        .setName(body.getName())
                        .setRegisteredAt(body.getRegisteredAt())
                        .setBusinessNumber(body.getBusinessNumber())
                        .setPartnerNumber(body.getPartnerNumber())
                        .setCallCenter(body.getCallCenter())
                        .setAddress(body.getAddress())
                        .setStatus(body.getStatus())
                        .setCategory(categoryRepository.getOne(body.getCategoryId()))
                        .setUnregisteredAt(body.getUnregisteredAt());

            Partner updatePartner = partnerRepository.save(newPartner);
            return response(updatePartner);
        }).orElseGet(()->Header.ERROR("데이터 없음"));

    }

    @Override
    public Header delete(Long id) {
        Optional<Partner> partner = partnerRepository.findById(id);

        return partner.map(newPartner->{
            partnerRepository.delete(newPartner);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("데이터 없음"));

    }

    public Header<PartnerApiResponse> response(Partner partner){

        PartnerApiResponse body = PartnerApiResponse.builder()
                                                .id(partner.getId())
                                                .status(partner.getStatus())
                                                .address(partner.getAddress())
                                                .businessNumber(partner.getBusinessNumber())
                                                .callCenter(partner.getCallCenter())
                                                .categoryId(partner.getCategory().getId())
                                                .ceoName(partner.getCeoName())
                                                .unregisteredAt(partner.getUnregisteredAt())
                                                .registeredAt(partner.getRegisteredAt())
                                                .name(partner.getName())
                                                .build();

        return Header.OK(body);
    }

    @Override
    public Header<List<UserApiResponse>> search(Pageable pageable) {
        return null;
    }
}
