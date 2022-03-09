package com.nnk.springboot.domain.entity;

import com.nnk.springboot.domain.dto.RuleNameDto;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Represents a ruleName (mapping class)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

    /**
     * Build a RuleName with RuleNameDto values
     *
     * @param ruleNameDto : The RuleNameDto with values to be copied
     */
    public RuleName(RuleNameDto ruleNameDto) {
        this.id = ruleNameDto.getId();
        this.name = ruleNameDto.getName();
        this.description = ruleNameDto.getDescription();
        this.json = ruleNameDto.getJson();
        this.template = ruleNameDto.getTemplate();
        this.sqlStr = ruleNameDto.getSqlStr();
        this.sqlPart = ruleNameDto.getSqlPart();
    }

    /**
     * Updates a RuleName with RuleNameDto values
     *
     * @param ruleNameDto : The RuleNameDto with values to be updated
     */
    public void updateFromDto(RuleNameDto ruleNameDto) {
        if (Objects.equals(ruleNameDto.getId(), this.getId())) {
            this.name = ruleNameDto.getName();
            this.description = ruleNameDto.getDescription();
            this.json = ruleNameDto.getJson();
            this.template = ruleNameDto.getTemplate();
            this.sqlStr = ruleNameDto.getSqlStr();
            this.sqlPart = ruleNameDto.getSqlPart();
        }
    }
}
