package a1_2001040178;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Word {
        private String word;
        public Word(){
            this.word = null;
        }
        public static Set<String> stopWords = new HashSet<String>();



        public boolean isKeyword(){
            String regex1 = "[a-zA-Z]{1}"; //at least one alphabeta
            String regex2 = "[0-9]{1}"; //no number
            String regex3 = "\\s"; //no space
            Pattern pattern = Pattern.compile(regex1);
            Matcher matcher = pattern.matcher(this.word);
            Pattern pattern2 = Pattern.compile(regex2);
            Matcher matcher2 = pattern2.matcher(this.word);
            Pattern pattern3 = Pattern.compile(regex3);
            Matcher matcher3 = pattern3.matcher(this.word);
            if(!matcher2.find()&&!matcher3.find()){
                if(matcher.find()&&!stopWords.contains(this.word.toLowerCase())){
                    return true;
                }
            }else return false;

            return false;
        }
        public String getPrefix(){
            Word thisWord = Word.createWord(this.word);
            String regex = "^[\"(«<]";
            String regex3 = "^\'-";
            Pattern pattern3 = Pattern.compile(regex3);
            Matcher matcher3 = pattern3.matcher(this.word);
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(this.word);
            if(matcher3.find()){
                return "";
            }
            if(matcher.find()&&thisWord.isKeyword()){
                return Character.toString(this.word.charAt(0));
            }
            return "";

        }
        public String getSuffix(){
            Word thisWord = Word.createWord(this.word);
            String regex1 = "[^a-zA-Z0-9]$";
            String regex2 = "\'";
            String regex3 = "^\'";
            Pattern pattern1 = Pattern.compile(regex1);
            Pattern pattern2 = Pattern.compile(regex2);
            Pattern pattern3 = Pattern.compile(regex3);
            Matcher matcher3 = pattern3.matcher(this.word);
            Matcher matcher1 = pattern1.matcher(this.word);
            Matcher matcher2 = pattern2.matcher(this.word);
            if(matcher3.find()){
                return "";
            }
            if(matcher2.find()&&thisWord.isKeyword()){
                return this.word.substring(this.word.indexOf("\'"));
            }
            if(matcher1.find()&&thisWord.isKeyword()){
                int index=0;
                for(int i = this.word.length()-1;i>=0;i--){
                    if(Character.isLetter(this.word.charAt(i))){
                        index= i;
                        break;
                    }
                }
                return this.word.substring(index+1);
            }
            return "";
        }

        public String getText(){
            Word thisWord = Word.createWord(this.word);
            String prefix = thisWord.getPrefix();
            String suffix = thisWord.getSuffix();
            if(!thisWord.isKeyword()){
                return this.word;
            }
            if(prefix!=null){
                if(suffix!=null){
                    return this.word.substring(prefix.length(), this.word.length()-suffix.length());
                }else{
                    return this.word.substring(prefix.length());
                }
            }else{
                if(suffix!=null){
                    return this.word.substring(0, this.word.length()-suffix.length());
                } else{
                    return this.word;
                }
            }
        }

        public boolean equals(Object o){
            Word thisWord = Word.createWord(this.word);
            if(thisWord.getText().toLowerCase().equals(((Word) o).getText().toLowerCase())){
                return true;
            }else return  false;
        }

        public String toString(){
            return this.word;
        }

        public static Word createWord(String rawText){
            Word newWord = new Word();
            newWord.word = rawText;
            return newWord;
        }

        public static boolean loadStopWords(String fileName) throws FileNotFoundException {
            try {
                File f = new File(fileName);
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String sw;
                while ((sw = br.readLine()) != null) {
                    stopWords.add(sw);
                }
            }catch(Exception e){
                return false;
            }
            return true;
        }
    }

