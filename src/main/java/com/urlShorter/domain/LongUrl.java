package com.urlShorter.domain;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "link")
public class LongUrl {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String longUrl;
    private String shorterUrl;
    private Date estimateDate;


    public LongUrl(String longLink, Date date, String shorterUrl) {
        this.longUrl = longLink;
        this.estimateDate = date;
        this.shorterUrl = shorterUrl;
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
        return shorterUrl;
    }

    public void setShorterUrl(String shorterUrl) {
        this.shorterUrl = shorterUrl;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }
}
