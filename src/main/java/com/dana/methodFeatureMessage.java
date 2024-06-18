package com.dana;

import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.SQLException;
import java.io.OutputStream;
import java.io.IOException;
import java.util.*;
import com.sun.net.httpserver.HttpExchange;

public class methodFeatureMessage {
    public static boolean cekTabel(String namaTabel) {
        String[] namaTabeValid = { "users", "products", "orders", "addresses", "order_details", "reviews" };

        return Arrays.asList(namaTabeValid).contains(namaTabel.toLowerCase());
    }
    public static void metodeTidakAda(HttpExchange exchange) throws SQLException, IOException {
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
}
