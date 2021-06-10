package com.nnk.springboot.services.implementation;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RuleNameServiceImpl implements RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Creates a new RuleName.
     * @param ruleName the RuleName to be created
     */
    @Override
    public void createRuleName(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
    }

    /**
     * Updates a rule name.
     * @param ruleName the rule name to be updated
     * @param id id of the ruleName to be updated
     */
    @Override
    public void updateRuleName(RuleName ruleName, Integer id) {
        RuleName updatedRuleName = getRuleNameById(id);
        updatedRuleName.setName(ruleName.getName());
        updatedRuleName.setDescription(ruleName.getDescription());
        ruleNameRepository.save(updatedRuleName);
    }

    /**
     * Gives all the rule names
     * @return all the rule names
     */
    @Override
    public List<RuleName> getAllRuleName() {
        return ruleNameRepository.findAll();
    }

    /**
     * returns a rule name from an id
     * @param id the id
     * @return the rule name
     */
    @Override
    public RuleName getRuleNameById(Integer id) throws IllegalArgumentException {
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rule name Id: " + id));
    }

    /**
     * delete a rule name from an id
     * @param id the id
     */
    @Override
    public void deleteRuleName(Integer id) throws IllegalArgumentException {
        ruleNameRepository.delete(getRuleNameById(id));
    }
}
