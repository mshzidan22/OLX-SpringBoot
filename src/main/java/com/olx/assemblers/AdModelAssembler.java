package com.olx.assemblers;

import com.olx.controller.*;
import com.olx.dto.AdDto;
import com.olx.dto.AdUserDto;
import com.olx.dto.MiniAdDto;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;



@Component
public class AdModelAssembler {


    // need to add other links
    @SneakyThrows
    public EntityModel<AdDto> toModel(AdDto adDto)  {
        Pageable page = null;
        return EntityModel.of(adDto,
                linkTo(methodOn(AdController.class).getAd(adDto.getId())).withSelfRel(),
                linkTo(methodOn(AdController.class).all()).withRel("ads"),
                linkTo(methodOn(LocationController.class).getAdByLocation(adDto.getCity(),page)).withRel("city"),
                linkTo(methodOn(LocationController.class).getAdByLocation(adDto.getGov(),page)).withRel("gov"),
                linkTo(methodOn(CategoryController.class).AdCategoryNameLv1(adDto.getCategoryDto().getCategoryLv1(),page)).withRel("category1"),
                linkTo(methodOn(CategoryController.class).AdCategoryNameLv2(adDto.getCategoryDto().getCategoryLv1(),adDto.getCategoryDto().getCategoryLv1(),page)).withRel("category2"),
                linkTo(methodOn(CategoryController.class).AdCategoryNameLv3(adDto.getCategoryDto().getCategoryLv1(),adDto.getCategoryDto().getCategoryLv2(),adDto.getCategoryDto().getCategoryLv3(),page)).withRel("category3")
        );


    }

    public static EntityModel<MiniAdDto> MiniToModel(MiniAdDto miniAdDto) {
        return EntityModel.of(miniAdDto,
                linkTo(methodOn(AdController.class).getAd(miniAdDto.getId())).withSelfRel(),
                linkTo(methodOn(AdController.class).all()).withRel("ads")
        );
    }


    public CollectionModel<EntityModel<MiniAdDto>> toCollectionModel(List<MiniAdDto> miniDtoList) {

        List<EntityModel<MiniAdDto>> miniEntityList = miniDtoList.stream().map(AdModelAssembler::MiniToModel)
                .collect(Collectors.toList());

        return CollectionModel.of(miniEntityList,
                linkTo(methodOn(AdController.class).all()).withSelfRel());
    }



    public EntityModel<AdUserDto> toAdUserDtoModel (AdUserDto adUserDto){
         return EntityModel.of(adUserDto,
                linkTo(methodOn(AdController.class).getAd(adUserDto.getId())).withSelfRel(),
                linkTo(methodOn(AccountController.class).deleteAd(adUserDto.getId())).withRel("delete")

         );

    }




}