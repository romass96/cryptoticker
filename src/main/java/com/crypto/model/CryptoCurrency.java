package com.crypto.model;

import javax.persistence.*;
import java.lang.reflect.Method;

@Entity
@Table(name = "crypto_currencies")
public class CryptoCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "short_name", unique = true)
    private String shortName;

    @Column(name = "full_name", unique = true)
    private String fullName;

    @Column(name = "max_supply")
    private Long maxSupply;

    @Column(name = "circulating_supply")
    private Long circulatingSupply;

    @Column(name = "capitalization")
    private Double capitalization;

    @Column(name = "price")
    private Double price;

    @Column(name = "day_volume")
    private Double dayVolume;

    @Column(name = "percent_change_1h")
    private Double percentChange1h;

    @Column(name = "percent_change_24h")
    private Double percentChange24h;

    @Column(name = "percent_change_7d")
    private Double percentChange7d;

    @Column(name = "description")
    @Lob
    private String description;

    @Column(name = "logo_url")
    private String logoUrl;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(Long maxSupply) {
        this.maxSupply = maxSupply;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDayVolume() {
        return dayVolume;
    }

    public void setDayVolume(Double dayVolume) {
        this.dayVolume = dayVolume;
    }

    public Double getPercentChange1h() {
        return percentChange1h;
    }

    public void setPercentChange1h(Double percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public Double getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(Double percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public Double getPercentChange7d() {
        return percentChange7d;
    }

    public void setPercentChange7d(Double percentChange7d) {
        this.percentChange7d = percentChange7d;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public void merge(CryptoCurrency source){
        Method[] methods = this.getClass().getMethods();

        for(Method fromMethod: methods){
            if(fromMethod.getDeclaringClass().equals(this.getClass())
                    && fromMethod.getName().startsWith("get")){

                String fromName = fromMethod.getName();
                String toName = fromName.replace("get", "set");

                try {
                    Method toMetod = this.getClass().getMethod(toName, fromMethod.getReturnType());
                    Object value = fromMethod.invoke(source, (Object[])null);
                    if(value != null){
                        toMetod.invoke(this, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String toString() {
        return "CryptoCurrency{" +
                "id=" + id +
                ", shortName='" + shortName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", maxSupply=" + maxSupply +
                ", circulatingSupply=" + circulatingSupply +
                ", capitalization=" + capitalization +
                ", price=" + price +
                ", dayVolume=" + dayVolume +
                ", percentChange1h=" + percentChange1h +
                ", percentChange24h=" + percentChange24h +
                ", percentChange7d=" + percentChange7d +
                ", description='" + description + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                '}';
    }
}
