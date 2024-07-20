package org.linkedin;

import org.linkedin.DB.DBSetup;
import org.linkedin.HttpServer.HttpServerSetup;

public class Main {
    public static void main(String[] args) throws Exception {
        DBSetup.setup();
        DBSetup.tableCreate();
        HttpServerSetup httpServer = new HttpServerSetup(8000);
    }
}