import java.util.List;

/**
 * Created by AndrewDem on 07.01.2017.
 */

public class BinarySearch {
    public static int search(int i, List<Integer> list) {
        int min = 0;
        int max = list.size();
        int mid;
        int current = -1; // -1 means NOT FOUND

        while (min < max)
        {
            mid = (max + min) / 2;

            if (list.get(mid) == i)
            {
                current = mid;
                break;
            }
            else if (list.get(mid) < i)
                min = mid+1;
            else if (list.get(mid) > i)
                max = mid;
        }
        return current;
    }
}
