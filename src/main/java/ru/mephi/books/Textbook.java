package ru.mephi.books;

import ru.mephi.people.Degree;

public class Textbook extends Book {

    private Degree level;
    private String type = "";
    private String university = "";

    public Textbook(int lvl, String name, String author, String university){
        super(name, author);
        if (lvl == 0) {
            level = Degree.BACHELORS;
        }
        else {
            level = Degree.MASTERS;
        }
        language = Language.ENGLISH;
        this.university = university;
    }

    public Textbook(String type, String name, String author) {
        super(name, author);
        this.type = type;
        language = Language.RUSSIAN;
    }

    public Degree getLevel() throws BookException {
        if (language == Language.ENGLISH) {
            return level;
        }
        else throw new BookException("wrong book");
    }

    public String getType() throws BookException {
        if (language == Language.RUSSIAN) {
            return type;
        }
        else throw new BookException("wrong book");
    }

    public String getAuthor() throws BookException {
        if (language == Language.ENGLISH) {
            return author;
        }
        else throw new BookException("wrong book");
    }

    public String getUniversity() throws BookException {
        if (language == Language.ENGLISH) {
            return university;
        }
        else throw new BookException("wrong book");
    }

    @Override
    public String toString() {
        return type + " '" + name + "' " + author + " " + university;
    }
}
