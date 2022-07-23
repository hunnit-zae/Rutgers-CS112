import java.io.*; 
import java.util.*;
import java.util.Scanner;
public class twosum_week3Sol{
    public static void main(String[] args){
        //code to read in input
        int length; 
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the number of elements you want to store: "); 

        //reading the number of elements from the that we want to enter  
        length=s.nextInt();  

        int[] intArray = new int[length]; 

        System.out.println("Enter the elements of the array: ");  
        for(int i=0; i<length; i++)  
        {  
            //reading array elements from the user   
            intArray[i]=s.nextInt();  
        }  

        System.out.println("Enter the target: "); 
        int target = s.nextInt(); 

        //running the method 
        int[] output = TwoSum(intArray, target); 

        System.out.println(output[0]);
        System.out.println(output[1]);  

        if(intArray[output[0]-1] + intArray[output[1]-1] == target){
            System.out.println("Success!");
        }
        else{
            System.out.println("ERROR! Elements at indices of output array do not match the target sum.");
        }
    }

    public static int[] TwoSum(int[] numbers, int target) {

        int[] a = new int[2];

        //initially i and j are set to the extremes of our numbers array
        int i = 0;
        int j = numbers.length - 1;

        while (i < j){ 
            if(numbers[i] + numbers[j] == target){ 
            //check if the sum of our numbers at current pointers is the target
            a[0] = i; 
            a[1] = j;
            break;
            } else if (numbers[i] + numbers[j] < target) {
                //if the sum is less than target, then we increase our smaller number which is at index i to i+1
                i++;
            } else {
                //else if the sum is greater than target, we decrease the bigger number which is at index j to j-1
                j--;
            }
        }
        //below is just for converting index 0 to 1 (1 is the position of value at index 0)
        //and same for every other index according to the problem specification
        a[0] += 1;
        a[1] += 1;
        return a;
    }
}
