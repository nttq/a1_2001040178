package a1_2001040178;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Word {
    private String text;
    private String suffix;
    private String prefix;
    static Set<String> stopWords;
    // Constructor khởi tạo đối tượng Word với tiền tố, text và hậu tố
    public Word(String t, String pre, String suf) {
        this.prefix = pre;
        this.text = t;
        this.suffix = suf;
    }
    // Getter trả về tiền tố
    public String getPrefix() {
        return prefix;
    }
    // Getter trả về hậu tố
    public String getSuffix() {
        return suffix;
    }
    // Getter trả về text
    public String getText() {
        return text;
    }
    // Phương thức kiểm tra xem từ có phải là từ khóa không
    public boolean isKeyword() {
        String t = getText();
        // Kiểm tra từ có rỗng hoặc null không
        if (t == null || t.isEmpty()) {
            return false;
        }
        // Duyệt từng ký tự trong từ và kiểm tra tính hợp lệ (chỉ chứa chữ cái, dấu nháy đơn, hoặc dấu gạch ngang)
        for (char c : t.toCharArray()) {
            if (!Character.isLetter(c) && c != '\'' && c != '-') {
                return false;
            }
        }
        // Kiểm tra từ có nằm trong danh sách stopWords không (nếu có, không phải từ khóa)
        for (String w : stopWords) {
            if (t.equalsIgnoreCase(w)) {
                return false;
            }
        }
        return true;        // Nếu không có vấn đề nào, từ này là từ khóa
    }
    // Phương thức so sánh hai đối tượng Word có bằng nhau không (so sánh không phân biệt hoa thường)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word = (Word) o;
        return getText().equalsIgnoreCase(word.getText());
    }
    // Phương thức tĩnh để tải danh sách stopWords từ một file
    public static boolean loadStopWords(String fileName) {
        stopWords = new HashSet<>();        // Khởi tạo tập hợp stopWords
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String stopWord;
            // Đọc từng dòng trong file và thêm vào stopWords
            while ((stopWord = br.readLine()) != null) {
                stopWords.add(stopWord);
            }
        } catch (IOException e) {
            return false;       // Nếu có lỗi, trả về false
        }
        return true;        // Đọc thành công, trả về true
    }

    // Phương thức tĩnh để tạo một đối tượng Word từ chuỗi thô
    public static Word createWord(String rawText) {
        if (rawText == null) return null;       // Chuỗi null thì trả về null
        if (rawText.isEmpty()) return new Word("", "", "");     // Chuỗi rỗng thì trả về Word rỗng

        int i = 0;
        int endPrefix = -1;

        // Tìm ký tự chữ cái đầu tiên để xác định tiền tố
        while (i < rawText.length()) {
            char c = rawText.charAt(i);
            if (Character.isLetter(c)) {
                endPrefix = i;
                break;
            }
            i++;
        }
        // Nếu không tìm thấy chữ cái, thì coi toàn bộ là tiền tố
        if (endPrefix == -1) return new Word("", rawText, "");

        String prefix = rawText.substring(0, endPrefix);

        // Kiểm tra tính hợp lệ của tiền tố
        for (char c : prefix.toCharArray()) {
            if (!Character.isLetter(c) && !isValidNonLetter(c)) {
                return new Word("", rawText, "");
            }
        }

        int j = rawText.length() - 1;
        int startSuffix = -1;

        // Tìm ký tự chữ cái cuối cùng để xác định hậu tố
        while (j >= 0) {
            char c = rawText.charAt(j);
            if (Character.isLetter(c)) {
                startSuffix = j + 1;
                break;
            }
            j--;
        }
        // Nếu không tìm thấy chữ cái, thì coi toàn bộ là hậu tố
        if (startSuffix == -1) return new Word("", rawText, "");

        String text = rawText.substring(endPrefix, startSuffix);
        String suffix = rawText.substring(startSuffix);

        // Điều chỉnh từ và hậu tố nếu trong từ có dấu nháy đơn
        if (text.contains("'") && !(text.endsWith("'t") && !text.endsWith("'T"))) {
            suffix = text.substring(text.indexOf("'")) + suffix;
            text = text.substring(0, text.indexOf("'"));
        }

        // Kiểm tra tính hợp lệ của từ chính
        for (char c : text.toCharArray()) {
            if (!Character.isLetter(c) && c != '\'' && c != '-') {
                return new Word("", rawText, "");
            }
        }

        // Kiểm tra tính hợp lệ của hậu tố
        for (char c : suffix.toCharArray()) {
            if (!Character.isLetter(c) && !isValidNonLetter(c)) {
                return new Word("", rawText, "");
            }
        }

        return new Word(prefix, text, suffix); // Trả về đối tượng Word đã được tạo
    }
    // Phương thức kiểm tra tính hợp lệ của các ký tự không phải chữ cái trong tiền tố và hậu tố
    private static boolean isValidNonLetter(char c) {
        return (c == '\'' || c == '-' ||
                c == '!' || c == '"' || c == '(' || c == ')' ||
                c == ',' || c == '.' || c == ':' || c == ';' ||
                c == '>' || c == '<' || c == '«' || c == '»' ||
                c == '?' || c == '{' || c == '}');
    }

    // Phương thức chuyển đối tượng Word thành chuỗi (từ, tiền tố, và hậu tố)
    @Override
    public String toString() {
        return getPrefix() + getText() + getSuffix();
    }
}
