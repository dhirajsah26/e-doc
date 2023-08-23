package com.example.edoc.util;
/*
* @author : Sanjay shah
* @date : Sep 14, 2019
*/

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

    private boolean success;

    private String message;

    private Object body;

    public Response(Object body, String message, boolean success) {
        this.body = body;
        this.message = message;
        this.success = success;
    }

}