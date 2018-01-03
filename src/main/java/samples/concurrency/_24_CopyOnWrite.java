package samples.concurrency;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class _24_CopyOnWrite {
    public static void main(String[] args) {
        List<Integer> cow = new CopyOnWriteArrayList<>();
        Integer toAdd = 1;
        boolean isOk = cow.add(toAdd);
        Integer got = cow.get(0);
        Integer removed = cow.remove(0);
        System.out.println(toAdd);
        System.out.println(got);
        System.out.println(removed);
    }
}
