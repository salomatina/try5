package ru.mephi.people;

import ru.mephi.books.*;

public class Teacher extends People {

    private final String patronymic;

    public Teacher(String name, String surname, String patronymic, int skl) {
        super(name, surname, skl);
        this.patronymic = patronymic;
    }

    public String getPatronymic() {
        return patronymic;
    }

    @Override
    public void takeBook(Book book) throws BookException {
        if (book instanceof Fiction) {
            if (book.getLanguage() == Language.ENGLISH) {
                if (((Fiction) book).getSkills() != getSkills()) {
                    return;
                }
            }
        }
        getBookList().add(book.toString());
    }
}
