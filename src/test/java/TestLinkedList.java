import com.zoran.List.ArrayList;
import com.zoran.List.List;
import com.zoran.List.linkedlist.CircleSingleLinkedList;
import org.w3c.dom.ls.LSInput;

public class TestLinkedList {
    /**
     * 用于测试：ReverseLinkedList
     */
    public static void main(String[] args) {
        CircleSingleLinkedList<Integer> list = new CircleSingleLinkedList<>();
        list.add(12);
        list.add(19);
        list.add(13);
        list.add(19);
        list.add(8);
        list.add(0, 98);
        //list.remove(0);
        System.out.println(list);
    }

}
