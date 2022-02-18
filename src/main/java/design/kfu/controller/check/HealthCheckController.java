package design.kfu.controller.check;

import design.kfu.controller.check.model.HealthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping
    public HealthResponse checkHealth() {
        return HealthResponse.builder().code(200).status("Сервис запущен и работает корректно").build();
    }
}
