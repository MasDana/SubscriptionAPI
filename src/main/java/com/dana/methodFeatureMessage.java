package com.dana;

import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.SQLException;
import java.io.OutputStream;
import java.io.IOException;
import java.util.*;
import com.sun.net.httpserver.HttpExchange;


public class methodFeatureMessage {
    private static final String[] tabel= {
            "addresses",
            "orderDetails",
            "orders",
            "products",
            "reviews",
            "users"
    };
    public static boolean isTableNameValid(String tableName) {
        return Arrays.asList(getTables()).contains(tableName);
    }
    public static String[] getTables() {
        return tabel;
    }
    public static boolean cekTabel(String namaTabel) {
        String[] namaTabeValid = { "users", "products", "orders", "addresses", "order_details", "reviews" };

        return Arrays.asList(namaTabeValid).contains(namaTabel.toLowerCase());
    }
    public static void noMethod(HttpExchange exchange) throws SQLException, IOException {
        String method = exchange.getRequestMethod();
        JSONObject jsonObject = new JSONObject();
        JSONArray data = new JSONArray();
        jsonObject.put("status_code", 400);
        jsonObject.put("pesan",
                "API ini hanya dapat memproses metode GET, PUT, POST, DELETE. Metode " + method
                        + " tidak tersedia. ");
        data.put(jsonObject);
        int statusCode = jsonObject.getInt("status_code");
        String response = data.toString(3);
        respon (exchange, statusCode, response);
    }

    public static void respon(HttpExchange exchange, int statusCode, String response) throws SQLException, IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    public static JSONObject updateSukses(String tableName, String id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status_code", 200);
        jsonObject.put("pesan", "Data dalam " + tableName + " dengan id = " + id + " berhasil diperbarui.");
        return jsonObject;
    }

    public static JSONObject insertSukses(String tableName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status_code", 200);
        jsonObject.put("pesan", "Data " + tableName + " berhasil dimasukkan.");
        return jsonObject;
    }
    public static JSONObject deleteSukses(String tableName, String id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status_code", 200);
        jsonObject.put("pesan", "Data dalam " + tableName + " dengan id " + id + " berhasil di hapus.");
        return jsonObject;
    }

    public static JSONObject updateError(String tableName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status_code", 400);
        jsonObject.put("pesan", "Data dalam " + tableName + " tidak valid.");
        return jsonObject;
    }

    public static JSONObject insertError(String tableName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status_code", 400);
        jsonObject.put("pesan", "Data tidak valid. Silahkan masukkan " + tableName + " dengan data yang benar.");
        return jsonObject;
    }
    public static JSONObject noTable(String tableName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status_code", 400);
        jsonObject.put("pesan", "Tabel tidak tersedia, silahkan perjelas nama tabel.");
        return jsonObject;
    }

    public static JSONObject pathInvalid(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status_code", 400);
        jsonObject.put("pesan", "Path tidak tersedia. Path " + path + " tidak terseda untuk " + method + "ini.");
        return jsonObject;
    }
}
