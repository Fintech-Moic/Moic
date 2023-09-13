package com.finp.moic.card.model.entity;

import com.finp.moic.card.model.entity.Card;
import com.finp.moic.user.model.entity.User;
import com.finp.moic.util.entity.Base;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="user_card")
@Table
@Getter
@Builder
public class UserCard extends Base {

    @Id
    @Column(name="user_card_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userCardSeq;

    /* 혜지 : FK 확인 필요 (양방향) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    /* 혜지 : FK 확인 필요 (단방향) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="card_name", referencedColumnName = "name")
    private Card card;

    public UserCard() {
    }

    @Builder
    public UserCard(long userCardSeq, User user, Card card) {
        this.userCardSeq = userCardSeq;
        this.user = user;
        this.card = card;
    }

    @Override
    public String toString() {
        return "UserCard{" +
                "userCardSeq=" + userCardSeq +
                ", user="+user.getId() +
                ", card="+card.getName() +
                '}';
    }
}
