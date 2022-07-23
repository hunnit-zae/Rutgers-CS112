// Rec 7 
// https://docs.google.com/presentation/d/11iKuYGomdl4eqI0FDSlz7g8yBxkkgvvnqAxHYsOeV7U/edit#slide=id.gf11c720fc2_0_1

// Question 1 
public int search(int[], nums, int target) {
    int low = 0, high = nums.length;

    while (low < high)
    {
        int mid = (low + high) / 2;
        int ele = num[mid] - target;
        
        if (ele == 0)
            return mid;

        if (ele < 0)
            low = mid + 1;
        else
            hi = mid;    // original hi = mid +1; but we already have +1 on the array list
    }
    return -1;
}

/* 
first thing first - find the mid  (hi + lo) / 2
                                  lo + ((hi - lo) / 2)  this for prevent overflow

How to calculate logn 
    0: n
    1: n/2
    2: n/4
    3: n/8 ...
    k: n/2^k

    n/2^k = log base2 n = k
*/

// Question 2 made on the test I think
/*
8
4
3
unsuccessful node is 4 because following the node path.
US node depth is 5 deepest node is answer

array access * node 
depth 1 = 1
depth 2 = 2
depth 3 = 4
depth 4 = 1
sum of this and divided by hi

*/
