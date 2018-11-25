package common.meta;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by pandechuan on 2018/10/23.
 */
@Data
@Builder
public class RunDetailReport {

    private String method;
    private String subject;
    private long costTime;
    private String info;
    private String error;
    private LocalDateTime sysTime;


}
