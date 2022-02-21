package com.nnk.springboot.domain.entity;

import com.nnk.springboot.domain.dto.RuleNameDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RuleNameTest {

    @Test
    public void updateFromDto() {
        RuleNameDto ruleNameDto = new RuleNameDto(1, "Name", "Description", "Json", "Template", "SqlStr", "SqlPart");
        RuleName ruleName = new RuleName(1, "Name2", "Description2", "Json", "Template", "SqlStr", "SqlPart");
        RuleName updatedRuleName = new RuleName(1, "Name", "Description", "Json", "Template", "SqlStr", "SqlPart");

        ruleName.updateFromDto(ruleNameDto);
        Assert.assertEquals(updatedRuleName, ruleName);

        // RuleNameDto with bad id
        ruleNameDto.setId(2);
        ruleNameDto.setDescription("New description");
        ruleName.updateFromDto(ruleNameDto);
        Assert.assertEquals(updatedRuleName, ruleName);
    }
}