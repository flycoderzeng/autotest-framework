package com.autotest.framework.common.entities.autotest;

import lombok.Data;

import java.util.List;
@Data
public class FieldDefine {
    public String description;
    public String type;
    public Integer minLength;
    public Integer maxLength;
    public Integer min;
    public Integer max;
    public String example;
    public String regex;
    public String includeChinese;
    public List<String> enums;
}
