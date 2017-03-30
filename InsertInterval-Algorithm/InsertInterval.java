import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 Problem from: https://leetcode.com/problems/insert-interval/
 Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).

 You may assume that the intervals were initially sorted according to their start times.

 Example 1:
 Given intervals [1,3],[6,9], insert and merge [2,5] in as [1,5],[6,9].

 Example 2:
 Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in as [1,2],[3,10],[12,16].

 This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].
 */
public class InsertInterval {
    public static void main(String args[]) {
        List<Interval> list = new ArrayList<>();
        list.add(new Interval(8, 10));
        list.add(new Interval(12, 16));
        long st = System.currentTimeMillis();
        List<Interval> result = insert(list, new Interval(11, 11));
        long delta = System.currentTimeMillis() - st;
        System.out.println("Time spent: " + delta + "ms");
        for (Interval i : result)
            System.out.println(i);
    }

    public static List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> result = new ArrayList<>();

        if (intervals == null || intervals.size() == 0) {
            result.add(newInterval);
            return result;
        }

        int start = newInterval.start, end = newInterval.end;

        for (Interval i : intervals) {
            if (newInterval.start >= i.start && newInterval.start <= i.end) {
                start = i.start;
                break;
            }
        }

        for (Interval i : intervals) {
            if (newInterval.end <= i.end && newInterval.end >= i.start) {
                end = i.end;
                break;
            }
        }

        result.add(new Interval(start, end));
        for (Interval i : intervals) {
            if (newInterval.start > i.end || newInterval.end < i.start)
                result.add(i);
        }

        Collections.sort(result);
        return result;
    }
}

class Interval implements Comparable<Interval>{
    int start;
    int end;
    Interval() { start = 0; end = 0; }
    Interval(int s, int e) { start = s; end = e;}

    @Override
    public String toString() {
        return "[" + start + ", " + end + "]";
    }

    @Override
    public int compareTo(Interval o) {
        return start - o.start;
    }
}
