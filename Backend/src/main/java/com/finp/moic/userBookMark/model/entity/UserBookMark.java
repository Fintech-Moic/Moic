package com.finp.moic.userBookMark.model.entity;

import com.finp.moic.shop.model.entity.Shop;
import com.finp.moic.user.model.entity.User;
import com.finp.moic.util.entity.Base;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="userBookMark")
@Table
@Getter
@Builder
@ToString
public class UserBookMark extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userBookMarkSeq;

    /* 혜지 : FK 확인 필요 (양방향) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId",referencedColumnName = "id")
    private User user;

    /* 혜지 : FK 확인 필요 (양방향) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shopSeq", referencedColumnName = "shopSeq")
    private Shop shop;

    @Builder
    public UserBookMark(long userBookMarkSeq, User user, Shop shop) {
        this.userBookMarkSeq = userBookMarkSeq;
        this.user = user;
        this.shop = shop;
    }

    public UserBookMark() {
    }

    @Builder
    public UserBookMark(BaseBuilder<?, ?> b, long userBookMarkSeq, User user,
                        Shop shop) {
        super(b);
        this.userBookMarkSeq = userBookMarkSeq;
        this.user = user;
        this.shop = shop;
    }

}
