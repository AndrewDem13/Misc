import java.util.HashSet;

/**
 Problem from: https://leetcode.com/problems/longest-palindromic-substring/
 Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000
 */
public class PalindromicSubstring {
    public static void main(String args[]) {
        long st = System.currentTimeMillis();
        String result = longestPalindrome("lshldghlaslalkhjkagslkhasgoauhlakshdgoilashghsaj");
        long delta = System.currentTimeMillis() - st;
        System.out.println("Time spent: " + delta + "ms");
        System.out.println("Result: " + result);
    }

    public static String longestPalindrome(String s) {
        HashSet<String> preResult = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            String temp = s.substring(i+1);
            while (temp.contains(""+s.charAt(i))) {
                preResult.add(s.charAt(i)+temp.substring(0, temp.lastIndexOf(s.charAt(i))+1));
                temp = temp.substring(0, temp.lastIndexOf(s.charAt(i)));
            }
        }
        String result = ""+s.charAt(0);
        for (String str : preResult) {
            if (str.length() > result.length() && isPalindrome(str))
                result = str;
        }
        return result;
    }
    private static boolean isPalindrome(String s) {
        StringBuilder sb2 = new StringBuilder(s).reverse();
        return s.equals(sb2.toString());
    }
}
