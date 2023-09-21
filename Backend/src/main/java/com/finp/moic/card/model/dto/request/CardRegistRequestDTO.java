package com.finp.moic.card.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@ToString
public class CardRegistRequestDTO {

    @NotNull
    @NotBlank
    private String cardName;

    /**
     * TO DO :: userId 삭제
     */
    private String userId;

    public CardRegistRequestDTO() {
    }

    @Builder
    public CardRegistRequestDTO(@NotNull String cardName, String userId) {
        this.cardName = cardName;
        this.userId=userId;
    }
}
