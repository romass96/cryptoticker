package com.crypto.model;

import java.sql.Timestamp;
import java.util.List;

public class CryptoNews extends PersistedObject {
    private String id;
    private Timestamp feedDate;
    private String source;
    private String title;
    private String imgUrl;
    private String link;
    private List<CryptoCurrency> relatedCoins;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getFeedDate() {
        return feedDate;
    }

    public void setFeedDate(Timestamp feedDate) {
        this.feedDate = feedDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<CryptoCurrency> getRelatedCoins() {
        return relatedCoins;
    }

    public void setRelatedCoins(List<CryptoCurrency> relatedCoins) {
        this.relatedCoins = relatedCoins;
    }
}
