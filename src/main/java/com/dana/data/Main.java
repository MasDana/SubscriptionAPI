package com.dana.data;

import com.dana.Server;

import java.io.IOException;

public class Main {
    private static final String nim = "2305551071";
    private static final int port = Integer.parseInt("9" + nim.substring(nim.length() -  3));
    private static final String rootPath = System.getProperty("user.dir");
    public static void main(String[] args) throws IOException {
        new Server(port);
    }
    public static String getRootPath() {
        return rootPath;
    }
}