package com.service.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    private final Map<String, String> headers;
    private final Map<String, String> headersOptions;

    @Value(value = "${Access-Control-Allow-Origin}")
    private String accessControlAllowOrigin;

    @Value(value = "${Access-Control-Allow-Credentials}")
    private String accessControlAllowCredentials;

    @Value(value = "${Access-Control-Allow-Methods}")
    private String accessControlAllowMethods;

    @Value(value = "${Access-Control-Allow-Headers}")
    private String accessControlAllowHeaders;

    @Value(value = "${Access-Control-Allow-Headers}")
    private String accessControlExposeHeaders;

    public CorsFilter() {
        this.headersOptions = new HashMap<>();
        this.headers = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        headers.put("Access-Control-Allow-Origin", accessControlAllowOrigin);
        headers.put("Access-Control-Allow-Credentials", accessControlAllowCredentials);
        headers.put("Access-Control-Allow-Methods", accessControlAllowMethods);
        headers.put("Access-Control-Allow-Headers", accessControlAllowHeaders);
        headers.put("Access-Control-Expose-Headers", accessControlExposeHeaders);
    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) sr1;
        HttpServletRequest request = (HttpServletRequest) sr;

        this.addHeaders(response, headers);
        if (request.getMethod().equals("OPTIONS")) {
            this.addHeaders(response, headersOptions);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(sr, sr1);
        }
    }

    public void addHeaders(final HttpServletResponse response, final Map<String, String> map) {
        Set<String> keys = map.keySet();
        keys.forEach(header -> response.addHeader(header, map.get(header)));
    }
}
