package es.ulpgc.searchengine;

import es.ulpgc.searchengine.finders.MetadataFinder;
import es.ulpgc.searchengine.finders.WordsFinder;

import java.io.IOException;
import java.util.*;

import static spark.Spark.get;

public class ApiController {

    public static void getParams() {
        Map<String, String> queryParams = new HashMap<>();
        get("/documents/:words", (req, res) -> {
            Set<String> allParams = req.queryParams();
            if (allParams.contains("from")) queryParams.put("from", req.queryParams("from").toLowerCase());
            if (allParams.contains("to")) queryParams.put("to", req.queryParams("to").toLowerCase());
            if (allParams.contains("author")) queryParams.put("author", req.queryParams("author").toLowerCase().replace("+"," "));

            List<String> requestParams = Arrays.asList(req.params("words").toLowerCase().split("\\+"));

            return solveApiConsult(queryParams, requestParams);
        });

    }

    public static void getStats() {
        get("/stats/:type", (req, res) -> {
            String type = req.params("type");

            if (type.equalsIgnoreCase("oldestbook")){
                return Stats.getOldestBook("./Datamart/MetaData/");

            }
            if (type.equalsIgnoreCase("mostcommonword")){
                return Stats.getMostCommonWord();
            }
            return null;
        });
    }

    public static String solveApiConsult(Map<String, String> queryParams, List<String> requestParams) throws IOException {
        ArrayList<String> metadataCoincidences = new MetadataFinder(queryParams).findCoincidences();

        requestParams = new StopwordsDelete().delete(requestParams);

        ArrayList<String> wordsCoincidences;
        if (requestParams.isEmpty()) wordsCoincidences = new MetadataFinder(queryParams).findCoincidences();
        else{
            wordsCoincidences = new WordsFinder(requestParams).findCoincidences();
        }

        ArrayList<String> output = CoincidencesGetter.getCoincidences(wordsCoincidences, metadataCoincidences);
        Collections.sort(output);

        if (JoinJson.Join(output).isEmpty()) return "No Matches";
        return JoinJson.Join(output);
    }

}
