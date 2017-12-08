package samples.cuncurrency;

public class StringRealization {
    private static Str str;

    public static void main(String[] args) {
        new Thread(() -> {
            str = new Str(new char[]{'H', 'e', 'l', 'l', 'o'});
        }).start();

        while (str == null) {
            new Object();
            System.out.println(str.chars);
            System.out.println(str.offset);
            System.out.println(str.len);
        }
    }

    static class Str {
//        Safe publiching
//        final char[] chars;
//        final int offset;
//        final int len;

        char[] chars;
        int offset;
        int len;

        public Str(char[] chars) {
            this.chars = chars;
        }
    }
}

