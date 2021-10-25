package library.with.tests;

import com.google.inject.Inject;
import lombok.Data;
import lombok.Singular;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

//@Data
public class Library implements ILibrary{
    @Singular
    private ArrayList<Book> books;
    //private Book[] books;

    private int capacity;

    //@Inject
    //@NotNull
    //@BooksFactoryAnnot
    public FileBookFactory fileBookFactory;
    //@Inject
    public Library(int capacity) throws IndexOutOfBoundsException{

        this.capacity = capacity;
        //books = new Book[capacity];
        books = new ArrayList<>(capacity);
        //fileBookFactory = new FileBookFactory("./books.txt");
        //this.setBooks(fileBookFactory.books());
        //if (fileBookFactory.books().size() > capacity) throw new IndexOutOfBoundsException("No available space in library");
        //books = fileBookFactory.books().toArray(Book[capacity]);
        //this.setBooks((ArrayList<Book>) fileBookFactory.books());
    }
    public Library(int capacity, FileBookFactory fileBookFactory) throws IndexOutOfBoundsException{

        this.capacity = capacity;
        books = new ArrayList<>(capacity);

        this.fileBookFactory = fileBookFactory;
        if (fileBookFactory.books().size() > capacity) throw new IndexOutOfBoundsException("No available space in library");

        this.setBooks(fileBookFactory.books());
    }
    public Library(int capacity, boolean empty) throws IndexOutOfBoundsException{
        books = new ArrayList<>(capacity);
        this.capacity = capacity;
        //books = new Book[capacity];
    }

    /*public Library(int capacity, String path) throws IndexOutOfBoundsException{
        books = new ArrayList<>(capacity);
        //fileBookFactory = new FileBookFactory(path);
        books = (ArrayList<Book>) fileBookFactory.books();
    }*/

    public void setBooks(@NotNull Collection<Book> books) {
        //this.books = (Book[])books.toArray();
        if (capacity < books.size()) throw new IndexOutOfBoundsException("No available space in library");
        this.books = (ArrayList<Book>) books;
    }

    public Book getBook(int cellNumber) throws IndexOutOfBoundsException, NoSuchElementException {
        Book requestedBook = books.get(cellNumber);
        //Book requestedBook = books[cellNumber];

        if (requestedBook == null) throw new NoSuchElementException("No book in requested cell");
        else {
            System.out.println("Book info:\n" +
                    "Cell number: " + cellNumber +
                    "\nName: " + requestedBook.getName() +
                    "\nAuthor: " + requestedBook.getAuthor().getName());
            books.set(cellNumber, null);
            //books[cellNumber] = null;

            return requestedBook;
        }
    }

    public void addBook(Book bookToAdd) throws IndexOutOfBoundsException{
        //for (int bookIndex = 0; bookIndex < books.size(); bookIndex++) {
        if (books.size() == capacity) throw new IndexOutOfBoundsException("No available space in library");
        //for (int bookIndex = 0; bookIndex < books.length; bookIndex++) {
            for (int bookIndex = 0; bookIndex < books.size(); bookIndex++) {

            if (books.get(bookIndex) == null) {
                books.add(bookIndex, bookToAdd);
                return;
            }/*
            if (books[bookIndex] == null) {
                books[bookIndex] = bookToAdd;
                return;
            }*/
        }
        throw new IndexOutOfBoundsException("No available space in library");
    }

    public void printContentsToConsole(){
        for (int bookIndex = 0; bookIndex < books.size(); bookIndex++) {
        //for (int bookIndex = 0; bookIndex < books.length; bookIndex++) {

            Book book = books.get(bookIndex);
            //Book book = books[bookIndex];

            if (book != null){
                System.out.print("----> ");
                System.out.println("Cell number: " + bookIndex +
                        "\nName: " + book.getName() +
                        "\nAuthor: " + book.getAuthor().getName());
                System.out.print("----------------\n");
            }
        }
    }

    public Collection<Book> getBooks(){
        return books;
    }

}
