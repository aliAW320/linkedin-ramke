package org.linkedin.Controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import com.sun.net.httpserver.HttpExchange;
import org.linkedin.Models.Education;
import org.linkedin.Models.LoginRequest;
import org.linkedin.utils.GsonBuilderRun;
import org.linkedin.utils.Response;

public class EducationController {
    public static void education(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case "POST", "PATCH" -> updateEducationData(exchange);
            case "GET" -> getUserEducationData(exchange);
//            case "DELETE" -> {}

        }
    }
    private static void updateEducationData(HttpExchange exchange) throws IOException {
        String authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            String body = new String(exchange.getRequestBody().readAllBytes());
            Education education = GsonBuilderRun.getGson().fromJson(body, Education.class);

            Response response;
            education.setUserID(LoginRequest.userTokenToID(token));
            if (Objects.equals(education.getUserID(), "17")) {
                response = new Response(false, "login error!", 400);
            }
            else {
                education.updateDB();
                response = new Response(true, String.valueOf(education.getId()), 200);
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
    private static void getUserEducationData(HttpExchange exchange) throws IOException {
        String authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            String body = new String(exchange.getRequestBody().readAllBytes());
            Education education = GsonBuilderRun.getGson().fromJson(body, Education.class);
            if(education == null)
                education = new Education();
            Response response;
            education.setUserID(LoginRequest.userTokenToID(token));
            if (Objects.equals(education.getUserID(), "17")) {
                response = new Response(false, "login error!", 400);
            }
            else {
                education.getEducationData();
                response = new Response(true, String.valueOf(education.getId()), 200,education);
            }

            String jRes = GsonBuilderRun.getGson().toJson(response);
            jRes = jRes.replaceAll("\"object\":", "\"education\":");
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
