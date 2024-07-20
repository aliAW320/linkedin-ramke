package org.linkedin.HttpServer;

import org.linkedin.Controllers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServerSetup {
    public HttpServerSetup(int port) throws IOException {
        com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/user", UserController::user);
        server.createContext("/post", PostController::post);
        server.createContext("/like", LikeController::like);
        server.createContext("/files", FileController::file);
        server.createContext("/login", LoginController::login);
        server.createContext("/education", EducationController::education);
        server.createContext("/follow", FollowController::follow);
        server.setExecutor(null);
        server.start();
    }


}
