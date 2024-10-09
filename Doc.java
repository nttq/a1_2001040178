package a1_2001040178;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doc {
    private final List<Word> title;
    private final List<Word> body;

    public String content;


    // Constructor khởi tạo đối tượng Doc từ chuỗi nội dung
    public Doc(String content) {
        this.title = new ArrayList<>();
        this.content = content;
        this.body = new ArrayList<>();
        // Kiểm tra xem nội dung có rỗng hoặc null không
        if (!content.isEmpty() && content != null) {
            Scanner sc = new Scanner(content);
            // Đọc dòng đầu tiên là tiêu đề và tách các từ
            String title = sc.nextLine();
            String[] titleWords = title.split(" ");
            // Tạo đối tượng Word cho từng từ trong tiêu đề và thêm vào danh sách title
            for (String word : titleWords) {
                this.title.add(Word.createWord(word));
            }
            // Đọc dòng thứ hai là nội dung thân và tách các từ
            String body = sc.nextLine();
            String[] bodyWords = body.split(" ");
            // Tạo đối tượng Word cho từng từ trong thân bài và thêm vào danh sách body
            for (String word : bodyWords) {
                this.body.add(Word.createWord(word));
            }
        }
    }
    // Getter trả về danh sách từ trong tiêu đề
    public List<Word> getTitle() {
        return title;
    }
    // Getter trả về danh sách từ trong thân bài
    public List<Word> getBody() {
        return body;
    }

    // Phương thức so sánh hai đối tượng Doc xem có bằng nhau không
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;    // Nếu hai đối tượng trỏ tới cùng một vùng nhớ, trả về true
        if (!(object instanceof Doc)) return false; // Kiểm tra xem object có phải là một đối tượng Doc không
        Doc d = (Doc) object;
        // So sánh số lượng từ trong tiêu đề của hai đối tượng Doc
        List<Word> title = d.getTitle();
        if (title.size() != getTitle().size()) return false;
        // Duyệt qua từng từ trong tiêu đề và so sánh từng từ giữa hai đối tượng
        for (int i = 0; i < title.size(); i++) {
            Word word = title.get(i);
            if (!word.equals(getTitle().get(i))) return false;
        }
        // So sánh số lượng từ trong thân bài của hai đối tượng Doc
        List<Word> body = d.getBody();
        if (body.size() != getBody().size()) return false;
        // Duyệt qua từng từ trong thân bài và so sánh từng từ giữa hai đối tượng
        for (int i = 0; i < body.size(); i++) {
            Word word = body.get(i);
            if (!word.equals(getBody().get(i))) return false;
        }
        return true;        // Nếu tất cả các từ trong cả tiêu đề và thân bài đều bằng nhau, trả về true
    }
}




