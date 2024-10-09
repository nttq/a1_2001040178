package a1_2001040178;

public class Match implements Comparable<Match>{
    private Doc doc;             // Đối tượng Doc (tài liệu) mà từ (word) được tìm thấy
    private Word word;           // Đối tượng Word (từ) mà khớp với truy vấn
    private int freq;            // Số lần xuất hiện của từ trong tài liệu (tần suất xuất hiện)
    private int firstIndex;      // Vị trí đầu tiên mà từ xuất hiện trong tài liệu

    // Constructor khởi tạo đối tượng Match với tài liệu, từ, tần suất xuất hiện và vị trí đầu tiên
    public Match(Doc doc, Word word, int freq, int firstIndex) {
        this.doc = doc;
        this.word = word;
        this.freq = freq;
        this.firstIndex = firstIndex;
    }
    // Getter trả về từ khớp với truy vấn
    public Word getWord() {
        return word;
    }
    // Getter trả về tần suất xuất hiện của từ trong tài liệu
    public int getFreq() {
        return freq;
    }
    // Getter trả về vị trí đầu tiên mà từ xuất hiện trong tài liệu
    public int getFirstIndex() {
        return firstIndex;
    }

    // Phương thức compareTo dùng để so sánh hai đối tượng Match dựa trên vị trí đầu tiên mà từ xuất hiện
    @Override
    public int compareTo(Match o) {
        return Integer.compare(getFirstIndex(), o.getFirstIndex());         // So sánh vị trí đầu tiên
    }
}
