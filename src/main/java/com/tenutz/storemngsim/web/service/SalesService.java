package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.config.file.CreditCardBINRow;
import com.tenutz.storemngsim.config.file.CreditCardBINTable;
import com.tenutz.storemngsim.domain.sales.*;
import com.tenutz.storemngsim.utils.PaymentUtils;
import com.tenutz.storemngsim.utils.TextUtils;
import com.tenutz.storemngsim.utils.TimeUtils;
import com.tenutz.storemngsim.web.api.common.dto.CommonCondition;
import com.tenutz.storemngsim.web.api.kiosksim.dto.payment.KioskMenuPaymentCreateRequest;
import com.tenutz.storemngsim.web.api.storemngsim.dto.store.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SalesService {

    private final SalesMasterRepository salesMasterRepository;
    private final SalesDetailsRepository salesDetailsRepository;
    private final SalesPaymentRepository salesPaymentRepository;
    private final CreditCardBINTable creditCardBINTable;

    public Page<SalesResponse> sales(String siteCd, String strCd, Pageable pageable, CommonCondition commonCond, SalesRequest request) {
        return salesMasterRepository.sales(siteCd, strCd, pageable, commonCond, request);
    }

    public SalesTotalResponse salesTotal(String siteCd, String strCd, CommonCondition commonCond, SalesRequest request) {
        return salesMasterRepository.totalSales(siteCd, strCd, commonCond, request);
    }

    public Page<StatisticsSalesByMenusResponse> statisticsSalesByMenu(String siteCd, String strCd, Pageable pageable, CommonCondition commonCond, StatisticsSaleByMenusRequest cond) {
        return salesMasterRepository.statisticsSalesByMenu(siteCd, strCd, pageable, commonCond, cond);
    }

    public StatisticsSalesTotalByMenusResponse statisticsSalesTotalByMenu(String siteCd, String strCd, Pageable pageable, CommonCondition commonCond, StatisticsSaleByMenusRequest cond) {
        return salesMasterRepository.statisticsSalesTotalByMenu(siteCd, strCd, pageable, commonCond, cond);
    }

    public StatisticsSalesByCreditCardResponse statisticsSalesByCreditCard(String siteCd, String strCd, CommonCondition commonCond) {
        return salesMasterRepository.statisticsSalesByCreditCard(siteCd, strCd, commonCond);
    }

    public StatisticsSalesTotalByCreditCardResponse statisticsSalesTotalByCreditCard(String siteCd, String strCd, CommonCondition commonCond) {
        return salesMasterRepository.statisticsSalesTotalByCreditCard(siteCd, strCd, commonCond);
    }

    public StatisticsSalesByTimeResponse statisticsSalesByTime(String siteCd, String strCd, Pageable pageable, CommonCondition commonCond) {
        return salesMasterRepository.statisticsSalesByTime(siteCd, strCd, pageable, commonCond);
    }

    public StatisticsSalesTotalByTimeResponse statisticsSalesTotalByTime(String siteCd, String strCd, CommonCondition commonCond) {
        return salesMasterRepository.statisticsSalesTotalByTime(siteCd, strCd, commonCond);
    }

    public StatisticsSalesByMenusTodayResponse statisticsSalesByMenusToday(String siteCd, String strCd) {
        return salesMasterRepository.statisticsSalesByMenusToday(siteCd, strCd);
    }

    @Transactional
    public void createKioskMenusPayments(String siteCd, String strCd, KioskMenuPaymentCreateRequest request) {
        //SalesMaster - 1. 포스번호(POS_NO), 2. 영수증번호(BILL_NO), 3. 호출번호(CALL_NO, 고객기준 주문번호), 4. 주문번호(ORD_ID)

        //1. 포스번호(POS_NO) XXXX-XX-XXX-XXX
        //가맹점 코드 - X1-X4
        String transactionCodeDigit1 = TextUtils.toNonStartZero(PaymentUtils.generateUniqueTransactionCode(strCd));

        //지점 코드 - X5-X6
        String siteStoreCode1 = RandomStringUtils.randomNumeric(2);

        //장비 코드 - X7-X9
        String equipmentCode1 = RandomStringUtils.random(3, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        //일련번호 - X10-X12
        String posSerialNumber1 = RandomStringUtils.randomNumeric(3);

        String posNumber = transactionCodeDigit1 + siteStoreCode1 + equipmentCode1 + posSerialNumber1;

        //2. 영수증번호(BILL_NO)
        String billNumber = TextUtils.toNonStartZero(RandomStringUtils.randomNumeric(10));

        //3. 호출번호(CALL_NO, 고객기준 주문번호)
        String callNumber;

        Optional<SalesMaster> salesMasterOptional = salesMasterRepository.findTopByCompCdAndStorCdOrderByCreatedAtDesc(siteCd, strCd);

        if(salesMasterOptional.isPresent() && salesMasterOptional.get().getOrderTm().substring(0, 8).compareTo(TimeUtils.yyyyMMdd(new Date())) >= 0) {
            callNumber = salesMasterOptional.get().getCallNumberCount();
        } else {
            callNumber = "0001";
        }

        //4. 주문번호(ORD_ID)
        //시스템 코드 - X1-X4
        //인앱 결제밖에 없으므로 "1000" 고정
        String systemCode4 = "1000";

        //가맹점 코드 - X5-X8
        String orderCodeDigit4 = PaymentUtils.generateUniqueTransactionCode(strCd);

        //거래 일련번호 - X9-X12
        String orderSerialNumber4;

        if(salesMasterOptional.isPresent()) {
            orderSerialNumber4 = salesMasterOptional.get().getOrderSerialNumberCount();
        } else {
            orderSerialNumber4 = "0001";
        }

        //추가 정보 코드 (선택적) - X13-X16
        //X13 - 1(거래승인), 2(거래취소), X14-X16 - 랜덤
        String additionalInfoCode4 = (request.isCanceled() ? "2" : "1") + RandomStringUtils.random(3, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        String orderId = systemCode4 + orderCodeDigit4 + orderSerialNumber4 + orderSerialNumber4;


        //SalesPayment - 5. 카드번호(CARD_NO), 6. 매입사명(BUY_NM), 7. 거래번호(TRAN_NO), 8. 승인번호(APPR_NO) 9. 발급사명(CARD_NM)

        //5. 카드번호(CARD_NO) - XXXX-XXXX-XXXX-XXXX
        //BIN - X1-X6(X8)
        List<CreditCardBINRow> rows = creditCardBINTable.getRowByCCCompanyCode(request.getCreditCardCompanyCode());
        int index = RandomUtils.nextInt(0, rows.size());
        String bin5 = rows.get(index).getBin();

        //각 금융기관 내부코드 - X7(X9)-X15
        String innerFinancialCompanyCode5 = RandomStringUtils.randomNumeric(15 - bin5.length());

        //검증숫자 - X16
        char checkDigit5 = PaymentUtils.getCheckDigit(bin5 + innerFinancialCompanyCode5);

        String creditCardNumber = bin5 + innerFinancialCompanyCode5 + checkDigit5;

        //6. 매입사명(BUY_NM)
        String purchaser = creditCardBINTable.getPurchaserByCCCompanyCode(request.getCreditCardCompanyCode());

        //7. 거래번호(TRAN_NO) - XXXXXXXXXXXX

        //시스템 코드 - X1-X4
        //인앱 결제밖에 없으므로 "1000" 고정
        String systemCode7 = "1000";

        //가맹점 코드 - X5-X8
        String transactionCodeDigit7 = PaymentUtils.generateUniqueTransactionCode(strCd);

        //거래 일련번호 - X9-X12
        String transactionSerialNumber7;

        if(salesMasterOptional.isPresent()) {
            transactionSerialNumber7 = salesMasterOptional.get().getOrderSerialNumberCount();
        } else {
            transactionSerialNumber7 = "0001";
        }

        //추가 정보 코드(선택적) - X13-X16
        //거래 일련번호는 동일 가맹점 내에서도 중복이 발생할 수 있으므로, 추가 정보 코드를 사용하여 중복을 방지한다.
        String additionalInfoCode7 = RandomStringUtils.random(4, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        String transactionCode = systemCode7 + transactionCodeDigit7 + transactionSerialNumber7 + additionalInfoCode7;

        //8. 승인번호(APPR_NO)

        //카드사 식별 코드 - X1-X2
        String identifiedCreditCardCorporationCode8 = String.valueOf(Integer.parseInt(request.getCreditCardCompanyCode())+10);

        //거래 일련번호 - X3-X6
        String creditCardCorporationSerialNumber8 = RandomStringUtils.random(4, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        //추가 정보 코드 - X7-X10
        //X7 - 1(거래승인), 2(거래취소), X8-X10 - 랜덤
        String additionalInfoCode8 = (request.isCanceled() ? "2" : "1") + RandomStringUtils.random(3, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        //체크섬 또는 보안코드 - XW
        char checkDigit8 = PaymentUtils.getCheckDigit(identifiedCreditCardCorporationCode8 + creditCardCorporationSerialNumber8 + additionalInfoCode8);

        String approvalNumber = identifiedCreditCardCorporationCode8 + creditCardCorporationSerialNumber8 + additionalInfoCode8 + checkDigit8;

        //9. 발급사명(CARD_NM)
        String issuer = creditCardBINTable.getIssuerByCCCompanyCode(request.getCreditCardCompanyCode());


        //SalesMaster 저장
        SalesMaster createdSalesMaster = salesMasterRepository.saveAndFlush(
                SalesMaster.create(
                        siteCd,
                        strCd,
                        orderId,
                        orderSerialNumber4,
                        posNumber,
                        billNumber,
                        request.isCanceled() ? "C" : "N",
                        request.getOrderType(),
                        callNumber)
        );

        try {
            //SalesPayment 저장
            salesPaymentRepository.save(
                    SalesPayment.create(
                            createdSalesMaster.getTlfSeq(),
                            request.getPaymentCode(),
                            creditCardNumber,
                            request.getCreditCardCompanyCode(),
                            issuer,
                            request.getTotalAmount(),
                            Math.round(request.getTotalAmount() * (1 / 11f)),
                            purchaser,
                            transactionCode,
                            approvalNumber
                    )
            );

            //각 메뉴별로 SalesDetails 저장

            /*List<KioskMenuPaymentCreateRequest.MenuPayment> menuPayments = request.getMenuPayments();

            for(KioskMenuPaymentCreateRequest.MenuPayment menuPayment : menuPayments) {
                SalesDetails salesDetails = salesDetailsRepository.saveAndFlush(
                        SalesDetails.create(
                                createdSalesMaster.getTlfSeq(),
                                request.isCanceled() ? "C" : "N",
                                callNumber,
                                menuPayment.getItemType(),
                                request.getMainCategoryCode(),
                                request.getMiddleCategoryCode(),
                                request.getSubCategoryCode(),
                                menuPayment.getItemCode(),
                                menuPayment.getItemName(),
                                menuPayment.getQuantity(),
                                menuPayment.getAmount(),
                                menuPayment.getAmount(),
                                menuPayment.getDetails()
                        )
                );
                salesDetails.getDtlSeq();
            }*/

            salesDetailsRepository.saveAll(
                    request.getMenuPayments()
                            .stream().map(menuPayment -> {
                                return SalesDetails.create(
                                        createdSalesMaster.getTlfSeq(),
                                        request.isCanceled() ? "C" : "N",
                                        callNumber,
                                        menuPayment.getItemType(),
                                        request.getMainCategoryCode(),
                                        request.getMiddleCategoryCode(),
                                        request.getSubCategoryCode(),
                                        menuPayment.getItemCode(),
                                        menuPayment.getItemName(),
                                        menuPayment.getQuantity(),
                                        menuPayment.getAmount(),
                                        menuPayment.getAmount(),
                                        menuPayment.getDetails()
                                );
                            }).collect(Collectors.toList())
            );

        } catch (Exception e) {
            salesMasterRepository.delete(createdSalesMaster);
            throw e;
        }
    }
}
