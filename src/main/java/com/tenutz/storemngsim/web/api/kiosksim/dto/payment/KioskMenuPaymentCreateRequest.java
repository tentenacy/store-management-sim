package com.tenutz.storemngsim.web.api.kiosksim.dto.payment;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KioskMenuPaymentCreateRequest {

    @NotNull
    private List<MenuPayment> menuPayments;

    @NotEmpty
    private String mainCategoryCode;

    @NotEmpty
    private String middleCategoryCode;

    @NotEmpty
    private String subCategoryCode;

    @NotEmpty
    private String orderType;

    @NotEmpty
    private String paymentCode;

    private String creditCardCompanyCode;

    @NotNull
    private Integer totalAmount;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MenuPayment {

        @NotEmpty
        private String itemCode;

        @NotEmpty
        private String itemType;

        @NotEmpty
        private String itemName;

        @NotNull
        private Integer price;

        @NotNull
        private Integer amount;

        @NotNull
        private Integer discountAmount;

        @NotNull
        private Integer quantity;

        private String details;
    }
}
