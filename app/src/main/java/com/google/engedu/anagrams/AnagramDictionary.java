/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    ArrayList<String> listOfWords = new ArrayList<String>();

    HashSet<String> wordset = new HashSet<>();
    HashMap<String , ArrayList<String>> sortedStringToAnagrams =new HashMap<>();
    ArrayList<String > minStrings = new HashSet<>();
    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
        String word = line.trim();
//            listOfWords.add(word);
        String sortedString = giveSortedString(word);
            wordset.add(sortedString);
        if(!sortedStringToAnagrams.containsKey(sortedString)){
            sortedStringToAnagrams.put(sortedString,new ArrayList<String>());
        }
        sortedStringToAnagrams.get(sortedString).add(word);
        }
    for(Map.Entry<String , ArrayList<String >> e: sortedStringToAnagrams.entrySet()){
            String key = e.getKey();
            if( e.getValue().size()>=MIN_NUM_ANAGRAMS && key.length() ==5){
                minStrings.add(key);
            }
    }
    }

    public boolean isGoodWord(String word, String base) {
        if (word.contains(base)) {
            return false;
        }
        if (wordset.contains(word)) {
            return true;
        }
        return false;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();

        for (String word: listOfWords ){
            //Log.e(tag:"Debug", msg giveSortedString(word)+ targetWord +" " targetword + " " +give )
            if(giveSortedString(word).equals(giveSortedString(targetWord))){
                result.add(word);
            }
        }
        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {

        ArrayList<String> result = new ArrayList<String>();
        for(int i=0;i<26;++i)
        {
            String newWord = word + (char)('a'+i);
            String sortedNewString = giveSortedString(newWord);

            if(sortedStringToAnagrams.containsKey(sortedNewString)){
            ArrayList<String > listWords = sortedStringToAnagrams.get(sortedNewString);
            for(String listWord: listWords){
                result.add(listWord);
            }
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        Random rand =new Random();
        int randNum =  rand.nextInt(minStrings.size());
        int count=0;
        while(true) {
            if (count == randNum) {
                return minStrings.get(randNum);
            }
        }
    }
    public String giveSortedString(String str) {
        char arr[] = str.toCharArray();
        Arrays.sort(arr);
//        return arr.toString();
        return new String( arr);
    }
}
