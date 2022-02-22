package com.nnk.springboot.domain.entity;

import com.nnk.springboot.domain.dto.RuleNameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public RuleName(RuleNameDto ruleNameDto) {
        this.id = ruleNameDto.getId();
        this.name = ruleNameDto.getName();
        this.description = ruleNameDto.getDescription();
        this.json = ruleNameDto.getJson();
        this.template = ruleNameDto.getTemplate();
        this.sqlStr = ruleNameDto.getSqlStr();
        this.sqlPart = ruleNameDto.getSqlPart();
    }

    public void updateFromDto(RuleNameDto ruleNameDto) {
        if (ruleNameDto.getId().equals(this.id)) {
            this.name = ruleNameDto.getName();
            this.description = ruleNameDto.getDescription();
            this.json = ruleNameDto.getJson();
            this.template = ruleNameDto.getTemplate();
            this.sqlStr = ruleNameDto.getSqlStr();
            this.sqlPart = ruleNameDto.getSqlPart();
        }
    }
}
