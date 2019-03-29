package com.urlShorter.repositories;


import com.urlShorter.domain.LongUrl;
import org.springframework.data.repository.CrudRepository;

public interface UrlRepository extends CrudRepository<LongUrl, Long> {

    LongUrl findByShorterUrl(String shorterUrl);
}
