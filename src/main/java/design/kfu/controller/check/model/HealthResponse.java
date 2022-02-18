package design.kfu.controller.check.model;

import lombok.*;

/**
 * @author Daniyar Zakiev
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HealthResponse {
    private String status;
    private int code;
}
