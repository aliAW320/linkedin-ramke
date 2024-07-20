package org.linkedin.Controllers;

import com.google.gson.JsonDeserializer;
import com.sun.net.httpserver.HttpExchange;
import org.linkedin.Models.Like;
import org.linkedin.Models.LoginRequest;
import org.linkedin.Models.User;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.linkedin.utils.GsonBuilderRun;
import org.linkedin.utils.LocalDateTimeDeserializer;
import org.linkedin.utils.Response;

import static java.lang.StringTemplate.STR;

public class UserController {
    public static void user(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case "POST" -> register(exchange);
            case "GET" -> getMyProfile(exchange);
            case "PATCH" -> completeProfile(exchange);
            case "DELETE" -> {
                return;
            }//user delete account

        }
    }
    public static void register(HttpExchange exchange) throws IOException {
        String header = exchange.getRequestURI().getQuery();
        String body = new String(exchange.getRequestBody().readAllBytes());
        User newUser = null;
        try {
            newUser = GsonBuilderRun.getGson().fromJson(body, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response response = null;

        response = newUser.userCreateValidation();

        if (response.is()) {
            newUser.setId();
            newUser.insertDB();
        }

        System.out.println(STR."\{response.getCode()},\{response.getMessage()}:\{response.is()}");
        String httpResponse = STR."response : \{response.getMessage()}";
        exchange.sendResponseHeaders(response.getCode(), httpResponse.length());
        OutputStream os = exchange.getResponseBody();
        os.write(httpResponse.getBytes());
        os.close();
    }



    public static void completeProfile(HttpExchange exchange) throws IOException {
        String authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            String body = new String(exchange.getRequestBody().readAllBytes());
            User user = GsonBuilderRun.getGson().fromJson(body, User.class);

            Response response;
            user.setId(LoginRequest.userTokenToID(token));
            if (Long.parseLong(user.getId()) == 17) {
                response = new Response(false, "login error!", 400);
            } else {
                user.updateUser();
                response = new Response(true, "profile change!", 200);
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
    public static void getMyProfile(HttpExchange exchange) throws IOException {
        String authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());
            String body = new String(exchange.getRequestBody().readAllBytes());
            System.out.println(authorizationHeader);
            User user = GsonBuilderRun.getGson().fromJson(body, User.class);
            if(user == null)
                user = new User();
            Response response;
            user.setId(LoginRequest.userTokenToID(token));
            if (Long.parseLong(user.getId()) == 17) {
                response = new Response(false, "login error!", 400);
            } else {
                user.getUserData();
                response = new Response(true, "here your profile!", 200,user);
            }

            String jRes = GsonBuilderRun.getGson().toJson(response);
            jRes = jRes.replaceAll("\"object\":", "\"user\":");
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


