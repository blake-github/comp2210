import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.lang.String;
import java.util.Arrays;


public class Autocomplete {
   Term[] array = new Term[0];
	/**
	 * Initializes a data structure from the given array of terms.
	 * This method throws a NullPointerException if terms is null.
	 */
   public Autocomplete(Term[] terms) 
   {
      this.array = terms;
      if (terms == null)
      {
         throw new NullPointerException();
      }
   
   }

	/** 
	 * Returns all terms that start with the given prefix, in descending order of weight. 
	 * This method throws a NullPointerException if prefix is null.
	 */
   public Term[] allMatches(String prefix)
   {
      if (prefix == null)
      {
         throw new NullPointerException();
      }
      int length = prefix.length();
      Comparator <Term> comparator = Term.byPrefixOrder(length);
      Term key = new Term(prefix, 853902);
      Arrays.sort(this.array);
      int first = BinarySearch.<Term>firstIndexOf(this.array, key, comparator);
      int last = BinarySearch.<Term>lastIndexOf(this.array, key, comparator);
      Term[] result = new Term[last - first + 1];
      int alias = first;
      for (int i = 0; i < last - first + 1; i++)
      {
         result[i] = this.array[alias];
         alias++;
      }
      Comparator<Term> weightComp = Term.byDescendingWeightOrder();
      //mergeSort(result, 0, this.array.length, weightComp);
      Arrays.sort(result, weightComp);
      return result;
   }
   
   
   private void mergeSort(Term[] a, int left, int right, Comparator<Term> comp)
   {
      if (right == left)
      {
         return;
      }
      int mid = left + (right - left) / 2;
      mergeSort(a, left, mid, comp);
      mergeSort(a, mid, right, comp);
      merge(a, left, mid, right, comp);
      return;
   }
   
   private void merge(Term[] a, int left, int mid, int right, Comparator<Term> comp)
   {
      Term[] aux = new Term[a.length];
      for (int k = left; k <= right; k++)
      {
         aux[k] = a[k];
      }
      int i = left;
      int j = mid + 1;
      for (int k = left; k <= right; k++)
      {
         if (i > mid)
         {
            a[k] = a[j++];
         }
         else if (j > right)
         {
            a[k] = aux[i++];
         }
         else if (comp.compare(aux[j], aux[i]) > 0)
         {
            a[k] = aux[j++];
         }
         else
         {
            a[k] = aux[i++];
         }
      }
   }

}