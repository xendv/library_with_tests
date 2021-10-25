package library.with.tests;

import lombok.NonNull;

import java.util.Collection;

public interface BooksFactory {
    @NonNull
    Collection<Book> books();
}
