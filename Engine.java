package a1_2001040178;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Engine {
    private Doc[] ds;       // Mảng chứa các đối tượng Doc (tài liệu)
    // Phương thức loadDocs dùng để tải các tài liệu từ một thư mục
    public int loadDocs(String dirname) {
        int documentCount = 0;      // Biến đếm số tài liệu được tải thành công
        File directory = new File(dirname);     // Tạo đối tượng File trỏ đến thư mục chứa các tài liệu
        File[] fileArray = directory.listFiles();       // Lấy danh sách các file trong thư mục

        // Nếu thư mục trống hoặc không có file nào, trả về số tài liệu (0)
        if (fileArray == null) {
            return documentCount;
        }

        Doc[] documentObjects = new Doc[fileArray.length];       // Khởi tạo mảng Doc với kích thước tương ứng số lượng file
        // Duyệt qua từng file trong thư mục
        for (int index = 0; index < fileArray.length; index++) {
            File currentFile = fileArray[index];        // Lấy file hiện tại
            StringBuilder fileContent = new StringBuilder();         // Tạo chuỗi dùng để chứa nội dung file

            // Kiểm tra nếu file là file .txt
            if (currentFile.isFile() && currentFile.getName().endsWith(".txt")) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(currentFile))) {
                    String line;
                    // Đọc từng dòng trong file và thêm vào fileContent
                    while ((line = bufferedReader.readLine()) != null) {
                        fileContent.append(line).append("\n");      // Append dòng vừa đọc và xuống dòng
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Nếu file có nội dung, tạo đối tượng Doc từ nội dung file và thêm vào mảng documentObjects
            if (!fileContent.isEmpty()) {
                documentObjects[index] = new Doc(fileContent.toString().trim());   // Tạo đối tượng Doc từ nội dung file
                documentCount++;        // Tăng biến đếm tài liệu
            }
        }
        return documentCount;       // Trả về số tài liệu đã tải
    }

    // Phương thức getDocs trả về mảng tài liệu đã nạp
    public Doc[] getDocs() {
            return this.ds; // Trả về mảng Doc được lưu trong biến ds
    }


    // Phương thức search dùng để tìm kiếm các tài liệu khớp với một truy vấn (Query)
    public List<Result> search(Query q) {
        List<Result> rList = new ArrayList<>();      // Danh sách các kết quả tìm kiếm

        // Duyệt qua từng tài liệu trong mảng tài liệu
        for(Doc doc: getDocs()) {
            List<Match> mList = q.matchAgainst(doc);    // So khớp truy vấn với tài liệu

            // Nếu có kết quả khớp, tạo một đối tượng Result và thêm vào danh sách kết quả
            if(!mList.isEmpty()) {
                Result r = new Result(doc, mList);  // Tạo đối tượng kết quả chứa tài liệu và các kết quả khớp
                rList.add(r);       // Thêm vào danh sách kết quả
            }
        }
        // Sắp xếp danh sách kết quả theo thứ tự giảm dần
        rList.sort(Comparator.reverseOrder());
        return rList;  // Trả về danh sách kết quả đã sắp xếp
    }

    // Phương thức htmlResult chuyển đổi danh sách kết quả tìm kiếm thành HTML
    public String htmlResult(List<Result> rs) {
        StringBuilder sb = new StringBuilder();     // Chuỗi dùng để chứa HTML

        // Duyệt qua từng kết quả trong danh sách
        for (Result r : rs) {
            String html = r.htmlHighlight();        // Lấy HTML có phần từ được tô sáng từ kết quả
            html = html.replaceAll("\n","");     // Loại bỏ các ký tự xuống dòng
            sb.append(html);    // Thêm HTML vào chuỗi kết quả
        }
        return sb.toString();   // Trả về chuỗi HTML cuối cùng
    }
}
