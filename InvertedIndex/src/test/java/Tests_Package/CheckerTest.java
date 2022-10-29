package Tests_Package;

import org.example.Checker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CheckerTest {
    public final Exception exception = new Exception("There are no Documents or Path is not correct.");

    @Test
    void documents_checker() throws Exception {
        String path = "./src/test/java/Test_resources/";
        ArrayList<String> expected = new ArrayList<>();
        expected.add("25267-8.txt");
        expected.add("68974-0.txt");
        expected.add("Ethics.txt");
        expected.add("TEST_Small_File.txt");

        Checker Documents_test = new Checker(path);
        ArrayList<String> output = Documents_test.documents_checker();
        assertEquals(expected,output);

        path = "./src/test/java/Test_resourcesERRORTRY/";
        Checker Error_Test = new Checker(path);
        String expectedMessage = "There are no Documents or Path is not correct.";
        Exception exception = assertThrows(Exception.class, () -> Error_Test.documents_checker());
        assertEquals(expectedMessage, exception.getMessage());
    }

}