package ru.mephi;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.mephi.books.Book;
import ru.mephi.books.BookException;
import ru.mephi.books.Fiction;
import ru.mephi.books.Textbook;
import ru.mephi.people.People;
import ru.mephi.people.Skills;
import ru.mephi.people.Student;
import ru.mephi.people.Teacher;

import java.io.IOException;
import java.util.*;

public class Generator {

    XSSFWorkbook workbook;
    Randomizer randomizer = new Randomizer();

    public Generator(String path) throws IOException {
        workbook = new XSSFWorkbook(path);
        workbook.close();
    }

    public List<Student> generateStudents() {
//        XSSFWorkbook book = new XSSFWorkbook(path);
        XSSFSheet sheet = workbook.getSheetAt(0);
//        book.close();
        int n = randomizer.getRandom(5, 10);
        List<Student> students = new ArrayList<>();
        while (students.size() < n) {
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
            students.add(student);
        }
        return students;
    }

    public List<Teacher> generateTeachers() {
//        XSSFWorkbook book = new XSSFWorkbook(path);
        XSSFSheet sheet = workbook.getSheetAt(1);
//        book.close();
        int n = randomizer.getRandom(5, 10);
        List<Teacher> teachers = new ArrayList<>();
        while (teachers.size() < n) {
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
            teachers.add(teacher);
        }
        return teachers;
    }

    public Book generateBook(){
//        XSSFWorkbook book = new XSSFWorkbook(path);
        int genre = randomizer.getBinaryNumber();
        int lang = randomizer.getBinaryNumber();
        Book bookForTake;
        if (genre == 0) {
            if (lang == 0) {
                int lvl = randomizer.getBinaryNumber();
                XSSFSheet sheet = workbook.getSheetAt(5);
                int nameNum = randomizer.getRandom(1, 4);
                int authorNum = randomizer.getRandom(1, 9);
                int uniNum = randomizer.getRandom(1, 9);
                String name = sheet.getRow(nameNum).getCell(1).getStringCellValue();
                String author = sheet.getRow(authorNum).getCell(0).getStringCellValue();
                String uni = sheet.getRow(uniNum).getCell(2).getStringCellValue();
                bookForTake = new Textbook(lvl , name, author, uni);
            }
            else {
                XSSFSheet sheet = workbook.getSheetAt(4);
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
                switch (skl) {
                    case 1:
                        skills = Skills.A1;
                        break;
                    case 2:
                        skills = Skills.A2;
                        break;
                    case 3:
                        skills = Skills.B1;
                        break;
                    case 4:
                        skills = Skills.B2;
                        break;
                    case 5:
                        skills = Skills.C1;
                        break;
                    default:
                        skills = Skills.C2;
                        break;
                }
                XSSFSheet sheet = workbook.getSheetAt(3);
                int nameNum = randomizer.getRandom(1, 15);
                String name = sheet.getRow(nameNum).getCell(1).getStringCellValue();
                String author = sheet.getRow(nameNum).getCell(0).getStringCellValue();
                bookForTake = new Fiction(skills, name, author);
            } else {
                XSSFSheet sheet = workbook.getSheetAt(2);
                int nameNum = randomizer.getRandom(1, 44);
                String name = sheet.getRow(nameNum).getCell(1).getStringCellValue();
                String author = sheet.getRow(nameNum).getCell(0).getStringCellValue();
                bookForTake = new Fiction(name, author);
            }
        }
//        book.close();
        return bookForTake;
    }

    public Map<Book, Integer> generateAvailableBookList(List<Book> uniqueBookList) {
        Map<Book, Integer> bookList = new HashMap<>();
        for (Book book : uniqueBookList) {
            int n = randomizer.getRandom(1, 5);
            bookList.put(book, n);
        }
        return bookList;
    }

    public List<Book> generateBookList() {
        int n = randomizer.getRandom(20, 30);
        Set<Book> uniqueBookList = new HashSet<>();
        while (uniqueBookList.size() < n) {
            Book book = generateBook();
            uniqueBookList.add(book);
        }
        return new ArrayList<>(uniqueBookList);
    }

    public Library generateLibrary(){
        Library library = Library.getInstance();
        library.getStudents().addAll(generateStudents());
        library.getTeachers().addAll(generateTeachers());
        List<Book> bookArrayList = generateBookList();
        library.getBookList().addAll(bookArrayList);
        library.getAvailableBooks().putAll(generateAvailableBookList(bookArrayList));
        return library;
    }

    public People getRandomPerson(Library library) {
        int stOrTeach = randomizer.getBinaryNumber();
        int n;
        if (stOrTeach == 0) {
            n = randomizer.getRandom(1, library.getStudents().size()) - 1;
            return library.getStudents().get(n);
        }
        else {
            n = randomizer.getRandom(1, library.getTeachers().size()) - 1;
            return library.getTeachers().get(n);
        }
    }

    public Book getRandomBook(Library library) {
        int numberOfAllBooks = library.getBookList().size();
        int n = randomizer.getRandom(1, numberOfAllBooks) - 1;
        return library.getBookList().get(n);
    }

    public void randomMove(Library library) throws BookException {
        People person = getRandomPerson(library);
        Book book = getRandomBook(library);
        int sizeBefore = person.getBookList().size();
        person.takeBook(book, library);
        while (person.getBookList().size() == sizeBefore) {
            int n = randomizer.getBinaryNumber();
            if (n == 0) {
                person = getRandomPerson(library);
                sizeBefore = person.getBookList().size();
            }
            else {
                book = getRandomBook(library);
            }
            person.takeBook(book, library);
        }
    }



}
