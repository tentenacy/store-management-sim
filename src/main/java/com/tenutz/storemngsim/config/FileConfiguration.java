package com.tenutz.storemngsim.config;

import com.tenutz.storemngsim.config.file.CreditCardBINRow;
import com.tenutz.storemngsim.config.file.CreditCardBINTable;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FileConfiguration {

    @Bean
    public List<CreditCardBINRow> creditCardBINList() {

        String filePath = new File("").getAbsolutePath() + "/files/excel/CreditCard_BIN_Table(20240715).xls";
        File excelFile = new File(filePath);

        ArrayList<CreditCardBINRow> creditCardBINRows = new ArrayList<>();

        try {

            InputStream inputStream = new FileInputStream(excelFile);
            String fileExtension = FilenameUtils.getExtension(excelFile.getName());

            Workbook workbook = null;

            // 엑셀 97 - 2003 까지는 HSSF(xls),  엑셀 2007 이상은 XSSF(xlsx)
            if (fileExtension.equals("xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                workbook = new XSSFWorkbook(inputStream);
            }

            Sheet worksheet = workbook.getSheetAt(0);

            // getPhysicalNumberOfRow() : 행의 갯수 가져오기
            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

                Row row = worksheet.getRow(i);

                CreditCardBINRow creditCardBINRow = null;

                if (row != null) {
                    creditCardBINRow = CreditCardBINRow.create(
                        Double.valueOf(row.getCell(0).getNumericCellValue()).intValue(),
                        row.getCell(1).getStringCellValue(),
                        row.getCell(2).getStringCellValue(),
                        row.getCell(3).getStringCellValue(),
                        row.getCell(4).getStringCellValue(),
                        row.getCell(5).getStringCellValue(),
                        row.getCell(6).getStringCellValue(),
                        row.getCell(7).getDateCellValue(),
                        row.getCell(8).getStringCellValue(),
                        row.getCell(9).getStringCellValue()
                    );

                }

                creditCardBINRows.add(creditCardBINRow);
            }

            workbook.close();
        }
        catch (Exception e) {
            return creditCardBINRows;
        }
        return creditCardBINRows;
    }

    @Bean
    @Autowired
    public CreditCardBINTable creditCardBINTable(List<CreditCardBINRow> creditCardBINRows) {
        return new CreditCardBINTable(creditCardBINRows);
    }
}
