import java.io.*; 
import java.util.*;
import java.util.Scanner;
public class reverseArray_week3Sol{
    public static void main(String[] args){
        //code to read in string, and then show output
        Scanner s = new Scanner(System.in);
        System.out.println("Input your string:");
        char[] a = s.next().toCharArray(); 
        int length = a.length;
        reverse(a, 0, length-1);
        System.out.println("" + new String(a));
    }

    public static void reverse(char arr[],  int start, int end){

        //start and end indices are initially set to the extremes of the array
        char temp;
        while (start < end)  {
            //Next three lines swap the first and the last letter of arr in first iteration
            temp = arr[start]; 
            arr[start] = arr[end]; 
            arr[end] = temp;

            //Next two lines change the pointers as shown in the picture below
            start++;
            end--;

            //in the next iteration, the next inner extremes get swapped
        }
    }
}