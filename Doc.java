package a1_2001040178;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doc {
    private final List<Word> title;
    private final List<Word> body;

    public String content;


    //constructor
    public Doc(String content) {
        this.title = new ArrayList<>();
        this.content = content;
        this.body = new ArrayList<>();
        if (!content.isEmpty() && content != null) {
            Scanner sc = new Scanner(content);
            String title = sc.nextLine();
            String[] titleWords = title.split(" ");
            for (String word : titleWords) {
                this.title.add(Word.createWord(word));
            }
            String body = sc.nextLine();
            String[] bodyWords = body.split(" ");
            for (String word : bodyWords) {
                this.body.add(Word.createWord(word));
            }
        }
    }

    public List<Word> getTitle() {
        return title;
    }

    public List<Word> getBody() {
        return body;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Doc)) return false;
        Doc d = (Doc) object;
        List<Word> title = d.getTitle();
        if (title.size() != getTitle().size()) return false;
        for (int i = 0; i < title.size(); i++) {
            Word word = title.get(i);
            if (!word.equals(getTitle().get(i))) return false;
        }

        List<Word> body = d.getBody();
        if (body.size() != getBody().size()) return false;
        for (int i = 0; i < body.size(); i++) {
            Word word = body.get(i);
            if (!word.equals(getBody().get(i))) return false;
        }
        return true;
    }
}





