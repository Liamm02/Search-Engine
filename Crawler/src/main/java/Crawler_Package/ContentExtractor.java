package Crawler_Package;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContentExtractor {


    public void ExtractContent(File folder){

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMd");
        String date = dateFormat.format(new Date());
        File f = new File(folder+"\\Documents\\"+date+"\\");
        File[] listOfFiles = f.listFiles();
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            filere


        }
        }
    }
}
