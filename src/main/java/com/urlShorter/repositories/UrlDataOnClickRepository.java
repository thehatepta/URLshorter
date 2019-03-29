package com.urlShorter.repositories;

import com.urlShorter.domain.UrlDataOnClick;
import org.springframework.data.repository.CrudRepository;

public interface UrlDataOnClickRepository  extends CrudRepository<UrlDataOnClick, Long> {
    UrlDataOnClick findByUrlId(Long urlId);
}
