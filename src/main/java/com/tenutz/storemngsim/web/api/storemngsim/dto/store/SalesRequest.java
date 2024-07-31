package com.tenutz.storemngsim.web.api.storemngsim.dto.store;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SalesRequest {

    private String paymentType;
    private String approvalType;
    private String orderType;
}
