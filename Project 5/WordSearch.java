import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Deque;
import java.util.Scanner;
import java.lang.Object;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.lang.Math;

/**
 *
 * @author Dean Hendrix (dh@auburn.edu)
 * @author YOUR NAME (you@auburn.edu)
 *
 */
public class WordSearch implements WordSearchGame {

   private TreeSet<String> lexicon;
   private String[][] letters;
   private String[] array;
   private boolean[][] visited;
   private SortedSet<String> validWords;
   private List<Integer> path;
   private List<Integer> actualPath;
   private SortedSet<String> words;
   private int minLength;
   private int length;
   
   
   public WordSearch()
   {
      lexicon = new TreeSet<String>();
      path = new ArrayList<Integer>();
      validWords = new TreeSet<String>();
      actualPath = new ArrayList<Integer>();
      letters = new String[4][4];
      length = 3;
      letters[0][0] = "E"; 
      letters[0][1] = "E"; 
      letters[0][2] = "C"; 
      letters[0][3] = "A"; 
      letters[1][0] = "A"; 
      letters[1][1] = "L"; 
      letters[1][2] = "E"; 
      letters[1][3] = "P"; 
      letters[2][0] = "H"; 
      letters[2][1] = "N"; 
      letters[2][2] = "B"; 
      letters[2][3] = "O"; 
      letters[3][0] = "Q"; 
      letters[3][1] = "T"; 
      letters[3][2] = "T"; 
      letters[3][3] = "Y";    
   }

  /**
    * Loads the lexicon into a data structure for later use. 
    * 
    * @param fileName A string containing the name of the file to be opened.
    * @throws IllegalArgumentException if fileName is null
    * @throws IllegalArgumentException if fileName cannot be opened.
    */
   public void loadLexicon(String fileName)
   {
      if (fileName == null)
      {
         throw new IllegalArgumentException();
      }
      lexicon = new TreeSet<String>();
      try {
         Scanner s = new Scanner(new BufferedReader(new FileReader(new File(fileName))));
         while (s.hasNext()) {
            String str = s.next();
            lexicon.add(str.toLowerCase());
            s.nextLine();
         }
      }
      catch (Exception e) {
         throw new IllegalArgumentException("Error loading word list: " + fileName + ": " + e);
      }
   }
   
   /**
    * Stores the incoming array of Strings in a data structure that will make
    * it convenient to find words.
    * 
    * @param letterArray This array of length N^2 stores the contents of the
    *     game board in row-major order. Thus, index 0 stores the contents of board
    *     position (0,0) and index length-1 stores the contents of board position
    *     (N-1,N-1). Note that the board must be square and that the strings inside
    *     may be longer than one character.
    * @throws IllegalArgumentException if letterArray is null, or is  not
    *     square.
    */
   public void setBoard(String[] letterArray)
   {
      // if (letterArray == null || Math.sqrt(letterArray.length) % 1 > 0)
      // {
         // throw new IllegalArgumentException();
      // }
      // length = (int) Math.sqrt(letterArray.length);
      // array = letterArray;
      // letters = new String[letterArray.length - 1][letterArray.length - 1];
      // for (int i = 0; i < letterArray.length; i++)
      // {
         // for (int j = 0; j < letterArray.length; j++)
         // {
            // letters[i][j] = letterArray[(j * letterArray.length) + 1];
         // }
      // }
      if (letterArray == null || Math.sqrt(letterArray.length) % 1 > 0)
      {
         throw new IllegalArgumentException();
      }
      length = (int) Math.sqrt(letterArray.length);
      array = letterArray;
      visited = new boolean[length][length];
      letters = new String[letterArray.length][letterArray.length];
      int r = 0;
      for (int i = 0; i < length; i++)
      {
         for (int j = 0; j < length; j++)
         {
            letters[i][j] = letterArray[r++];
         }
      }
   }
   
   /**
    * Creates a String representation of the board, suitable for printing to
    *   standard out. Note that this method can always be called since
    *   implementing classes should have a default board.
    */
   public String getBoard()
   {
      String out = letters.toString();
      return out;
   }
   
