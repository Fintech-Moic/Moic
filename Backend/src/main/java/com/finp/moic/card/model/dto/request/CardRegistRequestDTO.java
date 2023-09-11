package com.finp.moic.card.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@ToString
public class CardRegistRequestDTO {

    @NotNull
    @NotBlank
    private String company;

    @NotNull
    @NotBlank
    private String type;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String cardImage;

    @Builder
    public CardRegistRequestDTO(@NotNull String company, @NotNull String type, @NotNull String name,
                                @NotNull String cardImage) {
        this.company = company;
        this.type = type;
        this.name = name;
        this.cardImage = cardImage;
    }

}
