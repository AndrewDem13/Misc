/**
 A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 х 99.
 Find the largest palindrome made from the product of two 3-digit numbers.
 */

public class PalindromSearch {
    static int tempPalindrome = 997799; // closest to 999 * 999 result
    static int maxPalindrome;
    static int n1, n2, step;

    public static void main(String[] args){
        long st = System.currentTimeMillis();
        findPalindrome();
        long delta = System.currentTimeMillis() - st;
        System.out.println("Largest palindrome: " + maxPalindrome + " = " + n1 + " x " + n2);
        System.out.println("Time spent: " + delta + " ms");
    }

    private static void findPalindrome() {
       while (maxPalindrome == 0 && tempPalindrome > 99999) {
           if (tempPalindrome % 10 == 0) {
               n1 = 990;
               step = 10;
           }
           else if (tempPalindrome % 5 == 0) {
               n1 = 995;
               step = 5;
           }
           else if (tempPalindrome % 2 == 0) {  // random even number
               n1 = 998;
               step = 2;
           }
           else {     //  random odd number
               n1 = 999;
               step = 2;
           }

           for (; n1 >= Math.sqrt(tempPalindrome); n1 = n1 - step) {
               n2 = tempPalindrome / n1;
               if (n2 < 100 || n2 >999)
                   break;
               if (tempPalindrome % n1 == 0) {
                   maxPalindrome = tempPalindrome;
                   break;
               }
           }
           nextTemp();
       }
    }

    private static void nextTemp() {
        if ((tempPalindrome - (tempPalindrome % 10)) % 100000 == 0) tempPalindrome = tempPalindrome - 11;
        else if ((tempPalindrome - (tempPalindrome % 100)) % 10000 == 0) tempPalindrome = tempPalindrome - 110;
        else tempPalindrome = tempPalindrome - 1100;
    }
}