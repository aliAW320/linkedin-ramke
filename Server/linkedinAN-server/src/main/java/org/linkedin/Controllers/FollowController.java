package org.linkedin.Controllers;

import com.sun.net.httpserver.HttpExchange;
import org.linkedin.Models.Follow;
import org.linkedin.Models.LoginRequest;
import org.linkedin.utils.GsonBuilderRun;
import org.linkedin.utils.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class FollowController {
    public static void follow(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()){
            case "POST", "PATCH" -> followingChange(exchange);//update like (remove or liked)
            case "GET" -> getNumberFollowers(exchange);//get post number of likes
        }
    }

    private static void followingChange(HttpExchange exchange) throws IOException{
        String authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            String body = new String(exchange.getRequestBody().readAllBytes());
            Follow follow = GsonBuilderRun.getGson().fromJson(body, Follow.class);

            Response response;
            follow.setUser(LoginRequest.userTokenToID(token));
            if (Objects.equals(follow.getUser(), "17")) {
                response = new Response(false, "login error!", 400);
            } else {
                follow.changeFollowing();
                response = new Response(true, "like changed!", 200);
            }

            String jRes = GsonBuilderRun.getGson().toJson(response);
            exchange.sendResponseHeaders(response.getCode(), jRes.length());
            OutputStream os = exchange.getResponseBody();
            os.write(jRes.getBytes());
            os.close();
        } else {
            Response response = new Response(false, "No token provided", 401);
            String jRes = GsonBuilderRun.getGson().toJson(response);
            exchange.sendResponseHeaders(response.getCode(), jRes.length());
            OutputStream os = exchange.getResponseBody();
            os.write(jRes.getBytes());
            os.close();
        }
    }
    private static void getNumberFollowers(HttpExchange exchange) throws IOException{
        String authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            String body = new String(exchange.getRequestBody().readAllBytes());
            Follow follow = GsonBuilderRun.getGson().fromJson(body, Follow.class);

            Response response;
            follow.setUser(LoginRequest.userTokenToID(token));
            if (Objects.equals(follow.getUser(), "17")) {
                response = new Response(false, "login error!", 400);
            } else {
                Integer numberOfFollowers = follow.getNumberOfFollowers();
                response = new Response(true, "like changed!", 200,numberOfFollowers);
            }

            String jRes = GsonBuilderRun.getGson().toJson(response);
            exchange.sendResponseHeaders(response.getCode(), jRes.length());
            OutputStream os = exchange.getResponseBody();
            os.write(jRes.getBytes());
            os.close();
        } else {
            Response response = new Response(false, "No token provided", 401);
            String jRes = GsonBuilderRun.getGson().toJson(response);
            exchange.sendResponseHeaders(response.getCode(), jRes.length());
            OutputStream os = exchange.getResponseBody();
            os.write(jRes.getBytes());
            os.close();
        }
    }
}
