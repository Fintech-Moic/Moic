package com.finp.moic.shop.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BenefitResponseDTO {

    private String cardName;
    private String content;
    private String discount;
    private String point;
    private String cashBack;

    public BenefitResponseDTO() {
    }

    @Builder
    public BenefitResponseDTO(String cardName, String content,
                              String discount, String point, String cashBack) {
        this.cardName = cardName;
        this.content = content;
        this.discount = discount;
        this.point = point;
        this.cashBack = cashBack;
    }
}
