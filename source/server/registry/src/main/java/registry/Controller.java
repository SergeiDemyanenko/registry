package registry;

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
import registry.report.Report;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@RestController
public class Controller {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private Report report;

    @RequestMapping("/")
    public String index() {
        return "<html><body><h1>registry server</h1></body></html>";
    }

    @RequestMapping("/api/hi")
    public String hi() {
        return "hi";
    }

    private Map<String, String> menuMap = new HashMap<>();
    {
        menuMap.put("Физические лица", "api/directory/person");
        menuMap.put("Статус заявки", "api/directory/appStatus");
        menuMap.put("Характеристики", "api/directory/characteristics");
        menuMap.put("Результат проверки", "api/directory/checkResult");
        menuMap.put("Орган СРО", "api/directory/department");
        menuMap.put("Основания для исключения", "api/directory/expulsion");
        menuMap.put("Компенсационный фонд ОДО", "api/directory/findSumContact");
        menuMap.put("Компенсационный фонд ВВ", "api/directory/findSumHarm");
    }

    private static final String TITLE = "TITLE";
    private static final String ITEMS = "ITEMS";
    private static final String LINK = "LINK";
    private static final String ORDER = "ORDER";

    @RequestMapping ("/api/menu")
    public String menu () {
        JSONArray result = new JSONArray();

        // dict
        JSONArray dictMenu = new JSONArray();
        for (Map.Entry<String, String> entry : menuMap.entrySet()) {
            dictMenu.put(new JSONObject().put(TITLE, entry.getKey()).put(ORDER, dictMenu.length()).put(LINK, entry.getValue()));
        }
        result.put(new JSONObject().put(TITLE, "Справочники").put(ITEMS, dictMenu).put(ORDER, result.length()));

        result.put(new JSONObject().put(TITLE, "Документы").put(ORDER, result.length()).put(ITEMS, new JSONObject().put(TITLE, "test_line")));
        result.put(new JSONObject().put(TITLE, "Отчеты").put(ORDER, result.length()).put(ITEMS, new JSONObject().put(TITLE, "test_line")));
        result.put(new JSONObject().put(TITLE, "Настройки").put(ORDER, result.length()).put(ITEMS, new JSONObject().put(TITLE, "test_line")));
        result.put(new JSONObject().put(TITLE, "Помощь").put(ORDER, result.length()).put(ITEMS, new JSONObject().put(TITLE, "test_line")));

        return new JSONObject().put(ITEMS, result).toString();
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
}