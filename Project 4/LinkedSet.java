import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides an implementation of the Set interface.
 * A doubly-linked list is used as the underlying data structure.
 * Although not required by the interface, this linked list is
 * maintained in ascending natural order. In those methods that
 * take a LinkedSet as a parameter, this order is used to increase
 * efficiency.
 *
 * @author Dean Hendrix (dh@auburn.edu)
 * @author YOUR NAME (you@auburn.edu)
 *
 */
public class LinkedSet<T extends Comparable<T>> implements Set<T> {

   //////////////////////////////////////////////////////////
   // Do not change the following three fields in any way. //
   //////////////////////////////////////////////////////////

   /** References to the first and last node of the list. */
   Node front;
   Node rear;

   /** The number of nodes in the list. */
   int size;

   /////////////////////////////////////////////////////////
   // Do not change the following constructor in any way. //
   /////////////////////////////////////////////////////////

   /**
    * Instantiates an empty LinkedSet.
    */
   public LinkedSet() {
      front = null;
      rear = null;
      size = 0;
   }


   //////////////////////////////////////////////////
   // Public interface and class-specific methods. //
   //////////////////////////////////////////////////

   ///////////////////////////////////////
   // DO NOT CHANGE THE TOSTRING METHOD //
   ///////////////////////////////////////
   /**
    * Return a string representation of this LinkedSet.
    *
    * @return a string representation of this LinkedSet
    */
   @Override
   public String toString() {
      if (isEmpty()) {
         return "[]";
      }
      StringBuilder result = new StringBuilder();
      result.append("[");
      for (T element : this) {
         result.append(element + ", ");
      }
      result.delete(result.length() - 2, result.length());
      result.append("]");
      return result.toString();
   }


   ///////////////////////////////////
   // DO NOT CHANGE THE SIZE METHOD //
   ///////////////////////////////////
   /**
    * Returns the current size of this collection.
    *
    * @return  the number of elements in this collection.
    */
   public int size() {
      return size;
   }

   //////////////////////////////////////
   // DO NOT CHANGE THE ISEMPTY METHOD //
   //////////////////////////////////////
   /**
    * Tests to see if this collection is empty.
    *
    * @return  true if this collection contains no elements, false otherwise.
    */
   public boolean isEmpty() {
      return (size == 0);
   }


   /**
    * Ensures the collection contains the specified element. Neither duplicate
    * nor null values are allowed. This method ensures that the elements in the
    * linked list are maintained in ascending natural order.
    *
    * @param  element  The element whose presence is to be ensured.
    * @return true if collection is changed, false otherwise.
    */
   public boolean add(T element) {
      if (element == null)
      {
         return false;
      }
      if (contains(element) == true)
      {
         return false;
      }
      Node n = new Node(element);
      Node current = front;
      if(this.isEmpty())
      {
         front = n;
         rear = n;
         size++;
         return true;
      }
      if (front.element.compareTo(element) > 0)
      {
         n.next = front;
         front.prev = n;
         front = n;
         size++;
         return true;
      }
      if (front.element.compareTo(element) < 0 && rear.element.compareTo(element) < 0)
      {
         rear.next = n;
         n.prev = rear;
         rear = n;
         size++;
         return true;
      }
      else {
         current = prevNode(element);
         if (current == rear)
         {
            current.next = n;
            n.prev = current;
            rear = n;
         }
         else
         {
            current.next.prev = n;
            n.next = current.next;
            current.next = n;
            n.prev = current;
         }
      }
      size++;
      return true;
   }
   
   private Node prevNode(T element) {
      Node n = front;
      while (n != null) {
         if (n.element.compareTo(element) > 0) {
            return n.prev;
         }
         n = n.next;
      }
      return n;
   }

   /**
    * Ensures the collection does not contain the specified element.
    * If the specified element is present, this method removes it
    * from the collection. This method, consistent with add, ensures
    * that the elements in the linked lists are maintained in ascending
    * natural order.
    *
    * @param   element  The element to be removed.
    * @return  true if collection is changed, false otherwise.
    */
   public boolean remove(T element) {
      if (this.contains(element))
      {
         Node p = front;
         while (p != null)
         {
            if (p.element == element)
            {
               if (p.element == front.element && p.element == rear.element)
               {
                  front = null;
                  rear = null;
                  size = 0;
                  return true;
               }
               if(p.element == front.element)
               {
                  front = p.next;
                  p.next.prev = null;
                  p = null;
                  size--;
                  return true;
               }
               if (p.element == rear.element)
               {
                  p.prev.next = null;
                  rear = p.prev;
                  size--;
                  return true;
               }
               else
               {
                  p.next.prev = p.prev;
                  p.prev.next = p.next;
                  p = null;
                  size--;
                  return true;
               }
            }
            else
            {
               p = p.next;
            }
         }
      }
      if (size == 0)
      {
         front = null;
         rear = null;
      }
      return false;
   }