   /**
    * Retrieves all scorable words on the game board, according to the stated game
    * rules.
    * 
    * @param minimumWordLength The minimum allowed length (i.e., number of
    *     characters) for any word found on the board.
    * @return java.util.SortedSet which contains all the words of minimum length
    *     found on the game board and in the lexicon.
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public SortedSet<String> getAllScorableWords(int minimumWordLength)
   {
      if (minimumWordLength < 1)
      {
         throw new IllegalArgumentException();
      }
      if (lexicon.isEmpty())
      {
         throw new IllegalStateException();
      }
      //length = (int) Math.sqrt(array.length);
      validWords.clear();
      minLength = minimumWordLength;
      for (int i = 0; i < length; i++) {
         for (int j = 0; j < length; j++) {
            locateWord(letters[i][j], i, j);
         }
      }
      return validWords;
   }
   
   public void locateWord(String word, int x, int y) {
      if (isValidWord(word) && word.length() >= minLength) {
         validWords.add(word.toUpperCase());
      }
      if (!isValidPrefix(word)) {
         return;
      }
      visited[x][y] = true;
      for (int i = -1; i <= 1; i++) {
         for (int j = -1; j <= 1; j++) {
            if ((x + i) <= ((int) length - 1)
               && (y + j) <= ((int) length - 1)
               && (x + i) >= 0 && (y + j) >= 0 && !visited[x + i][y + j]) {
               visited[x + i][y + j] = true;
               locateWord(word + letters[x + i][y + j], x + i, y + j);
               visited[x + i][y + j] = false;
            }
         }
      }
      visited[x][y] = false;
   }
   
 /**
   * Computes the cummulative score for the scorable words in the given set.
   * To be scorable, a word must (1) have at least the minimum number of characters,
   * (2) be in the lexicon, and (3) be on the board. Each scorable word is
   * awarded one point for the minimum number of characters, and one point for 
   * each character beyond the minimum number.
   *
   * @param words The set of words that are to be scored.
   * @param minimumWordLength The minimum number of characters required per word
   * @return the cummulative score of all scorable words in the set
   * @throws IllegalArgumentException if minimumWordLength < 1
   * @throws IllegalStateException if loadLexicon has not been called.
   */  
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength)
   {
      if (minimumWordLength < 1)
      {
         throw new IllegalArgumentException();
      }
      if (lexicon.isEmpty())
      {
         throw new IllegalStateException();
      }
      int score = 0;
      for (String pre : words)
      {
         if (isOnBoard(pre) != null)
         {
            if (pre.length() >= minimumWordLength)
            {
               if (isValidWord(pre))
               {
                  int above = pre.length() - minimumWordLength;
                  score = score + above + 1;
               }
            }
         }
      }
      return score;
   }
   
   /**
    * Determines if the given word is in the lexicon.
    * 
    * @param wordToCheck The word to validate
    * @return true if wordToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidWord(String wordToCheck)
   {
      if (wordToCheck == null)
      {
         throw new IllegalArgumentException();
      }
      if (lexicon.isEmpty())
      {
         throw new IllegalStateException();
      }
      // for (String str : lexicon)
      // {
         // if (str.equals(wordToCheck))
         // {
            // return true;
         // }
      // }
      if (lexicon.contains(wordToCheck.toLowerCase()))
      {
         return true;
      }
      return false;
   }
   
   /**
    * Determines if there is at least one word in the lexicon with the 
    * given prefix.
    * 
    * @param prefixToCheck The prefix to validate
    * @return true if prefixToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if prefixToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidPrefix(String prefixToCheck)
   {
      if (prefixToCheck == null)
      {
         throw new IllegalArgumentException();
      }
      if (lexicon == null)
      {
         throw new IllegalStateException();
      }
      // for (String str : lexicon)
      // {
         // if (str.substring(0, prefixToCheck.length()).equals(prefixToCheck))
         // {
            // return true;
         // }
      // }
      //prefixToCheck = prefixToCheck.toLowerCase();
      //return lexicon.ceiling(prefixToCheck).toLowerCase().startsWith(prefixToCheck) || lexicon.ceiling(prefixToCheck).toLowerCase().equals(prefixToCheck);
      prefixToCheck = prefixToCheck.toLowerCase();
      String word = lexicon.ceiling(prefixToCheck);
      if (word == null)
      {
         return false;
      }
      word = word.toLowerCase();
      if (word != null) {
         return word.startsWith(prefixToCheck) || word.equals(prefixToCheck);
      }
      return false;
   }
       
   /**
    * Determines if the given word is in on the game board. If so, it returns
    * the path that makes up the word.
    * @param wordToCheck The word to validate
    * @return java.util.List containing java.lang.Integer objects with  the path
    *     that makes up the word on the game board. If word is not on the game
    *     board, return an empty list. Positions on the board are numbered from zero
    *     top to bottom, left to right (i.e., in row-major order). Thus, on an NxN
    *     board, the upper left position is numbered 0 and the lower right position
    *     is numbered N^2 - 1.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public List<Integer> isOnBoard(String wordToCheck)
   {
      if (wordToCheck == null)
      {
         throw new IllegalArgumentException();
      }
      if (lexicon.isEmpty())
      {
         throw new IllegalStateException();
      }
      path.clear();
      actualPath.clear();
      for (int i = 0; i < (int) length; i++) {
         for (int j = 0; j < length; j++) {
            if (Character.toUpperCase(letters[i][j].charAt(0)) == Character.toUpperCase(wordToCheck.charAt(0))) {
               int value = j + (i * length);
               path.add(value);
               recursion(wordToCheck, letters[i][j], i, j);
               if (!actualPath.isEmpty()) {
                  return actualPath;
               }
               path.clear();
               actualPath.clear();
            }
         }
      }
      return path;
   }
      
   private void recursion(String wordToCheck, String current, int x, int y)
   {
      visited[x][y] = true;
      if (!(isValidPrefix(current))) {
         return;
      }
      if (current.toUpperCase().equals(wordToCheck.toUpperCase())) {
         actualPath = new ArrayList<Integer>(path);
         return;
      }
      for (int i = -1; i <= 1; i++) {
         for (int j = -1; j <= 1; j++) {
            if (current.equals(wordToCheck)) {
               return;
            }
            if ((x + i) <= (length - 1)
               && (y + j) <= (length - 1)
               && (x + i) >= 0 && (y + j) >= 0 && !visited[x + i][y + j]) {
               visited[x + i][y + j] = true;
               int value = (x + i) * length + y + j;
               path.add(value);
               recursion(wordToCheck, current + letters[x + i][y + j], x + i, y + j);
               visited[x + i][y + j] = false;
               path.remove(path.size() - 1);
            }
         }
      }
      visited[x][y] = false;
      return;
   }



}