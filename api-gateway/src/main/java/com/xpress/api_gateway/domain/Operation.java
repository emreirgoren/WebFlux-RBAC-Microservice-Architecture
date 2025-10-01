package com.xpress.api_gateway.domain;

import org.springframework.http.HttpMethod;

import java.util.Map;

import static org.springframework.http.HttpMethod.*;

public enum Operation {

    READ, WRITE, UPDATE, DELETE;

    private static final Map<HttpMethod, Operation> map = Map.of(
            HttpMethod.GET, READ,
            HttpMethod.POST, WRITE,
            HttpMethod.PUT, UPDATE,
            HttpMethod.DELETE, DELETE
    );

    public static Operation fromHttpMethod(HttpMethod method) {
        return map.getOrDefault(method, null);
    }


}
