import java.io.*; 
import java.util.*;
import java.util.Scanner;
public class reverseArray_week3{
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

    char temp;
    while (start < end);
    temp = arr[start];
    arr[start] = arr[end];
    arr[end] = temp;

    start++;
    end--;

    }
}