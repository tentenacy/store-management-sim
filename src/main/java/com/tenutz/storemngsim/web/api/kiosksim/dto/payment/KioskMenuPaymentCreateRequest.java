package com.tenutz.storemngsim.web.api.kiosksim.dto.payment;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KioskMenuPaymentCreateRequest {

    private List<MenuPayment> menuPayments;

    private String mainCategoryCode;

    private String middleCategoryCode;

    private String subCategoryCode;

    private String orderType;

    private boolean isCanceled;

    private String paymentCode;

    private String creditCardCompanyCode;

    private Integer totalAmount;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MenuPayment {

        private String itemCode;

        private String itemType;

        private String itemName;

        private Integer price;

        private Integer amount;

        private Integer discountAmount;

        private Integer quantity;

        private String details;
    }
}
