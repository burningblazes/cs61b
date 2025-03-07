import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    private Comparator<T> comparator;

    public MaxArrayDeque61B(Comparator<T> c){
        comparator = c;
    }

    public T max(){
        if (isEmpty()) {
            return null;
        }
        return max(comparator);
    }

    public T max(Comparator<T> c){
        if (isEmpty()) {
            return null;
        }
        T maxElement = get(0);
        for (int i = 1; i < size(); i++) {
            T current = get(i);
            if (c.compare(current, maxElement) > 0) {
                maxElement = current;
            }
        }
        return maxElement;
    }
}
