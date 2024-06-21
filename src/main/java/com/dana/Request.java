package com.dana;

import com.dana.data.Main;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.google.gson.Gson;
import java.util.Map;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Request {
    private final String[] requestMethodsAllowed = {
            "POST",
            "GET",
            "PUT",
            "DELETE"
    };



    private static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new java.util.HashMap<>();
        if (query == null) return result;
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }

    public static class Handler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            try {
                Dotenv dotenv = Dotenv.configure().directory(Main.getRootPath()).filename(".env").load();
                String envApiKey = dotenv.get("API_KEY");
                String reqApiKey = null;

                Response response = new Response(exchange);
                exchange.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
                int statusCode;

                try {
                    reqApiKey = exchange.getRequestHeaders().get("api-key").get(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!envApiKey.equals(reqApiKey)) {
                    response.send(statusCode = 401, "{" +
                            "\"status\": " + statusCode + "," +
                            "\"message\": \"API key not authorized\"" +
                            "}");
                    return;
                }

                String requestMethod = exchange.getRequestMethod();
                String requestQuery = exchange.getRequestURI().getQuery();
                String[] requestPath = ParsingTool.pemisahString(exchange.getRequestURI().getPath(), "/");
                String requestBody = ParsingTool.parseInputStream(exchange.getRequestBody());

                System.out.println("Request Path: " + Arrays.toString(requestPath)); // Tambahkan log ini


                String tableMaster = null;
                String id = null;
                String tableDetail = null;

                try {
                    tableMaster = requestPath[0];
                    if (requestPath.length > 1) id = requestPath[1];
                    if (requestPath.length > 2) tableDetail = requestPath[2];
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!ValidateTool.validationRM(requestMethod)) {
                    response.send(statusCode = 405, "{" +
                            "\"status\": " + statusCode + "," +
                            "\"message\": \"Request method " + requestMethod + " not allowed\"" +
                            "}");
                    return;
                }

                if (!ValidateTool.validationTable(tableMaster)) {
                    response.send(statusCode = 404, "{" +
                            "\"status\": " + statusCode + "," +
                            "\"message\": \"Table " + tableMaster + " was not found\"" +
                            "}");
                    return;
                }

                if (id != null && !ValidateTool.validationID(id)) {

                    response.send(statusCode = 404, "{" +
                            "\"status\": " + statusCode + "," +
                            "\"message\": \"ID " + id + " was not found\"" +
                            "}");
                    return;
                }

                if (tableDetail != null && !ValidateTool.validationTable(tableDetail)) {
                    System.out.println("hallo");
                    response.send(statusCode = 404, "{" +
                            "\"status\": " + statusCode + "," +
                            "\"message\": \"Table " + tableDetail + " was not found\"" +
                            "}");
                    return;
                }

                String condition = null;

                if (!ValidateTool.validationRB(requestBody) && (requestMethod.equals("POST") || requestMethod.equals("PUT"))) {
                    response.send(statusCode = 400, "{" +
                            "\"status\": " + statusCode + "," +
                            "\"message\": \"Please input request body as JSON to inserting or updating data\"" +
                            "}");
                    return;
                }

                JsonNode jsonNode = null;

                if (requestMethod.equals("POST") || requestMethod.equals("PUT"))
                    jsonNode = ParsingTool.jsonParsing(requestBody);

                switch (requestMethod) {
                    case "GET":
                        if (requestQuery != null && requestQuery.contains("isActive=true")) {
                            System.out.println(requestQuery);
                            boolean isActive = "true".equalsIgnoreCase(requestQuery.split("=")[1]);
                            List<Item> items = Item.getItemsByIsActive(isActive);
                            String json = new Gson().toJson(items);
                            byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8); // Mengubah JSON ke byte array
                            exchange.sendResponseHeaders(200, jsonBytes.length); // Menggunakan panjang byte array
                            OutputStream os = exchange.getResponseBody();
                            os.write(jsonBytes);
                            os.close();
                        } else if (requestPath.length == 2 && requestQuery == null) {
                            assert id != null;
                            response.handleGet(tableMaster, Integer.parseInt(id), null);
                        } else if (requestPath.length == 3 && requestQuery == null) {
                            assert id != null;
                            response.handleGet(tableMaster, Integer.parseInt(id), tableDetail);
                        } else if (requestPath.length == 3 && "subscriptions".equals(requestPath[2])) {
                                try {
                                    int customerId = Integer.parseInt(requestPath[1]);
                                    if (requestQuery != null && requestQuery.contains("status=")) {
                                        String status = requestQuery.split("=")[1];
                                        List<Subscriptions> subscriptions = Subscriptions.getSubscriptionsByCustomerIdAndStatus(customerId, status);
                                        String json = new Gson().toJson(subscriptions);
                                        exchange.sendResponseHeaders(200, json.length());
                                        OutputStream os = exchange.getResponseBody();
                                        os.write(json.getBytes());
                                        os.close();
                                    } else {
                                        exchange.sendResponseHeaders(400, "Please provide 'status' parameter in query string".length());
                                        OutputStream os = exchange.getResponseBody();
                                        os.write("Please provide 'status' parameter in query string".getBytes());
                                        os.close();
                                    }
                                } catch (NumberFormatException e) {
                                    String responseMsg = "Invalid ID format";
                                    exchange.sendResponseHeaders(400, responseMsg.length());
                                    OutputStream os = exchange.getResponseBody();
                                    os.write(responseMsg.getBytes());
                                    os.close();
                                }

                        }else if (requestQuery != null && requestQuery.contains("sort_by=current_term_end&sort_type=desc")) {
                            System.out.println(requestQuery);
                            Map<String, String> params = queryToMap(requestQuery); // Menggunakan requestQuery langsung
                            String sortBy = params.get("sort_by");
                            String sortType = params.get("sort_type");

                            List<Subscriptions> subscriptions = Subscriptions.getAllSubscription(sortBy, sortType);
                            String json = new Gson().toJson(subscriptions);
                            exchange.sendResponseHeaders(200, json.length());
                            OutputStream os = exchange.getResponseBody();
                            os.write(json.getBytes());
                            os.close();
                        }


                        else if (requestPath.length == 1) {
                            response.handleGet(tableMaster, condition);
                        } else {
                            response.send(statusCode = 400, "{" +
                                    "\"status\": " + statusCode + "," +
                                    "\"message\": \"Please check your request\"" +
                                    "}");
                        }
                        return;

                    case "POST":
                        response.handlePost(tableMaster, jsonNode);
                        return;
                    case "PUT":
                        if (requestPath.length == 2) {
                        assert id != null;
                        response.handlePut(tableMaster, Integer.parseInt(id), jsonNode);
                    } else if (requestPath.length == 4 && "customer".equals(requestPath[0]) && "shippingAddresses".equals(requestPath[2])) {
                            try {
                                int customerId = Integer.parseInt(requestPath[1]);
                                int addressId = Integer.parseInt(requestPath[3]);

                                requestBody = new String(exchange.getRequestBody().readAllBytes());
                                // Assume requestBody contains fieldKeys and fieldValues as JSON string
                                Gson gson = new Gson();
                                JsonObject jsonObject = gson.fromJson(requestBody, JsonObject.class);
                                String fieldKeys = jsonObject.get("fieldKeys").getAsString();
                                String fieldValues = jsonObject.get("fieldValues").getAsString();



                            } catch (NumberFormatException e) {
                                String responseMsg = "Invalid ID format";
                                exchange.sendResponseHeaders(400, responseMsg.length());
                                OutputStream os = exchange.getResponseBody();
                                os.write(responseMsg.getBytes());
                                os.close();
                            } catch (IOException e) {
                                String responseMsg = "Invalid ID format";
                                exchange.sendResponseHeaders(400, responseMsg.length());
                                OutputStream os = exchange.getResponseBody();
                                os.write(responseMsg.getBytes());
                                os.close();
                            }
                        }


                        else {
                        String responseMsg = "{" +
                                "\"status\": 404," +
                                "\"message\": \"Shipping address with ID " + requestPath[3] + " not found\"" +
                                "}";
                        exchange.sendResponseHeaders(404, responseMsg.length());
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(responseMsg.getBytes());
                        }
                    }
                    return;
                    case "DELETE":
                        if (requestPath.length == 2 && "item".equals(requestPath[0])) {
                            assert id != null;
                            handleDeleteItemRequest(exchange, Integer.parseInt(id));
                        } else if (requestPath.length == 4 && "customer".equals(requestPath[0]) && "cards".equals(requestPath[2])) {
                            try {
                                int customerId = Integer.parseInt(requestPath[1]);
                                int cardId = Integer.parseInt(requestPath[3]);
                                handleDeleteCardRequest(exchange, customerId, cardId);
                            } catch (NumberFormatException e) {
                                response.send(statusCode = 400, "{" +
                                        "\"status\": 400," +
                                        "\"message\": \"Invalid ID format\"" +
                                        "}");
                            }
                        } else {
                            System.out.println("ini : " + id);
                            response.send(statusCode = 400, "{" +
                                    "\"status\": " + statusCode + "," +
                                    "\"message\": \"No ID detected, please check your request\"" +
                                    "}");
                        }
                        return;
                    default:
                        response.send(statusCode = 400, "{" +
                                "\"status\": " + statusCode + "," +
                                "\"message\": \"Please check your request\"" +
                                "}");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }


        private static void handleDeleteItemRequest(HttpExchange exchange, int id) throws IOException {
            boolean success = Item.deactivateItem(id);
            String responseMsg;
            int statusCode;

            if (success) {
                statusCode = 200;
                responseMsg = "{" +
                        "\"status\": " + statusCode + "," +
                        "\"message\": \"Item " + id + " successfully deactivated\"" +
                        "}";
            } else {
                statusCode = 404;
                responseMsg = "{" +
                        "\"status\": " + statusCode + "," +
                        "\"message\": \"Item " + id + " not found\"" +
                        "}";
            }

            exchange.sendResponseHeaders(statusCode, responseMsg.length());
            OutputStream os = exchange.getResponseBody();
            os.write(responseMsg.getBytes());
            os.close();
        }

        private static void handleDeleteCardRequest(HttpExchange exchange, int customerId, int cardId) throws IOException {
            boolean success = Cards.deleteNonPrimaryCard(customerId, cardId);
            String responseMsg;
            int statusCode;

            if (success) {
                statusCode = 200;
                responseMsg = "{" +
                        "\"status\": " + statusCode + "," +
                        "\"message\": \"Card " + cardId + " successfully deleted\"" +
                        "}";
            } else {
                statusCode = 404;
                responseMsg = "{" +
                        "\"status\": " + statusCode + "," +
                        "\"message\": \"Card " + cardId + " not found or is primary\"" +
                        "}";
            }

            exchange.sendResponseHeaders(statusCode, responseMsg.length());
            OutputStream os = exchange.getResponseBody();
            os.write(responseMsg.getBytes());
            os.close();
        }

        }
    public String[] getRequestMethodsAllowed() {
        return requestMethodsAllowed;
    }

    private static boolean updateShippingAddressById(int customerId, int addressId, String title) {
        try (Connection conn = connectionDatabase.getConnection()) {
            String sql = "UPDATE shippingAddresses SET title = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setInt(2, addressId);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    }

