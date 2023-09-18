package com.finp.moic.card.model.repository;

import com.finp.moic.card.model.entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCardRepository extends JpaRepository<UserCard,Long>, UserCardRepositoryCustom {
    Optional<UserCard> findByCardName(String cardName);

}
