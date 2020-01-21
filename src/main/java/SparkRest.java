import com.google.gson.Gson;
import model.User;
import resp.StandardResponse;
import service.impl.UserServiceImpl;
import static spark.Spark.*;

public class SparkRest{

    private static String HELLO = "Hello";
    private static int DEFAULT_PORT = 8081;
    public static UserServiceImpl userService = new UserServiceImpl();

    public static void main(String args[]){

        port(DEFAULT_PORT);

        get("/hello", (req, res)-> "say " + HELLO);

        get("/hello/:name", (req,res)->{
            return HELLO +" "+ req.params(":name");
        });

        post("/users", (request, response) -> {
            response.type("application/json");
            User user = new Gson().fromJson(request.body(), User.class);
            userService.addUser(user);
            return new Gson()
                    .toJson(new StandardResponse(StandardResponse.StatusResponse.SUCCESS));
        });

        get("/users", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(
                    new StandardResponse(StandardResponse.StatusResponse.SUCCESS,new Gson()
                            .toJsonTree(userService.getUsers())));
        });

        /*get("/users/:id", (request, response) -> {
            //...
        });
        put("/users/:id", (request, response) -> {
            //...
        });
        delete("/users/:id", (request, response) -> {
            //...
        });
        options("/users/:id", (request, response) -> {
            //...
        });*/

    }
}
