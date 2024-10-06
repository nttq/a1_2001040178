package a1_2001040178;

import java.util.List;

public class Result implements Comparable<Result> {
    public Doc d;
    public List<Match> matches;

    public Result(Doc d, List<Match> matches){
        this.d =d;
        this.matches = matches;
    }

    public List<Match> getMatches(){
        return this.matches;
    }
    public Doc getDoc(){
        return d;
    }
    public int getTotalFrequency(){
        int totalFreq = 0;
        for(Match match :this.matches){
            totalFreq = totalFreq + match.getFreq();
        }
        return totalFreq;
    }

    public double getAverageFirstIndex(){
        double total =0;
        for(Match match : this.matches){
            total = total + match.getFirstIndex();
        }
        return total/(matches.size());
    }
    public String htmlHighlight(){
        List<Word> t = this.d.getTitle();
        List<Word> b = this.d.getBody();
        for(Match match: this.matches){
            if(match.getFreq()>0){
                for(Word w : t){
                    if(match.getWord().equals(w)){
                        t.set(t.indexOf(w), Word.createWord(w.getPrefix()+"<u>"+w.getText()+"</u>"+w.getSuffix()));
                    }
                }
                for(Word w : b){
                    if(match.getWord().equals(w)){
                        b.set(b.indexOf(w), Word.createWord(w.getPrefix()+"<b>"+w.getText()+"</b>"+w.getSuffix()));
                    }
                }
            }
        }
        String title="";
        for(Word word : t){
            title = title + word.toString()+" ";
        }
        String body="";
        for(Word word : b){
            body = body + word.toString()+" ";
        }
        return "<h3>"+title.trim()+"</h3>"+"<p>"+body.trim()+"</p>";

    }
    @Override
    public int compareTo(Result o) {
        if (getMatches().size() > o.getMatches().size()) {
            return 1;
        } else if (getMatches().size() < o.getMatches().size()) {
            return -1;
        } else {
            if (getTotalFrequency() > o.getTotalFrequency()) {
                return 1;
            } else if (getTotalFrequency() < o.getTotalFrequency()) {
                return -1;
            } else {
                return Double.compare(o.getAverageFirstIndex(), getAverageFirstIndex());
            }
        }
    }
}