package a1_2001040178;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Engine {
    private Doc[] ds;

    public int loadDocs(String dirname) {
        int c = 0;
        File folder = new File(dirname);
        File[] fs = folder.listFiles();
        if (fs == null) return 0;
        ds = new Doc[fs.length];
        for (int i = 0; i < fs.length; i++) {
            StringBuilder sb = new StringBuilder();
            byte[] textContent = null;
            try {
                textContent = Files.readAllBytes(fs[i].getAbsoluteFile().toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.ds[i] = new Doc(textContent);
        }
        return fs.length;
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