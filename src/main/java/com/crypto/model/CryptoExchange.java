package com.crypto.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "crypto_exchanges")
public class CryptoExchange extends PersistedObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "web_site")
    private String webSite;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_launched")
    private Date dateLaunched;

    @Column(name = "logo_url")
    private String logoUrl;

    public CryptoExchange(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Date getDateLaunched() {
        return dateLaunched;
    }

    public void setDateLaunched(Date dateLaunched) {
        this.dateLaunched = dateLaunched;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
