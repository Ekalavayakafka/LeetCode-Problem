class findKthBit{
    public char solution(int n, int k) {
        int rsb = k & (-k);
        boolean inversion = (((k/rsb)>>1)&1) == 1;
        boolean originalbit = (k&1)==1;

        if(inversion){
            return originalbit ? '1':'0';
        } else {
            return originalbit ? '0':'1';
        }

    }
}



/*


Spent 2 days on a single LeetCode problem.
And honestly, I'm glad I did.

The problem was LeetCode 1545 – Find Kth Bit in Nth Binary String.

At first, it looks like a recursion problem. But if you keep optimizing it, it eventually turns into one of the most interesting bit manipulation problems I've seen.
This was the path I followed:

Brute Force
Generate the entire string and return the k-th character.
Time: O(2ⁿ)
Space: O(2ⁿ)
Simple, but wasteful.


class Solution {
    public char findKthBit(int n, int k) {
        String s ="0";
        for(int i=1;i<n;i++){
            String front = s;
            StringBuilder sb = new StringBuilder(s);
            for(int j=0;j<sb.length();j++){
                sb.setCharAt(j,sb.charAt(j) =='1'?'0':'1');
                sb.reverse();
                s =front+"1"+sb.toString();
            }
        }

        return s.charAt(k-1);
    }
}




Recursive Solution
The recursive definition makes the solution much cleaner.
Left half → same as S(n-1)
Middle → always '1'
Right half → mirror + invert
Time: O(n)
Space: O(n) (recursion stack)
This approach is intuitive once you understand the recursive structure.


class Solution {
    public char findKthBit(int n, int k) {
        int len = (1<<n)-1;
        if(len ==1){
            return '0';
        }
        int mid = len/2+1;
        if(mid==k){
             return '1';
        }else if(k>mid){
           k = len-k+1;
           char c = findKthBit(n-1,k);
           return (c=='1')?'0':'1';
        }else{
           return findKthBit(n-1,k);
        }
    }
}

Iterative Solution
The same recursive idea can be converted into iteration by tracking the inversion count.
Time: O(n)
Space: O(1)



class Solution {
    public char findKthBit(int n, int k) {
        int len = (1<<n)-1;
        int inv =0;
        int mid = (len/2)+1;

        while(len>1){
            mid = (len/2)+1;
            if(k==mid){
                return (inv &1) == 1?'0':'1';
            }else if(k > mid){
                k = len-k+1;
                inv++;
            }
            len = len/2;
        }

        return (inv&1 )== 1?'1':'0';


    }
}



This is already an excellent interview solution.
Then came the O(1) solution...
This is where the problem completely changed.
It stopped feeling like a recursion problem and started feeling like a mathematical puzzle.
The solution is only a few lines:
int rsb = k & -k;
boolean invert = (((k / rsb) >> 1) & 1) == 1;
boolean originalBitOne = (k & 1) == 0;

answer = originalBitOne ^ invert;
The code is tiny.
Understanding why it works is the real challenge.
Questions that kept bothering me were:
Why does k & -k represent the recursion scale?
Why do we divide k by the rightmost set bit?
Why is the least significant bit discarded?
Why does the next bit suddenly represent inversion parity?
Why does even k correspond to the base value 1, while odd k corresponds to 0?
I watched multiple solution videos.
I read discussions.
I understood the code.
But I still didn't understand the idea.
There is no obvious bridge between the recursive construction of the string and those three bitwise operations.
So I stopped watching solutions.
Instead, I started tracing the recursion manually.
For multiple values of k, I wrote down every recursive call, every mirror operation, every inversion, and every binary representation.
Only after almost two days did the pattern finally click.
The biggest lesson wasn't about this particular problem.
It was this:
Elegant bit manipulation solutions are not magic formulas—they're compressed mathematical proofs.
If we only memorize the final formula, we miss the most valuable part: how someone discovered it in the first place.
That's why I believe learning DSA isn't just about solving problems.
It's about understanding how one way of thinking (recursion) can eventually transform into another (bit manipulation).
Sometimes writing 5 lines of code takes 5 minutes.
Understanding why those 5 lines are correct can take 2 days.
And that's perfectly okay.
Have you ever encountered a problem where the "optimal" solution felt impossible to derive on your own? I'd love to hear which one.


 */