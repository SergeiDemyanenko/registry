package registry;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import registry.entity.menu.MenuItemRepository;
import registry.util.JsonHelper;
import registry.util.ModelHelper;
import registry.util.ReportUtils;
import registry.util.ResponseHelper;

import javax.sql.DataSource;
import java.lang.reflect.Method;


@RestController
public class Controller {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ReportUtils report;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private ModelHelper model;

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

    @RequestMapping("/api/directory/{modelName}")
    public ResponseEntity<Resource> getData(@PathVariable("modelName") String modelName) {
        return ResponseHelper.get(model.executeModel(modelName), MediaType.APPLICATION_JSON_UTF8);
    }
}