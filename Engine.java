package a1_2001040178;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Engine {
    private Doc[] ds;

    public int loadDocs(String dirname) {
        int documentCount = 0;
        File directory = new File(dirname);
        File[] fileArray = directory.listFiles();

        if (fileArray == null) {
            return documentCount;
        }

        Doc[] documentObjects = new Doc[fileArray.length];

        for (int index = 0; index < fileArray.length; index++) {
            File currentFile = fileArray[index];
            StringBuilder fileContent = new StringBuilder();

            if (currentFile.isFile() && currentFile.getName().endsWith(".txt")) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(currentFile))) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        fileContent.append(line).append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileContent.length() > 0) {
                documentObjects[index] = new Doc(fileContent.toString().trim());
                documentCount++;
            }
        }

        return documentCount;
    }



    public Doc[] getDocs() {
        return this.ds;
    }

    public List<Result> search(Query q) {
        List<Result> rList = new ArrayList<>();
        for(Doc doc: getDocs()) {
            List<Match> mList = q.matchAgainst(doc);
            if(mList.size() > 0) {
                Result r = new Result(doc, mList);
                rList.add(r);
            }
        }
        rList.sort(Comparator.reverseOrder());
        return rList;
    }

    public String htmlResult(List<Result> rs) {
        StringBuilder sb = new StringBuilder();
        for (Result r : rs) {
            String html = r.htmlHighlight();
            html = html.replaceAll("\n","");
            sb.append(html);
        }
        return sb.toString();
    }
}