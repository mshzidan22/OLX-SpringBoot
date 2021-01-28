package com.olx.service;

import com.olx.converter.AdConverter;
import com.olx.dto.AdDto;
import com.olx.dto.AdUserDto;
import com.olx.dto.MiniAdDto;
import com.olx.execption.AdNotFoundException;
import com.olx.model.Ad;
import com.olx.repository.AdRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdService {
    @Autowired
    private AdRepository adRepo;
    @Autowired
    private AdConverter adConverter;
    @SneakyThrows
    public AdDto getAd(Long id) {

        Ad ad = adRepo.findById(id).orElseThrow(()-> new AdNotFoundException("Ad not found with id = "+id));
        if(ad.getViews() == null) ad.setViews(new Long(0));
        ad.setViews(ad.getViews()+1);
        Ad ad1=saveAd(ad);
        return adConverter.entityToDto(ad1);
    }

    public ArrayList<Ad> getRelatedAds(Long advertiserId){
        return adRepo.findTop5ByAdvertiserId(advertiserId);
    }
    @SneakyThrows
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
    @Transactional
    public void deleteAd (Long id){
        adRepo.deleteSavedAd(id);
        adRepo.deleteById(id);
    }

    public Ad updateAd (Ad ad){

        return adRepo.save(ad);
    }

    public Optional<Ad> findAd (Long id){
        return adRepo.findById(id);
    }

    public Boolean IsAdBelongsToUser(Long adId , Long advertiserId){
        if(adRepo.countByIdAndAdvertiserId(adId, advertiserId) == 1) return true;
        else return false;
    }

    public void unSaveAd(Long adId , Long advertiserId){
        adRepo.unSaveAd(adId,advertiserId);
    }

    public List<Long> getSavedAdsByUser (Long advertiserId){
        return adRepo.findSavedAds(advertiserId);
    }

}
