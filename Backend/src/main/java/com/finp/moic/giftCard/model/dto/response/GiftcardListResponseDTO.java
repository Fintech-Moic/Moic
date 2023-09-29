package com.finp.moic.giftCard.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class GiftcardListResponseDTO {

    String id;
    String imageUrl;
    LocalDate dueDate;

    /* 혜지 : Giftcard 상세 조회 API 수정 위해 UUID 추가.
            UUID 수정으로 인해 Builder 어노테이션 이동. 확인 후 주석 삭제 요망! */
    @QueryProjection
    @Builder
    public GiftcardListResponseDTO(UUID id, String imageUrl, LocalDate dueDate) {
        this.id=id.toString();
        this.imageUrl = imageUrl;
        this.dueDate=dueDate;
    }
}
