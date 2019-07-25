package com.crypto.model;

import javax.persistence.*;

@Entity
@Table(name = "crypto_currencies")
public class CryptoCurrency extends PersistedObject{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "symbol",updatable = false)
    private String symbol;

    @Column(name = "name", updatable = false)
    private String name;

    @Column(name = "total_supply")
    private Long totalSupply;

    @Column(name = "available_supply")
    private Long availableSupply;

    @Column(name = "circulating_supply")
    private Long circulatingSupply;

    @Column(name = "capitalization")
    private Double capitalization;

    @Column(name = "price_usd")
    private Double priceUSD;

    @Column(name = "price_btc")
    private Double priceBTC;

    @Column(name = "day_volume")
    private Double dayVolume;

    @Column(name = "price_change_1h")
    private Double priceChange1h;

    @Column(name = "price_change_1d")
    private Double priceChange1d;

    @Column(name = "price_change_1w")
    private Double priceChange1w;

    @Column(name = "description")
    @Lob
    private String description;

    @Column(name = "icon")
    private String icon;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "reddit_url")
    private String redditUrl;

    @Column(name = "twitter_url")
    private String twitterUrl;

    @Column(name = "coin_market_cap_id")
    private Long coinMarketCapId;

    @Column(name = "coin_stats_id")
    private String coinstatsId;

    @Column(name = "contract_address")
    private String contractAddress;

    public static CryptoCurrency ofCoinStatsId(String coinStatsId) {
        CryptoCurrency cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setCoinstatsId(coinStatsId);
        return cryptoCurrency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(Long totalSupply) {
        this.totalSupply = totalSupply;
    }

    public Long getAvailableSupply() {
        return availableSupply;
    }

    public void setAvailableSupply(Long availableSupply) {
        this.availableSupply = availableSupply;
    }

    public Long getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(Long circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    public Double getCapitalization() {
        return capitalization;
    }

    public void setCapitalization(Double capitalization) {
        this.capitalization = capitalization;
    }

    public Double getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(Double priceUSD) {
        this.priceUSD = priceUSD;
    }

    public Double getPriceBTC() {
        return priceBTC;
    }

    public void setPriceBTC(Double priceBTC) {
        this.priceBTC = priceBTC;
    }

    public Double getDayVolume() {
        return dayVolume;
    }

    public void setDayVolume(Double dayVolume) {
        this.dayVolume = dayVolume;
    }

    public Double getPriceChange1h() {
        return priceChange1h;
    }

    public void setPriceChange1h(Double priceChange1h) {
        this.priceChange1h = priceChange1h;
    }

    public Double getPriceChange1d() {
        return priceChange1d;
    }

    public void setPriceChange1d(Double priceChange1d) {
        this.priceChange1d = priceChange1d;
    }

    public Double getPriceChange1w() {
        return priceChange1w;
    }

    public void setPriceChange1w(Double priceChange1w) {
        this.priceChange1w = priceChange1w;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getRedditUrl() {
        return redditUrl;
    }

    public void setRedditUrl(String redditUrl) {
        this.redditUrl = redditUrl;
    }

    public Long getCoinMarketCapId() {
        return coinMarketCapId;
    }

    public void setCoinMarketCapId(Long coinMarketCapId) {
        this.coinMarketCapId = coinMarketCapId;
    }

    public String getCoinstatsId() {
        return coinstatsId;
    }

    public void setCoinstatsId(String coinstatsId) {
        this.coinstatsId = coinstatsId;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    @Override
    public String toString() {
        return "CryptoCurrency{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", totalSupply=" + totalSupply +
                ", availableSupply=" + availableSupply +
                ", circulatingSupply=" + circulatingSupply +
                ", capitalization=" + capitalization +
                ", priceUSD=" + priceUSD +
                ", priceBTC=" + priceBTC +
                ", dayVolume=" + dayVolume +
                ", priceChange1h=" + priceChange1h +
                ", priceChange1d=" + priceChange1d +
                ", priceChange1w=" + priceChange1w +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", redditUrl='" + redditUrl + '\'' +
                ", twitterUrl='" + twitterUrl + '\'' +
                ", coinMarketCapId=" + coinMarketCapId +
                ", coinstatsId='" + coinstatsId + '\'' +
                ", contractAddress='" + contractAddress + '\'' +
                '}';
    }
}
