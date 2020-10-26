package com.olx.controller;

import com.olx.dto.MiniAdDto;
import com.olx.model.Location;
import com.olx.service.LocationService;
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


@RestController
public class LocationController {
    @Autowired
    private LocationService locationService;
    @Autowired
    private PagedResourcesAssembler pagedResourcesAssembler;


    @GetMapping("/location/{locationName}")
    public ResponseEntity<PagedModel<MiniAdDto>> getAdByLocation (@PathVariable String locationName, Pageable pageable){
        //Location location =locationService.getLocationIdByName(locationName);


        //Page<EntityModel<MiniAdDto>> adDtoPage = locationService.getAdByLocation(location.getId(),pageable);
        Page<EntityModel<MiniAdDto>> adDtoPage =locationService.getAdsByLocation(locationName,pageable);
        PagedModel pagedModel = pagedResourcesAssembler.toModel(adDtoPage);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }
}
