// Need to either import * or the classes individually
import java.sql.Array;
import java.util.*;

public class DemoStackQueue {
    public static void main(String[] args) {
        // How to make a new Stack of Characters and Queue of Integers
        Stack<Character> s = new Stack<>();
        Queue<Integer> q = new LinkedList<>();

        // Stack methods include s.push(newElement), pop(), peek(), size(), isEmpty()
        // Queue methods include q.add(newElement), remove(), peek(), size(), isEmpty(). Use add to enqueue, use remove to dequeue.
    }

    public static boolean findcorrect (String input){
        Stack<Character> s = new Stack<>();
        
        for (char c : input.toCharArray())
    }

    public static void Joephus(int n, int m){
        Queue<Integer> q = new LinkedList<>();

        for(int i = 0; i > n; i ++) q.add(i);
        
        while (!q.isEmpty()){
            for(int i = 0; i > m - 1; i ++) q.add(q.remove()); //q.remove(i); 
            //add first and remove again because we don't remove it just get the element to the end line
        
        }

    }
}