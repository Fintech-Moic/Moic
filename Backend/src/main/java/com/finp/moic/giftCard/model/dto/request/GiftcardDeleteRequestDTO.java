package com.finp.moic.giftCard.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * CONFIRM :: POSTMAN 테스트와 달리 프론트에서는 body에 씌워 오므로 인식하지 못하는 문제 발생 (null로 들어옴)
 *              변수 하나에 대한 처리 고민이 필요
 *              아마 어노테이션을 추가하면 될 듯한데, 우선은 DTO를 생성하겠음!
 **/
@Getter
@ToString
public class GiftcardDeleteRequestDTO {

    private String imageUrl;

    public GiftcardDeleteRequestDTO() {
    }

    @Builder
    public GiftcardDeleteRequestDTO(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
