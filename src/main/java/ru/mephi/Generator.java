package ru.mephi;

import ru.mephi.books.Book;
import ru.mephi.books.BookException;
import ru.mephi.books.Fiction;
import ru.mephi.books.Textbook;
import ru.mephi.people.Person;
import ru.mephi.people.Skills;
import ru.mephi.people.Student;
import ru.mephi.people.Teacher;

import java.io.IOException;
import java.util.*;

public class Generator {

    Randomizer randomizer = new Randomizer();
    Data data = new Data();

    public void setDataWorkbook(String path) throws IOException {
        data.setWorkbook(path);
    }

//    public Generator(String path) throws IOException {
//        workbook = new XSSFWorkbook(path);
//        workbook.close();
//    }

    public List<Student> generateStudents() {
        int n = randomizer.getRandom(5, 10);
        List<Student> students = new ArrayList<>();
        while (students.size() < n) {
            int nameNum = randomizer.getRandom(1, 30);
            int surnameNum = randomizer.getRandom(1, 60);
            int dgr = randomizer.getBinaryNumber();
            int skl = randomizer.getSkillsNumber();
            Map<String, String> params = data.getStudentData(nameNum, surnameNum);
            String name = params.get("name");
            String surname = params.get("surname");
            Student student = new Student(dgr, skl, name, surname);
            students.add(student);
        }
        return students;
    }

    public List<Teacher> generateTeachers() {
        int n = randomizer.getRandom(5, 10);
        List<Teacher> teachers = new ArrayList<>();
        while (teachers.size() < n) {
            int nameNum = randomizer.getRandom(1, 30);
            int surnameNum = randomizer.getRandom(1, 45);
            int patronNum = randomizer.getRandom(1, 15);
            Map<String, String> teacherData = data.getTeacherData(nameNum, surnameNum, patronNum);
            String name = teacherData.get("name");
            String surname = teacherData.get("surname");
            String patronymic = teacherData.get("patronymic");
            int skl = randomizer.getSkillsNumber();
            Teacher teacher = new Teacher(name, surname, patronymic, skl);
            teachers.add(teacher);
        }
        return teachers;
    }

    public Book generateEnTextbook() {
        int lvl = randomizer.getBinaryNumber();
        int nameNum = randomizer.getRandom(1, 4);
        int authorNum = randomizer.getRandom(1, 9);
        int uniNum = randomizer.getRandom(1, 9);
        Map<String, String> enTextBookData = data.getEnTextbook(nameNum, authorNum, uniNum);
        String name = enTextBookData.get("name");
        String author = enTextBookData.get("surname");
        String uni = enTextBookData.get("uni");
        return new Textbook(lvl , name, author, uni);
    }

    public Book generateRuTextbook() {
        int typeNum = randomizer.getRandom(1, 3);
        int nameNum = randomizer.getRandom(1, 19);
        Map<String, String> ruTextBookData = data.getRuTextbook(nameNum, typeNum);
        String type = ruTextBookData.get("type");
        String name = ruTextBookData.get("name");
        String author = ruTextBookData.get("author");
        return new Textbook(type, name, author);
    }

    public Book generateEnFiction() {
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
        int nameNum = randomizer.getRandom(1, 15);
        Map<String, String> enFictionData = data.getEnFiction(nameNum);
        String name = enFictionData.get("name");
        String author = enFictionData.get("author");
        return new Fiction(skills, name, author);
    }

    public Book generateRuFiction() {
        int nameNum = randomizer.getRandom(1, 44);
        Map<String, String> ruFictionData = data.getRuFiction(nameNum);
        String name = ruFictionData.get("name");
        String author = ruFictionData.get("author");
        return new Fiction(name, author);
    }

    public Book generateBook(){
        int genre = randomizer.getBinaryNumber();
        int lang = randomizer.getBinaryNumber();
        Book bookForTake;
        if (genre == 0) {
            if (lang == 0) {
                bookForTake = generateEnTextbook();
            }
            else {
                bookForTake = generateRuTextbook();
            }
        }
        else {
            if (lang == 0) {
                bookForTake = generateEnFiction();
            } else {
                bookForTake = generateRuFiction();
            }
        }
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
        Library library = new Library();
        library.getStudents().addAll(generateStudents());
        library.getTeachers().addAll(generateTeachers());
        List<Book> bookArrayList = generateBookList();
        library.getLibraryBookList().addAll(bookArrayList);
        library.getAvailableBooks().putAll(generateAvailableBookList(bookArrayList));
        return library;
    }

}
