package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * gives access to RuleName records.
 */
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
