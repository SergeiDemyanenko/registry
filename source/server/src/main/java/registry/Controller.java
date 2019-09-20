package registry;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import registry.entity.menu.MenuItemRepository;
import registry.utils.ModelUtils;
import registry.utils.ReportUtils;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;


@RestController
public class Controller {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ReportUtils report;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private ModelUtils model;

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

    @RequestMapping("/api/directory/{modelName}")
    public String getData(@PathVariable("modelName") String modelName) {
        return model.executeModel(modelName);
    }
}