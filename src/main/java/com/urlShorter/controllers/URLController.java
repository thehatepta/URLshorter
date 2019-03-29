package com.urlShorter.controllers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.urlShorter.domain.LongUrl;
import org.springframework.web.bind.annotation.*;
import com.urlShorter.repositories.UrlRepository;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.urlShorter.services.RandomCharCreators.getRandomChars;


@RequestMapping
@RestController
public class URLController {
    private final UrlRepository linkRepository;


    public URLController(UrlRepository linkRepository){
        this.linkRepository = linkRepository;
    }

    @GetMapping
    public Iterable<LongUrl> getAll() {
        return linkRepository.findAll();
    }
    @GetMapping("/{shorterUrl}")
    public RedirectView shortUrlRedirect(@PathVariable String shorterUrl) {
        LongUrl byShorterUrl = linkRepository.findByShorterUrl(shorterUrl);

        return new RedirectView(byShorterUrl.getLongUrl());

    }

    @PostMapping
    public Iterable<LongUrl> getTestCases(@RequestBody  @Valid final ShortenRequest shortenRequest) throws ParseException {
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(shortenRequest.getDate());
        LongUrl url = new LongUrl(shortenRequest.getUrl(), date, getRandomChars());
        linkRepository.save(url);
        return linkRepository.findAll();
    }

}
class ShortenRequest{
    private String url;
    private String date;

    @JsonCreator
    public ShortenRequest() {

    }

    @JsonCreator
    public ShortenRequest(@JsonProperty("url") String url, @JsonProperty("date") String date) {
        this.url = url;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
