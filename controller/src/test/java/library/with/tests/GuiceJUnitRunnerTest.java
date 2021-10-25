package library.with.tests;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import com.google.inject.Guice;
import lombok.Getter;
import library.with.tests.GuiceJUnitRunner.GuiceModules;
//import library.with.tests.GuiceJUnitRunnerTest.TestModule;

import org.jetbrains.annotations.NotNull;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.mockito.Mock;
import org.mockito.Mockito;

@RunWith(GuiceJUnitRunner.class)
//@GuiceModules(TestModule.class)
@GuiceModules(GuiceJUnitRunnerTest.LibraryModuleEmpty.class)
public class GuiceJUnitRunnerTest {
    //private static final String SOME_VALUE = "Some value!";
    //private static final String ANOTHER_VALUE = "Another value!";
    private static final int CAPACITY = 4;
    /*@Inject
    private SampleBean sample;*/
    //@Inject
    //private Library library;

    //@Inject
    @Mock
    private BooksFactory booksFactory;

    @Inject
    private Injector injector;

    private static final Collection<Integer> injectors = Sets.newHashSet();

    @Before
    public void setUp() {
        injectors.add(injector.hashCode());
    }

    @AfterClass
    public static void afterClass() {
        //assertThat(injectors, hasSize(3));
    }

    @Test
    public void basicUsage() {
        //assertThat(sample, is(notNullValue()));
        //assertThat(library, is(notNullValue()));
        //assertThat(sample.getValue(), is(SOME_VALUE));
    }

    @Test
    public void itShouldBuildANewInjectorForEveryTest() {
        basicUsage();
    }

    /*@Test
    @GuiceModules(TestAnotherModule.class)
    public void itShouldSupportMethodAnnotations() {
        //assertThat(library, is(notNullValue()));
        //assertThat(sample.getValue(), is(ANOTHER_VALUE));
    }*/

    @Test
    @GuiceModules(LibraryModuleEmpty.class)
    public void libCreationBooksOrderTest() {
        //injector = Guice.createInjector(new LibraryModule(CAPACITY));
        Mockito.when(booksFactory.books()).thenReturn(dataForTest());

        Library lib = injector.getInstance(LibraryFactory.class).library(4);
        lib.setBooks(booksFactory.books());
        assertThat(lib.getBooks(), contains(
                dataWithNulls()
        ));
        //assertThat(sample, is(notNullValue()));

        //assertThat(sample.getValue(), is(ANOTHER_VALUE));
    }

    public static @NotNull
    Collection<Book> dataForTest() {
        return Arrays.asList(
                new Book(new Author("A0"), "Book0"),
                new Book(new Author("A1"), "Book1"),
                new Book(new Author("A2"), "Book2")
        );
    }
    public static @NotNull
    Collection<Book> dataWithNulls() {
        return Arrays.asList(
                new Book(new Author("A0"), "Book0"),
                new Book(new Author("A1"), "Book1"),
                new Book(new Author("A2"), "Book2"),
                null,
                null
        );
    }

    public static class LibraryModuleEmpty extends AbstractModule {
        int capacity;

        public LibraryModuleEmpty(int capacity){
            this.capacity = capacity;
        }

        @Override
        protected void configure() {
            //bind(String.class).annotatedWith(Names.named("some-value")).toInstance(ANOTHER_VALUE);
            bind(Library.class).toInstance(new Library(CAPACITY));
        }
    }

    /*
    public static class TestModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(String.class).annotatedWith(Names.named("some-value")).toInstance(SOME_VALUE);
        }
    }*/

    /*public static class TestAnotherModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(String.class).annotatedWith(Names.named("some-value")).toInstance(ANOTHER_VALUE);
        }
    }*/



    /*public static class AnotherModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(String.class).annotatedWith(Names.named("some-value")).toInstance(ANOTHER_VALUE);
        }
    }*/


    /*@Getter
    public static class SampleBean {
        @Inject @Named("some-value")
        private String value;
    }*/
}