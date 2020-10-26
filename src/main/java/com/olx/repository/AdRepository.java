package com.olx.repository;

import com.olx.model.Ad;
import org.kolobok.annotation.FindWithOptionalParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad,Long> {
    public ArrayList<Ad> findTop5ByAdvertiserId (Long advertiserId);
    public ArrayList<Ad> findTop24ByOrderByIdDesc();
    public Page<Ad> findByLocationId(Long locationId, Pageable pageable);
    public Page<Ad> findByLocationParent(Long parent,Pageable pageable);
    public Page<Ad> findAllByCategoryId(Long categoryId ,Pageable pageable);
    public Page<Ad> findByCategoryParent(Long Id, Pageable pageable);
    public Page<Ad> findByCategoryIdIn(List<Long> categoryIdList,Pageable pageable);

    public Page<Ad> findByCategoryIdInAndLocationIdIn(List<Long> categoryList,List<Long> locationList,Pageable pageable);
    public Page<Ad> findByCategoryIdInAndLocationIdInAndTitleContaining(List<Long> categoryList,List<Long> locationList,String title,Pageable pageable);


    public Page<Ad> findByTitleContainingAndCategoryIdInAndLocationIdInAndConditionContainingAndBrandContainingAndPriceBetween(
            String title, List<Long> categoryIdList, List<Long> locationIdList, String condition, String brand,
            Integer minPrice, Integer maxPrice, Pageable pageable
    );

    public Page<Ad> findByTitleContainingAndConditionContainingAndBrandContainingAndPriceBetween(
            String title ,String condition, String brand,
            Integer minPrice, Integer maxPrice, Pageable pageable
    );

    public Page<Ad> findByTitleContainingAndConditionContainingAndBrandContainingAndPriceBetweenAndLocationIdIn(
            String title ,String condition, String brand,
            Integer minPrice, Integer maxPrice,List<Long> locationIdList ,Pageable pageable
    );

    public Page<Ad> findByTitleContainingAndConditionContainingAndBrandContainingAndPriceBetweenAndCategoryIdIn(
            String title ,String condition, String brand,
            Integer minPrice, Integer maxPrice,List<Long> categoryIdList ,Pageable pageable
    );

    public List<Ad> findByAdvertiserId (Long id);
    @Modifying
    @Transactional
    @Query(value = "insert into saved_ads values (:advertiserId,:adId)",nativeQuery = true)
    public void saveAdByUser(@Param("advertiserId") Long advertiserId,@Param("adId") Long adId);


    //@FindWithOptionalParams
}
