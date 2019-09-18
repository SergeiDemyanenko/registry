package registry;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import registry.dataBase.DataBase;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import registry.menu.MenuItemEntity;
import registry.menu.MenuItemRepository;
import registry.report.Report;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@RestController
public class Controller {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private Report report;
    @Autowired
    private MenuItemRepository menuItemRepository;

    @RequestMapping("/")
    public String index() {
        return "<html><body><h1>registry server</h1></body></html>";
    }

    @RequestMapping("/api/hi")
    public String hi() {
        return "hi";
    }

    @RequestMapping ("/api/menu")
    public String menu () throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return mapper.writeValueAsString(menuItemRepository.findAllRoot());
    }

    @RequestMapping ("/api/directory/person")
    public String person () {
        return DataBase.getJsonFromSQL(dataSource, "SELECT * from PERSON");
    }

    @RequestMapping(path = "/api/report/{report_id}")
    public ResponseEntity<Resource> getReport(@PathVariable("report_id") String report_id) {

        String result = this.report.createReport(Long.valueOf(report_id));
        ByteArrayResource resource = new ByteArrayResource(result.getBytes(StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + report_id + ".doc")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @RequestMapping("/api/derictory/{modelName}")
    public String getData(@PathVariable("modelName") String name) {
        return null;
    }
}