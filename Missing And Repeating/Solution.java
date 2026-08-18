class Solution {
    ArrayList<Integer> findTwoElement(int arr[]) {
        // code here
        int n = arr.length;
        int xor = 0;
        for(int i=0;i<n;i++){
            xor ^=  arr[i]^(i+1);
        }

        int bit = xor & -xor;
        int a =0;
        int b= 0;
        for(int i=0;i<n;i++){
            if((arr[i] & bit) != 0){
                a ^= arr[i];
            }else{
                b ^= arr[i];
            }
            if(((i+1) & bit) != 0){
                a ^= (i+1);
            }else{
                b ^= (i+1);
            }
        }

        ArrayList<Integer> ans = new ArrayList<>();

        for(int num:arr){
            if(num == a){
                ans.add(a);
                ans.add(b);
                return ans;
            }
        }

        ans.add(b);
        ans.add(a);



        return ans;

    }
}


/*

class Solution {
    ArrayList<Integer> findTwoElement(int arr[]) {
        // code here
        int n = arr.length;

        long expectedSum = (long) n * (n + 1) / 2;
        long expectedSquareSum =
                (long) n * (n + 1) * (2L * n + 1) / 6;

        long actualSum = 0;
        long actualSquareSum = 0;

        for (int num : arr) {
            actualSum += num;
            actualSquareSum += (long) num * num;
        }

        // missing - repeating
        long diff = expectedSum - actualSum;

        // missing^2 - repeating^2
        long squareDiff = expectedSquareSum - actualSquareSum;

        // missing + repeating
        long sum = squareDiff / diff;

        long missing = (diff + sum) / 2;
        long repeating = sum - missing;

        ArrayList<Integer> ans = new ArrayList<>();
        ans.add((int) repeating);
        ans.add((int) missing);

        return ans;
    }
}





1. Brute force — Sorting

Example:


arr = [1, 2, 2, 4]


Expected:


1 2 3 4


After sorting, duplicate values become adjacent:


1 2 2 4
  ↑ ↑


So:


Arrays.sort(arr);

for (int i = 0; i < arr.length - 1; i++) {
    if (arr[i] == arr[i + 1]) {
        // repeating
    }
}


You can also determine the missing number while traversing.

Complexity:


Time  = O(n log n)
Space = O(1) / O(log n), depending on sorting implementation




2. Boolean/visited array — O(n) time

This is the approach shown in the transcript.

For:


arr = [1, 2, 2, 4]


Create:

present = [false, false, false, false]
          1      2      3      4

Traverse the array.

See `1`


present[1] = false


Make it true:


present[1] = true


See `2`


present[2] = false

Make it true.

See another `2`


present[2] = true


It's already true → 2 is repeating.

See `4`

Make:


present[4] = true


Finally:


present:

1 → true
2 → true
3 → false   ← missing
4 → true


So:


Repeating = 2
Missing   = 3


Java:


class Solution {
    public int[] findTwoElement(int arr[]) {
        int n = arr.length;

        boolean[] present = new boolean[n + 1];

        int repeating = -1;
        int missing = -1;

        for (int num : arr) {
            if (present[num]) {
                repeating = num;
            } else {
                present[num] = true;
            }
        }

        for (int i = 1; i <= n; i++) {
            if (!present[i]) {
                missing = i;
                break;
            }
        }

        return new int[]{repeating, missing};
    }
}


Complexity

Time  = O(n)
Space = O(n)


This is much easier to understand** than the mathematical approach.


3. Mathematical approach — O(n) time, O(1) space

This is the important part of the video.

Suppose:

arr = [1, 2, 2, 4]


Expected:

1, 2, 3, 4


Let's call:

missing  = X
repeating = Y


In this example:

X = 3
Y = 2


Step 1: Find X - Y

Expected sum:

1 + 2 + 3 + 4 = 10


Actual sum:

1 + 2 + 2 + 4 = 9


Therefore:

expectedSum - actualSum

= 10 - 9
= 1

= X - Y


So:

X - Y = 1




Step 2: Find X + Y

Expected square sum:
1² + 2² + 3² + 4²
= 30


Actual square sum:

1² + 2² + 2² + 4²
= 25


Difference:

30 - 25
= 5


Mathematically:
X² - Y² = 5


Using:

X² - Y² = (X + Y)(X - Y)


We already know:

X - Y = 1


Therefore:


(X + Y) × 1 = 5

X + Y = 5


Now we have:

X - Y = 1
X + Y = 5


Add them:

2X = 6

X = 3

Therefore:


Y = 5 - 3
  = 2


So:
Missing   = 3
Repeating = 2


Java implementation

For this approach, use `long` because the square sum can become large.

class Solution {
    public int[] findTwoElement(int arr[]) {
        int n = arr.length;

        long expectedSum = (long) n * (n + 1) / 2;
        long expectedSquareSum =
                (long) n * (n + 1) * (2L * n + 1) / 6;

        long actualSum = 0;
        long actualSquareSum = 0;

        for (int num : arr) {
            actualSum += num;
            actualSquareSum += (long) num * num;
        }

        // missing - repeating
        long diff = expectedSum - actualSum;

        // missing^2 - repeating^2
        long squareDiff = expectedSquareSum - actualSquareSum;

        // missing + repeating
        long sum = squareDiff / diff;

        long missing = (diff + sum) / 2;
        long repeating = sum - missing;

        return new int[]{(int) repeating, (int) missing};
    }
}

The formulas to remember

You only need to remember these:

Expected Sum = n(n + 1) / 2

Expected Square Sum = n(n + 1)(2n + 1) / 6


Then:


diff = Expected Sum - Actual Sum
diff = Missing - Repeating


and:

squareDiff = Expected Square Sum - Actual Square Sum

squareDiff
= Missing² - Repeating²
= (Missing + Repeating)(Missing - Repeating)


Therefore:

Missing + Repeating
= squareDiff / diff


Finally:

Missing = (diff + sum) / 2
Repeating = sum - Missing


Comparison

| Approach      |       Time |   Extra Space | Difficulty |
| ------------- | ---------: | ------------: | ---------- |
| Sorting       | O(n log n) | O(1)/O(log n) | Easy       |
| Boolean array |   **O(n)** |          O(n) | **Easy**   |
| Mathematics   |   **O(n)** |      **O(1)** | Medium     |

For your current learning, I'd recommend **first implementing the boolean-array approach**, then the mathematical approach. The mathematical solution is optimal in space but much easier to make an integer-overflow mistake.



 */