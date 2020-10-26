package com.olx.service;

import com.olx.assemblers.AdModelAssembler;
import com.olx.converter.AdConverter;
import com.olx.dto.MiniAdDto;
import com.olx.execption.CategoryNotFoundException;
import com.olx.model.Category;
import com.olx.repository.AdRepository;
import com.olx.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository catRepo;
    @Autowired
    private AdRepository adRepo;
    @Autowired
    private AdConverter adConverter;
    @Autowired
    private AdModelAssembler adModelAssembler;

    public Category getCatById(Long id){
        return catRepo.findById(id).orElseThrow(()-> new CategoryNotFoundException("Category Not Found"));
    }
    public Category getCategoryByNameAndParent(String name,Long parent){
        //make excetion notfound
        return catRepo.findByNameIgnoreCaseAndParent(name,parent).orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));
    }

    public Category getCategoryByName(String name){
        return catRepo.findByNameIgnoreCase(name).orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));
    }

    public Page<EntityModel<MiniAdDto>> getAdsByCategory(Long id , Pageable pageable){
      return    adRepo.findAllByCategoryId(id,pageable).map(adConverter::entityToEntityModel);
    }

    public Page<EntityModel<MiniAdDto>> getAdsByCategoryChildren(List<Long> catIdList,Pageable pageable){
        return adRepo.findByCategoryIdIn(catIdList,pageable).map(adConverter::entityToEntityModel);
    }



}
