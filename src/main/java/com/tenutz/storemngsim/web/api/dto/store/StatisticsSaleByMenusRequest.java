package com.tenutz.storemngsim.web.api.dto.store;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StatisticsSaleByMenusRequest {

    private String mainCategoryCode;
}
