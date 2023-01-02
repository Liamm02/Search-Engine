package es.ulpgc.searchengine;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class DocumentsGetter {

    public static Set<String> getDocuments(String folder){
        Set<String> fileSet = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(folder))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileSet.add(path.getFileName()
                            .toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileSet;

    }

    public static String[] getFolders(String Path){
        File file = new File(Path);
        return file.list((current, name) -> new File(current, name).isDirectory());
    }


}
