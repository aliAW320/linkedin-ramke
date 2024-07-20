package org.linkedin.Controllers;

import com.sun.net.httpserver.HttpExchange;
import org.linkedin.Models.Chat;
import org.linkedin.Models.Like;
import org.linkedin.Models.LoginRequest;
import org.linkedin.utils.GsonBuilderRun;
import org.linkedin.utils.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

public class ChatController  {
    public static void chat(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()){
            case "POST" -> addChatMess(exchange);
            case "GET" -> getChat(exchange);
        }
    }
    private static void addChatMess(HttpExchange exchange) throws IOException {
        String authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            String body = new String(exchange.getRequestBody().readAllBytes());
            Chat chat = GsonBuilderRun.getGson().fromJson(body, Chat.class);

            Response response;
            chat.setSenderId(LoginRequest.userTokenToID(token));
            if (Objects.equals(chat.getSenderId(), "17")) {
                response = new Response(false, "login error!", 400);
            } else {
                chat.addMessage();
                response = new Response(true, "message sent!", 200);
            }

            String jRes = GsonBuilderRun.getGson().toJson(response);
            exchange.sendResponseHeaders(response.getCode(), jRes.length());
            OutputStream os = exchange.getResponseBody();
            os.write(jRes.getBytes());
            os.close();
        } else {
            // No token provided, return an error
            Response response = new Response(false, "No token provided", 401);
            String jRes = GsonBuilderRun.getGson().toJson(response);
            exchange.sendResponseHeaders(response.getCode(), jRes.length());
            OutputStream os = exchange.getResponseBody();
            os.write(jRes.getBytes());
            os.close();
        }
    }
    private static void getChat(HttpExchange exchange) throws IOException {
        String authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            String body = new String(exchange.getRequestBody().readAllBytes());
            Chat chat = GsonBuilderRun.getGson().fromJson(body, Chat.class);

            Response response;
            chat.setSenderId(LoginRequest.userTokenToID(token));
            if (Objects.equals(chat.getSenderId(), "17")) {
                response = new Response(false, "login error!", 400);
            } else {
                List<Chat> chats = chat.getOrderMessageBetweenTwo();
                response = new Response(true, "update chat!", 200,chats);
            }
            String jRes = GsonBuilderRun.getGson().toJson(response);
            exchange.sendResponseHeaders(response.getCode(), jRes.length());
            OutputStream os = exchange.getResponseBody();
            os.write(jRes.getBytes());
            os.close();
        } else {
            // No token provided, return an error
            Response response = new Response(false, "No token provided", 401);
            String jRes = GsonBuilderRun.getGson().toJson(response);
            exchange.sendResponseHeaders(response.getCode(), jRes.length());
            OutputStream os = exchange.getResponseBody();
            os.write(jRes.getBytes());
            os.close();
        }
    }
}
