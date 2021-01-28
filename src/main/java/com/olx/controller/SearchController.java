package com.olx.controller;

import com.olx.dto.MiniAdDto;
import com.olx.model.Category;
import com.olx.model.Location;
import com.olx.service.CategoryService;
import com.olx.service.LocationService;
import com.olx.service.SearchService;
import com.olx.util.Utils;
import lombok.experimental.PackagePrivate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin

public class SearchController {
    @Autowired
    private LocationService locationService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PagedResourcesAssembler pagedResourcesAssembler;
    @Autowired
    private Utils utils;
    @Autowired
    private SearchService searchService;

    @GetMapping({"ads/search/{cat1}/{loc}","search/{cat1}/{cat2}/{loc}","ads/search/{cat1}/{cat2}/{cat3}/{loc}" })
    public ResponseEntity<PagedModel<MiniAdDto>> SearchCatLoc(
            @PathVariable("cat1") String cat1,
            @PathVariable(value = "cat2",required = false) String cat2,
            @PathVariable(value = "cat3",required = false) String cat3,
            @PathVariable("loc") String loc,
            @RequestParam(value = "title",required = false) String title,
            Pageable pageable) throws IOException {

        System.out.println(title);

            Category category2 = null;
            Category category3 = null;
            Category category1 =categoryService.getCategoryByName(cat1);
            if(cat2 != null)  category2 =categoryService.getCategoryByName(cat2);
            if(cat3 != null) category3 =categoryService.getCategoryByName(cat2);
            Location location = locationService.getLocationByName(loc);

            Category selectedCategory = (cat3 != null)?category3 : (cat2 != null)?category2 :category1;

            List<Long> cl =  utils.getCategoryAllChildren(selectedCategory.getId());
            List<Long> ll = utils.getLocationAllChildren(location.getId());

        Page<EntityModel<MiniAdDto>> adDtoPage = (title==null)?
                searchService.getByCategoryInAndLocationIn(cl,ll,pageable)
                :searchService.getByCategoryInAndLocationInAndTitle(cl,ll,title,pageable);
        PagedModel pagedModel = pagedResourcesAssembler.toModel(adDtoPage);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);


    }




    @GetMapping("ads/search")
    public ResponseEntity<PagedModel<MiniAdDto>> getAdByAllAttributes(
            @RequestParam(value = "title",required = false) Optional<String> title,
            @RequestParam(value = "category",required = false) Optional<String> category,
            @RequestParam(value = "location",required = false) Optional<String> location,
            @RequestParam(value = "condition",required = false) Optional<String> condition,
            @RequestParam(value = "brand",required = false) Optional<String> brand,
            @RequestParam(value = "minPrice",required = false) Optional<Integer> minPrice,
            @RequestParam(value = "maxPrice",required = false) Optional<Integer> maxPrice,
            Pageable page
            ) throws IOException {
            Page<EntityModel<MiniAdDto>> adDtoPage =null;

            if(!location.isPresent() && !category.isPresent()){
                adDtoPage =
                        searchService.getAdsByAllWithoutLocAndCat(title.orElse(""),condition.orElse(""),
                                brand.orElse(""),minPrice.orElse(0), maxPrice.orElse(999999999),page);
            }

            else if(location.isPresent() && !category.isPresent()){
                Location location1 = locationService.getLocationByName(location.orElse("Egypt"));
                List<Long> ll = utils.getLocationAllChildren(location1.getId());
                adDtoPage = searchService.getAdsByAllWithoutCat(title.orElse(""),ll,condition.orElse(""),
                        brand.orElse(""),minPrice.orElse(0), maxPrice.orElse(999999999),page);

            }
            else if(!location.isPresent() && category.isPresent()){
                Category cat1 = categoryService.getCategoryByName(category.get());
                List<Long> cl= utils.getCategoryAllChildren(cat1.getId());
                adDtoPage = searchService.getAdsByAllWithoutLoc(title.orElse(""),cl,condition.orElse(""),
                        brand.orElse(""),minPrice.orElse(0), maxPrice.orElse(999999999),page);

            }

            else{
                Location location1 = locationService.getLocationByName(location.orElse("Egypt"));
                List<Long> ll = utils.getLocationAllChildren(location1.getId());
                Category cat1 = categoryService.getCategoryByName(category.get());
                List<Long> cl= utils.getCategoryAllChildren(cat1.getId());
                adDtoPage = searchService.getAdsByEveryParameters(title.orElse(""),cl,ll,condition.orElse(""),
                        brand.orElse(""),minPrice.orElse(0), maxPrice.orElse(999999999),page);


            }


        PagedModel pagedModel = pagedResourcesAssembler.toModel(adDtoPage);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);







    }









}
