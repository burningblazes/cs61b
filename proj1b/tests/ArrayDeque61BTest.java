import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

//     @Test
//     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }

    @Test
    public void addFirstTest(){
        Deque61B<Integer> lst = new ArrayDeque61B<>();
        lst.addFirst(1); //[1]
        assertThat(lst.toList()).containsExactly(1).inOrder();

        lst.addFirst(2); //[2,1]
        assertThat(lst.toList()).containsExactly(2,1).inOrder();

        for (int i = 0; i < 10; i++) {
            lst.addFirst(i+5);
        }
        assertThat(lst.toList()).containsExactly(14,13,12,11,10,9,8,7,6,5,2,1).inOrder();
    }

    @Test
    public void addLastTest(){
        Deque61B<Integer> lst = new ArrayDeque61B<>();

        lst.addLast(-3); //[-3]
        assertThat(lst.toList()).containsExactly(-3).inOrder();

        lst.addLast(4);
        assertThat(lst.toList()).containsExactly(-3,4).inOrder();

        for (int i = 0; i < 10; i++) {
            lst.addLast(i);
        }
        assertThat(lst.toList()).containsExactly(-3,4,0,1,2,3,4,5,6,7,8,9).inOrder();
    }

    @Test
    public void getTest(){
        Deque61B<Integer> lst = new ArrayDeque61B<>();
        lst.addLast(1);
        lst.addLast(2);
        lst.addLast(3); //[1,2,3]

        assertThat(lst.get(2)).isEqualTo(3);
        assertThat(lst.get(6)).isNull();
        assertThat(lst.get(-4)).isNull();
    }

    @Test
    public void isEmptyTest(){
        Deque61B<Integer> lst = new ArrayDeque61B<>();
        assertThat(lst.isEmpty()).isTrue();
        assertThat(lst.toList()).containsExactly().inOrder();

        lst.addLast(1);
        assertThat(lst.isEmpty()).isFalse();
    }

    @Test
    public void removeTest(){
        Deque61B<Integer> lst = new ArrayDeque61B<>();
        lst.addLast(1);
        lst.addLast(2);
        assertThat(lst.size()).isEqualTo(2);
        lst.removeLast();
        assertThat(lst.toList()).containsExactly(1).inOrder();

        lst.removeLast();
        assertThat(lst.toList()).containsExactly().inOrder();
        assertThat(lst.size()).isEqualTo(0);

        lst.removeLast();
        assertThat(lst.size()).isEqualTo(0);

        lst.addLast(3);
        lst.addLast(4);
        assertThat(lst.toList()).containsExactly(3,4).inOrder();
        lst.removeFirst();
        assertThat(lst.toList()).containsExactly(4).inOrder();
        lst.removeFirst();

        lst.addFirst(5);
        assertThat(lst.toList()).containsExactly(5).inOrder();
        assertThat(lst.size()).isEqualTo(1);

    }

    @Test
    public void removeLastResizeTest(){
        Deque61B<Integer> lst = new ArrayDeque61B<>();
        for (int i = 0; i < 12; i++) {
            lst.addLast(i);
        }//[0,1,2,3,4,5,6,7,8,9,10,11]
        assertThat(lst.toList()).containsExactly(0,1,2,3,4,5,6,7,8,9,10,11).inOrder();
        for (int i = 0; i < 10; i++) {
            lst.removeLast(); //[0,1]
        }
        assertThat(lst.toList()).containsExactly(0,1).inOrder();
    }

    @Test
    public void removeFirstResizeTest(){
        Deque61B<Integer> lst = new ArrayDeque61B<>();
        for (int i = 0; i < 12; i++) {
            lst.addLast(i);
        }//[0,1,2,3,4,5,6,7,8,9,10,11]
        assertThat(lst.toList()).containsExactly(0,1,2,3,4,5,6,7,8,9,10,11).inOrder();
        for (int i = 0; i < 10; i++) {
            lst.removeFirst(); //[10,11]
        }
        assertThat(lst.toList()).containsExactly(10,11).inOrder();
    }

    @Test
    public void testEqualDeques61B() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }

}
