package a1_2001040178;

public class Match implements Comparable<Match>{
    private Doc doc;
    private Word word;
    private int freq;
    private int firstIndex;

    public Match(Doc doc, Word word, int freq, int firstIndex) {
        this.doc = doc;
        this.word = word;
        this.freq = freq;
        this.firstIndex = firstIndex;
    }

    public Word getWord() {
        return word;
    }

    public int getFreq() {
        return freq;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    @Override
    public int compareTo(Match other) {

        if(this.getFirstIndex()>other.getFirstIndex()){
            return 1;
        }
        if(this.getFirstIndex()<other.getFirstIndex()){
            return -1;
        }
        return 0;
    }

}
