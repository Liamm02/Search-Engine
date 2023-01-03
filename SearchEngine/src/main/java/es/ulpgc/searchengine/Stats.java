package es.ulpgc.searchengine;

import es.ulpgc.searchengine.finders.MetadataFinder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.*;

import static es.ulpgc.searchengine.ApiController.solveApiConsult;


public class Stats {
    public static String getOldestBook() throws IOException {
        Map<String, String> bookDate = new HashMap<>();
        ArrayList<String> metadataCoincidences;
        for (int i = 1450; i < Year.now().getValue(); i++) {
            bookDate.put("from", String.valueOf(i));
            bookDate.put("to", String.valueOf(i));
            metadataCoincidences = new MetadataFinder(bookDate).findCoincidences();
            if (!(metadataCoincidences.isEmpty())){
                return solveApiConsult(bookDate, new ArrayList<>());
            }
            bookDate.clear();
        }

        return "A problem occurred";
    }
    public static Map<String, Integer> getMostCommonWord() throws IOException, ParseException {
        String word  = "";
        int count = 0;
        Map<String,Integer> result = new HashMap<>();
        JSONParser parser = new JSONParser();
        File dir = new File("./Datamart/Inv_Index.json");
        Object obj=  parser.parse(new java.io.FileReader(dir));
        JSONObject jsonObject = (JSONObject) obj;
        for (Object key : jsonObject.keySet()){
                JSONArray array = (JSONArray) jsonObject.get(key);
            for (Object o : array) {
                JSONObject object = (JSONObject) o;
                for (Object k : object.keySet()) {
                    JSONArray ar = (JSONArray) object.get(k);
                    if (ar.size() > count) {
                        count = ar.size();
                        word = String.valueOf(key);
                    }
                }
            }

        }
        result.put(word,count);
        return result;
    }
}
