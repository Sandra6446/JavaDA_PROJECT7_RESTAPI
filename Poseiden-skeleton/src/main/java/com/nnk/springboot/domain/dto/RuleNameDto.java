package com.nnk.springboot.domain.dto;

import com.nnk.springboot.domain.entity.RuleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleNameDto {

    private Integer id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Description is mandatory")
    private String description;
    @NotBlank(message = "Json is mandatory")
    private String json;
    @NotBlank(message = "Template is mandatory")
    private String template;
    @NotBlank(message = "SqlStr is mandatory")
    private String sqlStr;
    @NotBlank(message = "SqlPart is mandatory")
    private String sqlPart;

    public RuleNameDto(RuleName ruleName) {
        this.id = ruleName.getId();
        this.name = ruleName.getName();
        this.description = ruleName.getDescription();
        this.json = ruleName.getJson();
        this.template = ruleName.getTemplate();
        this.sqlStr = ruleName.getSqlStr();
        this.sqlPart = ruleName.getSqlPart();
    }
}
