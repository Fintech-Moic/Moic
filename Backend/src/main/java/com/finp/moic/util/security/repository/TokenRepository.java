package com.finp.moic.util.security.repository;

import com.finp.moic.util.security.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
}
