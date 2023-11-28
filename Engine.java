package a1_2001040178;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Engine {
    private Doc[] documents;

    public int loadDocs(String dirname) {
        File folder = new File(dirname);
        File[] file = folder.listFiles();
        if (file == null) {
            return 0;
        }
        this.documents = new Doc[file.length];
        for (int i = 0; i < file.length; i++) {
            byte[] textContent = null;
            try {
                textContent = Files.readAllBytes(file[i].getAbsoluteFile().toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.documents[i] = new Doc(textContent);
        }

        return file.length;
    }

    public Doc[] getDocs() {
        return this.documents;
    }

    public List<Result> search(Query query) {
        List<Result> results = new ArrayList<>();
        for(int i =0; i<this.documents.length;i++){
            List<Match> matchList = query.matchAgainst(this.documents[i]);
        }
        return results;
    }

    public String htmlResult(List<Result> results) {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        for (Result result : results) {
            html.append("<div>");
            html.append(result);
            html.append("</div>");
        }
        html.append("</body></html>");
        return html.toString();
    }
}