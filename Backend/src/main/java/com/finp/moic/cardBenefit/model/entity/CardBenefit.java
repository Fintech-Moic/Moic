package com.finp.moic.cardBenefit.model.entity;

import com.finp.moic.card.model.entity.Card;
import com.finp.moic.util.entity.Base;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="cardBenefit")
@Table(indexes = {
        @Index(name="cardBenefit_shop", columnList = "category, shopName"),
})
@Getter
@Builder
@ToString
public class CardBenefit extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cardBenefitSeq;

    /* 혜지 : FK 확인 필요 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cardName", referencedColumnName = "name")
    private Card card;

    @Column(length = 20, nullable = false)
    private String category;

    @Column(length = 20, nullable = false)
    private String shopName;

    @Column(length = 100)
    private String content;

    @Column(length = 20)
    private String discount;

    @Column(length = 20)
    private String point;

    @Column(length = 20)
    private String cashBack;

    @Builder
    public CardBenefit(long cardBenefitSeq, Card card, String category, String shopName, String content, String discount, String point, String cashBack) {
        this.cardBenefitSeq = cardBenefitSeq;
        this.card = card;
        this.category = category;
        this.shopName = shopName;
        this.content = content;
        this.discount = discount;
        this.point = point;
        this.cashBack = cashBack;
    }

    @Builder
    public CardBenefit(BaseBuilder<?, ?> b, long cardBenefitSeq, Card card, String category, String shopName, String content, String discount, String point, String cashBack) {
        super(b);
        this.cardBenefitSeq = cardBenefitSeq;
        this.card = card;
        this.category = category;
        this.shopName = shopName;
        this.content = content;
        this.discount = discount;
        this.point = point;
        this.cashBack = cashBack;
    }

    public CardBenefit() {

    }
}
