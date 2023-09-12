package com.finp.moic.card.model.entity;

import com.finp.moic.cardBenefit.model.entity.CardBenefit;
import com.finp.moic.util.entity.Base;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name="card")
@Table(indexes = {
 @Index(name = "card_name", columnList = "name"),
})
@Getter
@Builder
public class Card extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cardSeq;

    @Column(length = 20, nullable = false)
    private String company;

    @Column(length = 10, nullable = false)
    private String type;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String cardImage;

    @OneToMany(mappedBy = "card")
    private List<CardBenefit> cardBenefits;

    public Card() {
    }

    @Builder
    public Card(long cardSeq, String company, String type,
                String name, String cardImage, List<CardBenefit> cardBenefits) {
        this.cardSeq = cardSeq;
        this.company = company;
        this.type = type;
        this.name = name;
        this.cardImage = cardImage;
        this.cardBenefits = cardBenefits;
    }

    @Builder
    public Card(BaseBuilder<?, ?> b, long cardSeq, String company,
                String type, String name, String cardImage,
                List<CardBenefit> cardBenefits) {
        super(b);
        this.cardSeq = cardSeq;
        this.company = company;
        this.type = type;
        this.name = name;
        this.cardImage = cardImage;
        this.cardBenefits = cardBenefits;
    }

    @Override
    public String toString() {
        return "Card{" +
//                "cardSeq=" + cardSeq +
                ", company='" + company + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", cardImage='" + cardImage + '\'' +
                '}';
    }
}
