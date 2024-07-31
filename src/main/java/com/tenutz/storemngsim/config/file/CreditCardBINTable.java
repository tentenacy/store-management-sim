package com.tenutz.storemngsim.config.file;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CreditCardBINTable {

    private final List<CreditCardBINRow> creditCardBINRows;

    /**
     * 카드사코드 01 -> 국민
     * 카드사코드 02 -> 신한
     * 카드사코드 03 -> 비씨
     * 카드사코드 04 -> 삼성
     * 카드사코드 05 -> 현대
     * 카드사코드 06 -> 농협
     * 카드사코드 07 -> 롯데
     * 카드사코드 08 -> 하나
     * 카드사코드 09 -> 우리
     * 카드사코드 10 -> 씨티
     * 카드사코드 11 -> 광주은행
     * 카드사코드 12 -> 전북은행
     * 카드사코드 13 -> 우체국
     * 카드사코드 14 -> 제주
     * 카드사코드 15 -> MG새마을
     * 카드사코드 16 -> 신협
     * 카드사코드 17 -> 카카오뱅크
     * 카드사코드 18 -> 케이뱅크
     */
    Map<String, String> issuerMapper = new HashMap<String, String>() {{
        put("01", "KB국민카드");
        put("02", "신한카드");
        put("03", "BC카드");
        put("04", "삼성카드");
        put("05", "현대카드");
        put("06", "NH농협카드");
        put("07", "롯데카드");
        put("08", "하나카드");
        put("09", "우리카드");
        put("10", "씨티카드");
        put("11", "광주은행");
        put("12", "전북은행");
        put("13", "BC카드");
        put("14", "제주은행");
        put("15", "BC카드");
        put("16", "BC카드");
        put("17", "국민카드");
        put("18", "BC카드");
    }};

    Map<String, String> purchaserMapper = new HashMap<String, String>() {{
        put("01", "국민카드");
        put("02", "신한카드");
        put("03", "비씨카드");
        put("04", "삼성카드");
        put("05", "현대카드");
        put("06", "농협카드");
        put("07", "롯데카드");
        put("08", "하나카드");
        put("09", "우리카드");
        put("10", "비씨카드");
        put("11", "비씨카드");
        put("12", "국민카드");
        put("13", "비씨카드");
        put("14", "비씨카드");
        put("15", "비씨카드");
        put("16", "비씨카드");
        put("17", "국민카드");
        put("18", "비씨카드");
    }};

    public CreditCardBINTable(List<CreditCardBINRow> creditCardBINRows) {
        this.creditCardBINRows = creditCardBINRows;
    }

    public List<CreditCardBINRow> getRowByCCCompanyCode(String creditCardCompanyCode) {
        return creditCardBINRows.stream()
                .filter(v -> v.getIssuer().contains(issuerMapper.getOrDefault(creditCardCompanyCode, "BC카드")))
                .collect(Collectors.toList());
    }

    public String getPurchaserByCCCompanyCode(String creditCardCompanyCode) {
        return purchaserMapper.getOrDefault(creditCardCompanyCode, "BC카드");
    }

    public String getIssuerByCCCompanyCode(String creditCardCompanyCode) {
        return issuerMapper.getOrDefault(creditCardCompanyCode, "비씨카드");
    }
}
