package com.olx.converter;

import com.olx.assemblers.AdModelAssembler;
import com.olx.dto.AdDto;
import com.olx.dto.AdInputDto;
import com.olx.dto.AdUserDto;
import com.olx.dto.MiniAdDto;
import com.olx.model.Ad;
import com.olx.model.Img;
import com.olx.service.AccountService;
import com.olx.service.CategoryService;
import com.olx.service.LocationService;
import com.olx.util.GovAndCity;
import com.olx.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AdConverter  {
    @Autowired
    private  ModelMapper mapper;
    @Autowired
    private  Utils utils;
    @Autowired
    private AdModelAssembler adModelAssembler;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private AccountService accountService;

    public  AdDto entityToDto(Ad ad) {
        AdDto adDto = mapper.map(ad, AdDto.class);
        adDto.setAddedAt(utils.convertDate(ad.getTime()));
        GovAndCity govAndCity = utils.getGovAndCity(ad.getLocation());
        adDto.setGov(govAndCity.getGov());
        adDto.setCity(govAndCity.getCity());
        adDto.setCategoryDto(utils.getCategoryAndSub(ad.getCategory()));
        adDto.setRelevantAdDto(utils.GetRelevantAds(ad.getAdvertiser().getId()));
        return adDto;
    }
    public MiniAdDto entityToMiniAdDto (Ad ad){
        MiniAdDto miniAdDto = new MiniAdDto();

        // need to be encapsulated
        miniAdDto.setId(ad.getId());
        miniAdDto.setTitle(ad.getTitle());
        miniAdDto.setPrice(ad.getPrice());
        miniAdDto.setAddedAt(utils.convertDate(ad.getTime()));
        miniAdDto.setImg(ad.getImages().iterator().next().getImgSrc());
        GovAndCity govAndCity = utils.getGovAndCity(ad.getLocation());
        miniAdDto.setGov(govAndCity.getGov());
        miniAdDto.setCity(govAndCity.getCity());
        miniAdDto.setCategoryDto(utils.getCategoryAndSub(ad.getCategory()));
        return miniAdDto;
    }

    public  List<MiniAdDto> listEntityToDto (ArrayList<Ad> ads) {
        List<MiniAdDto> list = new ArrayList<>();

        for (Ad ad : ads){
            list.add(entityToMiniAdDto(ad));

        }
         return list;
    }

    public EntityModel<MiniAdDto> entityToEntityModel (Ad ad) {
        MiniAdDto miniAdDto = entityToMiniAdDto(ad);
        return adModelAssembler.MiniToModel(miniAdDto);
    }


    public Ad adInputDtoToAd(AdInputDto adInputDto){
        Ad ad = new Ad();
        Set<Img> imgs = new HashSet<>();
        ad.setTitle(adInputDto.getTitle());
        ad.setDescription(adInputDto.getDescription());
        ad.setBrand(adInputDto.getBrand());
        ad.setPrice(adInputDto.getPrice());
        ad.setTime(LocalDateTime.now());
        ad.setCondition(adInputDto.getCondition());
        adInputDto.getImages().forEach((i)->imgs.add(new Img(i)));
        ad.setImages(imgs);
        ad.setCategory(categoryService.getCatById(adInputDto.getCategoryId()));
        ad.setLocation(locationService.getLocationById(adInputDto.getLocationId()));
        ad.setAdvertiser(accountService.getAccountByEmail(adInputDto.getEmail()).get().getAdvertiser());

      return ad;

    }

    public AdUserDto AdToAdUserDto (Ad ad){
           AdUserDto adUserDto = mapper.map(ad,AdUserDto.class);
           adUserDto.setAddedAt(utils.convertDate(ad.getTime()));
           return adUserDto;
    }



}