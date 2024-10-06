package a1_2001040178;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Word {
        private String text;
        private String suffix;
        private String prefix;
        static Set<String> stopWords;

        public Word(String t, String pre, String suf) {
            this.prefix = pre;
            this.text = t;
            this.suffix = suf;
        }
        public String getPrefix() {
        return prefix;
    }

        public String getSuffix() {
        return suffix;
    }

        public String getText() {
        return text;
    }

//        public static Set<String> stopWords = new HashSet<String>();


        public boolean isKeyword() {
            String t = getText();
            if (t == null || t.isEmpty()) {
                return false;
            }
            for (char c : t.toCharArray()) {
                if (!Character.isLetter(c) && c != '\'' && c != '-') {
                    return false;
                }
            }

            for (String w : stopWords) {
                if (t.equalsIgnoreCase(w)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Word)) return false;
            Word word = (Word) o;
            return getText().equalsIgnoreCase(word.getText());
        }

        public static boolean loadStopWords(String fileName) {
            stopWords = new HashSet<>();
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String stopWord;
                while ((stopWord = br.readLine()) != null) {
                    stopWords.add(stopWord);
                }
            } catch (IOException e) {
                return false;
            }
            return true;
        }


    public static Word createWord(String rawText) {
        if (rawText == null) return null;
        if (rawText.isEmpty()) return new Word("", "", "");

        int i = 0;
        int endPrefix = -1;

        // Find the first alphabet character for prefix
        while (i < rawText.length()) {
            char c = rawText.charAt(i);
            if (Character.isLetter(c)) {
                endPrefix = i;
                break;
            }
            i++;
        }

        if (endPrefix == -1) return new Word("", rawText, "");

        String prefix = rawText.substring(0, endPrefix);

        // Validate prefix
        for (char c : prefix.toCharArray()) {
            if (!Character.isLetter(c) && !isValidNonLetter(c)) {
                return new Word("", rawText, "");
            }
        }

        int j = rawText.length() - 1;
        int startSuffix = -1;

        // Find the last alphabet character for suffix
        while (j >= 0) {
            char c = rawText.charAt(j);
            if (Character.isLetter(c)) {
                startSuffix = j + 1;
                break;
            }
            j--;
        }

        if (startSuffix == -1) return new Word("", rawText, "");

        String text = rawText.substring(endPrefix, startSuffix);
        String suffix = rawText.substring(startSuffix);

        // Adjust text and suffix if there's an apostrophe in the text
        if (text.contains("'") && !(text.endsWith("'t") && !text.endsWith("'T"))) {
            suffix = text.substring(text.indexOf("'")) + suffix;
            text = text.substring(0, text.indexOf("'"));
        }

        // Validate text
        for (char c : text.toCharArray()) {
            if (!Character.isLetter(c) && c != '\'' && c != '-') {
                return new Word("", rawText, "");
            }
        }

        // Validate suffix
        for (char c : suffix.toCharArray()) {
            if (!Character.isLetter(c) && !isValidNonLetter(c)) {
                return new Word("", rawText, "");
            }
        }

        return new Word(prefix, text, suffix);
    }

    private static boolean isValidNonLetter(char c) {
        return (c == '\'' || c == '-' ||
                c == '!' || c == '"' || c == '(' || c == ')' ||
                c == ',' || c == '.' || c == ':' || c == ';' ||
                c == '>' || c == '<' || c == '«' || c == '»' ||
                c == '?' || c == '{' || c == '}');
    }


    @Override
        public String toString() {
        return getPrefix() + getText() + getSuffix();
    }
    }

