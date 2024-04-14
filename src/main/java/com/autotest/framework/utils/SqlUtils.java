package com.autotest.framework.utils;

import com.autotest.framework.common.enums.DbTypeEnum;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class SqlUtils {

    public static String translateMysqlSql(String mysqlSql, DbTypeEnum desDbTypeEnum) {
        switch (desDbTypeEnum) {
            case ORACLE:
                return translateMysqlToOracle(mysqlSql);
            default:
                return mysqlSql;
        }
    }

    public static String translateMysqlToOracle(String mysqlSql) {
        if (StringUtils.isBlank(mysqlSql)) {
            return mysqlSql;
        }
        String resultSql = String.copyValueOf(mysqlSql.toCharArray()).trim();
        List<String> tableList = new ArrayList<>();
        List<String> columnList = new ArrayList<>();
        try {
            Statement statement = CCJSqlParserUtil.parse(mysqlSql);
            if (statement instanceof Select) {
                Select selectStatement = (Select) statement;
                TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
                for (String tableName : tablesNamesFinder.getTables(statement)) {
                    tableList.add(tableName);
                }
                for (SelectItem selectItem : selectStatement.getPlainSelect().getSelectItems()) {
                    Expression expression = selectItem.getExpression();
                    if (expression instanceof Column) {
                        columnList.add(((Column) expression).getColumnName());
                    }
                }
            }
            if (statement instanceof Update) {
                Update updateStatement = (Update) statement;
                tableList.add(updateStatement.getTable().getFullyQualifiedName());
                updateStatement.getColumns().forEach(item -> {
                    columnList.add(item.getColumnName());
                });
            }
            if (statement instanceof Insert) {
                Insert insertStatement = (Insert) statement;
                tableList.add(insertStatement.getTable().getFullyQualifiedName());
                insertStatement.getColumns().forEach(item -> {
                    columnList.add(item.getColumnName());
                });
            }
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        // 把表名改成大写
        Iterator<String> tableIterator = tableList.stream().sorted(Comparator.comparing(String::length).reversed()).iterator();
        List<String> tableNameList = new ArrayList<>();
        while (tableIterator.hasNext()) {
            String next = tableIterator.next();
            String finalTableName = next.toLowerCase().replace("`", "");
            resultSql = resultSql.replace(next, finalTableName);
            String[] strings = finalTableName.split("\\.");
            if (finalTableName.indexOf(".") > -1 && !strings[0].startsWith("\"")) {
                String temp = "\"" + strings[0] + "\".\"" + strings[1] + "\"";
                resultSql = resultSql.replace(finalTableName, temp);
                finalTableName = temp;
            }
            if (finalTableName.indexOf(".") == -1 && !strings[0].startsWith("\"")) {
                String temp = "\"" + strings[0] + "\"";
                resultSql = resultSql.replace(finalTableName, temp);
                finalTableName = temp;
            }
            resultSql = resultSql.replace(finalTableName, "###" + new StringBuffer(finalTableName).reverse() + "###");
            tableNameList.add(finalTableName);
        }

        // 把列名改成大写
        Iterator<String> columnIterator = columnList.stream().sorted(Comparator.comparing(String::length).reversed()).iterator();
        while (columnIterator.hasNext()) {
            String next = columnIterator.next();
            resultSql = resultSql.replace(next, next.toUpperCase());
        }

        resultSql = resultSql.replace("`", "\"");
        resultSql = resultSql.replace("\"test\"", "\"TEST\"");
        if (resultSql.endsWith(";")) {
            resultSql = resultSql.substring(0, resultSql.length() - 1);
        }

        //替换掉时间函数
        resultSql = resultSql.replace("now()", "CURRENT_TIMESTAMP");
        resultSql = resultSql.replace("NOW()", "CURRENT_TIMESTAMP");

        for (String t : tableNameList) {
            resultSql = resultSql.replace("###" + new StringBuffer(t).reverse() + "###", t);
        }

        return resultSql;
    }
}
