package com.tenutz.storemngsim.web.service;

import com.tenutz.storemngsim.config.file.CreditCardBINRow;
import com.tenutz.storemngsim.config.file.CreditCardBINTable;
import com.tenutz.storemngsim.domain.sales.*;
import com.tenutz.storemngsim.utils.PaymentUtils;
import com.tenutz.storemngsim.utils.TextUtils;
import com.tenutz.storemngsim.utils.TimeUtils;
import com.tenutz.storemngsim.web.api.common.dto.CommonCondition;
import com.tenutz.storemngsim.web.api.kiosksim.dto.common.PaymentInfo;
import com.tenutz.storemngsim.web.api.kiosksim.dto.payment.KioskMenuPaymentCreateRequest;
import com.tenutz.storemngsim.web.api.kiosksim.dto.payment.KioskMenuPaymentCreateResponse;
import com.tenutz.storemngsim.web.api.storemngsim.dto.store.*;
import com.tenutz.storemngsim.web.exception.business.CEntityNotFoundException;
import com.tenutz.storemngsim.web.exception.business.CInvalidValueException;
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
    public KioskMenuPaymentCreateResponse createKioskMenusPaymentsByCreditCard(String siteCd, String strCd, String kioskCode, KioskMenuPaymentCreateRequest request) {

        PaymentInfo paymentInfo = creditCardPaymentInfo(siteCd, strCd, kioskCode, request.getCreditCardCompanyCode(), false);

        //SalesMaster 저장
        SalesMaster createdSalesMaster = salesMasterRepository.saveAndFlush(
                SalesMaster.create(
                        siteCd,
                        strCd,
                        paymentInfo.getPaymentInfoNoCreditCard().getOrderId(),
                        paymentInfo.getPaymentInfoNoCreditCard().getOrderSerialNumber(),
                        paymentInfo.getPaymentInfoNoCreditCard().getPosNumber(),
                        paymentInfo.getPaymentInfoNoCreditCard().getBillNumber(),
                        "N",
                        request.getOrderType(),
                        paymentInfo.getPaymentInfoNoCreditCard().getCallNumber())
        );

        try {
            //SalesPayment 저장
            salesPaymentRepository.save(
                    SalesPayment.createByCreditCard(
                            createdSalesMaster.getTlfSeq(),
                            request.getPaymentCode(),
                            paymentInfo.getCreditCardNumber(),
                            request.getCreditCardCompanyCode(),
                            paymentInfo.getIssuer(),
                            request.getTotalAmount(),
                            Math.round(request.getTotalAmount() * (1 / 11f)),
                            paymentInfo.getPurchaser(),
                            paymentInfo.getPaymentInfoNoCreditCard().getTransactionCode(),
                            paymentInfo.getApprovalNumber()
                    )
            );

            //각 메뉴별로 SalesDetails 저장

            salesDetailsRepository.saveAll(
                    request.getMenuPayments()
                            .stream().map(menuPayment -> {
                                return SalesDetails.create(
                                        createdSalesMaster.getTlfSeq(),
                                        "N",
                                        paymentInfo.getPaymentInfoNoCreditCard().getCallNumber(),
                                        menuPayment.getItemType(),
                                        request.getMainCategoryCode(),
                                        request.getMiddleCategoryCode(),
                                        menuPayment.getSubCategoryCode(),
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
        return new KioskMenuPaymentCreateResponse(paymentInfo.getPaymentInfoNoCreditCard().getCallNumber());
    }

    @Transactional
    public KioskMenuPaymentCreateResponse createKioskMenusPaymentsByCashOrCoupon(String siteCd, String strCd, String kioskCode, KioskMenuPaymentCreateRequest request) {

        PaymentInfo.PaymentInfoNoCreditCard paymentInfoNoCreditCard = cashOrCouponPaymentInfo(siteCd, strCd, kioskCode, false);

        //SalesMaster 저장
        SalesMaster createdSalesMaster = salesMasterRepository.saveAndFlush(
                SalesMaster.create(
                        siteCd,
                        strCd,
                        paymentInfoNoCreditCard.getOrderId(),
                        paymentInfoNoCreditCard.getOrderSerialNumber(),
                        paymentInfoNoCreditCard.getPosNumber(),
                        paymentInfoNoCreditCard.getBillNumber(),
                        "N",
                        request.getOrderType(),
                        paymentInfoNoCreditCard.getCallNumber())
        );

        try {
            //SalesPayment 저장
            salesPaymentRepository.save(
                    SalesPayment.createByCashOrCoupon(
                            createdSalesMaster.getTlfSeq(),
                            request.getPaymentCode(),
                            request.getTotalAmount(),
                            Math.round(request.getTotalAmount() * (1 / 11f)),
                            paymentInfoNoCreditCard.getTransactionCode()
                    )
            );

            //각 메뉴별로 SalesDetails 저장
            salesDetailsRepository.saveAll(
                    request.getMenuPayments()
                            .stream().map(menuPayment -> {
                                return SalesDetails.create(
                                        createdSalesMaster.getTlfSeq(),
                                        "N",
                                        paymentInfoNoCreditCard.getCallNumber(),
                                        menuPayment.getItemType(),
                                        request.getMainCategoryCode(),
                                        request.getMiddleCategoryCode(),
                                        menuPayment.getSubCategoryCode(),
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
        return new KioskMenuPaymentCreateResponse(paymentInfoNoCreditCard.getCallNumber());
    }

    @Transactional
    public void deleteKioskMenusPayments(String siteCd, String strCd, String kioskCode, String callNumber) {

        SalesMaster foundSalesMaster = salesMasterRepository.findFirstRecentOrder(siteCd, strCd, callNumber).orElseThrow(CEntityNotFoundException.CSalesMasterNotFoundException::new);
        SalesPayment foundSalesPayment = salesPaymentRepository.findByTlfSeq(foundSalesMaster.getTlfSeq()).orElseThrow(CEntityNotFoundException.CSalesPaymentNotFoundException::new);
        List<SalesDetails> foundSalesDetailsList = salesDetailsRepository.findByTlfSeq(foundSalesMaster.getTlfSeq());

        //이미 취소된 경우 예외 발생
        if(salesMasterRepository.existsByCompCdAndStorCdAndCallNoAndProcFg(siteCd, strCd, callNumber, "C")) {
            throw new CInvalidValueException.CAlreadyPaymentCanceledException();
        }


        if(foundSalesPayment.getPayCd().equals("02")) {

            PaymentInfo paymentInfo = creditCardPaymentInfo(siteCd, strCd, kioskCode, foundSalesPayment.getCardCd(), true);

            SalesMaster createdSalesMaster = salesMasterRepository.saveAndFlush(
                    foundSalesMaster.copyItselfWith(
                            paymentInfo.getPaymentInfoNoCreditCard().getOrderId(),
                            paymentInfo.getPaymentInfoNoCreditCard().getOrderSerialNumber(),
                            paymentInfo.getPaymentInfoNoCreditCard().getPosNumber(),
                            paymentInfo.getPaymentInfoNoCreditCard().getBillNumber(),
                            "C",
                            paymentInfo.getPaymentInfoNoCreditCard().getCallNumber()
                    )
            );

            try {
                salesPaymentRepository.save(
                        foundSalesPayment.copyItselfWith(
                                createdSalesMaster.getTlfSeq(),
                                paymentInfo.getPaymentInfoNoCreditCard().getTransactionCode(),
                                paymentInfo.getApprovalNumber()
                        )
                );

                salesDetailsRepository.saveAll(
                        foundSalesDetailsList.stream()
                                .map(salesDetails ->
                                        salesDetails.copyItselfWith(
                                                createdSalesMaster.getTlfSeq(),
                                                "C",
                                                paymentInfo.getPaymentInfoNoCreditCard().getCallNumber()
                                        )
                                )
                                .collect(Collectors.toList())
                );
            } catch (Exception e) {
                salesMasterRepository.delete(createdSalesMaster);
                throw  e;
            }

        } else {

            PaymentInfo.PaymentInfoNoCreditCard paymentInfoNoCreditCard = cashOrCouponPaymentInfo(siteCd, strCd, kioskCode, true);

            SalesMaster createdSalesMaster = salesMasterRepository.saveAndFlush(
                    foundSalesMaster.copyItselfWith(
                            foundSalesMaster.getOrdId(),
                            foundSalesMaster.getOrdSrNo(),
                            foundSalesMaster.getPosNo(),
                            foundSalesMaster.getBillNo(),
                            "C",
                            foundSalesMaster.getCallNo()
                    )
            );

            try {
                salesPaymentRepository.save(
                        foundSalesPayment.copyItselfWith(
                                createdSalesMaster.getTlfSeq(),
                                paymentInfoNoCreditCard.getTransactionCode()
                        )
                );

                salesDetailsRepository.saveAll(
                        foundSalesDetailsList.stream()
                                .map(salesDetails ->
                                        salesDetails.copyItselfWith(
                                                createdSalesMaster.getTlfSeq(),
                                                "C",
                                                paymentInfoNoCreditCard.getCallNumber()
                                        )
                                )
                                .collect(Collectors.toList())
                );
            } catch (Exception e) {
                salesMasterRepository.delete(createdSalesMaster);
                throw  e;
            }
        }
    }

    private PaymentInfo creditCardPaymentInfo(String siteCd, String strCd, String kioskCode, String creditCardCompanyCode, boolean canceled) {

        PaymentInfo.PaymentInfoNoCreditCard paymentInfoNoCreditCard = cashOrCouponPaymentInfo(siteCd, strCd, kioskCode, canceled);

        //SalesMaster - 1. 포스번호(POS_NO), 2. 영수증번호(BILL_NO), 3. 호출번호(CALL_NO, 고객기준 주문번호), 4. 주문번호(ORD_ID)

        //SalesPayment - 5. 카드번호(CARD_NO), 6. 매입사명(BUY_NM), 8. 승인번호(APPR_NO), 9. 발급사명(CARD_NM)

        //5. 카드번호(CARD_NO) - XXXX-XXXX-XXXX-XXXX
        //BIN - X1-X6(X8)
        List<CreditCardBINRow> rows = creditCardBINTable.getRowByCCCompanyCode(creditCardCompanyCode);
        int index = RandomUtils.nextInt(0, rows.size());
        String bin5 = rows.get(index).getBin();

        //각 금융기관 내부코드 - X7(X9)-X15
        String innerFinancialCompanyCode5 = RandomStringUtils.randomNumeric(15 - bin5.length());

        //검증숫자 - X16
        char checkDigit5 = PaymentUtils.getCheckDigit(bin5 + innerFinancialCompanyCode5);

        String creditCardNumber = bin5 + innerFinancialCompanyCode5 + checkDigit5;

        //6. 매입사명(BUY_NM)
        String purchaser = creditCardBINTable.getPurchaserByCCCompanyCode(creditCardCompanyCode);

        //8. 승인번호(APPR_NO)

        //카드사 식별 코드 - X1-X2
        String identifiedCreditCardCorporationCode8 = String.valueOf(Integer.parseInt(creditCardCompanyCode)+10);

        //거래 일련번호 - X3-X6
        String creditCardCorporationSerialNumber8 = RandomStringUtils.random(4, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        //추가 정보 코드 - X7-X10
        //X7 - 1(거래승인), 2(거래취소), X8-X10 - 랜덤
        String additionalInfoCode8 = (canceled ? "2" : "1") + RandomStringUtils.random(3, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        //체크섬 또는 보안코드 - XW
        char checkDigit8 = PaymentUtils.getCheckDigit(identifiedCreditCardCorporationCode8 + creditCardCorporationSerialNumber8 + additionalInfoCode8);

        String approvalNumber = identifiedCreditCardCorporationCode8 + creditCardCorporationSerialNumber8 + additionalInfoCode8 + checkDigit8;

        //9. 발급사명(CARD_NM)
        String issuer = creditCardBINTable.getIssuerByCCCompanyCode(creditCardCompanyCode);

        return new PaymentInfo(
                paymentInfoNoCreditCard,
                creditCardNumber,
                purchaser,
                approvalNumber,
                issuer
        );
    }

    private PaymentInfo.PaymentInfoNoCreditCard cashOrCouponPaymentInfo(String siteCd, String strCd, String kioskCode, boolean canceled) {
        //SalesMaster - 1. 포스번호(POS_NO), 2. 영수증번호(BILL_NO), 3. 호출번호(CALL_NO, 고객기준 주문번호), 4. 주문번호(ORD_ID)

        //1. 포스번호(POS_NO) XXXX-XX-XXX-XXX
        //가맹점 코드 - X1-X4
        String transactionCodeDigit1 = TextUtils.toNonStartZero(PaymentUtils.generateUniqueTransactionCode(strCd));

        //지점 코드 - X5-X6
        //"10" 고정
        String siteStoreCode1 = "10";

        //장비 코드 - X7-X9
        //키오스크 코드를 이용하여 생성한다.
        String equipmentCode1 = PaymentUtils.generateNumberString(kioskCode, 3);

        //일련번호 - X10-X12
        //"100" 고정
        String posSerialNumber1 = "100";

        String posNumber = transactionCodeDigit1 + siteStoreCode1 + equipmentCode1 + posSerialNumber1;

        //2. 영수증번호(BILL_NO)
        String billNumber = TextUtils.toNonStartZero(RandomStringUtils.randomNumeric(10));

        //3. 호출번호(CALL_NO, 고객기준 주문번호)
        String callNumber;

        Optional<SalesMaster> salesMasterOptional = salesMasterRepository.findFirstRecentOrder(siteCd, strCd);

        if(!canceled) {
            if(salesMasterOptional.isPresent() && salesMasterOptional.get().getOrderTm().substring(0, 8).compareTo(TimeUtils.yyyyMMdd(new Date())) >= 0) {
                callNumber = salesMasterOptional.get().getCallNumberCount();
            } else {
                callNumber = "0001";
            }
        } else {
            callNumber = salesMasterOptional.map(SalesMaster::getCallNo).orElse(null);
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
        String additionalInfoCode4 = (canceled ? "2" : "1") + RandomStringUtils.random(3, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        String orderId = systemCode4 + orderCodeDigit4 + orderSerialNumber4 + additionalInfoCode4;


        //SalesPayment - 7. 거래번호(TRAN_NO)

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

        return new PaymentInfo.PaymentInfoNoCreditCard(
                posNumber,
                billNumber,
                callNumber,
                orderId,
                orderSerialNumber4,
                transactionCode
        );
    }

}
