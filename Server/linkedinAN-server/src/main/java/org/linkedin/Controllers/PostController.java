package org.linkedin.Controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.sun.net.httpserver.HttpExchange;
import org.linkedin.Models.LoginRequest;
import org.linkedin.Models.Post;
import org.linkedin.Models.TextPost;
import org.linkedin.Models.User;
import org.linkedin.utils.GsonBuilderRun;
import org.linkedin.utils.LocalDateTimeDeserializer;
import org.linkedin.utils.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PostController {
    public static void post(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()){
            case "POST" -> addPost(exchange);
            case "GET" -> getPosts(exchange);
            case "PATCH" ->{return;}//user complete profile
            case "DELETE" ->{return;}//user delete account

        }
    }
    private static void getPosts(HttpExchange exchange) throws IOException {
        // Get the token from the header
        String authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            String body = new String(exchange.getRequestBody().readAllBytes());
            TextPost textPost = GsonBuilderRun.getGson().fromJson(body, TextPost.class);
            if(textPost == null)
                textPost = new TextPost();
            Response response;
            textPost.setUserID(Long.parseLong(LoginRequest.userTokenToID(token)));
            if (textPost.getUserID() == 17) {
                response = new Response(false, "login error!", 400);
            } else {
                response = new Response(true, "all of your post!",200 ,textPost.getFollowingPosts());
            }
            String jRes = GsonBuilderRun.getGson().toJson(response);
            jRes = jRes.replaceAll("\"object\":", "\"listPost\":");
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

    private static void addPost(HttpExchange exchange) throws IOException {
        // Get the token from the header
        String authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            String body = new String(exchange.getRequestBody().readAllBytes());
            TextPost textPost = GsonBuilderRun.getGson().fromJson(body, TextPost.class);

            Response response;
            textPost.setUserID(Long.parseLong(LoginRequest.userTokenToID(token)));
            if (textPost.getUserID() == 17) {
                response = new Response(false, "login error!", 400);
            }
            else {
                textPost.insertDB();
                response = new Response(true, String.valueOf(textPost.getId()), 200);
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