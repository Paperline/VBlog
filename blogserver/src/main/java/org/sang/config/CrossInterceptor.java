package org.sang.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CrossInterceptor implements HandlerInterceptor {

    private final static String ORIGIN_FLAG = "u-origin";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String origin = request.getHeader("Origin");
        if(origin!= null && !"".equals(origin) || "null".equals(origin)){
            origin = "*";
        }
        if(request.getHeader(ORIGIN_FLAG) != null && !"".equals(request.getHeader(ORIGIN_FLAG))){
            origin = request.getHeader(ORIGIN_FLAG);
        }

        response.addHeader("Access-Control-Allow-Origin", origin);
        response.addHeader("Access-Control-Allow-Methods","*");
        response.addHeader("Access-Control-Max-Age","1800");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type,u-origin");
        response.addHeader("Access-Control-Allow-Credentials","true");

        return request.getHeader(HttpHeaders.ORIGIN) == null
                || !HttpMethod.OPTIONS.matches(request.getMethod())
                || request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD) == null;
    }
}