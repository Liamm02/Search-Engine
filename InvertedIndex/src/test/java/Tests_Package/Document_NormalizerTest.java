package Tests_Package;

import org.example.Checker;
import org.example.Document_Normalizer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class Document_NormalizerTest {

    @Test
    void translator() throws Exception {
        List<String> expected = new ArrayList<>();
        expected.add("this is a test file that has some punctuation ");
        expected.add("with this file 3 we will check the document normalizer translator function ");
        expected.add("output will not have any punctuation characters ");
        expected.add("and will be split by lines");

        String path = "./src/test/java/Test_resources/";
        Checker checker = new Checker(path);
        ArrayList<String> documents = checker.documents_checker();
        checker.Inv_Index_checker();
        Document_Normalizer dn = new Document_Normalizer(path);
        List<String> lines = dn.Translator(documents.get(3));

        assertEquals(expected,lines);
    }

    @Test
    void stopwords_Deleter() throws Exception {
        String path = "./src/test/java/Test_resources/";
        Checker checker = new Checker(path);
        ArrayList<String> documents = checker.documents_checker();
        checker.Inv_Index_checker();
        Document_Normalizer dn = new Document_Normalizer(path);
        List<String> lines = dn.Translator(documents.get(3));

        HashMap<String, Set<Integer>> Doc_InvertedIndex = dn.Stopwords_Deleter(lines,"Spanish");
        //Stopwords were deleted, and it was stored correctly in a hashmap
        System.out.println(Doc_InvertedIndex);

    }
}