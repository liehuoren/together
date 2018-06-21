package com.zhlzzz.together.match;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import java.io.Serializable;

@ApiModel(description = "匹配参数")
@Data
public class MatchParam implements Serializable {

    private static final long serialVersionUID = -7062376040431349272L;

    public MatchParam() {

    }
    private String name;
    private Integer gameTypeId;
    private Long userId;
    @NonNull
    private String formId;
    private Integer memberNum;
    private Integer minute;
    private Match.Range matchRange;
    private String otherItem;

}
