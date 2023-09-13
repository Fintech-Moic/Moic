package com.finp.moic.card.model.repository;

import com.finp.moic.card.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {

    Optional<Card> findByName(String name);

    @Query(value = "SELECT c.is_delete as is_delete, c.created_at as created_at, " +
            "c.updated_at as updated_at, c.deleted_at as deleted_at, " +
            "c.company as company, c.type as type, " +
            "c.name as name, c.card_image as card_image, " +
            "card_seq as card_seq " +
            "FROM card AS c join user_card AS u " +
            "ON c.name = u.card_name " +
            "WHERE u.user_id = ? ", nativeQuery = true)
    List<Card> findAllCardNameByUserId(String userId);
}
