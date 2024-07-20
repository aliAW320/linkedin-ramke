package org.linkedin.Controllers;

import com.sun.net.httpserver.HttpExchange;
import org.linkedin.Models.Like;
import org.linkedin.Models.LoginRequest;
import org.linkedin.utils.GsonBuilderRun;
import org.linkedin.utils.Response;

import java.io.IOException;
import java.io.OutputStream;

public class LikeController {
    public static void like(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()){
            case "POST", "PATCH", "DELETE" -> liked(exchange);//update like (remove or liked)
            case "GET" -> {return;}//get post number of likes
        }
    }

    private static void liked(HttpExchange exchange) throws IOException {
        String authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            String body = new String(exchange.getRequestBody().readAllBytes());
            Like like = GsonBuilderRun.getGson().fromJson(body, Like.class);

            Response response;
            like.setIDUser(LoginRequest.userTokenToID(token));
            if (like.getUserID() == 17) {
                response = new Response(false, "login error!", 400);
            } else {
                like.doLikeAction();
                response = new Response(true, "like changed!", 200);
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
