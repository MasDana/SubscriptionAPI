package com.dana;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
public class ParsingTool {
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
        // Pisahkan query string menjadi pasangan kunci-nilai menggunakan "&"
        String[] queries = requestQuery.split("&");

        // Jika query memiliki 3 bagian, asumsikan format field, condition, value
        if (queries.length == 3) {
            String field = "";
            String condition = "";
            String value = "";
            try {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Jika query hanya memiliki 1 bagian, asumsikan format key=value
        else if (queries.length == 1) {
            try {
                String[] queryParts = queries[0].split("=");
                if (queryParts.length != 2) {
                    return null;
                }
                String key = queryParts[0];
                String value = queryParts[1];
                return key + " = \"" + value + "\"";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
