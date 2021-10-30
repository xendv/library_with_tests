package library.with.tests;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.jetbrains.annotations.NotNull;

public class LibraryModule extends AbstractModule {
    String filePath;
    int capacity = -1;

    public LibraryModule(String filePath, int capacity){
        this.filePath = filePath;
        this.capacity = capacity;
    }

    public LibraryModule(int capacity){
        this.capacity = capacity;
    }

    @Override
    protected void configure(){
        if (filePath != null){
            bind(Library.class).toInstance(new Library(capacity, fileBookFactory(filePath)));
        }
        else if (capacity != -1) bind(Library.class).toInstance(new Library(capacity));
    }

    @Provides
    FileBookFactory fileBookFactory(@NotNull String filePath){
        return new FileBookFactory(filePath);
    }
}
