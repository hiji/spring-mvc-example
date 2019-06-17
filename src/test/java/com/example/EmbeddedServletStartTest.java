package com.example;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmbeddedServletStartTest {

    @Test
    void test() throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        StandardContext ctx = (StandardContext) tomcat.addWebapp(
                "/",
                new File("src/main/webapp").getAbsolutePath());
        // web.xmlが読めてないようで、コードでServlet設定すると動いた
        Tomcat.addServlet(ctx, "helloServlet", new HelloServlet()).addMapping("/hello");

        // Tomcat9からgetConnector()を呼ばないと起動に失敗してしまう
        tomcat.getConnector();
        tomcat.start();
//        tomcat.getServer().await();

        HttpURLConnection conn = (HttpURLConnection) URI.create("http://localhost:8080/hello").toURL().openConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            assertEquals("Hello Servlet!!", reader.readLine());
        }
        conn.disconnect();

        tomcat.stop();
    }
}
