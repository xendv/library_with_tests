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
    public LibraryModule(){

    }

    @Override
    protected void configure(){
        //bind(FileBookFactory.class).annotatedWith(BooksFactoryAnnot.class).toInstance(new FileBookFactory(filePath));
        /*if (filePath != null)
                bind(Library.class).toInstance(new Library(capacity));*/
        if (filePath != null){
            //bind(FileBookFactory.class).annotatedWith(BooksFactoryAnnot.class).toInstance(new FileBookFactory(filePath));
            bind(Library.class).toInstance(new Library(capacity, fileBookFactory(filePath)));
        }
        else if (capacity != -1) bind(Library.class).toInstance(new Library(capacity));
        //else bind(Library.class).toInstance(new Library());
    }

    //@NotNull
    @Provides
    FileBookFactory fileBookFactory(@NotNull String filePath){
        return new FileBookFactory(filePath);
    }

}
