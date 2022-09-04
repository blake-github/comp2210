import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @author   Blake Moore (bmm0066@auburn.edu)
* @author   Dean Hendrix (dh@auburn.edu)
* @version  9/1/2020
*
*/
public final class Selector {

   /**
    * Can't instantiate this class.
    *
    * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
    *
    */
   private Selector() { }


   /**
    * Selects the minimum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static <T> T min(Collection<T> c, Comparator<T> comp) throws IllegalArgumentException
   {
      if (c == null)
      {
         throw new IllegalArgumentException();
      }
      if (c.size() == 0)
      {
         throw new IllegalArgumentException();
      }
      // Object[] b = c.toArray();
      // Object[] d = c.toArray(b);
      // Object min = b[0];
      List a = new ArrayList[c];
      a = c.toArray(a);
      int min = a[0];
      int result = -1;
      
      for (Integer x : a)
      {
         if (comp.compare(x, min) < 0)
         {
            min = x;
         }
      }
      //for (int i = 0; i < a.length; i++)
      //{
         // if (min < a[i])
         // {
            // result = i;
         // }
         // if (min > a[i])
         // {
            // 
         // }
         // if (min == a[i])
         // {
            // result = 1;
         // }
      //}
      return min;
   }


   /**
    * Selects the maximum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int max(int[] a) throws IllegalArgumentException
   {
      if (a == null)
      {
         throw new IllegalArgumentException();
      }
      if (a.length == 0)
      {
         throw new IllegalArgumentException();
      }
      int max = a[0];
      int result = -1;
      for (Integer x : a)
      {
         if (x.compareTo(max) > 0)
         {
            max = x;
         }
      }
      return max;
   }


   /**
    * Selects the kth minimum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth minimum value. Note that there is no kth
    * minimum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmin(int[] a, int k) throws IllegalArgumentException
   {
      if (a == null || a.length == 0)
      {
         throw new IllegalArgumentException();
      }
      if (k < 1 || k > a.length)
      {
         throw new IllegalArgumentException();
      }
      int[] b = Arrays.copyOf(a, a.length);
      Arrays.sort(b);
      b = Arrays.stream(b).distinct().toArray();
      if (k > b.length)
      {
         throw new IllegalArgumentException();
      }
      int kmin = b[k - 1];
      return kmin;
   }


   /**
    * Selects the kth maximum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth maximum value. Note that there is no kth
    * maximum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmax(int[] a, int k) throws IllegalArgumentException
   {
      if (a == null || a.length == 0)
      {
         throw new IllegalArgumentException();
      }
      if (k < 1 || k > a.length)
      {
         throw new IllegalArgumentException();
      }
      int[] b = Arrays.copyOf(a, a.length);
      Arrays.sort(b);
      Selector.reverse(b);
      b = Arrays.stream(b).distinct().toArray();
      if (k > b.length)
      {
         throw new IllegalArgumentException();
      }
      int kmax = b[k - 1];
      return kmax;
   }


   /**
    * Returns an array containing all the values in a in the
    * range [low..high]; that is, all the values that are greater
    * than or equal to low and less than or equal to high,
    * including duplicate values. The length of the returned array
    * is the same as the number of values in the range [low..high].
    * If there are no qualifying values, this method returns a
    * zero-length array. Note that low and high do not have
    * to be actual values in a. This method throws an
    * IllegalArgumentException if a is null or has zero length.
    * The array a is not changed by this method.
    */
   public static int[] range(int[] a, int low, int high) throws IllegalArgumentException
   {
      if (a == null || a.length == 0)
      {
         throw new IllegalArgumentException();
      }
      int[] b = new int[0];
      for (int i = 0; i < a.length; i++)
      {
         if (a[i] >= low && a[i] <= high)
         {
            b = Selector.add(b, a[i]);
         }
      }
      return b;
   }


   /**
    * Returns the smallest value in a that is greater than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int ceiling(int[] a, int key) throws IllegalArgumentException
   {
      if (a == null || a.length == 0)
      {
         throw new IllegalArgumentException();
      }
      int[] b = Selector.range(a, key, 2147483647);
      int ceiling = Selector.min(b);
      return ceiling;
   }


   /**
    * Returns the largest value in a that is less than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int floor(int[] a, int key) throws IllegalArgumentException
   {
      if (a == null || a.length == 0)
      {
         throw new IllegalArgumentException();
      }
      int[] b = Selector.range(a, -2147483647, key);
      int ceiling = Selector.max(b);
      return ceiling;
   }
   
   /**
   * Returns the amount of distinct elements in array
   */
   private static int countDistinct(int [] a) throws IllegalArgumentException
   {
      if (a == null || a.length == 0)
      {
         throw new IllegalArgumentException();
      }
      int res = 1;
      for (int i = 0; i < a.length; i++)
      {
         for (int j = 0; j < i; j++)
         {
            if (a[i] == a[j])
            {
               break;
            }
            if (i ==j)
            {
               res++;
            }
         }
      }
      return res;
   }
   
   /**
   * Reverses the input array
   */
   private static void reverse(int[] a)
   {
      for (int i = 0; i < a.length / 2; i++)
      {
         int x = a[i];
         a[i] = a[a.length - i - 1];
         a[a.length - i - 1] = x;
      }
   }
   
   /**
   * Adds elements to an array
   */
   private static int[] add(int[] b, int x)
   {
      int[] result = new int[b.length + 1];
      for (int i = 0; i < b.length; i++)
      {
         result[i] = b[i];
      }
      result[result.length - 1] = x;
      return result;
   }

}
