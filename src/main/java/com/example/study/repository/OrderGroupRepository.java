package com.example.study.repository;

import com.example.study.model.entitiy.Category;
import com.example.study.model.entitiy.OrderGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderGroupRepository extends JpaRepository<OrderGroup,Long> {

    Optional<OrderGroup> findByRevName(String name);
}
