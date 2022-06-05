package ru.mephi.books;

import ru.mephi.people.Skills;

public class Fiction extends Book {

    private Skills skills;

    public Fiction(Skills skills, String name, String author) {
        super(name, author);
        language = Language.ENGLISH;
        this.skills = skills;
    }

    public Fiction(String name, String author) {
        super(name, author);
        language = Language.RUSSIAN;
    }

    public Skills getSkills() throws BookException {
        if (language == Language.ENGLISH) {
            return skills;
        }
        else throw new BookException("wrong book");
    }

    @Override
    public String toString() {
        return " '" + name + "' " + author;
    }
}
