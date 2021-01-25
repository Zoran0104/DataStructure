import com.zoran.List.ArrayList;
import com.zoran.List.LinkedList;

public class TestLinkedList {
    /**
     * 用于测试：ReverseLinkedList
     */
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(10);
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        for (int i = 0; i < 100; i++) {
            list.remove(0);
        }
    }

}
