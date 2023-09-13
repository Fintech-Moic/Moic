package com.finp.moic.card.model.repository;

import com.finp.moic.card.model.entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCardRepository extends JpaRepository<UserCard,Long> {

    @Query(value = "SELECT card_name FROM user_card WHERE user_id= :userid", nativeQuery = true)
    List<UserCard> findAllCardNameByUserId(@Param("userid") String userId);
}
