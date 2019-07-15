package registry;

import dataBase.DataBase;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @RequestMapping("/")
    public String index() {
        return "<html><body><h1>Java is cool!!!</h1></body></html>";
    }

    @RequestMapping("/hi")
    public String hi() {
        return "hi";
    }

    @RequestMapping ("/person")
    public String person () {
        Connection conn = DataBase.getConnection();
        String query = "SELECT  * from PERSON";
        return DataBase.executeQuery(conn, query);
    }

}