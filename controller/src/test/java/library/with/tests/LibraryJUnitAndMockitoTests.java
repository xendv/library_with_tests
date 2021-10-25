package library.with.tests;


import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.*;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

//@RunWith(Parameterized.class)
public class LibraryJUnitAndMockitoTests {
    static Injector injector;
    @Mock
    private Library library;

    //@Spy
    //final LibraryFactory libraryFactory = new LibraryFactory();

    @Mock
    private BooksFactory booksFactory;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public static void beforeClass(){

    }

    public static @NotNull
    Collection<Book> dataForTest() {
        return new ArrayList<Book>() {
            {
                add(new Book(new Author("A0"), "Book0"));
                add(new Book(new Author("A1"), "Book1"));
                add(new Book(new Author("A2"), "Book2"));

            }
        };
    }
    public static @NotNull
    Collection<Book> data1ForTest() {
        return new ArrayList<Book>() {
            {
                add(new Book(new Author("A0"), "Book0"));
                add(new Book(new Author("A1"), "Book1"));
                add(new Book(new Author("A2"), "Book2"));

            }
        };
    }
    public static @NotNull
    Collection<Book> dataWithNulls() {
        return new ArrayList<Book>() {
            {
                add(new Book(new Author("A0"), "Book0"));
                add(new Book(new Author("A1"), "Book1"));
                add(new Book(new Author("A2"), "Book2"));
                add(null);
                add(null);
            }
        };
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

    @Test(expected = IndexOutOfBoundsException.class)
    public void testThrowIndexOutOfBoundsException() {
        //libraryFactory = new LibraryFactory();
        injector = Guice.createInjector(new LibraryModule(2));

        Mockito.when(booksFactory.books()).thenReturn(dataForTest());

        library = injector.getInstance(LibraryFactory.class).library(2);

        library.setBooks(booksFactory.books());
    }

    @Test(expected = NoSuchElementException.class)
    public void libTakeNothingTest() {
        //injector = Guice.createInjector(new LibraryModule(CAPACITY));
        injector = Guice.createInjector(new LibraryModule(5));
        Mockito.when(booksFactory.books()).thenReturn(dataWithEmptyCell());

        Library lib = injector.getInstance(LibraryFactory.class).library(5);

        lib.setBooks(booksFactory.books());
        lib.getBook(2);
    }

    @Test
    public void libCreationBooksOrderTest() {
        injector = Guice.createInjector(new LibraryModule(5));
        Mockito.when(booksFactory.books()).thenReturn(dataForTest());

        Library lib = injector.getInstance(LibraryFactory.class).library(5);

        lib.setBooks(booksFactory.books());

        assertThat(lib.getBooks(), contains(dataWithNulls()));
    }


    @After
    public void after() {
        Mockito.reset();
    }
}
