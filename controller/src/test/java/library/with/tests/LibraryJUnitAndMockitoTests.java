package library.with.tests;


import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class LibraryJUnitAndMockitoTests {
    static Injector injector;

    static Library library;

    @Mock
    static private BooksFactory booksFactory;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    // Библиотека бросает исключение при создании,
    // если ее вместимость меньше чем количество книг,
    // возвращаемое фабрикой
    @Test(expected = IndexOutOfBoundsException.class)
    public void testThrowIndexOutOfBoundsException() {
        injector = Guice.createInjector(new LibraryModule(2));

        Mockito.when(booksFactory.books()).thenReturn(dataForTest());

        library = injector.getInstance(LibraryFactory.class).library(2);

        library.setBooks(booksFactory.books());
    }

    // Если при добавлении книги свободных ячеек нет,
    // библиотека бросает исключение
    @Test(expected = IndexOutOfBoundsException.class)
    public void noAvailableSpaceInLibraryTest(){
        injector = Guice.createInjector(new LibraryModule(3));
        Library library = injector.getInstance(LibraryFactory.class).createdLibrary();
        Mockito.when(booksFactory.books()).thenReturn(dataForTest());
        library.setBooks(booksFactory.books());

        library.addBook(new Book(new Author(""),""));
    }

    //При добавлении книги она размещается в первой свободной ячейке
    @Test
    public void addBookTest(){
        injector = Guice.createInjector(new LibraryModule(4));
        Library library = injector.getInstance(LibraryFactory.class).createdLibrary();

        Mockito.when(booksFactory.books()).thenReturn(dataWithEmptyCell());
        library.setBooks(booksFactory.books());

        library.addBook(new Book(new Author("A4"), "Book4"));
        assertArrayEquals(addBookTestResult().toArray(), library.getBooks());
    }

    // Данные для Mock-объекта:

    public static @NotNull
    List<Book> dataForTest() {
        Book[] b = {new Book(new Author("A0"), "Book0"),
                new Book(new Author("A1"), "Book1"),
                new Book(new Author("A2"), "Book2")};
        return List.of(b);
    }

    public static @NotNull
    Collection<Book> dataWithEmptyCell() {
        return new ArrayList<Book>() {
            {
                add(new Book(new Author("A0"), "Book0"));
                add(new Book(new Author("A1"), "Book1"));
                add(null);
                add(new Book(new Author("A2"), "Book2"));
            }
        };
    }

    public static @NotNull
    List<Book> addBookTestResult(){
        Book[] b = {new Book(new Author("A0"), "Book0"),
                new Book(new Author("A1"), "Book1"),
                new Book(new Author("A4"), "Book4"),
                new Book(new Author("A2"), "Book2")};
        return Arrays.asList(b);
    }

    @After
    public void after() {
        Mockito.reset();
    }
}
