package com.olx.service;

import com.olx.converter.AdConverter;
import com.olx.dto.AdDto;
import com.olx.dto.AdUserDto;
import com.olx.dto.MiniAdDto;
import com.olx.execption.AdNotFoundException;
import com.olx.model.Ad;
import com.olx.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdService {
    @Autowired
    private AdRepository adRepo;
    @Autowired
    private AdConverter adConverter;

    public AdDto getAd(Long id){

        Ad ad = adRepo.findById(id).orElseThrow(()-> new AdNotFoundException("Ad not found with id = "+id));
        return adConverter.entityToDto(ad);
    }

    public ArrayList<Ad> getRelatedAds(Long advertiserId){
        return adRepo.findTop5ByAdvertiserId(advertiserId);
    }

    public List<MiniAdDto> getHomeAds (){
        return adConverter.listEntityToDto(adRepo.findTop24ByOrderByIdDesc());
    }

    public Ad saveAd (Ad ad){
        return adRepo.save(ad);
    }

    public void saveAdByUser(Long advertiserId, Long adId){
        adRepo.saveAdByUser(advertiserId,adId);
    }

    public List<AdUserDto> getAllAdsByAdvertiser (Long advertiserId){
            List<Ad> adList = adRepo.findByAdvertiserId(advertiserId);
            return adList.stream().map(adConverter::AdToAdUserDto).collect(Collectors.toList());
    }

    public void deleteAd (Long id){
        adRepo.deleteById(id);
    }

    public void updateAd (Ad ad){
        adRepo.save(ad);
    }

}
