package es.ulpgc.searchengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CoincidencesGetter {

    public static ArrayList<String> getCoincidences(ArrayList<String> WordsCoincidences, ArrayList<String> MetadataCoincidences){
        List<String> total = new ArrayList<>();
        total.addAll(WordsCoincidences);
        total.addAll(MetadataCoincidences);
        ArrayList<String> output = new ArrayList<>();

        Map<String, Long> frequencies = total.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        frequencies.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .forEach(entry -> output.add(entry.getKey()));
        return output;
    }

}
