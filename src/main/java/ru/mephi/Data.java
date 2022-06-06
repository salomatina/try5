package ru.mephi;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.mephi.books.Fiction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {

    XSSFWorkbook workbook;

//    public Data(String path) throws IOException {
//        workbook = new XSSFWorkbook(path);
//        workbook.close();
//    }

    public void setWorkbook(String path) throws IOException {
        workbook = new XSSFWorkbook(path);
        workbook.close();
    }


    public Map<String, String> getStudentData(int nameNum, int surnameNum) {
        XSSFSheet sheet = workbook.getSheetAt(0);
        String name = sheet.getRow(nameNum).getCell(0).getStringCellValue();
        String surname = sheet.getRow(surnameNum).getCell(1).getStringCellValue();
        if (sheet.getRow(nameNum).getCell(2).getStringCellValue().contains("f")) {
            surname += "а";
        }
        Map<String, String> studentData = new HashMap<>();
        studentData.put("name", name);
        studentData.put("surname", surname);
        return studentData;
    }

    public Map<String, String> getTeacherData(int nameNum, int surnameNum, int patronNum) {
        XSSFSheet sheet = workbook.getSheetAt(1);
        String name = sheet.getRow(nameNum).getCell(0).getStringCellValue();
        String surname = sheet.getRow(surnameNum).getCell(1).getStringCellValue();
        String patronymic;
        String sex = sheet.getRow(nameNum).getCell(2).getStringCellValue();
        if (sex.equals("f")) {
            surname += "а";
            patronymic = sheet.getRow(patronNum).getCell(3).getStringCellValue();
        }
        else {
            patronymic = sheet.getRow(patronNum).getCell(4).getStringCellValue();
        }
        Map<String, String> teacherData = new HashMap<>();
        teacherData.put("name", name);
        teacherData.put("surname", surname);
        teacherData.put("patronymic", patronymic);
        return teacherData;
    }

    public Map<String, String> getEnTextbook(int nameNum, int authorNum, int uniNum) {
        XSSFSheet sheet = workbook.getSheetAt(5);
        String name = sheet.getRow(nameNum).getCell(1).getStringCellValue();
        String author = sheet.getRow(authorNum).getCell(0).getStringCellValue();
        String uni = sheet.getRow(uniNum).getCell(2).getStringCellValue();
        Map<String, String> enTextBookData = new HashMap<>();
        enTextBookData.put("name", name);
        enTextBookData.put("author", author);
        enTextBookData.put("uni", uni);
        return enTextBookData;
    }

    public Map<String, String> getRuTextbook(int nameNum, int typeNum) {
        XSSFSheet sheet = workbook.getSheetAt(4);
        String name = sheet.getRow(nameNum).getCell(0).getStringCellValue();
        String type = sheet.getRow(typeNum).getCell(1).getStringCellValue();
        String author = sheet.getRow(nameNum).getCell(2).getStringCellValue();
        Map<String, String> ruTextBookData = new HashMap<>();
        ruTextBookData.put("name", name);
        ruTextBookData.put("author", author);
        ruTextBookData.put("type", type);
        return ruTextBookData;
    }

    public Map<String, String> getEnFiction(int nameNum) {
        XSSFSheet sheet = workbook.getSheetAt(3);
        String name = sheet.getRow(nameNum).getCell(1).getStringCellValue();
        String author = sheet.getRow(nameNum).getCell(0).getStringCellValue();
        Map<String, String> enFictionData = new HashMap<>();
        enFictionData.put("name", name);
        enFictionData.put("author", author);
        return enFictionData;
    }

    public Map<String, String> getRuFiction(int nameNum) {
        XSSFSheet sheet = workbook.getSheetAt(2);
        String name = sheet.getRow(nameNum).getCell(1).getStringCellValue();
        String author = sheet.getRow(nameNum).getCell(0).getStringCellValue();
        Map<String, String> ruFictionData = new HashMap<>();
        ruFictionData.put("name", name);
        ruFictionData.put("author", author);
        return ruFictionData;
    }


}
