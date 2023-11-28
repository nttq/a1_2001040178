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
        return this.d;
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
        List<Word> title1 = this.d.getTitle();
        List<Word> body1 = this.d.getBody();
        for(Match match: this.matches){
            if(match.getFreq()>0){
                for(Word word : title1){
                    if(match.getWord().equals(word)){
                        title1.set(title1.indexOf(word), Word.createWord(word.getPrefix()+"<u>"+word.getText()+"</u>"+word.getSuffix()));
                    }
                }
                for(Word word : body1){
                    if(match.getWord().equals(word)){
                        body1.set(body1.indexOf(word), Word.createWord(word.getPrefix()+"<b>"+word.getText()+"</b>"+word.getSuffix()));
                    }
                }
            }
        }
        String title="";
        for(Word word : title1){
            title = title + word.toString()+" ";
        }
        String body="";
        for(Word word : body1){
            body = body + word.toString()+" ";
        }
        return "<h3>"+title.trim()+"</h3>"+"<p>"+body.trim()+"</p>";

    }
    @Override
    public int compareTo(Result o){
        if(this.matches.size()>o.matches.size()){
            return -1;
        }
        if(this.matches.size()<o.matches.size()){
            return 1;
        }
        if(this.getTotalFrequency()>o.getTotalFrequency()){
            return -1;
        }
        if(this.getTotalFrequency()<o.getTotalFrequency()){
            return 1;
        }
        if(this.getAverageFirstIndex()>o.getAverageFirstIndex()){
            return 1;
        }
        if(this.getAverageFirstIndex()<o.getAverageFirstIndex()){
            return -1;
        }
        return 0;
    }
}