package controllers;

import java.util.Random;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result getUsers() {
        ObjectNode result = Json.newObject();
        Random r = new Random();
        ArrayNode users = result.putArray("users");
        for (int i = 0; i < 10; i++) {
            users.add(userToObjectNode(new User("User " + 1, r.nextInt(90 + i))));
        }

        return ok(result);
    }

    public static Result getUserByName(String name) {
        User user = new User(name, new Random().nextInt(99));
        return ok(userToObjectNode(user));
    }

    private static ObjectNode userToObjectNode(User user) {
        ObjectNode node = Json.newObject();
        node.put("name", user.getName());
        node.put("age", user.getAge());
        return node;
    }

    public static class User {
        private final String name;
        private final Integer age;

        public User(String name, Integer age) {
            super();
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        @Override
        public String toString() {
            return this.name + ": " + this.age;
        }
    }

}
