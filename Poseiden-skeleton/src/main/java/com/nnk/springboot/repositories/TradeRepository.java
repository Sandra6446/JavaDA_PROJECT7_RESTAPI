package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Creates, reads, updates and deletes Trades in database
 *
 * @see Trade
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
