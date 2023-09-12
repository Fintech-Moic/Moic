package com.finp.moic.card.model.repository;

import com.finp.moic.card.model.entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCardRepository extends JpaRepository<UserCard,Long> {

}
