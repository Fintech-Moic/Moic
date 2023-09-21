package com.finp.moic.card.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CardDeleteRequestDTO {

    @NotNull
    @NotBlank
    private String cardName;

    /**
     * TO DO :: userId 삭제
     */
    private String userId;

    public CardDeleteRequestDTO() {
    }

    @Builder
    public CardDeleteRequestDTO(@NotNull String cardName, String userId) {
        this.cardName = cardName;
        this.userId=userId;
    }

}
