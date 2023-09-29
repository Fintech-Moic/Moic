package com.finp.moic.giftCard.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GiftcardListResponseDTO {

    String imageUrl;
    LocalDate dueDate;

    @QueryProjection
    public GiftcardListResponseDTO(String imageUrl, LocalDate dueDate) {
        this.imageUrl = imageUrl;
        this.dueDate=dueDate;
    }
}
