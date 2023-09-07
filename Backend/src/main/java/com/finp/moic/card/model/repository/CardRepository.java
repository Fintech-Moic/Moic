package com.finp.moic.card.model.repository;

import com.finp.moic.card.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card,Long> {
    Optional<Card> findByName(String name);
}
