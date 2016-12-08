package com.company;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    static User name;
    static ArrayList<Messages> messageList = new ArrayList();

    public static void main(String[] args) {
        Spark.init();

        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap createUser = new HashMap();
                    if (name == null) {
                        return new ModelAndView(createUser, "index.html");
                    } else {
                        createUser.put("name", name.userName);
                        createUser.put("messages", messageList);
                        return new ModelAndView(createUser, "messages.html");
                    }
                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/create-user",
                ((request, response) -> {
                    String user = request.queryParams("createUser");
                    String password = request.queryParams("enterPassword");
                    name = new User(user, password);
                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/create-message",
                ((request, response) -> {
                    String blogPost = request.queryParams("createMessage");
                    messageList.add(new Messages(blogPost));
                    response.redirect("/");
                    return "";
                })
        );
    }
}