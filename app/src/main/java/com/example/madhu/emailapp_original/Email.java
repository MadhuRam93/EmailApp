package com.example.madhu.emailapp_original;

/**
 * Created by madhu on 16-04-2017.
 * Email class
 */

public class Email {
    private String to, body, subject;

    public Email(String to, String subject, String body) {
        this.to = to;
        this.body = body;
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
