package org.linkedin.Controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileController{
    public static void file(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()){
            case "GET" -> dowonload(exchange);
        }
    }
    private static final String MEDIA_FOLDER = "/home/alireza/Documents/linkedin/userData";

    public static void dowonload(HttpExchange exchange) throws IOException {
        System.out.println(exchange.getRemoteAddress());
        String requestPath = exchange.getRequestURI().getPath();
        String fileName = requestPath.substring(requestPath.lastIndexOf("/") + 1);
        File file = new File(MEDIA_FOLDER, fileName);

        if (file.exists() && file.isFile()) {
            sendFile(exchange, file);
        } else {
            sendErrorResponse(exchange, "File not found", 404);
        }
    }

    private static void sendFile(HttpExchange exchange, File file) throws IOException {
        String contentType = Files.probeContentType(file.toPath());
        if (contentType != null && (contentType.startsWith("image/") || contentType.startsWith("video/"))) {
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, file.length());
        } else {
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.getResponseHeaders().set("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            exchange.sendResponseHeaders(200, file.length());
        }

        try (OutputStream os = exchange.getResponseBody()) {
            Files.copy(file.toPath(), os);
        }
    }

    private static void sendErrorResponse(HttpExchange exchange, String message, int code) throws IOException {
        exchange.sendResponseHeaders(code, message.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(message.getBytes());
        }
    }
}
