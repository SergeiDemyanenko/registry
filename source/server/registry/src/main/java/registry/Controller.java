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
        menuMap.put("Физические лица", "person");
        menuMap.put("Статус заявки", "appStatus");
        menuMap.put("Характеристики", "characteristics");
        menuMap.put("Результат проверки", "checkResult");
        menuMap.put("Орган СРО", "department");
        menuMap.put("Основания для исключения", "expulsion");
        menuMap.put("Компенсационный фонд ОДО", "findSumContact");
        menuMap.put("Компенсационный фонд ВВ", "findSumHarm");
    }

    private static final String NAME = "NAME";
    private static final String TITLE = "TITLE";
    private static final String ITEMS = "ITEMS";
    private static final String URL = "URL";
    private static final String NUMBER = "NUMBER";

    @RequestMapping ("/api/menu")
    public String menu () {
        JSONArray result = new JSONArray();

        // dict
        JSONArray dictMenu = new JSONArray();
        for (Map.Entry<String, String> entry : menuMap.entrySet()) {
            dictMenu.put(new JSONObject().put(NAME, entry.getValue()).put(TITLE, entry.getKey()).put(NUMBER, dictMenu.length()).put(URL, "/api${path}"));
        }
        result.put(new JSONObject().put(NAME, "directory").put(TITLE, "Справочники").put(ITEMS, dictMenu).put(NUMBER, result.length()));

        result.put(new JSONObject().put(NAME, "documents").put(TITLE, "Документы").put(NUMBER, result.length()).put(ITEMS, new JSONArray().put(new JSONObject().put(NAME, "test_line").put(TITLE, "test_line"))));
        result.put(new JSONObject().put(NAME, "reports").put(TITLE, "Отчеты").put(NUMBER, result.length()).put(ITEMS, new JSONArray().put(new JSONObject().put(NAME, "test_line").put(TITLE, "test_line"))));
        result.put(new JSONObject().put(NAME, "settings").put(TITLE, "Настройки").put(NUMBER, result.length()).put(ITEMS, new JSONArray().put(new JSONObject().put(NAME, "test_line").put(TITLE, "test_line"))));
        result.put(new JSONObject().put(NAME, "help").put(TITLE, "Помощь").put(NUMBER, result.length()).put(ITEMS, new JSONArray().put(new JSONObject().put(NAME, "test_line").put(TITLE, "test_line"))));

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