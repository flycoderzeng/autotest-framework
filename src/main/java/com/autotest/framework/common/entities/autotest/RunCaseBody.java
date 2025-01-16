package com.autotest.framework.common.entities.autotest;


import lombok.Data;

import java.util.List;

@Data
public class RunCaseBody {
    private String envName;
    private List<String> caseIds;
}
