package com.finp.moic.userBookmark.model.repository;

import com.finp.moic.userBookmark.model.dto.response.UserBookmarkLookupResponseDTO;
import com.finp.moic.userBookmark.model.entity.UserBookmark;

import java.util.List;
import java.util.Optional;

public interface UserBookmarkRepositoryCustom {

    boolean exist(String id, String name, String location);

    Optional<UserBookmark> findByUserIdAndShopSeq(String id, String shopName, String shopLocation);

    List<UserBookmarkLookupResponseDTO> findAllByUserId(String userId);
}
