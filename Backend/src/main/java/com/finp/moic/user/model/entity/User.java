package com.finp.moic.user.model.entity;

import com.finp.moic.giftCard.model.entity.GiftCard;
import com.finp.moic.userBookMark.model.entity.UserBookMark;
import com.finp.moic.card.model.entity.UserCard;
import com.finp.moic.util.entity.Base;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name="user")
@Table(indexes = {
        @Index(name = "user_id",columnList = "id"),
        @Index(name = "user_email",columnList = "email"),
})
@Builder
@Getter
@ToString
public class User extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userSeq;

    @Column(length = 20, unique = true, nullable = false)
    private String id;

    @Column(length = 500, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String email;

    @Column(length = 10)
    private String gender;

    @Column
    private int yearOfBirth;

    @OneToMany(mappedBy = "user")
    private List<GiftCard> giftCards;

    @OneToMany(mappedBy = "user")
    private List<UserCard> userCards;

    @OneToMany(mappedBy = "user")
    private List<UserBookMark> userBookMarks;

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    @Builder
    public User(long userSeq, String id, String password,
                String name, String email, String gender,
                int yearOfBirth, List<GiftCard> giftCards, List<UserCard> userCards,
                List<UserBookMark> userBookMarks) {
        this.userSeq = userSeq;
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.yearOfBirth = yearOfBirth;
        this.giftCards = giftCards;
        this.userCards = userCards;
        this.userBookMarks = userBookMarks;
    }

}
