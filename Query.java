package a1_2001040178;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Query{
    private String searchPhase;         // Chuỗi truy vấn tìm kiếm
    private List<Word> kws;             // Danh sách các từ khóa (keywords) trong truy vấn

    // Constructor nhận chuỗi tìm kiếm và tạo danh sách từ khóa từ chuỗi đó
    public Query(String searchPhrase) {
        kws = new ArrayList<>();        // Khởi tạo danh sách từ khóa
        String[] raw = searchPhrase.split(" ");     // Tách chuỗi tìm kiếm thành các từ bằng khoảng trắng
        for (String rText: raw) {
            Word w = Word.createWord(rText);        // Tạo đối tượng Word từ mỗi từ trong chuỗi tìm kiếm
            if (w != null) {                        // Kiểm tra nếu Word được tạo không phải null
                if (w.isKeyword()) {                // Nếu từ đó là từ khóa (không phải stop word)
                    kws.add(w);                     // Thêm vào danh sách từ khóa
                }
            }
        }
    }
    // Getter trả về danh sách từ khóa
    public List<Word> getKeywords() {
        return kws;
    }
    // Phương thức matchAgainst dùng để match với (Doc)
    public List<Match> matchAgainst(Doc doc){
        List<Match> listMatch = new ArrayList<>();      // Danh sách các kết quả match
        List<Word> t = doc.getTitle();                  // Lấy danh sách từ trong tiêu đề tài liệu
        List<Word> b = doc.getBody();                    // Lấy danh sách từ trong nội dung tài liệu
        // Duyệt qua từng từ khóa trong truy vấn
        for(Word kw : kws){
            int dup = 0;            // Biến đếm số lần xuất hiện của từ khóa trong tài liệu
            int firstI = -1;        // Vị trí đầu tiên mà từ khóa xuất hiện trong tài liệu
            // Duyệt qua danh sách từ trong tiêu đề tài liệu và so khớp với từ khóa
            for (int i = 0; i < t.size(); i++) {
                // Nếu từ trong tiêu đề khớp với từ khóa
                if (t.get(i).equals(kw)) {
                    if (firstI == -1) firstI = i;       // Ghi lại vị trí đầu tiên nếu chưa có
                    dup++;
                }
            }
            // Duyệt qua danh sách từ trong nội dung tài liệu và so khớp với từ khóa
            for (int i = 0; i < b.size(); i++) {
                // Nếu từ trong nội dung khớp với từ khóa
                if (b.get(i).equals(kw)) {
                    if (firstI == -1) firstI = i + t.size();        // Ghi lại vị trí đầu tiên nếu chưa có
                    dup++;
                }
            }
            // Nếu từ khóa xuất hiện trong tài liệu, tạo đối tượng Match và thêm vào danh sách
            if(dup != 0) {
                listMatch.add(new Match(doc, kw, dup, firstI));      // Tạo đối tượng Match lưu thông tin về từ khóa khớp
            }
        }
        Collections.sort(listMatch);        // Sắp xếp danh sách các kết quả khớp theo vị trí đầu tiên từ khóa xuất hiện
        return listMatch;                   // Trả về danh sách các kết quả khớp
    }

    public String getSearchPhase() {
        return searchPhase;
    }

    public void setSearchPhase(String searchPhase) {
        this.searchPhase = searchPhase;
    }
}
