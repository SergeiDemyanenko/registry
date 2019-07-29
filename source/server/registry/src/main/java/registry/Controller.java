package registry;

import org.springframework.core.io.Resource;
import dataBase.DataBase;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import report.Report;

import java.nio.charset.StandardCharsets;
import java.sql.*;


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

    @RequestMapping ("directory/person")
    public String person () {
        Connection conn = DataBase.getConnection();
        String query = "SELECT  * from PERSON";
        return DataBase.getJsonFromSQL(conn, query);
    }

    @RequestMapping(path = "/report/{report_id}")
    public ResponseEntity<Resource> getReport(@PathVariable("report_id") String report_id) {

        String result = Report.createReport(DataBase.getConnection(), Long.valueOf(report_id));
        ByteArrayResource resource = new ByteArrayResource(result.getBytes(StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + report_id + ".doc")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }
}