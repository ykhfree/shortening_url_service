package com.ykh.shorteningurl;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "url_entity", indexes = {@Index(name="idx_origin_url", unique = true, columnList = "origin_url")})
public class UrlEntity implements Serializable {

    private static final long serialVersionUID = -1675481506678944751L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(name = "origin_url", length = 2048, nullable = false)
    private String originUrl;

    @Column(name = "count")
    private long count;

    @Column(name = "insert_date")
    private Date insertDate = new Date();

    @Column(name = "update_date")
    private Date updateDate = new Date();

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public UrlEntity buildWithUrl(String originUrl) {
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginUrl(originUrl);

        return urlEntity;
    }
}
