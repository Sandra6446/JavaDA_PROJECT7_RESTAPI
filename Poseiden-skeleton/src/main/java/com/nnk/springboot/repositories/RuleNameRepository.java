package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.entity.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Creates, reads, updates and deletes Rulenames in database
 *
 * @see RuleName
 */
@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
