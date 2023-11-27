package com.olx.converter;

import com.olx.assemblers.AdModelAssembler;
import com.olx.dto.AdDto;
import com.olx.dto.AdCreationDto;
import com.olx.dto.AdUserDto;
import com.olx.dto.MiniAdDto;
import com.olx.model.Ad;
import com.olx.model.Img;
import com.olx.service.AccountService;
import com.olx.service.CategoryService;
import com.olx.service.LocationService;
import com.olx.util.GovAndCity;
import com.olx.util.Utils;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
    public  AdDto entityToDto(Ad ad)  {
        AdDto adDto = mapper.map(ad, AdDto.class);
        adDto.setAddedAt(utils.convertDate(ad.getTime()));
        GovAndCity govAndCity = utils.getGovAndCity(ad.getLocation());
        adDto.setGov(govAndCity.getGov());
        adDto.setCity(govAndCity.getCity());
        adDto.setCategoryDto(utils.getCategoryAndSub(ad.getCategory()));
        adDto.setRelevantAdDto(utils.GetRelevantAds(ad.getAdvertiser().getId()));
        return adDto;
    }
    @SneakyThrows
    public MiniAdDto entityToMiniAdDto (Ad ad) throws IOException {
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
    @SneakyThrows
    public  List<MiniAdDto> listEntityToDto (ArrayList<Ad> ads) throws IOException {
        List<MiniAdDto> list = new ArrayList<>();

        for (Ad ad : ads){
            list.add(entityToMiniAdDto(ad));

        }
         return list;
    }
    @SneakyThrows
    public EntityModel<MiniAdDto> entityToEntityModel (Ad ad) {
        MiniAdDto miniAdDto = entityToMiniAdDto(ad);
        return adModelAssembler.MiniToModel(miniAdDto);
    }

    @SneakyThrows
    public Ad adCreationDtoToAd(AdCreationDto adCreationDto){
        Ad ad = new Ad();
        Set<Img> imgs = new HashSet<>();
        ad.setTitle(adCreationDto.getTitle());
        ad.setDescription(adCreationDto.getDescription());
        ad.setBrand(adCreationDto.getBrand());
        ad.setPrice(adCreationDto.getPrice());
        ad.setTime(LocalDateTime.now());
        ad.setCondition(adCreationDto.getCondition());
        adCreationDto.getImages().forEach((i)->imgs.add(new Img()));
        ad.setImages(imgs);
        ad.setCategory(categoryService.getCatById(adCreationDto.getCategoryId()));
        ad.setLocation(locationService.getLocationById(adCreationDto.getLocationId()));
        ad.setAdvertiser(accountService.getAccountByEmail(adCreationDto.getEmail()).get().getAdvertiser());

      return ad;

    }
    @SneakyThrows
    public AdUserDto AdToAdUserDto (Ad ad){
           AdUserDto adUserDto = mapper.map(ad,AdUserDto.class);
           adUserDto.setAddedAt(utils.convertDate(ad.getTime()));
           return adUserDto;
    }



}