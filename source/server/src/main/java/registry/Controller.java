package registry;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import registry.entity.menu.MenuItemRepository;
import registry.util.JsonHelper;
import registry.util.ModelHelper;
import registry.util.ReportUtils;
import registry.util.ResponseHelper;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Map;


@RestController
public class Controller {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ReportUtils report;
    @Autowired
    private MenuItemRepository menuItemRepository;

    @RequestMapping("/api/hi")
    public ResponseEntity<Resource> getHi() {
        return ResponseHelper.get("hi", MediaType.TEXT_PLAIN);
    }

    @RequestMapping ("/api/menu")
    public ResponseEntity<Resource> getMenu() throws JsonProcessingException {
        return ResponseHelper.getAsJson(JsonHelper.getAsBytes(menuItemRepository.findAllRoot()));
    }

    @RequestMapping("/api/report/{report_id}")
    public ResponseEntity<Resource> getReport(@PathVariable("report_id") String report_id) {
        String result = this.report.createReport(Long.valueOf(report_id));
        return ResponseHelper.get(result, MediaType.APPLICATION_OCTET_STREAM, report_id + ".doc");
    }

    @GetMapping("/api/model/{model_name}")
    public ResponseEntity<Resource> getModel(@PathVariable("model_name") String modelName,
                                             @RequestBody(required = false) Map<String, String> parameters) throws IOException {
        return ResponseHelper.getAsJson(ModelHelper.getItem(modelName, "get", parameters));
    }

    @PostMapping("/api/model/{model_name}")
    public ResponseEntity<Resource> postModel(@PathVariable("model_name") String modelName,
                                             @RequestBody(required = false) Map<String, String> parameters) throws IOException {
        return ResponseHelper.getAsJson(ModelHelper.getItem(modelName, "sql_insert", parameters));
    }

    @PutMapping("/api/model/{model_name}")
    public ResponseEntity<Resource> putModel(@PathVariable("model_name") String modelName,
                                             @RequestBody(required = false) Map<String, String> parameters) throws IOException {
        return ResponseHelper.getAsJson(ModelHelper.getItem(modelName, "sql_update", parameters));
    }

    @DeleteMapping("/api/model/{model_name}")
    public ResponseEntity<Resource> deleteModel(@PathVariable("model_name") String modelName,
                                             @RequestBody(required = false) Map<String, String> parameters) throws IOException {
        return ResponseHelper.getAsJson(ModelHelper.getItem(modelName, "sql_delete", parameters));
    }
}