package com.finp.moic.userBookmark.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class UserBookmarkDeleteRequestDTO {

    private List<ShopRequestDTO> shopList;

    private String userId;

    public UserBookmarkDeleteRequestDTO() {
    }

    @Builder
    public UserBookmarkDeleteRequestDTO(List<ShopRequestDTO> shopList, String userId) {
        this.shopList = shopList;
        this.userId = userId;
    }
}
