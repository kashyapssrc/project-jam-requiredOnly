package com.objectfrontier.training.web.application.util;

public class Response {

    int statusCode;
    String content;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Response(int statusCode, String content) {
        super();
        this.statusCode = statusCode;
        this.content = content;
    }

    public Response() {
        super();
        // TODO Auto-generated constructor stub
    }
}
