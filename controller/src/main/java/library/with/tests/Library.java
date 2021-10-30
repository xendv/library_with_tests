package library.with.tests;

import lombok.Singular;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

public class Library implements ILibrary{
    @Singular
    private Book[] books;

    private int capacity;

    public FileBookFactory fileBookFactory;

    public Library(int capacity) throws IndexOutOfBoundsException{
        this.capacity = capacity;
        books = new Book[capacity];
        Arrays.fill(books,null);
    }

    public Library(int capacity, FileBookFactory fileBookFactory) throws IndexOutOfBoundsException{
        this.capacity = capacity;
        books = new Book[capacity];
        Arrays.fill(books,null);
        this.fileBookFactory = fileBookFactory;
        if (fileBookFactory.books().size() > capacity) throw new IndexOutOfBoundsException("No available space in library");
        this.setBooks(fileBookFactory.books());
    }

    public void setBooks(@NotNull Collection<Book> books) {
        if (capacity < books.size()) throw new IndexOutOfBoundsException("No available space in library");
        Book[] booksArray = books.toArray(new Book[0]);
        for (int index = 0; index < books.size(); index ++) {
            this.books[index] = booksArray[index];
        }
        for (int index = books.size(); index < capacity; index++){
            this.books[index] = null;
        }
    }

    public Book getBook(int cellNumber) throws IndexOutOfBoundsException, NoSuchElementException {
        Book requestedBook = books[cellNumber];
        if (requestedBook == null) throw new NoSuchElementException("No book in requested cell");
        else {
            System.out.println("Book info:\n" +
                    "Cell number: " + cellNumber +
                    "\nName: " + requestedBook.getName() +
                    "\nAuthor: " + requestedBook.getAuthor().getName());
            books[cellNumber] = null;
            return requestedBook;
        }
    }

    public void addBook(Book bookToAdd) throws IndexOutOfBoundsException{
        for (int bookIndex = 0; bookIndex < books.length; bookIndex++) {
            if (books[bookIndex] == null) {
                books[bookIndex] = bookToAdd;
                return;
            }
        }
        throw new IndexOutOfBoundsException("No available space in library");
    }

    public void printContentsToConsole(){
        for (int bookIndex = 0; bookIndex < books.length; bookIndex++) {
            Book book = books[bookIndex];
            if (book != null){
                System.out.print("----> ");
                System.out.println("Cell number: " + bookIndex +
                        "\nName: " + book.getName() +
                        "\nAuthor: " + book.getAuthor().getName());
                System.out.print("----------------\n");
            }
        }
    }

    public Book[] getBooks(){
        return books;
    }
}
