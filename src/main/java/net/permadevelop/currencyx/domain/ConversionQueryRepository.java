package net.permadevelop.currencyx.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ConversionQueryRepository extends JpaRepository<ConversionQuery, Long> {
    Collection<ConversionQuery> findTop10ByUserOrderByCreationTimestampDesc(Account user);
}
