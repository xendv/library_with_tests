package library.with.tests;
import lombok.Data;
import lombok.NonNull;

@Data
public class Book {
    //@NonNull
    private Author author = null;
    //@NonNull
    private String name = null;
    Book(Author author, String name){
        this.author = author;
        this.name = name;
    }
}
