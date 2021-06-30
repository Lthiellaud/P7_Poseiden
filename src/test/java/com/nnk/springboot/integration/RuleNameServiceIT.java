package com.nnk.springboot.integration;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Sql("/sql/data.sql")
public class RuleNameServiceIT {

    @Autowired
    private RuleNameService ruleNameService;

    @Test
    public void createRuleNameTest() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setName("Rule name");
        ruleName.setDescription("Rule description");
        ruleName.setJson("Json");
        ruleName.setTemplate("Temp");
        ruleName.setSqlStr("Sql");
        ruleName.setSqlPart("Sql");

        ruleNameService.createRuleName(ruleName);

        assertThat(ruleNameService.getRuleNameById(3).getName()).isEqualTo("Rule name");
    }

    @Test
    public void updateRuleNameTest() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setName("New rule name");
        ruleName.setDescription("Rule description");
        ruleName.setJson("");
        ruleName.setTemplate("");
        ruleName.setSqlStr("");
        ruleName.setSqlPart("");

        //`json`, `template`, `sqlStr`, `sqlPart`
        ruleNameService.updateRuleName(ruleName, 1);

        assertThat(ruleNameService.getRuleNameById(1).getName()).isEqualTo("New rule name");
        assertThat(ruleNameService.getRuleNameById(1).getDescription()).isEqualTo("Rule description");
        assertThat(ruleNameService.getRuleNameById(1).getJson()).isEqualTo("");

    }

    @Test
    public void deleteRuleNameTest() throws Exception {
        ruleNameService.deleteRuleName(2);

        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ruleNameService.getRuleNameById(2));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid rule name Id: 2");

    }
}
