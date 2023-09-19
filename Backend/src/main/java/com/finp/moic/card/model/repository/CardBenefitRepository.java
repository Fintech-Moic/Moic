package com.finp.moic.card.model.repository;

import com.finp.moic.card.model.entity.CardBenefit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardBenefitRepository extends JpaRepository<CardBenefit,Long>, CardBenefitRepositoryCustom {
}
