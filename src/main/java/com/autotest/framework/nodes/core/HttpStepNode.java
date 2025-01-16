package com.autotest.framework.nodes.core;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.autotest.framework.AutoTestContext;
import com.autotest.framework.nodes.StepNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Data
public class HttpStepNode extends StepNode {

    protected String uri;
    protected String requestType;
    protected String httpTokenType;
    protected String body;
    protected String saveFileName;
    protected Map<String, Object> requestMap = new HashMap<>();

    public HttpStepNode() {
    }

    public HttpStepNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext,
                        String httpTokenType, String requestType, String uri) {
        super(stepSeq, stepName, autoTestContext);
        this.httpTokenType = httpTokenType;
        this.requestType = requestType;
        this.uri = uri;
    }

    public HttpStepNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext,
                        String httpTokenType, String requestType, String uri,
                        String bodyOrSaveFileName) {
        super(stepSeq, stepName, autoTestContext);
        this.httpTokenType = httpTokenType;
        this.requestType = requestType;
        this.uri = uri;
        this.body = bodyOrSaveFileName;
        if("GET".equals(requestType)) {
            this.saveFileName = body;
            this.body = null;
        }
    }

    public HttpStepNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext,
                        String httpTokenType, String requestType, String uri,
                        Map<String, Object> requestMap) {
        super(stepSeq, stepName, autoTestContext);
        this.httpTokenType = httpTokenType;
        this.requestType = requestType;
        this.uri = uri;
        this.requestMap = requestMap;
    }

    public HttpStepNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext,
                        String httpTokenType, String requestType, String uri,
                        String body, String saveFileName) {
        super(stepSeq, stepName, autoTestContext);
        this.httpTokenType = httpTokenType;
        this.requestType = requestType;
        this.uri = uri;
        this.body = body;
        this.saveFileName = saveFileName;
    }

    @Override
    public void run() throws Exception {
        log.info("===========>执行步骤: {}", stepName);
        if (skip()) {
            return;
        }
        String url = replacePlaceholder(this.uri);
        if(!url.startsWith("http")) {
            url = "https://" + autoTestContext.userTestContext.platformMasterServerIp + ":8443" + url;
        }
        if(StringUtils.equals("application_token", httpTokenType)) {
            url = url.replace(":8443", "");
        }
        if(url.contains("/openApis/")) {
            url = url.replace(":8443", ":9443");
        }

        HttpRequest httpRequest = HttpUtil.createGet(url);
        if (StringUtils.equals("POST", requestType)) {
            log.info("发送 POST 请求");
            httpRequest = HttpUtil.createPost(url);
        }

        String messagesDigest = null;
        String actualBody = null;
        if (StringUtils.isNoneBlank(body)) {
            actualBody = replacePlaceholder(body);
            httpRequest.body(actualBody);
        }

        log.info("请求地址: {}", url);

        if (headers != null && !headers.isEmpty()) {
            httpRequest.addHeaders(headers);
        }
        if (cookies != null && !cookies.isEmpty()) {
            httpRequest.cookie(cookies);
        }

        final StringBuilder logHeaders = new StringBuilder("Request Headers: \r\n");
        httpRequest.headers().forEach((k, v) -> logHeaders.append(String.format("%s: %s%n", k, v)));
        log.info(logHeaders.toString());
        if (StringUtils.equals("POST", requestType)) {
            log.info("请求内容:\r\n{}", actualBody);
        }

        HttpResponse response = httpRequest.execute();

        log.info(response.toString());
        Assert.assertEquals(response.getStatus(), 200);

        if(StringUtils.isNoneBlank(saveFileName)) {
            InputStream inputStream = response.bodyStream();
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            String saveFilePath = saveFileName;
            File targetFile = new File(saveFilePath);
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
            outStream.close();
        }

        autoTestContext.putVariableObject(BUILTIN_VARIABLE_RESPONSE, response.body());
    }
}
