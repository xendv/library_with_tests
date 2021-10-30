package library.with.tests;

import lombok.Data;

@Data
public class Book {
    private Author author = null;
    private String name = null;
    Book(Author author, String name){
        this.author = author;
        this.name = name;
    }
}
