package library.with.tests;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

public class LibraryFactory {
    @Inject
    @NotNull
    Library library;

    public Library library (int capacity){
        return library;
    }

    @TestOnly
    public Library createdLibrary(){
        return library;
    }
}
