package test;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.util.*;

public class JsonPathReplacer {

    public static void replaceJsonValuesWithPaths(JSONObject jsonObject, Map<String, Object> pathValueMap) throws JSONException {
        traverseJsonObject(jsonObject, "$", pathValueMap);
    }

    private static void traverseJsonObject(JSONObject jsonObject, String currentPath, Map<String, Object> pathValueMap) throws JSONException {
        Iterator<String> keys = jsonObject.keySet().iterator();
        List<String> keyList = new ArrayList<>();
        while (keys.hasNext()) {
            keyList.add(keys.next());
        }

        for (String key : keyList) {
            String newPath = currentPath.isEmpty() ? key : currentPath + "." + key;
            Object value = jsonObject.get(key);

            if (value instanceof JSONObject) {
                traverseJsonObject((JSONObject) value, newPath, pathValueMap);
            } else if (value instanceof JSONArray) {
                traverseJsonArray((JSONArray) value, newPath, pathValueMap);
            } else {
                jsonObject.put(key, "${" + newPath + "}");
                pathValueMap.put(newPath, value);
            }
        }
    }

    private static void traverseJsonArray(JSONArray jsonArray, String currentPath, Map<String, Object> pathValueMap) throws JSONException {
        for (int i = 0; i < jsonArray.size(); i++) {
            String newPath = currentPath + "[" + i + "]";
            Object value = jsonArray.get(i);

            if (value instanceof JSONObject) {
                traverseJsonObject((JSONObject) value, newPath, pathValueMap);
            } else if (value instanceof JSONArray) {
                traverseJsonArray((JSONArray) value, newPath, pathValueMap);
            } else {
                jsonArray.set(i, "${" + newPath + "}");
                pathValueMap.put(newPath, value);
            }
        }
    }

    public static void main(String[] args) {
        String jsonString = "{\n" +
                "               \"applicationId\": \"65a8f41dfd98fc576cc19885\",\n" +
                "               \"metadataStructureIds\": [\"660cf2d3ae41480c5853d12c\"],\n" +
                "               \"encryptPolicy\": {\n" +
                "                   \"secretKeyCode\": \"1711529818868601\",\n" +
                "                   \"encryptAlgorithm\": \"SM4\",\n" +
                "                   \"typeCode\": \"105\",\n" +
                "                   \"encryptMode\": \"ECB\",\n" +
                "                   \"columnFormat\": \"3.3\",\n" +
                "                   \"stage\": \"2\",\n" +
                "                   \"hasEncryptField\": 1\n" +
                "               },\n" +
                "    \"hashPolicy\": {\n" +
                "        \"hashAlgorithm\": \"HMAC-SM3\",\n" +
                "        \"hashSecretKeyCode\": \"1712657286705101\",\n" +
                "        \"hashLen\": 8,\n" +
                "        \"hashVerify\": 1,\n" +
                "        \"hasHashField\": 1\n" +
                "    }\n" +
                "           }";
        try {
            Map<String, Object> pathValueMap = new HashMap<>();
            JSONObject jsonObject = JSONObject.parseObject(jsonString, Feature.OrderedField);
            replaceJsonValuesWithPaths(jsonObject, pathValueMap);
            System.out.println(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
