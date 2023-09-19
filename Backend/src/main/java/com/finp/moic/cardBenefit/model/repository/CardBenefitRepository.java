package com.finp.moic.cardBenefit.model.repository;

import com.finp.moic.cardBenefit.model.entity.CardBenefit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardBenefitRepository extends JpaRepository<CardBenefit,Long> {
}
