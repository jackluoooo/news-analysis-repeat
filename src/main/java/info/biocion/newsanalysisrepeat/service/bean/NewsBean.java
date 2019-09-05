package info.biocion.newsanalysisrepeat.service.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 返回前段实体
 */
@Data
public class NewsBean {
    private Long id;
    private String title;
    private String content;
    private String cover;
    private String origin;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date date;
    private Integer classId;
}
