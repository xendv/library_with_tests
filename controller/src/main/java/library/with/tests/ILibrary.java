package library.with.tests;

import com.google.inject.Inject;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public interface ILibrary {
    Book getBook(@NotNull int cellNumber) throws NoSuchElementException;

    public void setBooks(Collection<Book> books);

    void addBook(@NotNull Book bookToAdd) throws IndexOutOfBoundsException;

    void printContentsToConsole();

    final class Dummy implements ILibrary{
        public static final @NotNull ILibrary instance = new Dummy();

        @Override public Book getBook(@NotNull int cellNumber) throws NoSuchElementException{
            return new Book(null, null);
        }

        @Override public void setBooks(Collection<Book> books){}

        @Override public void addBook(@NotNull Book bookToAdd) throws IndexOutOfBoundsException{}

        @Override public void printContentsToConsole(){}
    }
}
