package library.with.tests;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public final class TestCapacity5DataForTest {
    static Injector injector;

    static Library library;

    @Mock
    public static BooksFactory booksFactory;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        injector = Guice.createInjector(new LibraryModule(5));
        library = injector.getInstance(LibraryFactory.class).createdLibrary();
        Mockito.when(booksFactory.books()).thenReturn(dataForTest());
        library.setBooks(booksFactory.books());
    }

    public static @NotNull
    List<Book> dataForTest() {
        Book[] b = {new Book(new Author("A0"), "Book0"),
                new Book(new Author("A1"), "Book1"),
                new Book(new Author("A2"), "Book2")};
        return List.of(b);
    }

    public static @NotNull
    List<Book> dataWithNulls() {
        Book[] b = {new Book(new Author("A0"), "Book0"),
                new Book(new Author("A1"), "Book1"),
                new Book(new Author("A2"), "Book2"),
                null,
                null};
        return Arrays.asList(b);
    }

    // При создании библиотеки все книги расставлены по ячейкам
    // в порядке как они возвращаются фабрикой книг.
    // Остальные ячейки пусты.
    @Test
    public void libCreationBooksOrderTest() {
        var libsBooks = library.getBooks();
        assertEquals(true, Arrays.equals(libsBooks, dataWithNulls().toArray(new Book[0])));
    }

    // При взятии книги информация о ней и ячейке выводится
    @Test
    public void bookInfoTest(){
        String expectedOutput="Book info:\n" +
                "Cell number: 1" +
                "\nName: Book1" +
                "\nAuthor: A1\r\n";
        //в формате вывода есть \r - возврат каретки
        ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bAOS));
        library.getBook(1);
        assertThat(bAOS.toString(), equalTo(expectedOutput));
        System.setOut(System.out);
    }

    // При попытке взять книгу из пустой ячейки
    // библиотека бросает исключение
    @Test(expected = NoSuchElementException.class)
    public void libTakeNothingTest() {
        library.getBook(4);
    }

    // При взятии книги возвращается именно та книга, что была в этой ячейке
    @Test
    public void takeBookTest() {
        Book takenBook = library.getBook(1);
        assertEquals(new Book(new Author("A1"), "Book1"), takenBook);
    }

    // Вызов метода “напечатать в консоль содержимое”
    // выводит информацию о содержимом ячеек библиотеки
    @Test
    public void printContentsToConsoleTest(){
        String expectedOutput="----> Cell number: 0\nName: Book0\nAuthor: A0\r\n----------------\n" +
                "----> Cell number: 1\nName: Book1\nAuthor: A1\r\n----------------\n" +
                "----> Cell number: 2\nName: Book2\nAuthor: A2\r\n----------------\n";
        //в формате вывода есть \r - возврат каретки
        ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bAOS));
        library.printContentsToConsole();
        assertThat(bAOS.toString(), equalTo(expectedOutput));
        System.setOut(System.out);
    }
}
