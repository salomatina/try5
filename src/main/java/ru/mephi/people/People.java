package ru.mephi.people;

import ru.mephi.Library;
import ru.mephi.Request;
import ru.mephi.books.Book;
import ru.mephi.books.BookException;

import java.util.HashSet;
import java.util.Set;

public abstract class People {

    private Set<Book> bookList;
    private Skills skills;
    protected String name;
    protected String surname;

    public People(String name, String surname, int skl) {
        this.name = name;
        this.surname = surname;
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
    }

    public void takeBook(Book book, Library library) throws BookException {
        getBookList();
        Request request = new Request(book, this);
        if (library.getAvailableBooks().get(book) > 0) {
            bookList.add(book);
        }
        else {
            if (!book.getBookRequests().contains(request)) {
                book.getBookRequests().add(request);
//                library.getRequestLine().put(book, book.getBookRequests());
                System.out.println("request " + this.getName() + " " + book);
            }
        }
    }

    public void returnBook(Book book, Library library) throws BookException {
        bookList.remove(book);
            library.getAvailableBooks().put(book, library.getAvailableBooks().get(book) + 1);
            try {
                Request request = book.getBookRequests().poll();
                if (request != null) {
                    request.getPerson().takeBook(book, library);
                    System.out.println(" book was taken " + request.getPerson().toString() +
                            " " + book);
                }
            }
            catch (NullPointerException ignored) {
            }
    }

    public Set<Book> getBookList() {
        if (bookList == null) {
            bookList = new HashSet<>();
        }
        return bookList;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
