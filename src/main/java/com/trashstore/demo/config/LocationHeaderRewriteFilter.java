package com.trashstore.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class LocationHeaderRewriteFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response) {
            @Override
            public void setHeader(String name, String value) {
                if ("Location".equalsIgnoreCase(name) && value != null) {
                    value = rewriteLocation(value, request);
                }
                super.setHeader(name, value);
            }

            @Override
            public void addHeader(String name, String value) {
                if ("Location".equalsIgnoreCase(name) && value != null) {
                    value = rewriteLocation(value, request);
                }
                super.addHeader(name, value);
            }
        };

        filterChain.doFilter(request, wrapper);
    }

    private String rewriteLocation(String location, HttpServletRequest request) {
        if (location.contains(":8080")) {
            String forwardedHost = request.getHeader("X-Forwarded-Host");
            if (forwardedHost != null && !forwardedHost.isEmpty()) {
                try {
                    java.net.URI uri = new java.net.URI(location);
                    // Use forwardedHost (may include port). Passing -1 for port keeps forwardedHost's port if present.
                    java.net.URI newUri = new java.net.URI(uri.getScheme(), uri.getUserInfo(), forwardedHost, -1, uri.getPath(), uri.getQuery(), uri.getFragment());
                    return newUri.toString();
                } catch (Exception e) {
                    return location.replace(":8080", "");
                }
            }
            return location.replace(":8080", "");
        }
        return location;
    }
}
