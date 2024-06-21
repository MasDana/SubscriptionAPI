package com.dana;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Arrays;




public class Response {
    private final HttpExchange exchange;
    private final Database database = new Database();
    public Response(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public void handleGet(String tableName, String condition) throws IOException {
        Result result = database.selectFromTable(tableName, condition);
        int statusCode = result.getStatus();

        if (result.isSukses()) {
            this.send(statusCode, "{" +
                    "\"status\": " + statusCode + "," +
                    "\"message\": " + result.getPesan() + "," +
                    "\"data\": " + result.getData() +
                    "}"
            );
        } else {
            this.send(statusCode, "{" +
                    "\"status\": " + statusCode + "," +
                    "\"message\": " + result.getPesan() +
                    "}"
            );
        }
    }

    public void handleGet(String tableMaster, int id, String tableDetail) throws IOException, SQLException {
        Result resultParent = database.selectFromTable(tableMaster, "id=" + id);
        String jsonResult = resultParent.getData();

        int statusCode = resultParent.getStatus();
        boolean isSukses = resultParent.isSukses();
        String message = resultParent.getPesan();



        if (!isSukses) {
            this.send(statusCode, "{" +
                    "\"status\": " + statusCode + "," +
                    "\"message\": " + message +
                    "}");
            return;
        }



        if (tableMaster.equals("customer")) {
            if (tableDetail == null) {
                Result shipping_addresses = database.selectFromTable("shippingAddresses", "customer=" + id);
                isSukses = shipping_addresses.isSukses();
                if (!isSukses) {
                    statusCode = shipping_addresses.getStatus();
                    message = shipping_addresses.getPesan();
                } else {
                    jsonResult = Database.JSONBuilder(jsonResult,
                            "shippingAddresses", shipping_addresses.getData());
                }
            } else if (tableDetail.equals("cards")) {
                Result cards = database.selectFromTable("cards", "customer=" + id);
                isSukses = cards.isSukses();
                if (!isSukses) {
                    statusCode = cards.getStatus();
                    message = cards.getPesan();
                } else {
                    jsonResult = database.JSONBuilder(jsonResult,
                            "cards", cards.getData());
                }
            } else if (tableDetail.equals("subscriptions")) {
                Result subscriptions = database.selectFromTable("subscriptions", "customer=" + id);
                isSukses = subscriptions.isSukses();
                if (!isSukses) {
                    statusCode = subscriptions.getStatus();
                    message = subscriptions.getPesan();
                } else {
                    jsonResult = Database.JSONBuilder(jsonResult,
                            "subscriptions", subscriptions.getData());
                }
            }



        } else if (tableMaster.equals("subscriptions")) {
            if (tableDetail == null){
                // Query subscription items dengan kolom yang diinginkan
                List<String> subscriptionItemColumns = Arrays.asList("quantity", "amount");
                Result subscriptionItems = database.selectColumnsFromTable("subscriptionItems", "item=" + id, subscriptionItemColumns);
                isSukses = subscriptionItems.isSukses();
                if (!isSukses) {
                    statusCode = subscriptionItems.getStatus();
                    message = subscriptionItems.getPesan();
                } else {
                    jsonResult = Database.JSONBuilder(jsonResult, "subscriptionItems", subscriptionItems.getData());
                }

                // Query customer dengan kolom yang diinginkan
                List<String> customerColumns = Arrays.asList("id", "first_name", "last_name");
                Result customer = database.selectColumnsFromTable("customer", "id=" + id, customerColumns);
                isSukses = customer.isSukses();
                if (!isSukses) {
                    statusCode = customer.getStatus();
                    message = customer.getPesan();
                } else {
                    jsonResult = Database.JSONBuilder(jsonResult, "customer", customer.getData());
                }

                // Query customer dengan kolom yang diinginkan
                List<String> itemColumns = Arrays.asList("id", "name", "price", "type");
                Result item = database.selectColumnsFromTable("item", "id=" + id, itemColumns);
                isSukses = item.isSukses();
                if (!isSukses) {
                    statusCode = item.getStatus();
                    message = item.getPesan();
                } else {
                    jsonResult = Database.JSONBuilder(jsonResult, "item", item.getData());
                }
            }



    }else if (tableMaster.equals("item")) {
            if (tableDetail == null) {
                Result item = database.selectFromTable("item", "id=" + id);
                isSukses = item.isSukses();
                if (!isSukses) {
                    statusCode = item.getStatus();
                    message = item.getPesan();
                } else {
                    jsonResult = Database.JSONBuilder(jsonResult,
                            "item", item.getData());
                }
            }  else {
                this.send(statusCode = 400, "{" +
                        "\"status\": " + statusCode + "," +
                        "\"message\": " + "\"No matching data found, please check your request\"" +
                        "}"
                );
                return;
            }
        }
        if (!isSukses) {
            send(statusCode, "{" +
                    "\"status\": " + statusCode + "," +
                    "\"message\": " + message +
                    "}");
        } else {
            send(statusCode, "{" +
                    "\"status\": " + statusCode + "," +
                    "\"message\": " + message + "," +
                    "\"data\": " + jsonResult +
                    "}"
            );
        }
    }

    public void handlePost(String tableName, JsonNode jsonNode) throws IOException {
        StringBuilder fieldKeys = new StringBuilder();
        StringBuilder fieldValues = new StringBuilder();

        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            fieldKeys.append(field.getKey());
            fieldKeys.append(",");

            fieldValues.append(field.getValue());
            fieldValues.append(",");
        }

        // Remove the comma (,) character at the end of the string
        fieldKeys.deleteCharAt(fieldKeys.length() - 1);
        fieldValues.deleteCharAt(fieldValues.length() - 1);

        Result result = database.insertToTable(tableName, fieldKeys.toString(), fieldValues.toString());
        int statusCode = result.getStatus();

        if (result.isSukses()) {
            this.send(statusCode, "{" +
                    "\"status\": " + statusCode + "," +
                    "\"message\": " + result.getPesan() + "," +
                    "\"data\": " + result.getData() +
                    "}");
        } else {
            this.send(statusCode, "{" +
                    "\"status\": " + statusCode + "," +
                    "\"message\": " + result.getPesan()  +
                    "}");
        }
    }

