package com.jacobyang.mini.bean.result;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author guochao
 * @since 1.0.0
 */
@Data
public class DataVisitDistributionListResult{
    private String ref_date;
    private List<DataVisitDistributionObject> list;
    @Builder.Default
    private Integer errcode = 0;
    @Builder.Default
    private String errmsg = "success";
}