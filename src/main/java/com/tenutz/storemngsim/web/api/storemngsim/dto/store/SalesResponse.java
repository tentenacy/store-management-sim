package com.tenutz.storemngsim.web.api.storemngsim.dto.store;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SalesResponse {

    private String storeCode;
    private String orderNumber;
    private String orderType;
    private String orderedAt;
    private String posNumber;
    private String approvalType;
    private String paymentType;
    private int paymentAmount;
    private String saleNumber;
    private String approvalNumber;
    private String creditCardCompanyName;
}
