package com.autotest.framework.nodes;

import com.autotest.framework.AutoTestContext;
import com.autotest.framework.adapter.DataTypeAdapter;
import com.autotest.framework.adapter.LocalDateTimeAdapter;
import com.autotest.framework.adapter.TimeTypeAdapter;
import com.autotest.framework.adapter.TimestampTypeAdapter;
import com.autotest.framework.antlr.expression.core.ExpressionLexer;
import com.autotest.framework.antlr.expression.core.ExpressionParser;
import com.autotest.framework.antlr.expression.visitor.AssertExpressionVisitor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.lang3.StringUtils;

import java.net.HttpCookie;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.autotest.framework.utils.PairwiseTestUtils.randomStringByRegex;

@Slf4j
@Data
public abstract class StepNode {

    public static final Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().registerTypeAdapter(new TypeToken<Map<String, Object>>() {
            }.getType(),
            new DataTypeAdapter())
            .registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter())
            .registerTypeAdapter(Time.class, new TimeTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static final String BUILTIN_VARIABLE_RESPONSE = "__response";
    public static final String BUILTIN_VARIABLE_STOP_RUN_CASE = "__stop_run_case";
    public static final String BUILTIN_VARIABLE_IN_IF_LOGIC = "__in_if_logic";
    public static final String BUILTIN_VARIABLE_IN_IF_RESULT = "__in_if_result";
    public static final String BUILTIN_VARIABLE_UPDATED_ROWS = "__updated_rows";
    public static final String BUILTIN_VARIABLE_GENERATED_KEY = "__generated_key";
    // 密码运算结果码
    public static final String BUILTIN_VARIABLE_ENC_DEC_CODE = "__enc_dec_code";
    public static final String BUILTIN_VARIABLE_CIPHERTEXT = "__ciphertext";
    public static final String BUILTIN_VARIABLE_DECRYPTED_PLAINTEXT = "__decrypted_plaintext";
    public static final String BUILTIN_VARIABLE_OUTPUT_CIPHER_LEN = "__output_cipher_len";

    public static final Pattern TEMPLATE_CASE_VARIABLE_PATTERN = Pattern.compile("\\$\\{(.*?)\\}");
    public static final Pattern TEMPLATE_GLOBAL_VARIABLE_PATTERN = Pattern.compile("#\\{(.*?)\\}");
    public static final Pattern TEMPLATE_REGEX_PATTERN = Pattern.compile("@/(.*?)/");

    public static final Pattern TEMPLATE_PATH_VARIABLE_PATTERN = Pattern.compile("%\\{(.*?)\\}");

    protected String stepName;
    protected AutoTestContext autoTestContext;
    protected Integer stepSeq;
    protected Map<String, String> headers;
    protected Collection<HttpCookie> cookies;

    protected StepNode() {
    }

    protected StepNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext) {
        this.stepSeq = stepSeq;
        this.stepName = stepName;
        this.autoTestContext = autoTestContext;
        this.headers = autoTestContext.headers;
        this.cookies = autoTestContext.cookies;
    }

    public abstract void run() throws Exception;

    protected boolean skip() {
        Boolean inConditionLogic = (Boolean) autoTestContext.getVariableObject(BUILTIN_VARIABLE_IN_IF_LOGIC);
        Stack<Boolean> ifLogicStack = (Stack<Boolean>) autoTestContext.getVariableObject(BUILTIN_VARIABLE_IN_IF_RESULT);
        if (inConditionLogic == null || ifLogicStack == null || ifLogicStack.isEmpty()) return false;
        if (inConditionLogic && ifLogicStack != null && !ifLogicStack.isEmpty()) {
            Boolean skip = ifLogicStack.peek();
            if(skip == null || !skip) {
                log.info("不满足执行条件, 跳过此步骤");
                return true;
            }
        }
        return false;
    }

    protected String replacePlaceholder(String src) {
        if (StringUtils.isBlank(src)) {
            return src;
        }
        src = replacePlaceholder(src, TEMPLATE_CASE_VARIABLE_PATTERN, 1);
        src = replacePlaceholder(src, TEMPLATE_GLOBAL_VARIABLE_PATTERN, 2);
        src = replacePlaceholder(src, TEMPLATE_REGEX_PATTERN, 3);
        src = src.replace("\"__NULL__\"", "null");
        src = src.replace("__NULL__", "null");
        return src;
    }

    protected String replacePathPlaceholder(String src) {
        if (StringUtils.isBlank(src)) {
            return src;
        }
        src = replacePlaceholder(src, TEMPLATE_PATH_VARIABLE_PATTERN, 4);
        return src;
    }

    protected String replacePlaceholder(String src, Pattern pattern, int type) {
        if (StringUtils.isBlank(src)) {
            return src;
        }
        Matcher m = pattern.matcher(src);
        List<String> listExpr = new ArrayList<>();
        while (m.find()) {
            listExpr.add(m.group(1));
        }
        for (String expr : listExpr) {
            if (!StringUtils.isBlank(expr)) {
                if (type == 1) {
                    src = src.replace("${" + expr + "}", autoTestContext.getVariableString(expr));
                }
                if (type == 2) {
                    src = src.replace("#{" + expr + "}", autoTestContext.getGlobalVariableString(expr));
                }
                if (type == 3) {
                    src = src.replace("@/" + expr + "/", randomStringByRegex(expr));
                }
                if (type == 4) {
                    src = src.replace("%{" + expr + "}", autoTestContext.getVariableString(expr));
                }
            }
        }
        return src;
    }

    protected List<String> extractVariableName(String src, Pattern pattern) {
        Matcher m = pattern.matcher(src);
        List<String> listVariableName = new ArrayList<>();
        while (m.find()) {
            listVariableName.add(m.group(1));
        }
        return listVariableName;
    }

    protected Object execExpression(String expression) {
        CharStream input = CharStreams.fromString(expression);
        ExpressionLexer lexer = new ExpressionLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExpressionParser parser = new ExpressionParser(tokens);
        ExpressionParser.ExpressionContext context = parser.expression();
        AssertExpressionVisitor visitor = new AssertExpressionVisitor(autoTestContext);
        return visitor.visit(context);
    }
}