   /**
    * Searches for specified element in this collection.
    *
    * @param   element  The element whose presence in this collection is to be tested.
    * @return  true if this collection contains the specified element, false otherwise.
    */
   public boolean contains(T element) {
      Node p = front;
      while (p != null)
      {
         if (p.element == element)
         {
            return true;
         }
         p = p.next;
      }
      return false;
   }


   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
   public boolean equals(Set<T> s) {
      Node n = front;
      while (n != null)
      {
         if (s.contains(n.element) == true && this.size() == s.size())
         {
            n = n.next;
         }
         else
         {
            return false;
         }
      }
      return true;
   }


   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
   public boolean equals(LinkedSet<T> s) {
      if(size == s.size() && complement(s).size() == 0) {
         return true;
      }
      return false;
   }


   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
   public Set<T> union(Set<T> s){
      if (s == null)
      {
         throw new NullPointerException();
      }
      LinkedSet<T> set = new LinkedSet<T>();
      Node n = front;
      while (n != null)
      {
         set.add(n.element);
         n = n.next;
      }
      Iterator<T> it = s.iterator();
      while (it.hasNext())
      {
         set.add(it.next());
      }
      return set;
   }


   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
   public Set<T> union(LinkedSet<T> s){
      LinkedSet<T> ls1 = new LinkedSet<T>();
      ls1.front = front;
      ls1.rear = rear;
      ls1.size = size;
      LinkedSet<T> ls2 = new LinkedSet<T>();
      ls2.front = s.front;
      ls2.rear = s.rear;
      ls2.size = s.size;
      LinkedSet<T> ls3 = new LinkedSet<T>();
      Node current1 = front;
      Node current2 = s.front;
      Node current3 = ls3.front;
   
      if (current1 == null) {
         return ls2;
      }
      else if (current2 == null) {
         return ls1;
      }
   
      if ((current1 != null) && (current2 != null)) {
         if ((current1.element.compareTo(current2.element)) < 0) {
            ls3.front = new Node(current1.element);
            ls3.rear = new Node(current1.element);
            current1 = current1.next;
         }
         else if ((current1.element.compareTo(current2.element)) > 0) {
            ls3.front = new Node(current2.element);
            ls3.rear = new Node(current2.element);
            current2 = current2.next;
         }
         else if ((current1.element.compareTo(current2.element)) == 0) {
            ls3.front = new Node(current1.element);
            ls3.rear = new Node(current1.element);
            current1 = current1.next;
            current2 = current2.next;
         }
         current3 = ls3.front;
         ls3.size++;
      }
   
      while ((current1 != null) || (current2 != null)) {
         if (current1 == null) {
            current3.next = new Node(current2.element);
            current2 = current2.next;
         }
         else if (current2 == null) {
            current3.next = new Node(current1.element);
            current1 = current1.next;
         }
         else if ((current1.element.compareTo(current2.element)) < 0) {
            current3.next = new Node(current1.element);
            current1 = current1.next;
         }
         else if ((current1.element.compareTo(current2.element)) > 0) {
            current3.next = new Node(current2.element);
            current2 = current2.next;
         }
         else if ((current1.element.compareTo(current2.element)) == 0) {
            current3.next = new Node(current1.element);
            current1 = current1.next;
            current2 = current2.next;
         }
         current3.next.prev = current3;
         current3 = current3.next;
         ls3.rear = current3;
         ls3.size++;
      }
      return ls3;
   }
   
