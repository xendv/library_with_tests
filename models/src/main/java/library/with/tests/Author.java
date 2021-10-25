package library.with.tests;
import lombok.Data;
import lombok.NonNull;

@Data
public class Author {
    private String name;
    Author(String name){
        this.name = name;
    }
}
