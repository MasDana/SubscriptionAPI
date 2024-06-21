package com.dana;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParsingTool {

    public static <T> T jsonParsing(String json, Class<T> valueType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, valueType);
    }

    public static JsonNode jsonParsing(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(json);
    }

    public static String[] pemisahString(String text, String delimiter) {
        return Arrays.stream(text.split(delimiter)).filter(value -> value != null && value.length() > 0).toArray(String[]::new);
    }

    public static String parseInputStream(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        int b;
        StringBuilder buffer = new StringBuilder();
        while ((b = bufferedReader.read()) != -1) {
            buffer.append((char) b);
        }

        bufferedReader.close();
        inputStreamReader.close();

        return buffer.toString();
    }

    public static String queryParsing(String requestQuery) {
        if (requestQuery == null || requestQuery.isEmpty()) {
            return null;
        }

        try {
            String[] queries = requestQuery.split("&");

            // Jika query memiliki 3 bagian, asumsikan format field, condition, value
            if (queries.length == 3) {
                String field = "";
                String condition = "";
                String value = "";

                for (String query : queries) {
                    String[] queryParts = query.split("=");
                    if (queryParts.length != 2) {
                        return null;
                    }
                    String queryKey = queryParts[0];
                    String queryValue = queryParts[1];

                    switch (queryKey) {
                        case "f":
                            field = queryValue;
                            break;
                        case "c":
                            switch (queryValue) {
                                case "greaterEqual":
                                    condition = ">=";
                                    break;
                                case "greater":
                                    condition = ">";
                                    break;
                                case "lessEqual":
                                    condition = "<=";
                                    break;
                                case "less":
                                    condition = "<";
                                    break;
                                case "equal":
                                    condition = "=";
                                    break;
                                case "notEqual":
                                    condition = "<>";
                                    break;
                                case "true":
                                    condition = "1"; // Mengubah true menjadi 1
                                    break;
                                case "false":
                                    condition = "0"; // Mengubah false menjadi 0
                                    break;
                                default:
                                    return null;
                            }
                            break;
                        case "v":
                            value = queryValue;
                            break;
                        default:
                            return null;
                    }
                }
                return field + " " + condition + " " + value;
            }
            // Jika query hanya memiliki 1 bagian, asumsikan format key=value
            else if (queries.length == 1) {
                String[] queryParts = queries[0].split("=");
                if (queryParts.length != 2) {
                    return null;
                }
                String key = queryParts[0];
                String value = queryParts[1];

                // Memeriksa apakah value adalah "true" atau "false"
                if (value.equalsIgnoreCase("true")) {
                    return key + " = 1"; // Mengembalikan "isActive = 1"
                } else if (value.equalsIgnoreCase("false")) {
                    return key + " = 0"; // Mengembalikan "isActive = 0"
                } else {
                    return key + " = \"" + value + "\""; // Mengembalikan "isActive = value"
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Atau, bisa juga return null untuk menangani kondisi tidak valid dengan lebih baik
            return null;
        }

        return null;
    }


    public static Map<String, String> parseQuery(String query) {
        Map<String, String> queryPairs = new HashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(pair.substring(0, idx), pair.substring(idx + 1));
        }
        return queryPairs;
    }
}