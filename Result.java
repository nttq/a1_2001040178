package a1_2001040178;

import java.util.List;

public class Result implements Comparable<Result> {
    public Doc d;
    public List<Match> matches;
    // Constructor nhận tài liệu và danh sách các kết quả khớp (matches)
    public Result(Doc d, List<Match> matches){
        this.d =d;
        this.matches = matches;
    }
    // Getter trả về danh sách các kết quả khớp
    public List<Match> getMatches(){
        return this.matches;
    }

    // Getter trả về đối tượng tài liệu liên quan đến kết quả này
    public Doc getDoc(){
        return d;
    }

    // Tính tổng tần suất xuất hiện của các từ khóa trong tài liệu
    public int getTotalFrequency(){
        int totalFreq = 0;      // Biến lưu trữ tổng tần suất
        for(Match match :this.matches){      // Duyệt qua từng đối tượng Match
            totalFreq = totalFreq + match.getFreq();  // Cộng tần suất của mỗi từ khóa vào tổng
        }
        return totalFreq;        // Trả về tổng tần suất
    }
    // Tính trung bình vị trí đầu tiên mà các từ khóa xuất hiện trong tài liệu
    public double getAverageFirstIndex(){
        double total =0;        // Biến lưu trữ tổng vị trí
        for(Match match : this.matches){        // Duyệt qua từng đối tượng Match
            total = total + match.getFirstIndex();       // Cộng vị trí đầu tiên vào tổng
        }
        return total/(matches.size());       // Trả về giá trị trung bình của vị trí đầu tiên
    }
    // Tạo HTML highlight các từ khóa trong tiêu đề và nội dung tài liệu
    public String htmlHighlight(){
        List<Word> t = this.d.getTitle();
        List<Word> b = this.d.getBody();
        // Duyệt qua từng kết quả khớp
        for(Match match: this.matches){
            // Nếu từ khóa xuất hiện trong tài liệu
            if(match.getFreq()>0){
                // Highlight từ khóa trong tiêu đề bằng cách thêm thẻ <u> (gạch chân)
                for(Word w : t){
                    if(match.getWord().equals(w)){
                        t.set(t.indexOf(w), Word.createWord(w.getPrefix()+"<u>"+w.getText()+"</u>"+w.getSuffix()));
                    }
                }
                // Highlight từ khóa trong nội dung bằng cách thêm thẻ <b> (in đậm)
                for(Word w : b){
                    if(match.getWord().equals(w)){
                        b.set(b.indexOf(w), Word.createWord(w.getPrefix()+"<b>"+w.getText()+"</b>"+w.getSuffix()));
                    }
                }
            }
        }
        // Tạo chuỗi HTML cho tiêu đề
        String title="";
        for(Word word : t){
            title = title + word.toString()+" ";// Nối từng từ trong tiêu đề thành chuỗi
        }

        // Tạo chuỗi HTML cho nội dung
        String body="";
        for(Word word : b){
            body = body + word.toString()+" ";// Nối từng từ trong nội dung thành chuỗi
        }
        // Trả về chuỗi HTML với tiêu đề và nội dung đã highlight
        return "<h3>"+title.trim()+"</h3>"+"<p>"+body.trim()+"</p>";

    }
    // So sánh hai đối tượng Result để sắp xếp
    @Override
    public int compareTo(Result o) {
        // So sánh theo số lượng từ khóa khớp
        if (getMatches().size() > o.getMatches().size()) {
            return 1;
        } else if (getMatches().size() < o.getMatches().size()) {
            return -1;
        } else {
            // Nếu số lượng từ khóa khớp bằng nhau, so sánh tổng tần suất xuất hiện của các từ khóa
            if (getTotalFrequency() > o.getTotalFrequency()) {
                return 1;
            } else if (getTotalFrequency() < o.getTotalFrequency()) {
                return -1;
            } else {
                // Nếu tần suất bằng nhau, so sánh theo vị trí đầu tiên xuất hiện của các từ khóa
                return Double.compare(o.getAverageFirstIndex(), getAverageFirstIndex());
            }
        }
    }
}
