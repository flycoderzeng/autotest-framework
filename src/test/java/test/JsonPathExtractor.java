package test;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonPathExtractor {
    public static List<String> extractJsonPaths(JSONObject jsonObject) throws JSONException {
        List<String> paths = new ArrayList<>();
        traverseJsonObject(jsonObject, "$", paths);
        return paths;
    }

    private static void traverseJsonObject(JSONObject jsonObject, String currentPath, List<String> paths) throws JSONException {
        Iterator<String> keys = jsonObject.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            String newPath = currentPath.isEmpty() ? key : currentPath + "." + key;
            Object value = jsonObject.get(key);

            if (value instanceof JSONObject) {
                traverseJsonObject((JSONObject) value, newPath, paths);
            } else if (value instanceof JSONArray) {
                traverseJsonArray((JSONArray) value, newPath, paths);
            } else {
                paths.add(newPath);
            }
        }
    }

    private static void traverseJsonArray(JSONArray jsonArray, String currentPath, List<String> paths) throws JSONException {
        for (int i = 0; i < jsonArray.size(); i++) {
            String newPath = currentPath + "[" + i + "]";
            Object value = jsonArray.get(i);

            if (value instanceof JSONObject) {
                traverseJsonObject((JSONObject) value, newPath, paths);
            } else if (value instanceof JSONArray) {
                traverseJsonArray((JSONArray) value, newPath, paths);
            } else {
                paths.add(newPath);
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
                "                   \"stage\": \"2\",\n" +
                "                   \"hasEncryptField\": 1\n" +
                "               }\n" +
                "           }";
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonString, Feature.OrderedField);
            List<String> jsonPaths = extractJsonPaths(jsonObject);
            for (String path : jsonPaths) {
                System.out.println(path);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

