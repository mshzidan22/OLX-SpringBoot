package com.olx.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olx.dto.CategoryDto;
import com.olx.dto.RelevantAdDto;
import com.olx.model.Ad;
import com.olx.model.Category;
import com.olx.model.Location;
import com.olx.service.AdService;
import com.olx.service.CategoryService;
import com.olx.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class Utils {
    @Autowired
    private LocationService locServ;
    @Autowired
    private CategoryService catServ;
    @Autowired
    private AdService adServ;
    @Autowired
    private ObjectMapper objectMapper;


    public String convertDate(LocalDateTime time) {
        String hhmm = time.getHour() + ":" + time.getMinute();
        String mmdd = time.getMonth().toString() + " " + time.getDayOfMonth();
        boolean today = time.toLocalDate().isEqual(LocalDateTime.now().toLocalDate());
        boolean yesterday = time.toLocalDate().isEqual(LocalDateTime.now().toLocalDate().minusDays(1));

        if (today) return "Today " + hhmm;
        else if (yesterday) return "Yesterday " + hhmm;
        else return mmdd;

    }

    // need to be worked without DB
    public GovAndCity getGovAndCity(Location loc) {
        boolean isInWholeEgypt = loc.getParent() == 0;
        boolean isInWholeGov = loc.getParent() == 1;

        if (isInWholeEgypt) return null;
        else if (isInWholeGov) return new GovAndCity(loc.getName(), null);
        else return new GovAndCity(locServ.getLocationById((long) loc.getParent()).getName(), loc.getName());

    }

    // need new version without using DB
    public CategoryDto getCategoryAndSub(Category category) {
        Long parent = (long) category.getParent();
        boolean isLv1 = parent == 0;
        boolean isLv2 = isCategoryLv2(category);

        if (isLv1) return new CategoryDto(category.getName(), null, null);

        else if (isLv2) {
            return new CategoryDto(catServ.getCatById(parent).getName(),
                    category.getName(), null);
        } else {
            Category lv2 = catServ.getCatById(parent);
            Category lv1 = catServ.getCatById((long) lv2.getParent());

            return new CategoryDto(lv1.getName(), lv2.getName(), category.getName());
        }

    }

    // need new Version without Db
    //delete this method
    private boolean isCategoryLv2(Category category) {
        int[] lv1Category = {6, 14, 17, 20, 100, 129, 138, 147, 206, 223, 230, 241};
        return Arrays.stream(lv1Category).anyMatch(s -> s == category.getParent());

    }

    public Set<RelevantAdDto> GetRelevantAds(Long advertiserId) {
        Set<RelevantAdDto> relevantAdDtoSet = new HashSet<>();
        ArrayList<Ad> rel = adServ.getRelatedAds(advertiserId);
        for (Ad ad : rel) {

            RelevantAdDto relDto = new RelevantAdDto();
            relDto.setId(ad.getId());
            relDto.setTitle(ad.getTitle());
            relDto.setPrice(ad.getPrice());
            relDto.setAddedAt(convertDate(ad.getTime()));
            relDto.setImg(ad.getImages().iterator().next().getImgSrc());
            relDto.setCity(getGovAndCity(ad.getLocation()).getCity());
            relDto.setGov(getGovAndCity(ad.getLocation()).getGov());
            relDto.setCategoryDto(getCategoryAndSub(ad.getCategory()));
            relevantAdDtoSet.add(relDto);
        }
        return relevantAdDtoSet;
    }


    public List<Long> getCategoryAllChildren(Long categoryId) throws IOException {
        List<Long> childrenIdsList = new ArrayList<Long>();
        //need to be relative path
        CategoryJson[] categories = objectMapper.readValue(
                new File("C:\\Users\\mshzidanPC\\spring_boot\\olx\\src\\main\\resources\\static\\json_files\\category.json"),
                CategoryJson[].class);

        for (CategoryJson c : categories) {
            if (c.getParent().equals(categoryId)) {
                childrenIdsList.add(c.getCategory_id());
            }
        }

        for (CategoryJson c : categories) {
            if (childrenIdsList.contains(c.getParent())) {
                childrenIdsList.add(c.getCategory_id());
            }

        }
        childrenIdsList.add(categoryId);
        return childrenIdsList;
    }

    public List<Long> getLocationAllChildren(Long locationId) throws IOException {
        List<Long> childrenIdsList = new ArrayList<Long>();
        LocationJson[] locations = objectMapper.readValue(
                new File("C:\\Users\\mshzidanPC\\spring_boot\\olx\\src\\main\\resources\\static\\json_files\\location.json"),
                LocationJson[].class);
        System.out.println("");
        for (LocationJson l : locations) {
            if (l.getParent().equals(locationId)) {

                childrenIdsList.add(l.getId());
            }
        }

        for (LocationJson l : locations) {
            if (childrenIdsList.contains(l.getParent())) {

                childrenIdsList.add(l.getId());
            }
        }
        childrenIdsList.add(locationId);
        return childrenIdsList;
    }
}