      // Iterator<T> scan = s.iterator();
      // Iterator<T> it = this.iterator();
      // LinkedSet<T> set = new LinkedSet<T>();
      // Node index = set.front;
      // if (this.isEmpty())
      // {
         // set = s;
         // return set;
      // }
      // if (s.isEmpty())
      // {
         // set = this;
         // return set;
      // }
      // if (this.size() > s.size())
      // {
         // while(it.hasNext())
         // {
            // Node n = front;
            // Node p = s.front;
            // if (n.element.compareTo(p.element) < 0)
            // {
               // if (n == front && index == set.front)
               // {
                  // set.front = new Node(n.element);
                  // index = set.front;
                  // n = n.next;
               // }
               // else
               // {
                  // index.next = new Node(n.element);
                  // n = n.next;
                  // index = index.next;
               // }
            // }
            // if (n.element.compareTo(p.element) > 0)
            // {
               // if (p == s.front)
               // {
                  // set.front = new Node(p.element);
                  // p = p.next;
               // }
               // else
               // {
                  // index.next = new Node(n.element);
                  // n = n.next;
                  // index = index.next;
               // }
            // }
            // else
            // {
               // if (n == front && index == set.front)
               // {
                  // set.front = new Node(n.element);
                  // index = set.front;
                  // n = n.next;
                  // p = p.next;
               // }
               // else
               // {
                  // index.next = new Node(n.element);
                  // n = n.next;
                  // p = p.next;
                  // index = index.next;
               // }
            // }
            // it.next();
         // }
      // }
      // if (this.size() < s.size())
      // {
         // while(scan.hasNext())
         // {
            // Node n = front;
            // Node p = s.front;
            // if (n.element.compareTo(p.element) < 0)
            // {
               // if (n == front && index == set.front)
               // {
                  // set.front = new Node(n.element);
                  // index = set.front;
                  // n = n.next;
               // }
               // else
               // {
                  // index.next = new Node(n.element);
                  // n = n.next;
                  // index = index.next;
               // }
            // }
            // if (n.element.compareTo(p.element) > 0)
            // {
               // if (p == s.front)
               // {
                  // set.front = new Node(p.element);
                  // index = set.front;
                  // p = p.next;
               // }
               // else
               // {
                  // index.next = new Node(n.element);
                  // n = n.next;
                  // index = index.next;
               // }
            // }
            // else
            // {
               // if (n == front && index == set.front)
               // {
                  // set.front = new Node(n.element);
                  // index = set.front;
                  // n = n.next;
                  // p = p.next;
               // }
               // else
               // {
                  // index.next = new Node(n.element);
                  // n = n.next;
                  // p = p.next;
                  // index = index.next;
               // }
            // }
            // scan.next();
         // }
      // }
      // else
      // {
         // Node n = front;
         // Node p = s.front;
         // while(it.hasNext())
         // {
            // if (n.element.compareTo(p.element) < 0)
            // {
               // if (n == front && index == set.front)
               // {
                  // set.front = new Node(n.element);
                  // index = set.front;
                  // n = n.next;
                  // set.size++;
               // }
               // else
               // {
                  // index.next = new Node(n.element);
                  // n = n.next;
                  // index = index.next;
                  // set.size++;
               // }
            // }
            // else if (n.element.compareTo(p.element) > 0)
            // {
               // if (p == s.front)
               // {
                  // set.front = new Node(p.element);
                  // index = set.front;
                  // p = p.next;
                  // set.size++;
               // }
               // else
               // {
                  // index.next = new Node(n.element);
                  // n = n.next;
                  // index = index.next;
                  // set.size++;
               // }
            // }
            // else
            // {
               // if (set.isEmpty())
               // {
                  // set.front = new Node(n.element);
                  // set.rear = new Node(n.element);
                  // index = set.rear;
                  // n = n.next;
                  // p = p.next;
                  // set.size++;
               // }
               // else
               // {
                  // Node j = new Node(n.element);
                  // index.next = j;
                  // index.next.prev = index;
                  // n = n.next;
                  // p = p.next;
                  // index = index.next;
                  // set.rear = index;
                  // set.size++;
               // }
            // }
            // it.next();
         // }
      // }
      // return set;
   // }


   /**
    * Returns a set that is the intersection of this set and the parameter set.
    *
    * @return  a set that contains elements that are in both this set and the parameter set
    */
   public Set<T> intersection(Set<T> s) {
      LinkedSet<T> set = new LinkedSet<T>();
      Iterator<T> scan = s.iterator();
      if (this.isEmpty())
      {
         return set;
      }
      if (this.size() < s.size())
      {
         while (scan.hasNext())
         {
            if (this.contains(scan.next()))
            {
               set.add(scan.next());
            }
            scan.next();
         }
      }
      else
      {
         Node n = this.front;
         while (scan.hasNext())
         {
            if (s.contains(n.element))
            {
               set.add(n.element);
            }
            n = n.next;
            scan.next();
         }
      }
      return set;
   }

