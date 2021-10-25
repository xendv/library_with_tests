package library.with.tests;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;

public class LibraryFactory {
    @Inject
    @NotNull
    Library library;

    public Library library (int capacity){
        //library = new Library(capacity);
        return library;
    }
    public Library emptyLibrary (int capacity){
        library = new Library(capacity, true);
        return library;
    }
}
