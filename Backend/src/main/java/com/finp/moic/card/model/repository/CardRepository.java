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

    @Query(value = "SELECT DISTINCT company FROM card", nativeQuery = true)
    List<String> findAllCompany();

    @Query(value = "SELECT DISTINCT type FROM card", nativeQuery = true)
    List<String> findAllType();

}
