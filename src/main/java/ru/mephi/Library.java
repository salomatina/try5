package ru.mephi;

import ru.mephi.books.*;
import ru.mephi.people.Student;
import ru.mephi.people.Teacher;

import java.util.*;

public class Library {

    private static Library instance = null;

    private final List<Student> students;
    private final List<Teacher> teachers;
    private final Map<Book, Integer> availableBooks;
    private final List<Book> bookList; // need that to take random book (to get it from index)
//    private final Map<Book, Queue> requestLine;
    private final List<Record> takenBooks;

    private Library() {
//        requestLine = new HashMap<>();
        students = new ArrayList<>();
        teachers = new ArrayList<>();
        availableBooks = new HashMap<>();
        bookList = new ArrayList<>();
        takenBooks = new ArrayList<>();
    }

    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

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

    public List<Book> getBookList() {
        return bookList;
    }

    public List<Record> getTakenBooks() {
        return takenBooks;
    }
}
