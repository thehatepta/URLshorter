package com.urlShorter.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "urlData")
public class UrlDataOnClick {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String sourceIp;
    private String browser;
    private LocalDate clickDate;
    private Long urlId;



    public UrlDataOnClick(Long urlId, String sourceIp, String browser) {
        this.urlId = urlId;
        this.clickDate = LocalDate.now();
        this.sourceIp = sourceIp;
        this.browser = browser;
    }

    public UrlDataOnClick() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public LocalDate getClickDate() {
        return clickDate;
    }

    public void setClickDate(LocalDate clickDate) {
        this.clickDate = clickDate;
    }

    public Long getUrlId() {
        return urlId;
    }

    public void setUrlId(Long urlId) {
        this.urlId = urlId;
    }
}
