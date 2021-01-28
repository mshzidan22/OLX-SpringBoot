package com.olx.repository;

import com.olx.model.Account;
import com.olx.model.Ad;
import com.olx.model.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdvertiserRepository extends JpaRepository<Advertiser,Long> {
    public Optional<Advertiser> findByAccountEmail (String email);

}
