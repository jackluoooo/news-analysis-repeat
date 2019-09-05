package info.biocion.newsanalysisrepeat.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.*;

@Data
public class News {
    //
    @TableField("Id")
    private Long id;

    private String title;

    private String content;

    private String cover;

    private String origin;

    private Date date;

    private Integer classId;

    private Integer classOrder;

    private Integer recommend;

    private Integer isNeedCheck;

    private Integer isFilter;

    private Integer isBlack;

}
