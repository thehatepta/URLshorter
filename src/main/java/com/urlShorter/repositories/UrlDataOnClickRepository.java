package com.urlShorter.repositories;

import com.urlShorter.domain.UrlDataOnClick;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface UrlDataOnClickRepository  extends CrudRepository<UrlDataOnClick, Long> {
    UrlDataOnClick findByUrlId(Long urlId);
    ArrayList<UrlDataOnClick> findByClickDate(String urlId);
    ArrayList <UrlDataOnClick> findByBrowser(String urlId);

}
