package beside.sunday8turtle.pickabookserver.modules.home.controller;

import beside.sunday8turtle.pickabookserver.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public CommonResponse home() {
        Map<String, String> result = new HashMap<>();
        result.put("server start", "success");
        return CommonResponse.success(result);
    }
}
