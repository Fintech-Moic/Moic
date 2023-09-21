package com.finp.moic.card.model.repository.jpa;

import com.finp.moic.card.model.entity.UserCard;
import com.finp.moic.card.model.repository.queryDSL.UserCardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCardRepository extends JpaRepository<UserCard,Long>, UserCardRepositoryCustom {

    /**
     * TO DO :: 필요한 칼럼만 받고, DTO로 리턴하도록 수정
     **/
    Optional<UserCard> findByCardName(String cardName);

}
