package com.dana;

import com.dana.Database;
import com.dana.ParsingTool;
import com.dana.Request;

import java.util.Arrays;

public class ValidateTool {
    public static boolean validationRM(String requestMethod) {
        return Arrays.asList(new Request().getRequestMethodsAllowed()).contains(requestMethod);
    }

    public static boolean validationTable(String tableName) {
        Database database = new Database();
        return Arrays.asList(database.getTables()).contains(tableName);
    }

    public static boolean validationID(String id) {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean validationRB(String requestBody) {
        try {
            ParsingTool.jsonParsing(requestBody);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
