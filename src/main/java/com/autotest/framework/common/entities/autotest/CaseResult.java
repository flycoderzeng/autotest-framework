package com.autotest.framework.common.entities.autotest;

import lombok.Data;

@Data
public class CaseResult {
    private String caseId = "";
    private String testClass = "";
    private String testMethod = "";
    private String caseName = "";
    private String caseDescription = "";
    private long startMillis;
    private long endMillis;
    private long costTime;
    private String resultLog = "";
    private String status;
}
