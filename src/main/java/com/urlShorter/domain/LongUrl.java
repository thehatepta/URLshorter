package com.urlShorter.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "link")
public class LongUrl {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String longUrl;
    private String shorterUrl;
    private LocalDate estimateDate;
    private Boolean activation;
    @OneToMany(cascade = {CascadeType.ALL}, targetEntity=UrlDataOnClick.class, mappedBy="urlId", fetch=FetchType.EAGER)
    private List<UrlDataOnClick> urlData = new ArrayList<UrlDataOnClick>();


    public LongUrl(String longLink, LocalDate date, String shorterUrl, Boolean activaion) {
        this.longUrl = longLink;
        this.estimateDate = date;
        this.shorterUrl = shorterUrl;
        this.activation = activaion;
    }

    public LongUrl() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShorterUrl() {
        return "http://localhost:8080/" + shorterUrl;
    }

    public void setShorterUrl(String shorterUrl) {
        this.shorterUrl = shorterUrl;
    }

    public LocalDate getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(LocalDate estimateDate) {
        this.estimateDate = estimateDate;
    }

    public Boolean getActivaion() {
        return activation;
    }

    public void setActivaion(Boolean activaion) {
        this.activation = activaion;
    }

    public List<UrlDataOnClick> getUrlData() {
        return urlData;
    }

    public void setUrlData(List<UrlDataOnClick> urlData) {
        this.urlData = urlData;
    }
}
