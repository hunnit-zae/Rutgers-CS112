import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class MaxPQ {
    //We'll be using an arraylist to handle resizing automatically
    private ArrayList<Integer> heap;

    //Constructor for empty heap
    public MaxPQ() {
        heap = new ArrayList<Integer>();
        heap.add(0); //We ignore index 0, just add a dummy value
    }

    //Constructor for preexisting arraylist (might not be heap yet)
    public MaxPQ(ArrayList<Integer> h) {
        heap = h;
        heap.add(0, 0); //We ignore index 0, just add a dummy value at the beginning
    }
    
    public int size() {
        // Index 0 is ignored, so the actual number of elements is 1 less
        return heap.size() - 1;
    }

    public int peek() {
        return heap.get(1);
    }

    public void swap(int i1, int i2) {
        int temp = heap.get(i1);
        heap.set(i1, heap.get(i2));
        heap.set(i2, temp);
    }

    //Print method to help with testing
    public void print(int index, String indent, boolean isRight, boolean isRoot) {
        System.out.print(indent);

        if (!isRoot) System.out.print(isRight ? "┣━R━ " : "┗━L━ ");
        else System.out.print("┗━━━ ");

        if (index >= heap.size()) {
            System.out.println("empty");
            return;
        }
        
        System.out.println(heap.get(index));

        if (2*index >= heap.size()) return;

        indent += isRight ? "┃    " : "     ";

        print(2*index+1, indent, true, false);
        print(2*index,  indent, false, false);
    }

    //Print wrapper method with no parameters
    public void print() {
        print(1, "", false, true);
        System.out.println();

        System.out.println("Array Storage: " + heap);
        System.out.println("Index 0 is ignored for the heap structure");
    }

    public void swim(int i){
        while (i > 1)
        {
            if(heap.get(i) > heap.get(i/2))
            {
                swap(i , i/2);
                i /= 2;
            }
        }
    }
    
    public void insert(int n){
        heap.add(n);
        swim(heap.size() - 1);
    }

    public void sink(int i) {

    }

    public boolean inMaxHeap(){
        for ~~;
        if i * 2 .= heapsize break;
        if heap(i*2) > heap(i) return
    
        if (i*2+1 >=heapsize) break;
        if heap(i*2) > heap(i) return

        return true;
    }

    public static ArrayList<Integer> topK(ArrayList<Integer> a , int k){
        
    }
}