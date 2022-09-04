import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.lang.String;

/**
 * ArrayIterator.java
 * Provides iteration behavior over an array of elements.
 *
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2016-02-24
 *
 */


public class Term implements Comparable<Term> {
   private String query = "";
   private long weight = 0;
   /**
    * Initialize a term with the given query and weight.
    * This method throws a NullPointerException if query is null,
    * and an IllegalArgumentException if weight is negative.
    */
   public Term(String query, long weight) 
   { 
      this.query = query;
      this.weight = weight;
      if (query == null)
      {
         throw new NullPointerException();
      }
      if (weight < 0)
      {
         throw new IllegalArgumentException();
      }
      
   
   }

   /**
    * Compares the two terms in descending order of weight.
    */
   public static Comparator<Term> byDescendingWeightOrder() 
   { 
      return 
         new Comparator<Term>()
         {
            @Override
            public int compare(Term t1, Term t2)
            {
               if (t1.weight == t2.weight)
               {
                  return 0;
               }
               if (t1.weight < t2.weight)
               {
                  return 1;
               }
               else
               {
                  return -1;
               }
            }
         };
   
   
   }

   /**
    * Compares the two terms in ascending lexicographic order of query,
    * but using only the first length characters of query. This method
    * throws an IllegalArgumentException if length is less than or equal
    * to zero.
    */
   public static Comparator<Term> byPrefixOrder(int length) 
   { 
      if (length <= 0)
      {
         throw new IllegalArgumentException();
      }
      return 
         new Comparator<Term>()
         {
            @Override
            public int compare(Term t1, Term t2)
            {
               String sub1 = t1.query;
               String sub2 = t2.query;
               if (length > t1.query.length() || length > t2.query.length())
               {
                  sub1 = t1.query.substring(0, t1.query.length() - 1);
                  sub2 = t2.query.substring(0, t2.query.length() - 1);
               }
               else
               {
                  sub1 = t1.query.substring(0, length);
                  sub2 = t2.query.substring(0, length);
               }
               if (sub1.compareTo(sub2) < 0)
               {
                  return -1;
               }
               if (sub1.compareTo(sub2) > 0)
               {
                  return 1;
               }
               else{
                  return 0;
               }
            }
         };
   
   
   
   
   
   }

   /**
    * Compares this term with the other term in ascending lexicographic order
    * of query.
    */
   @Override
   public int compareTo(Term other) 
   { 
      // if (this.query.compareTo(other.query) < 0)
      // {
         // return -1;
      // }
      // if (this.query.compareTo(other.query) > 0)
      // {
         // return 1;
      // }
      // else
      // {
         // return 0;
      // }
      Comparator<Term> comp = other.byPrefixOrder(other.query.length());
      return comp.compare(this, other);
   
   
   
   }

   /**
    * Returns a string representation of this term in the following format:
    * query followed by a tab followed by weight
    */
   @Override
   public String toString(){ 
      return this.query + "\t" + String.valueOf(this.weight);  
   }
   
   

}