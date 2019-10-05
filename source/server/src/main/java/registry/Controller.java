package registry;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import registry.entity.menu.MenuItemRepository;
import registry.util.JsonHelper;
import registry.util.ModelHelper;
import registry.util.ReportUtils;
import registry.util.ResponseHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("/api/")
public class Controller {

    public static final String USER_PARAM = "username";
    private static final String PASS_PARAM = "password";

    @Autowired
    private ReportUtils report;
    @Autowired
    private MenuItemRepository menuItemRepository;

    @RequestMapping("login")
    public void getLogin(@RequestBody(required = false) Map<String, String> parameters,
                                             HttpServletRequest request, HttpServletResponse response) {
        Object userName = request.getSession().getAttribute(USER_PARAM);
        if (userName == null) {
            request.getSession().setAttribute(USER_PARAM, "user");
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @RequestMapping("logout")
    public void getLogin(HttpServletRequest request, HttpServletResponse response) {
        request.removeAttribute(USER_PARAM);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @RequestMapping("hi")
    public ResponseEntity<Resource> getHi() {
        return ResponseHelper.get("hi", MediaType.TEXT_PLAIN);
    }

    @RequestMapping("menu")
    public ResponseEntity<Resource> getMenu() throws JsonProcessingException {
        return ResponseHelper.getAsJson(JsonHelper.getAsBytes(menuItemRepository.findAllRoot()));
    }

    @RequestMapping("report/{report_id}")
    public ResponseEntity<Resource> getReport(@PathVariable("report_id") String report_id) {
        String result = this.report.createReport(Long.valueOf(report_id));
        return ResponseHelper.get(result, MediaType.APPLICATION_OCTET_STREAM, report_id + ".doc");
    }

    @RequestMapping("model/{model_name}")
    public ResponseEntity<Resource> getModel(@PathVariable("model_name") String modelName,
                                             @RequestBody(required = false) Map<String, String> parameters,
                                             HttpServletRequest request) throws IOException {
        return getModel(modelName, request.getMethod().toLowerCase(), parameters);
    }

    @RequestMapping("model/{model_name}/{item_name}")
    public ResponseEntity<Resource> getModel(@PathVariable("model_name") String modelName,
                                             @PathVariable("item_name") String itemName,
                                             @RequestBody(required = false) Map<String, String> parameters) throws IOException {
        return ResponseHelper.getAsJson(ModelHelper.getItem(modelName, itemName, parameters));
    }
}