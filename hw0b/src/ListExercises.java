import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        int res=0;
        for (Integer i : L) {
            res += i;
        }
        return res;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> res = new ArrayList<>();
        for (Integer i : L) {
            if (i%2==0) {
                res.add(i);
            }
        }
        return res;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> res = new ArrayList<>();
        for (Integer i : L1) {
            if (L2.contains(i)) {
                res.add(i);
            }
        }
        return res;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int res=0;
        for (String word : words) {
            for (char ci : word.toCharArray()) {
                if (ci == c) {
                    res++;
                }
            }
        }
        return res;
    }
}
