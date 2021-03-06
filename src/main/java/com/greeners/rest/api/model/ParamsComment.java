package com.greeners.rest.api.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParamsComment {
    @NotEmpty
    @Size(min = 2, max = 50)
    @ApiModelProperty(value = "작성자명", required = true)
    private String author;
    @NotEmpty
    @Size(min = 2, max = 500)
    @ApiModelProperty(value = "내용", required = true)
    private String content;
}
