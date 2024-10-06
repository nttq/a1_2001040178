package a1_2001040178;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Query{
    private String searchPhase;
    private List<Word> kws;

    public Query(String searchPhrase) {
        kws = new ArrayList<>();
        String[] raw = searchPhrase.split(" ");
        for (String rText: raw) {
            Word w = Word.createWord(rText);
            if (w != null) {
                if (w.isKeyword()) {
                    kws.add(w);
                }
            }
        }
    }
    public List<Word> getKeywords() {
        return kws;
    }

    public List<Match> matchAgainst(Doc doc){
        List<Match> listMatch = new ArrayList<>();
        List<Word> t = doc.getTitle();
        List<Word> b = doc.getBody();
        for(Word kw : kws){
            int dup = 0;
            int firstI = -1;
            for (int i = 0; i < t.size(); i++) {
                if (t.get(i).equals(kw)) {
                    if (firstI == -1) firstI = i;
                    dup++;
                }
            }
            for (int i = 0; i < b.size(); i++) {
                if (b.get(i).equals(kw)) {
                    if (firstI == -1) firstI = i + t.size();
                    dup++;
                }
            }
            if(dup != 0) {
                listMatch.add(new Match(doc, kw, dup, firstI));
            }
        }
        Collections.sort(listMatch);
        return listMatch;
    }

}
