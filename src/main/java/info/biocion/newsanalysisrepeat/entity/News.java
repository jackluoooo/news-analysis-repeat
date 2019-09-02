package info.biocion.newsanalysisrepeat.entity;

import lombok.Data;

import java.util.Date;

@Data
public class News {
    //
    private Long id;
   //内容
    private String content;

    private Date date;

    private Integer isSearch;

    private Integer isNeedCheck;

    private Integer isFilter;
}
