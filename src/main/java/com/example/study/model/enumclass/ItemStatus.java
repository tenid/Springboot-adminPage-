package com.example.study.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;

@AllArgsConstructor
@Getter
public enum  ItemStatus {

    REGISTERED(0,"등록","상품 등록 상태"),
    UNREGISTERED(1,"해지","상품 해지 상태"),
    WAITING(3,"검수(대기)","상품 검수 상태");

    private Integer id;
    private String title;
    private String description;
}
