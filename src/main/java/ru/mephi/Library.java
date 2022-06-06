package ru.mephi;

import ru.mephi.books.*;
import ru.mephi.people.Person;
import ru.mephi.people.Student;
import ru.mephi.people.Teacher;

import java.util.*;

public class Library {

//    private static Library instance = null;

    private final List<Student> students;
    private final List<Teacher> teachers;
    private final Map<Book, Integer> availableBooks;
    private final List<Book> libraryBookList; // need that to take random book (to get it from index)
//    private final Map<Book, Queue> requestLine;
    private final List<Record> takenBooks;

    public Library() {
//        requestLine = new HashMap<>();
        students = new ArrayList<>();
        teachers = new ArrayList<>();
        availableBooks = new HashMap<>();
        libraryBookList = new ArrayList<>();
        takenBooks = new ArrayList<>();
    }

    public Person getRandomPerson() {
        Randomizer randomizer = new Randomizer();
        int stOrTeach = randomizer.getBinaryNumber();
        int n;
        if (stOrTeach == 0) {
            n = randomizer.getRandom(1, getStudents().size()) - 1;
            return getStudents().get(n);
        }
        else {
            n = randomizer.getRandom(1, getTeachers().size()) - 1;
            return getTeachers().get(n);
        }
    }

    public Book getRandomBook() {
        Randomizer randomizer = new Randomizer();
        int numberOfAllBooks = getLibraryBookList().size();
        int n = randomizer.getRandom(1, numberOfAllBooks) - 1;
        return getLibraryBookList().get(n);
    }

    public void takeBook(Book book) throws BookException {
        getAvailableBooks().put(book, getAvailableBooks().get(book) + 1);
        try {
            Request request = book.getBookRequests().poll();
            if (request != null) {
                request.getPerson().takeBook(book, this);
                System.out.println("book was taken " + request.getPerson().toString() +
                        " " + book);
            }
        }
        catch (NullPointerException ignored) {
        }
    }

    public void giveBook(Book book, Person person) throws BookException {
        if (person instanceof Student) {
            if (book.getLanguage().equals(Language.ENGLISH)) {
                if (book instanceof Textbook) {
                    if (((Textbook) book).getLevel() != ((Student) person).getDegree()) {
                        return;
                    }
                }
                else {
                    if (((Fiction) book).getSkills() != person.getSkills()) {
                        return;
                    }
                }
            }
            int numberOfBooks = getAvailableBooks().get(book);
            if (numberOfBooks > 0) {
                int sizeBefore = person.getBookList().size();
                person.getBookList().add(book);
                if (person.getBookList().size() > sizeBefore) {
                    getAvailableBooks().put(book, numberOfBooks - 1);
                    Record record = new Record(book, person);
                    getTakenBooks().add(record);
                }
            }
            else {
                Request request = new Request(book, person);
                if (!book.getBookRequests().contains(request)) {
                    book.getBookRequests().add(request);
//                library.getRequestLine().put(book, book.getBookRequests());
                    System.out.println("request " + person.toString() + " " + book);
                }
                else {
                    System.out.println("request exists");
                }
            }
        }
        else { // if teachers
            if (book instanceof Fiction) {
                if (book.getLanguage() == Language.ENGLISH) {
                    if (((Fiction) book).getSkills() != person.getSkills()) {
                        return;
                    }
                }
            }
            int numberOfBooks = getAvailableBooks().get(book);
            if (numberOfBooks > 0) {
                int sizeBefore = person.getBookList().size();
                person.getBookList().add(book);
                if (person.getBookList().size() > sizeBefore) {
                    getAvailableBooks().put(book, numberOfBooks - 1);
                    Record record = new Record(book, person);
                    getTakenBooks().add(record);
                }
            }
            else {
                Request request = new Request(book, person);
                if (!book.getBookRequests().contains(request)) {
                    book.getBookRequests().add(request);
//                library.getRequestLine().put(book, book.getBookRequests());
                    System.out.println("request " + person + " " + book);
                }
                else {
                    System.out.println("request exists");
                }
            }
        }
    }


//    public static Library getInstance() {
//        if (instance == null) {
//            instance = new Library();
//        }
//        return instance;
//    }

//    public Map<Book, Queue> getRequestLine() {
//        return requestLine;
//    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public Map<Book, Integer> getAvailableBooks() {
        return availableBooks;
    }

    public List<Book> getLibraryBookList() {
        return libraryBookList;
    }

    public List<Record> getTakenBooks() {
        return takenBooks;
    }
}
