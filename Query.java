package a1_2001040178;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Query{
    private String searchPhase;
    public Query(String searchPhase){
        this.searchPhase = searchPhase;
    }
    public List<Word> getKeywords(){
        List<Word> keyWords = new ArrayList<Word>();
        String[] words = this.searchPhase.split("\\s");
        for(String word:words){
            Word someWord = Word.createWord(word);
            if(someWord.isKeyword()){
                keyWords.add(someWord);
            }
        }
        return keyWords;
    }
    public List<Match> matchAgainst(Doc doc){
        List<Match> listMatch = new ArrayList<Match>();
        List<Word> keyWords = this.getKeywords();
        List<Word> words = new ArrayList<Word>();
        words = doc.getTitle();
        words.addAll(doc.getBody());
        for(Word keyWord : keyWords){
            int freq =0;
            int firstIndex = 0;
            for(Word word:words){
                if(keyWord.equals(word)){
                    firstIndex = words.indexOf(word);
                    freq = freq+1;
                }
            }
            Match match = new Match(doc, keyWord, freq, firstIndex);
            if(match.getFreq()!=0){
                listMatch.add(match);
            }

        }
        Collections.sort(listMatch);
        return listMatch;
    }

}
