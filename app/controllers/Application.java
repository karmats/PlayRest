package controllers;

import java.util.List;
import java.util.Random;

import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result getUsers() {
        ObjectNode result = Json.newObject();
        
        ArrayNode userJson = result.putArray("users");
        List<User> users = Ebean.find(User.class).findList();
        for (User user : users) {
            userJson.add(userToObjectNode(user));
        }

        return ok(result);
    }

    public static Result getUserByName(String name) {
        User user = new User(name, new Random().nextInt(99));
        return ok(userToObjectNode(user));
    }

    public static Result addUser() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("JSON payload expected");
        }
        User user = new User(json.get("name").asText(), json.get("age").asInt());
        user.save();
        return ok(userToObjectNode(user));
    }

    private static ObjectNode userToObjectNode(User user) {
        ObjectNode node = Json.newObject();
        node.put("name", user.getName());
        node.put("age", user.getAge());
        return node;
    }

}
