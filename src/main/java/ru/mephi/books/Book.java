package ru.mephi.books;

public abstract class Book {

    protected Language language;
    protected String name;
    protected String author;

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public Language getLanguage() {
        return language;
    }

}