    public void handlePut(String tableName, int id, JsonNode jsonNode) throws IOException {
        StringBuilder fieldKeys = new StringBuilder();
        StringBuilder fieldValues = new StringBuilder();

        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            fieldKeys.append(field.getKey());
            fieldKeys.append(",");

            fieldValues.append(field.getValue());
            fieldValues.append(",");
        }

        // Remove the comma (,) character at the end of the string
        fieldKeys.deleteCharAt(fieldKeys.length() - 1);
        fieldValues.deleteCharAt(fieldValues.length() - 1);

        Result result = database.updateTable(tableName, id, fieldKeys.toString(), fieldValues.toString());
        int statusCode = result.getStatus();

        if (result.isSukses()) {
            send(statusCode, "{" +
                    "\"status\": " + statusCode + "," +
                    "\"message\": " + result.getPesan() + "," +
                    "\"data\": " + result.getData() +
                    "}");
        } else {
            send(statusCode, "{" +
                    "\"status\": " + statusCode + "," +
                    "\"message\": " + result.getPesan()  +
                    "}");
        }
    }

    public void handleDelete(String tableName, int id) throws IOException {
        Result result = database.delete(tableName, id);
        int statusCode = result.getStatus();

        if (result.isSukses()) {
            this.send(statusCode, "{" +
                    "\"status\": " + statusCode + "," +
                    "\"message\": " + result.getPesan() + "," +
                    "\"data\": " + result.getData() +
                    "}");
        } else {
            this.send(statusCode, "{" +
                    "\"status\": " + statusCode + "," +
                    "\"message\": " + result.getPesan()  +
                    "}");
        }
    }

    public void send(int statusCode, String jsonMessage) throws IOException {
        OutputStream outputStream = exchange.getResponseBody();
        exchange.sendResponseHeaders(statusCode, jsonMessage.length());
        outputStream.write(jsonMessage.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}