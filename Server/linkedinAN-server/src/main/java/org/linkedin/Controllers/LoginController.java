package org.linkedin.Controllers;

import com.sun.net.httpserver.HttpExchange;
import org.linkedin.Models.Like;
import org.linkedin.Models.LoginRequest;
import org.linkedin.Models.User;
import org.linkedin.utils.GsonBuilderRun;
import org.linkedin.utils.Response;

import java.io.IOException;
import java.io.OutputStream;

public class LoginController {
    public static void login(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case "POST", "GET", "PATCH", "DELETE" -> tryLogin(exchange);
        }
    }
    private static void tryLogin(HttpExchange exchange) throws IOException {
        String header =  exchange.getRequestURI().getQuery();
        String body = new String(exchange.getRequestBody().readAllBytes());
        System.out.println(body);
        LoginRequest loginRequest = GsonBuilderRun.getGson().fromJson(body, LoginRequest.class);
        Response response = null;
        if(!User.authenticateUser(loginRequest.getEmail(),loginRequest.getPassword())){
            response = new Response(false, "username or password is incorrect!", 400);
        }else {
            String token = loginRequest.createLoginSession();
            response = new Response(true,"login",200,token);
        }
        String jRes = GsonBuilderRun.getGson().toJson(response);
        exchange.sendResponseHeaders(response.getCode(), jRes.length());
        OutputStream os = exchange.getResponseBody();
        os.write(jRes.getBytes());
        os.close();
    }



}
