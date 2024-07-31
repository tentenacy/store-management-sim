package com.tenutz.storemngsim.web.api.kiosksim.dto.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PaymentInfo {

    private PaymentInfoNoCreditCard paymentInfoNoCreditCard;
    private String creditCardNumber;
    private String purchaser;
    private String approvalNumber;
    private String issuer;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class PaymentInfoNoCreditCard {
        private String posNumber;
        private String billNumber;
        private String callNumber;
        private String orderId;
        private String orderSerialNumber;
        private String transactionCode;
    }
}
