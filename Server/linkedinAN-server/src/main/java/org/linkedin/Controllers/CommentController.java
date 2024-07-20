package org.linkedin.Controllers;

import com.sun.net.httpserver.HttpExchange;
import org.linkedin.Models.Comment;
import org.linkedin.Models.LoginRequest;
import org.linkedin.Models.Post;
import org.linkedin.utils.GsonBuilderRun;
import org.linkedin.utils.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

public class CommentController {
    public static void comment(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case "POST" -> addComment(exchange);
            case "GET" -> getPostComments(exchange);
            case "DELETE" -> deleteComment(exchange);
        }
    }
    private static void addComment(HttpExchange exchange) throws IOException {
        String authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            String body = new String(exchange.getRequestBody().readAllBytes());
            Comment comment = GsonBuilderRun.getGson().fromJson(body, Comment.class);

            Response response;
            comment.setUserID(LoginRequest.userTokenToID(token));
            if (Objects.equals(comment.getUserID(), "17")) {
                response = new Response(false, "login error!", 400);
            } else {
                comment.addComment();
                response = new Response(true, "comment added!", 200);
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
    private static void deleteComment(HttpExchange exchange) throws IOException {
        String authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            String body = new String(exchange.getRequestBody().readAllBytes());
            Comment comment = GsonBuilderRun.getGson().fromJson(body, Comment.class);

            Response response;
            comment.setUserID(LoginRequest.userTokenToID(token));
            if (Objects.equals(comment.getUserID(), "17")) {
                response = new Response(false, "login error!", 400);
            } else {
                comment.deleteComment();
                response = new Response(true, "comment deleted!", 200);
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
    private static void getPostComments(HttpExchange exchange) throws IOException {
        String authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            String body = new String(exchange.getRequestBody().readAllBytes());
            Post post = GsonBuilderRun.getGson().fromJson(body, Post.class);

            Response response;
            post.setUserID(Long.valueOf(LoginRequest.userTokenToID(token)));
            if (Objects.equals(post.getUserID(), "17")) {
                response = new Response(false, "login error!", 400);
            } else {
                List<Comment> commentList = Comment.getPostComments(Long.valueOf(post.getId()));
                response = new Response(true, "here post comments!", 200,commentList);
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


