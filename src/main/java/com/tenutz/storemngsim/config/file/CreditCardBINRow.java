package com.tenutz.storemngsim.config.file;

import lombok.Data;

import java.util.Date;

@Data
public class CreditCardBINRow {

    private Integer no;
    private String issuer;
    private String bin;
    private String slipFactorName;
    private String individualsOrCorporations;
    private String brand;
    private String creditOrCheck;
    private Date lastModifiedAt;
    private String changes;
    private String note;

    public static CreditCardBINRow create(Integer no, String issuer, String bin, String slipFactorName, String individualsOrCorporations, String brand, String creditOrCheck, Date lastModifiedAt, String changes, String note) {
        CreditCardBINRow creditCardBINRow = new CreditCardBINRow();
        creditCardBINRow.setNo(no);
        creditCardBINRow.setIssuer(issuer);
        creditCardBINRow.setBin(bin);
        creditCardBINRow.setSlipFactorName(slipFactorName);
        creditCardBINRow.setIndividualsOrCorporations(individualsOrCorporations);
        creditCardBINRow.setBrand(brand);
        creditCardBINRow.setCreditOrCheck(creditOrCheck);
        creditCardBINRow.setLastModifiedAt(lastModifiedAt);
        creditCardBINRow.setChanges(changes);
        creditCardBINRow.setNote(note);
        return creditCardBINRow;
    }
}
