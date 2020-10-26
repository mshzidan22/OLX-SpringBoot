package com.olx.service;

import com.olx.assemblers.AdModelAssembler;
import com.olx.converter.AdConverter;
import com.olx.dto.MiniAdDto;
import com.olx.execption.LocationNotFoundException;
import com.olx.model.Location;
import com.olx.repository.AdRepository;
import com.olx.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locRepo;
    @Autowired
    private AdRepository adRepo;
    @Autowired
    private AdConverter adConverter;
    @Autowired
    private AdModelAssembler adModelAssembler;

    public Location getLocationById(Long id){
       return locRepo.findById(id).orElseThrow(()-> new LocationNotFoundException("Location not found"));
    }

    public Location getLocationByName(String name){
        // need to throw custom excption
        return locRepo.findByNameIgnoreCase(name).orElseThrow(()-> new LocationNotFoundException("Location not found"));
    }


    public Page<EntityModel<MiniAdDto>> getAdByLocation (Long locationId , Pageable pageable){
       //may be error here

        return  adRepo.findByLocationId(locationId,pageable).map(adConverter::entityToEntityModel);
    }

    public Page<EntityModel<MiniAdDto>>  getAdsByLocation (String location , Pageable pageable){
        Location loc = getLocationByName(location);
        //
        if(loc.getParent() == 1) {
           return adRepo.findByLocationParent(loc.getId(),pageable).map(adConverter::entityToEntityModel);
        }
        else
            return  adRepo.findByLocationId(loc.getId(),pageable).map(adConverter::entityToEntityModel);

    }




}
