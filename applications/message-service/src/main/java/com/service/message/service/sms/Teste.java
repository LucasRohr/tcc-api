package com.service.message.service.sms;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Teste {
    public static void main(String[] args) throws IOException {
        String apiResponse = "";

        final String url = "https://registrocivil.org.br:8443/api/carrinho/pedidos/validarCodigoHash/dnsajdfsjdnfjdsnfdsfkdjsfjksdjkf";

        HttpURLConnection httpClient =
                (HttpURLConnection) new URL(url).openConnection();

        httpClient.setRequestMethod("GET");
        httpClient.setRequestProperty("user-agent", "Chrome/81.0.4044.129");
        httpClient.setRequestProperty(
                "authorization",
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiI2b2oxTDB2Ynl0bjMzMzRTWWJaNVF" +
                        "JdlpuVGhhZGVYeCIsImhhc2hfY29kZSI6ImZzZGYxczFkZjFkZjY1ZjZzZGY2c2ZkZjZkczZmMX" +
                        "NkIn0.WCUgzSlTFbeWodyP8z6oLYeGZD8o-jBqDRjxIALgguA"
        );

        httpClient.setRequestProperty(
                "apikey",
                "7CojClx9l62Mz6SJcEHFWZfK2NtSHXgI"
        );

        int responseCode = httpClient.getResponseCode();

        System.out.print(responseCode);

        if (responseCode != 200) {
            System.out.println("erro aaaaa");
        }

        InputStream inputStream = new ByteArrayInputStream(apiResponse.getBytes());
        StringBuilder stringBuilder = new StringBuilder();

        try (Reader reader = new BufferedReader(new InputStreamReader
                (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;

            while ((c = reader.read()) != -1) {
                stringBuilder.append((char) c);
            }
        }

        System.out.println("====== " + stringBuilder.toString() + " ======");
    }
}
