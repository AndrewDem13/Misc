import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


/**
 * Created by Admin on 07.01.2017.
 */
public class BinarySearchTest {
    ArrayList<Integer> list = new ArrayList<>();

    @Test
    public void empty() {
        assertEquals(-1, BinarySearch.search(5, list));
    }

    @Test
    public void one() {
        list.add(0);
        assertEquals(0, BinarySearch.search(0, list));
        assertEquals(-1, BinarySearch.search(55, list));
    }

    @Test
    public void two() {
        list.add(0);
        list.add(1);
        assertEquals(0, BinarySearch.search(0, list));
        assertEquals(1, BinarySearch.search(1, list));
        assertEquals(-1, BinarySearch.search(55, list));
    }

    @Test
    public void three() {
        list.add(0);
        list.add(1);
        list.add(2);
        assertEquals(0,BinarySearch.search(0, list));
        assertEquals(2,BinarySearch.search(2, list));
        assertEquals(-1,BinarySearch.search(55, list));
    }

    @Test
    public void alot() {
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        list.add(12);
        list.add(12);
        list.add(13);
        list.add(14);
        assertEquals(5, BinarySearch.search(5, list));
        assertEquals(0, BinarySearch.search(0, list));
        assertEquals(14, BinarySearch.search(14, list));
        assertEquals(11, BinarySearch.search(12, list));
        assertEquals(-1, BinarySearch.search(55, list));
    }
}