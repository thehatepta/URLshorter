package com.urlShorter.controllers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.urlShorter.domain.LongUrl;
import com.urlShorter.domain.UrlDataOnClick;
import com.urlShorter.exception.ResourceNotFoundException;
import com.urlShorter.repositories.UrlDataOnClickRepository;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.web.bind.annotation.*;
import com.urlShorter.repositories.UrlRepository;
import org.springframework.web.servlet.view.RedirectView;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.urlShorter.services.urlServices.getRandomChars;


@RequestMapping
@RestController
public class URLController {



    private final UrlRepository urlRepository;
    private final UrlDataOnClickRepository urlDataOnClickRepository;

    public URLController(UrlRepository linkRepository, UrlDataOnClickRepository urlDataOnClickRepository) {
        this.urlRepository = linkRepository;
        this.urlDataOnClickRepository = urlDataOnClickRepository;
    }

    @GetMapping
    public Iterable<LongUrl> getAll() {

        return urlRepository.findAll();
    }



    @GetMapping("/{shorterUrl}")
    public RedirectView shortUrlRedirect(@PathVariable String shorterUrl, HttpServletRequest request, @RequestHeader(value="User-Agent", required=false) String userAgentString) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LongUrl byShorterUrl = urlRepository.findByShorterUrl(shorterUrl);
        LocalDate estimateDate = byShorterUrl.getEstimateDate();
        LocalDate current = LocalDate.now();

        String name = null;
        if (null != userAgentString) {
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            name = userAgent.getBrowser().getName();
        }else {
            name = "request sent not from browser";
        }
        if(name.contains("Unknown")) {
            name = "request sent not from browser";
        }




        if(byShorterUrl.getActivaion().equals(false)){
            throw new ResourceNotFoundException("URL expired");}
        else if(!(current.equals(estimateDate))) {

            UrlDataOnClick newUrlData = new UrlDataOnClick(byShorterUrl.getId(), request.getRemoteAddr(), name);
            urlDataOnClickRepository.save(newUrlData);
            return new RedirectView(byShorterUrl.getLongUrl());




        } else  {
            byShorterUrl.setActivaion(false);
            throw new ResourceNotFoundException("URL expired");}

    }

    @GetMapping("{shorterUrl}/{filter}")
    public ArrayList<UrlDataOnClick> shortUrlRedirect(@PathVariable String shorterUrl, @PathVariable String filter) {
        List<UrlDataOnClick> urlData = urlRepository.findByShorterUrl(shorterUrl).getUrlData();
        ArrayList results = new ArrayList();
        if(!(filter.equals("all"))) {
            for (UrlDataOnClick data : urlData) {
                if (filter.contains("date")) {
                    results.add(data.getClickDate());
                } else if (filter.contains("browser")) {
                    results.add(data.getBrowser());
                }
                else if (filter.contains("amount")) {
                    results.add(urlData.size());
                }
            }
            return results;
        }else {
            return new ArrayList<>(urlRepository.findByShorterUrl(shorterUrl).getUrlData());
        }

    }


    @DeleteMapping("{shorterUrl}")
    public Iterable<LongUrl> deleteshortenUrl(@PathVariable String shorterUrl) throws ParseException {
        LongUrl urlToDelete = urlRepository.findByShorterUrl(shorterUrl);
        urlRepository.delete(urlToDelete);
        return urlRepository.findAll();
    }

    @PostMapping()
    public Iterable<LongUrl> creteShortUrlFromLong(@RequestBody  @Valid final ShortenRequest shortenRequest) throws ParseException {
        String date = shortenRequest.getDate();
        LocalDate localDate = LocalDate.parse(date);
        LongUrl url = new LongUrl(shortenRequest.getUrl(), localDate, getRandomChars(), shortenRequest.isActivation());
        urlRepository.save(url);
        return urlRepository.findAll();
    }

}
class ShortenRequest{
    private String url;
    private String date;
    private Boolean activation;

    @JsonCreator
    public ShortenRequest() {

    }

    @JsonCreator
    public ShortenRequest(@JsonProperty("url") String url, @JsonProperty("date") String date, @JsonProperty("activation") Boolean activation) {
        this.url = url;
        this.date = date;
        this.activation = activation;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public Boolean isActivation() {
        return activation;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
    }
}