   /**
    * Returns a set that is the intersection of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    */
   public Set<T> intersection(LinkedSet<T> s) {
      Set<T> set1 = this.union(s);
      Set<T> set2 = this.complement(s);
      Set<T> set3 = s.complement(this);
      Set<T> set4 = set2.union(set3);
      Set<T> set5 = set1.complement(set4);
      return set5;
   }


   /**
    * Returns a set that is the complement of this set and the parameter set.
    *
    * @return  a set that contains elements that are in this set but not the parameter set
    */
   public Set<T> complement(Set<T> s) {
      Node n = this.front;
      LinkedSet<T> set = new LinkedSet<T>();
      while (n != null)
      {
         if (!s.contains(n.element))
         {
            set.add(n.element);
         }
         n = n.next;
      }
      return set;
   }


   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    */
   public Set<T> complement(LinkedSet<T> s) {
      Node n = this.front;
      LinkedSet<T> set = new LinkedSet<T>();
      Node index = set.front;
      Node p = s.front;
      if (this.isEmpty())
      {
         return set;
      }
      if (s.isEmpty())
      {
         return this;
      }
      while (n != null)
      {
         if (!s.contains(n.element))
         {
            if (set.isEmpty())
            {
               set.front = new Node(n.element);
               set.rear = new Node(n.element);
               index = set.front;
               set.size++;
            }
            else
            {
               Node j = new Node(n.element);
               index.next = j;
               j.prev = index;
               index = index.next;
               set.rear = index;
               set.size++;
            }
         }
         n = n.next;
      }
      return set;
   }


   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in ascending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> iterator() {
      return new LinkedIterator();
   }
   
   private class LinkedIterator implements Iterator<T>{
      private Node current = front;
   
      public boolean hasNext()
      {
         return current != null;
      }
   
      public T next()
      {
         if (!hasNext())
            throw new NoSuchElementException();
         T result = current.element;
         current = current.next;
         return result;
      }
   
      public void remove()
      {
         throw new UnsupportedOperationException();
      }
   
   
   
   }


   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in descending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> descendingIterator() {
      return new desLinkedIterator();
   }
   
   private class desLinkedIterator implements Iterator<T>{
      private Node current = rear;
   
      public boolean hasNext()
      {
         return current != null && current.element != null;
      }
   
      public T next()
      {
         if (!hasNext())
         {
            throw new NoSuchElementException();
         }
         T result = current.element;
         current = current.prev;
         return result;
      }
   
      public void remove()
      {
         throw new UnsupportedOperationException();
      }
   
   
   
   }


   /**
    * Returns an iterator over the members of the power set
    * of this LinkedSet. No specific order can be assumed.
    *
    * @return  an iterator over members of the power set
    */
   public Iterator<Set<T>> powerSetIterator() {
      return new PowerSetIterator(rear,size);
   }

   private class PowerSetIterator implements Iterator<Set<T>> {
      private Node current;
      private int siz;
      private int count;
   
      public PowerSetIterator(Node rear,int size) {
         current = rear;
         siz = size;
         count = 0;
      }
   
      public boolean hasNext() {
         if (count == 0) {
            return true;
         }
         return ((count < (int) Math.pow(2,siz)) && (current != null));
      }
   
      public Set<T> next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         Set<T> result = new LinkedSet<T>();
         if (count == 0) {
            count++;
            return result;
         }
         String binary = Integer.toBinaryString(count);
         for (int i = binary.length() - 1; i >= 0; i--) {
            if (binary.charAt(i) == '1') {
               result.add(current.element);
            }
            current = current.prev;
         }
         count++;
         current = rear;
         return result;
      }
   
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }



   //////////////////////////////
   // Private utility methods. //
   //////////////////////////////

   // Feel free to add as many private methods as you need.

   ////////////////////
   // Nested classes //
   ////////////////////

   //////////////////////////////////////////////
   // DO NOT CHANGE THE NODE CLASS IN ANY WAY. //
   //////////////////////////////////////////////

   /**
    * Defines a node class for a doubly-linked list.
    */
   class Node {
      /** the value stored in this node. */
      T element;
      /** a reference to the node after this node. */
      Node next;
      /** a reference to the node before this node. */
      Node prev;
   
      /**
       * Instantiate an empty node.
       */
      public Node() {
         element = null;
         next = null;
         prev = null;
      }
   
      /**
       * Instantiate a node that containts element
       * and with no node before or after it.
       */
      public Node(T e) {
         element = e;
         next = null;
         prev = null;
      }
   }

}
