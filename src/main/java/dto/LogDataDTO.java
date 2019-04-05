package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogDataDTO {
    private String id;
    private String state;
    private String type;
    private String host;
    private String timestamp;
}
