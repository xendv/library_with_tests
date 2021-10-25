package library.with.tests;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Application {
    public static void main(String[] args){
        String errMsg = "Please enter full path to lib and its capacity";
        if(args.length != 2){
            System.out.println(errMsg);
        }
        else{
            if ( !args[1].matches("\\d+") )
                System.out.println(errMsg);
            else {
                int capacity = Integer.parseInt(args[1]);
                final Injector injector = Guice.createInjector(new LibraryModule(args[0], Integer.parseInt(args[1])));
                Library library = injector.getInstance(LibraryFactory.class).library(capacity);
                library.printContentsToConsole();
            }
        }
    }
}
