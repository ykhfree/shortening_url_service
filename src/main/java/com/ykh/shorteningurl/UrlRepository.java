package com.ykh.shorteningurl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Integer> {

    UrlEntity findByOriginUrl(String originUrl);
}
