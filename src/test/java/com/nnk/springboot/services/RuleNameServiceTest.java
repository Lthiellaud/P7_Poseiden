package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class RuleNameServiceTest {

    @MockBean
    private RuleNameRepository ruleNameRepository;

    @Autowired
    private RuleNameService ruleNameService;

    private static RuleName ruleName;

    @BeforeEach
    public void initTest() {
        ruleName = new RuleName();
        ruleName.setName("Rule name");
        ruleName.setDescription("Rule description");        
        ruleName.setId(1);
    }

    @Test
    void createRuleNameTest() {
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);
        ruleNameService.createRuleName(ruleName);

        verify(ruleNameRepository, times(1)).save(any(RuleName.class));
    }

    @Test
    void updateRuleNameTest() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);
        ruleNameService.updateRuleName(ruleName, 1);

        verify(ruleNameRepository, times(1)).save(any(RuleName.class));
    }

    @Test
    void getRuleNameByIdTest() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        RuleName ruleName1 = ruleNameService.getRuleNameById(1);

        verify(ruleNameRepository, times(1)).findById(1);
        assertThat(ruleName1).isEqualTo(ruleName);
    }

    @Test
    void getRuleNameByIdNotFoundedTest() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ruleNameService.getRuleNameById(1));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid rule name Id: 1");
        verify(ruleNameRepository, times(1)).findById(1);

    }

    @Test
    void deleteRuleNameNotFoundedTest() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ruleNameService.deleteRuleName(1));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid rule name Id: 1");
        verify(ruleNameRepository, times(1)).findById(1);

    }
}