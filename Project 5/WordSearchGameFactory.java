
/**
 * Provides a factory method for creating word search games. 
 */
public class WordSearchGameFactory {

   /**
    * Returns an instance of a class that implements the WordSearchGame
    * interface.
    */
   public static WordSearchGame createGame() {
      // WordSearch game = new WordSearch();
      // game.loadLexicon("wordfiles/words.txt");
      // game.setBoard(new String[]{"E", "E", "C", "A", "A", "L", "E", "P", "H", 
         //                          "N", "B", "O", "Q", "T", "T", "Y"});
      // System.out.print("LENT is on the board at the following positions: ");
      // System.out.println(game.isOnBoard("LENT"));
      // System.out.print("POPE is not on the board: ");
      // System.out.println(game.isOnBoard("POPE"));
      // System.out.println("All words of length 6 or more: ");
      // System.out.println(game.getAllScorableWords(6));
      return new WordSearch();
   }

}
