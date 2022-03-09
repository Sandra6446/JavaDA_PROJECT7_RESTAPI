package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.entity.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Creates, reads, updates and deletes Bidlists in database
 *
 * @see BidList
 */
@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
