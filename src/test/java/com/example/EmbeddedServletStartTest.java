package com.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmbeddedServletStartTest {

    private static Tomcat tomcat;

    @BeforeAll
    void initAll() throws Exception {
        tomcat = new Tomcat();
        tomcat.setPort(8080);
        // addWebapp呼ばなかったらエラーになった
        StandardContext ctx = (StandardContext) tomcat.addWebapp(
                "/",
                new File("src/main/webapp").getAbsolutePath());
        // web.xml使うため一旦コメントアウト
//        Tomcat.addServlet(ctx, "helloServlet", new HelloServlet()).addMapping("/hello");

        // Tomcat9からgetConnector()を呼ばないと起動に失敗してしまう
        tomcat.getConnector();
        tomcat.start();
        // テスト実行中だけ起動するので await は呼ばない
//        tomcat.getServer().await();
    }

    @AfterAll
    static void tearDownAll() throws Exception {
        tomcat.stop();
    }

    @Test
    void helloGetAPI() throws Exception {
        Response response = RestAssured.get("/app/hello");
        assertEquals("hoge", response.jsonPath().getString("name"));
        assertEquals(18, response.jsonPath().getInt("age"));
    }

    @Test
    void helloServlet() throws Exception {
        HttpURLConnection conn = (HttpURLConnection) URI.create("http://localhost:8080/hello").toURL().openConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            assertEquals("Hello Servlet!!", reader.readLine());
        }
        conn.disconnect();
    }
}
