package registry;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}