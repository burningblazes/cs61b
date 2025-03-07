import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxDeque61BTest {
    @Test
    public void testMaxDeque61B() {
        MaxArrayDeque61B<Integer> m = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        assertThat(m.max()).isNull();

        m.addLast(3);
        m.addLast(2);
        m.addLast(1);
        m.addLast(7);
        m.addLast(5);

        assertThat(m.max()).isEqualTo(7);

        Comparator<Integer>  rv = Comparator.reverseOrder();
        assertThat(m.max(rv)).isEqualTo(1);
    }
}
