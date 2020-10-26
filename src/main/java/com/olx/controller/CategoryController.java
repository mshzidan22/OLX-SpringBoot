package com.olx.controller;

import com.olx.dto.MiniAdDto;
import com.olx.model.Category;
import com.olx.service.AdService;
import com.olx.service.CategoryService;
import com.olx.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PagedResourcesAssembler pagedResourcesAssembler;
    @Autowired
    private Utils utils;




    @GetMapping("/category/{lv1}")
    public ResponseEntity<PagedModel<MiniAdDto>> AdCategoryNameLv1(
            @PathVariable("lv1") String lv1Name,
            Pageable pageable) throws IOException {


             Category category1 =categoryService.getCategoryByName(lv1Name);
             if(category1.getCategoryLevel() != 1) throw new RuntimeException("no such Category");
             List<Long> l =  utils.getCategoryAllChildren(category1.getId());
             Page<EntityModel<MiniAdDto>> adDtoPage = categoryService.getAdsByCategoryChildren(l,pageable);
            PagedModel pagedModel = pagedResourcesAssembler.toModel(adDtoPage);
            return new ResponseEntity<>(pagedModel, HttpStatus.OK);



    }

    @GetMapping("/category/{lv1}/{lv2}")
    public ResponseEntity<PagedModel<MiniAdDto>> AdCategoryNameLv2(@PathVariable("lv1") String lv1Name,
                                                                   @PathVariable("lv2") String lv2Name,
                                                                   Pageable pageable) throws IOException {
        Category category1 =categoryService.getCategoryByName(lv1Name);
        Category category2 =categoryService.getCategoryByName(lv2Name);
        if(category1.getCategoryLevel() != 1) throw new RuntimeException("no such Category");
        if(category2.getCategoryLevel() != 2) throw new RuntimeException("no such Category");

        List<Long> l =  utils.getCategoryAllChildren(category2.getId());
        Page<EntityModel<MiniAdDto>> adDtoPage = categoryService.getAdsByCategoryChildren(l,pageable);
        PagedModel pagedModel = pagedResourcesAssembler.toModel(adDtoPage);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);

    }



    @GetMapping("category/{lv1}/{lv2}/{lv3}")
    public ResponseEntity<PagedModel<MiniAdDto>> AdCategoryNameLv3(@PathVariable("lv1") String lv1Name,
                                                                   @PathVariable("lv2") String lv2Name,
                                                                   @PathVariable("lv3") String lv3Name,
                                                                   Pageable pageable) {
        Category category1 = categoryService.getCategoryByName(lv1Name);
        Category category2 = categoryService.getCategoryByName(lv2Name);
        Category category3 = categoryService.getCategoryByNameAndParent(lv3Name,category2.getId());



        if (category1.getCategoryLevel() != 1 || category2.getCategoryLevel() != 2 || category3.getCategoryLevel() != 3)
            throw new RuntimeException("no such Category");

        Page<EntityModel<MiniAdDto>> adDtoPage = categoryService.getAdsByCategory(category3.getId(), pageable);
        PagedModel pagedModel = pagedResourcesAssembler.toModel(adDtoPage);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);


    }
}
