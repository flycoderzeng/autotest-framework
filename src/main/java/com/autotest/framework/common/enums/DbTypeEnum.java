package com.autotest.framework.common.enums;


public enum DbTypeEnum {
    MYSQL("mysql"), GBASE("gbase"), OPEN_GAUSS("opengauss"),
    POSTGRESQL("postgresql"), SQLSERVER("sqlserver"), ORACLE("oracle"),
    MARIADB("mariadb"), OCEAN_BASE("oceanbase");
    String value;

    DbTypeEnum(String value) {
        this.value = value;
    }

    DbTypeEnum() {
    }

    public static DbTypeEnum get(String value) {
        switch (value) {
            case "mysql":
                return DbTypeEnum.MYSQL;
            case "gbase":
                return DbTypeEnum.GBASE;
            case "opengauss":
                return DbTypeEnum.OPEN_GAUSS;
            case "postgresql":
                return DbTypeEnum.POSTGRESQL;
            case "sqlserver":
                return DbTypeEnum.SQLSERVER;
            case "oracle":
                return DbTypeEnum.ORACLE;
            case "mariadb":
                return DbTypeEnum.MARIADB;
            case "oceanbase":
                return DbTypeEnum.OCEAN_BASE;
            default:
                return null;
        }
    }

    public String val() {
        return value;
    }
}
