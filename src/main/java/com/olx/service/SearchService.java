package com.olx.service;

import com.olx.assemblers.AdModelAssembler;
import com.olx.converter.AdConverter;
import com.olx.dto.MiniAdDto;
import com.olx.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private AdRepository adRepo;
    @Autowired
    private AdConverter adConverter;
    @Autowired
    private AdModelAssembler adModelAssembler;

    public Page<EntityModel<MiniAdDto>> getByCategoryInAndLocationIn (List<Long> catList, List<Long> locList, Pageable pageable){
        return adRepo.findByCategoryIdInAndLocationIdIn(catList,locList,pageable).map(adConverter::entityToEntityModel);

    }

    public Page<EntityModel<MiniAdDto>> getByCategoryInAndLocationInAndTitle (List<Long> catList, List<Long> locList, String title,Pageable pageable){
        return adRepo.findByCategoryIdInAndLocationIdInAndTitleContaining(catList,locList,title,pageable).map(adConverter::entityToEntityModel);

    }
    public Page<EntityModel<MiniAdDto>> getAdsByEveryParameters(String title,List<Long> catList, List<Long> locList,
     String condition, String brand,Integer minPrice, Integer maxPrice, Pageable pageable){
        return adRepo.findByTitleContainingAndCategoryIdInAndLocationIdInAndConditionContainingAndBrandContainingAndPriceBetween(
                title,catList,locList,condition,brand,minPrice,maxPrice,pageable).map(adConverter::entityToEntityModel);

    }
    public Page<EntityModel<MiniAdDto>> getAdsByAllWithoutLocAndCat(String title,
                                                                String condition, String brand,Integer minPrice, Integer maxPrice, Pageable pageable){
        return adRepo.findByTitleContainingAndConditionContainingAndBrandContainingAndPriceBetween(
                title,condition,brand,minPrice,maxPrice,pageable).map(adConverter::entityToEntityModel);

    }

    public Page<EntityModel<MiniAdDto>> getAdsByAllWithoutLoc(String title,List<Long> catIdList,
                                                                    String condition, String brand,Integer minPrice, Integer maxPrice, Pageable pageable){
        return adRepo.findByTitleContainingAndConditionContainingAndBrandContainingAndPriceBetweenAndCategoryIdIn(
                title,condition,brand,minPrice,maxPrice,catIdList,pageable).map(adConverter::entityToEntityModel);

    }

    public Page<EntityModel<MiniAdDto>> getAdsByAllWithoutCat(String title,List<Long> locIdList,
                                                              String condition, String brand,Integer minPrice, Integer maxPrice, Pageable pageable){
        return adRepo.findByTitleContainingAndConditionContainingAndBrandContainingAndPriceBetweenAndLocationIdIn(
                title,condition,brand,minPrice,maxPrice,locIdList,pageable).map(adConverter::entityToEntityModel);

    }



}
