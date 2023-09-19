package com.finp.moic.userBookmark.model.repository;

import com.finp.moic.userBookmark.model.entity.UserBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBookmarkRepository extends JpaRepository<UserBookmark,Long> {
}
