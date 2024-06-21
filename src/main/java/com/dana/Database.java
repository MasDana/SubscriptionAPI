package com.dana;

import java.sql.*;
import java.util.*;
import java.lang.String;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

public class Database {
    private final String[] tabel = {
            "cards",
            "customer",
            "item",
            "shippingAddresses",
            "subscriptionItem",
            "subscriptions"
    };

    public Result selectFromTable(String tableName, String condition) {
        List<String> rows = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + tableName +
                    (condition != null ? " WHERE " + condition : "");
            Connection connection = connectionDatabase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                StringBuilder row = new StringBuilder();
                row.append("{");
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    row.append("\"").append(columnName).append("\":\"").append(columnValue).append("\",");
                }
                row.deleteCharAt(row.length() - 1);
                row.append("}");
                // End of formatting

                ObjectMapper objectMapper = new ObjectMapper();
                Object object = objectMapper.readValue(row.toString(), Class.forName("com.dana." + classGetter(tableName)));
                rows.add(convertJSON(object));
            }

            if (rows.size() == 0) return new Result(null, "Data tidak ditemukan", 404, false);
            else if (rows.size() == 1) return new Result(rows.get(0), "Select sukses", 200, true);
            return new Result(rows, "Select sukses", 200, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(null, e.getMessage(), 400, false);
        }

    }

    public Result selectColumnsFromTable(String tableName, String condition, List<String> columns) {
        List<String> rows = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder("SELECT ");
            if (columns == null || columns.isEmpty()) {
                query.append("*");
            } else {
                query.append(String.join(", ", columns));
            }
            query.append(" FROM ").append(tableName);
            if (condition != null) {
                query.append(" WHERE ").append(condition);
            }

            Connection connection = connectionDatabase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                StringBuilder row = new StringBuilder();
                row.append("{");
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    row.append("\"").append(columnName).append("\":\"").append(columnValue).append("\",");
                }
                row.deleteCharAt(row.length() - 1);  // Remove the last comma
                row.append("}");
                // End of formatting

                ObjectMapper objectMapper = new ObjectMapper();
                Object object = objectMapper.readValue(row.toString(), Class.forName("com.dana." + columngetter(tableName)));
                rows.add(convertJSON(object));
            }

            if (rows.size() == 0) return new Result(null, "Data tidak ditemukan", 404, false);
            else if (rows.size() == 1) return new Result(rows.get(0), "Select sukses", 200, true);
            return new Result(rows, "Select sukses", 200, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(null, e.getMessage(), 400, false);
        }
    }

    public Result selectWithQuery(String customQuery) {
        List<String> rows = new ArrayList<>();
        try {
            Connection connection = connectionDatabase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(customQuery);

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                // Format to json
                StringBuilder row = new StringBuilder();
                row.append("{");
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    if (columnValue instanceof Double || columnValue instanceof Integer) row.append("\"").append(columnName).append("\":").append(columnValue).append(",");
                    else row.append("\"").append(columnName).append("\":\"").append(columnValue).append("\",");
                }
                row.deleteCharAt(row.length() - 1);
                row.append("}");
                // End of formatting

                rows.add(row.toString());
            }

            if (rows.size() == 0) return new Result(null, "Data tidak ditemukan", 404, false);
            else if (rows.size() == 1) return new Result(rows.get(0), "Select sukses", 200, true);
            return new Result(rows, "Select sukses", 200, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(null, e.getMessage(), 400, false);
        }
    }

    public Result insertToTable(String tableName, String fieldKeys, String fieldValues) {
        Result result;
        try {
            String query = "INSERT INTO " + tableName + " (" + fieldKeys + ") " + "VALUES (" + fieldValues + ") ";
            Connection connection = connectionDatabase.getConnection();
            Statement statement = connection.createStatement();
            result = new Result(statement.executeUpdate(query), "Insert sukses", 200,true);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(null, e.getMessage(), 404,false);
        }

        return result;
    }

    public Result updateTable(String tableName, int id, String fieldKeys, String fieldValues) {
        if (!this.selectFromTable(tableName, "id=" + id).isSukses()) {
            return new Result(null, "Data tidak ditemukan", 404, false);
        }

        Result result;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("UPDATE ").append(tableName).append(" SET ");

            String[] fieldKeysParsed = ParsingTool.pemisahString(fieldKeys, ",");
            String[] fieldValuesParsed = ParsingTool.pemisahString(fieldValues, ",");

            for (int i = 0; i < fieldKeysParsed.length; i++) {
                stringBuilder.append(fieldKeysParsed[i]).append("=").append(fieldValuesParsed[i]).append(",");
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(" WHERE id=").append(id);

            String query = stringBuilder.toString();

            Connection connection = connectionDatabase.getConnection();
            Statement statement = connection.createStatement();
            result = new Result(statement.executeUpdate(query), "Update sukses", 200,true);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(null, e.getMessage(), 400,false);
        }

        return result;
    }

    public Result delete(String tableName, int id) {
        if (!this.selectFromTable(tableName, "id=" + id).isSukses()) return new Result(null, "Data tidak ditemukan", 404, false);

        Result result;
        try {
            String query = "DELETE FROM " + tableName + " WHERE id=" + id;
            Connection connection = connectionDatabase.getConnection();
            Statement statement = connection.createStatement();
            result = new Result(statement.executeUpdate(query), "Delete sukses", 200,true);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(null, e.getMessage(), 400,false);
            return result;
        }
    }

    public String classGetter(String tableName) {
        StringBuilder stringBuilder = new StringBuilder(tableName);
        stringBuilder.deleteCharAt(0);
        stringBuilder.insert(0, tableName.toUpperCase().charAt(0));
        return stringBuilder.toString();
    }

    public static String convertJSON(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String JSONBuilder(String firstJson, String tableName, String secondJson) {
        StringBuilder stringBuilder = new StringBuilder(firstJson);

        // Array
        if (stringBuilder.charAt(0) == '[' && stringBuilder.charAt(stringBuilder.length() - 1) == ']') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1); // ]
            stringBuilder.deleteCharAt(stringBuilder.length() - 1); // }
            stringBuilder.append(",");
            stringBuilder.append("\"").append(tableName).append("\"").append(":");
            stringBuilder.append(secondJson);
            stringBuilder.append("}]");
        }

        // Object
        else if (stringBuilder.charAt(0) == '{' && stringBuilder.charAt(stringBuilder.length() - 1) == '}') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1); // }
            stringBuilder.append(",");
            stringBuilder.append("\"").append(tableName).append("\"").append(":");
            stringBuilder.append(secondJson);
            stringBuilder.append("}");
        }

        return stringBuilder.toString();
    }

    public String[] getTables() {
        return tabel;
    }

    private String columngetter(String tableName) {
        switch (tableName) {
            case "customer":
                return "Customer";
            case "subscriptionItems":
                return "SubscriptionItems";
            case "item":
                return "Item";
            default:
                return "";
        }
    }


}



