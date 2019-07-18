package com.crypto.model;

import javax.persistence.*;

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

    private Long capitalization;

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

    public Long getCapitalization() {
        return capitalization;
    }

    public void setCapitalization(Long capitalization) {
        this.capitalization = capitalization;
    }

    @Override
    public String toString() {
        return "CryptoCurrency{" +
                "id=" + id +
                ", shortName='" + shortName + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
