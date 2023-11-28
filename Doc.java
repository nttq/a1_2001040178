package a1_2001040178;

import java.util.ArrayList;
import java.util.List;

public class Doc {
    public String content;

    public Doc(String content) {
        this.content = content;
    }

    public Doc(byte[] textContent) {
    }

    //    public List<Word> getTitle() {
//        List<Word> listTitle = new ArrayList<>();
//        String[] list = this.content.split("\n")[0].split("\\s");
//        listTitle = Arrays.stream(list).map(Word::createWord).collect(Collectors.toList());
//        return listTitle;
//    }
//
//    public List<Word> getBody() {
//        List<Word> listBody = new ArrayList<>();
//        String[] list = this.content.split("\n")[1].split("\\s");
//        listBody = Arrays.stream(list).map(Word::createWord).collect(Collectors.toList());
//        return listBody;
//    }
    public List<Word> getTitle() {
        List<Word> wordList = new ArrayList<Word>();
        String[] line = this.content.split("\n");
        String title = line[0];
        String[] words = title.split("\\s");
        for (String word : words) {
            Word thisWord = Word.createWord(word);
            wordList.add(thisWord);
        }
        return wordList;
    }

    public List<Word> getBody() {
        List<Word> wordList = new ArrayList<Word>();
        String[] line = this.content.split("\n");
        String body = line[1];
        String[] words = body.split("\\s");
        for (String word : words) {
            Word thisWord = Word.createWord(word);
            wordList.add(thisWord);
        }
        return wordList;
    }
        public boolean equals(Object o){
            if(o==null){
                return false;
            }
            if(((Doc) o).getBody().equals(this.getBody()) && ((Doc) o).getTitle().equals(this.getTitle()) ){
                return true;
            }
            return false;
        }
    }

//    public boolean equals(Object obj) {
//        if( this == obj){
//            return true;
//        }
//       if(!(obj instanceof Doc)){
//               return false;
//           }
//       Doc doc = (Doc) obj;
//       return this.getTitle().equals(doc.getTitle())&&this.getBody().equals(doc.getBody());
//    }

