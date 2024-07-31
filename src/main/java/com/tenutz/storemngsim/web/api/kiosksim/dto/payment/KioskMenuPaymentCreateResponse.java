package com.tenutz.storemngsim.web.api.kiosksim.dto.payment;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class KioskMenuPaymentCreateResponse {

    private String callNumber;
}
