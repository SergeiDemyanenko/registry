package registry;

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

    public List<JSONObject> person(Connection connection) throws Exception{
        ResultSet result;
        List<JSONObject> jsonList = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement();
                result = statement.executeQuery("SELECT  * from PERSON");
                rsmd = result.getMetaData();
                int columnNumber = rsmd.getColumnCount();
                if (columnNumber == 0) {
                    throw new SQLException("Table PERSON does not exist");
                }
                ArrayList<String> row;
                JSONObject json;
                ArrayList<String> header = new ArrayList<String>();
                for (int i = 1; i <= columnNumber; i++) {
                    header.add(rsmd.getColumnName(i));
                }
                while (result.next()) {
                    row = new ArrayList<>();
                    json = new JSONObject();
                    for (int i = 1; i <= columnNumber; i++) {
                        if (result.getString(i) != null) {
                            json.put(header.get(i-1), result.getString(i));
                            row.add(result.getString(i));
                        }
                    }
                    jsonList.add(json);
                }
                statement.close();
                result.close();
            }

        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return jsonList;
    }



}