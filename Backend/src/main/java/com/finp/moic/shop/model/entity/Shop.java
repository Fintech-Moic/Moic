package com.finp.moic.shop.model.entity;

import com.finp.moic.userBookMark.model.entity.UserBookMark;
import com.finp.moic.util.entity.Base;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name="shop")
@Table
@Getter
@Builder
@ToString
public class Shop extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long shopSeq;

    @Column(length = 20, nullable = false)
    private String mainCategory;

    @Column(length = 20, nullable = false)
    private String category;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String location;

    @Column(length = 50, nullable = false)
    private String address;

    @Column(nullable = false)
    private long latitude;

    @Column(nullable = false)
    private long longitude;

    @OneToMany(mappedBy = "shop")
    private List<UserBookMark> userBookMarks;

    @Builder
    public Shop(long shopSeq, String mainCategory, String category, String name, String location, String address, long latitude, long longitude, List<UserBookMark> userBookMarks) {
        this.shopSeq = shopSeq;
        this.mainCategory = mainCategory;
        this.category = category;
        this.name = name;
        this.location = location;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userBookMarks = userBookMarks;
    }

    @Builder
    public Shop(BaseBuilder<?, ?> b, long shopSeq, String mainCategory, String category, String name, String location, String address, long latitude, long longitude, List<UserBookMark> userBookMarks) {
        super(b);
        this.shopSeq = shopSeq;
        this.mainCategory = mainCategory;
        this.category = category;
        this.name = name;
        this.location = location;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userBookMarks = userBookMarks;
    }

    public Shop() {

    }
}
