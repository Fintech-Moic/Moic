package com.finp.moic.card.model.repository;

import com.finp.moic.card.model.entity.Card;
import com.finp.moic.card.model.entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCardRepository extends JpaRepository<UserCard,Long> {

    @Query(value = "SELECT card.company, card.type, card.name, card.cardImage " +
            "FROM card join userCard " +
            "ON card.name=userCard.cardName " +
            "WHERE userCard.userId=?",
    nativeQuery = true)
    List<Card> findAllByUserId(String userId);

}
