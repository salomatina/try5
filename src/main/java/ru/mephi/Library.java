package ru.mephi;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.mephi.books.*;
import ru.mephi.people.Skills;
import ru.mephi.people.Student;
import ru.mephi.people.Teacher;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Library {

    private Set<Student> studentSet;
    private Set<Teacher> teacherSet;
    private final Randomizer randomizer = new Randomizer();

    public void createStudentsSet(String path) throws IOException {
        XSSFWorkbook book = new XSSFWorkbook(path);
        XSSFSheet sheet = book.getSheetAt(0);
        book.close();
        int n = randomizer.getRandom(5, 10);
        studentSet = new HashSet<>();
        while (studentSet.size() < n) {
            int nameNum = randomizer.getRandom(1, 30);
            String name = sheet.getRow(nameNum).getCell(0).getStringCellValue();
            int surnameNum = randomizer.getRandom(1, 60);
            String surname = sheet.getRow(surnameNum).getCell(1).getStringCellValue();
            if (sheet.getRow(nameNum).getCell(2).getStringCellValue().contains("f")) {
                surname += "а";
            }
            int dgr = randomizer.getBinaryNumber();
            int skl = randomizer.getSkillsNumber();
            Student student = new Student(dgr, skl, name, surname);
            studentSet.add(student);
        }
    }

    public void createTeachersSet(String path) throws IOException {
        XSSFWorkbook book = new XSSFWorkbook(path);
        XSSFSheet sheet = book.getSheetAt(1);
        book.close();
        int n = randomizer.getRandom(5, 10);
        teacherSet = new HashSet<>();
        while (teacherSet.size() < n) {
            int nameNum = randomizer.getRandom(1, 30);
            String name = sheet.getRow(nameNum).getCell(0).getStringCellValue();
            int surnameNum = randomizer.getRandom(1, 45);
            String surname = sheet.getRow(surnameNum).getCell(1).getStringCellValue();
            String patronymic;
            int patronNum = randomizer.getRandom(1, 15);
            String sex = sheet.getRow(nameNum).getCell(2).getStringCellValue();
            if (sex.equals("f")) {
                surname += "а";
                patronymic = sheet.getRow(patronNum).getCell(3).getStringCellValue();
            }
            else {
                patronymic = sheet.getRow(patronNum).getCell(4).getStringCellValue();
            }
            int skl = randomizer.getSkillsNumber();
            Teacher teacher = new Teacher(name, surname, patronymic, skl);
            teacherSet.add(teacher);
        }
    }

    public Book generateBook(String path) throws IOException {
        XSSFWorkbook book = new XSSFWorkbook(path);
        int genre = randomizer.getBinaryNumber();
        int lang = randomizer.getBinaryNumber();
        Book bookForTake;
        if (genre == 0) {
            if (lang == 0) {
                int lvl = randomizer.getBinaryNumber();
                XSSFSheet sheet = book.getSheetAt(5);
                int nameNum = randomizer.getRandom(1, 4);
                int authorNum = randomizer.getRandom(1, 9);
                int uniNum = randomizer.getRandom(1, 9);
                String name = sheet.getRow(nameNum).getCell(1).getStringCellValue();
                String author = sheet.getRow(authorNum).getCell(0).getStringCellValue();
                String uni = sheet.getRow(uniNum).getCell(2).getStringCellValue();
                bookForTake = new Textbook(lvl , name, author, uni);
            }
            else {
                XSSFSheet sheet = book.getSheetAt(4);
                int typeNum = randomizer.getRandom(1, 3);
                int nameNum = randomizer.getRandom(1, 19);
                String name = sheet.getRow(nameNum).getCell(0).getStringCellValue();
                String type = sheet.getRow(typeNum).getCell(1).getStringCellValue();
                String author = sheet.getRow(nameNum).getCell(2).getStringCellValue();
                bookForTake = new Textbook(type, name, author);
            }
        }
        else {
            if (lang == 0) {
                int skl = randomizer.getSkillsNumber();
                Skills skills;
                if (skl == 1) {
                    skills = Skills.A1;
                } else if (skl == 2) {
                    skills = Skills.A2;
                } else if (skl == 3) {
                    skills = Skills.B1;
                } else if (skl == 4) {
                    skills = Skills.B2;
                } else if (skl == 5) {
                    skills = Skills.C1;
                } else {
                    skills = Skills.C2;
                }
                XSSFSheet sheet = book.getSheetAt(3);
                int nameNum = randomizer.getRandom(1, 15);
                String name = sheet.getRow(nameNum).getCell(1).getStringCellValue();
                String author = sheet.getRow(nameNum).getCell(0).getStringCellValue();
                bookForTake = new Fiction(skills, name, author);
            } else {
                XSSFSheet sheet = book.getSheetAt(2);
                int nameNum = randomizer.getRandom(1, 44);
                String name = sheet.getRow(nameNum).getCell(1).getStringCellValue();
                String author = sheet.getRow(nameNum).getCell(0).getStringCellValue();
                bookForTake = new Fiction(name, author);
            }
        }
        book.close();
        return bookForTake;
    }

    public void takeStudentsBooks(String path) throws IOException, BookException {
        for (Student student : studentSet) {
            int n = randomizer.getRandom(3, 10);
            while (student.getBookList().size() < n) {
                Book book = generateBook(path);
                if (book.getLanguage() == Language.ENGLISH) {
                    if (book instanceof Textbook) {
                        if (((Textbook) book).getLevel() != student.getDegree()) {
                            continue;
                        }
                    }
                    else {
                        if (((Fiction) book).getSkills() != student.getSkills()) {
                            continue;
                        }
                    }
                }
                student.takeBook(book);
            }
        }
    }

    public void takeTeachersBooks(String path) throws IOException, BookException {
        for (Teacher teacher : teacherSet) {
            int n = randomizer.getRandom(3, 10);
            while (teacher.getBookList().size() < n) {
                Book book = generateBook(path);
                if (book.getLanguage() == Language.ENGLISH && book instanceof Fiction) {
                    if (((Fiction) book).getSkills() != teacher.getSkills()) {
                        continue;
                    }
                }
                teacher.takeBook(book);
            }
        }
    }

    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public Set<Teacher> getTeacherSet() {
        return teacherSet;
    }
}
