package com.example.study.service;

import com.example.study.controller.CrudController;
import com.example.study.ifs.CrudInterface;
import com.example.study.model.entitiy.Category;
import com.example.study.model.entitiy.Partner;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.CategoryApiRequest;
import com.example.study.model.network.request.PartnerApiRequest;
import com.example.study.model.network.response.CategoryApiResponse;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryApiLogicService extends BaseService<CategoryApiRequest,CategoryApiResponse,Category> {



    @Override
    public Header<CategoryApiResponse> create(Header<CategoryApiRequest> request) {
        CategoryApiRequest body = request.getData();

        Category category=Category.builder().title(body.getTitle()).type(body.getType()).build();

        Category newCategory = baseRepository.save(category);

        return response(newCategory);
    }

    @Override
    public Header<CategoryApiResponse> read(Long id) {
        Optional<Category> optionalCategory = baseRepository.findById(id);

        return optionalCategory.map(category -> response(category)).orElseGet(()-> Header.ERROR("데이터 없음"));

    }

    @Override
    public Header<CategoryApiResponse> update(Header<CategoryApiRequest> request) {
        CategoryApiRequest body = request.getData();

        Optional<Category> category = baseRepository.findById(body.getId());

       return category.map(newCategory-> {
            newCategory.setType(body.getType()).setTitle(body.getTitle());

           baseRepository.save(newCategory);
            return response(newCategory);

        }).orElseGet(()->Header.ERROR("데이터 없음"));

    }

    @Override
    public Header delete(Long id) {
        Optional<Category> category = baseRepository.findById(id);


        return category.map(newCategory-> {
            baseRepository.delete(newCategory);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    public Header<CategoryApiResponse> response(Category category){
        CategoryApiResponse body =  CategoryApiResponse.builder()
                                            .id(category.getId())
                                            .title(category.getTitle())
                                            .type(category.getType())
                                            .build();
        return Header.OK(body);
    }

    @Override
    public Header<List<UserApiResponse>> search(Pageable pageable) {
        return null;
    }
}
