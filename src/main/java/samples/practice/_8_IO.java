package samples.practice;

//1 D
//2 D - B
//3 D
//4 D
//5 B - A
//6 A,C - B,C

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class _8_IO {
    enum E{
        A,B,C
    }
    public static void main(String[] args) throws IOException {
        IntStream.of();
    }

    private static void fileTimes() throws IOException {
        Path path = Paths.get("");
        BasicFileAttributeView view = Files.getFileAttributeView(path, BasicFileAttributeView.class);
        BasicFileAttributes attributes = view.readAttributes();
        view.setTimes(null,null,null);
    }

    private static void inConsoleOutFileViaConsole() throws FileNotFoundException {
        PrintStream printStream = System.out;
        try (PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("1.txt")))) {
            System.setOut(out);
            String heartForMySweetyAlinka = "00000000000000000000000000000\n" +
                    "00000077777000000077777000000\n" +
                    "00007777777770007777777770000\n" +
                    "00077777777777077777777777000\n" +
                    "00077777777777777777777777000\n" +
                    "00007777777777777777777770000\n" +
                    "00000777777777777777777700000\n" +
                    "00000007777777777777770000000\n" +
                    "00000000007777777777700000000\n" +
                    "00000000000077777000000000000\n" +
                    "00000000000000700000000000000\n" +
                    "00000000000000000000000000000";
//            Scanner scanner = new Scanner(System.in);
//            while (scanner.hasNext()) {
//                String line = scanner.nextLine();
//                if ("exit".equals(line)) break;
//                System.out.println(line);
//            }
            System.out.println("This is for YOU Alino4ka\n" + heartForMySweetyAlinka);
            System.setOut(printStream);
            System.out.println("Take a look in 1.txt file");
        }
    }
}

//Chapter 8 Test
//1 - A - A,D не знал
//2 - B,F - B,E,F не знал
//3 - C,D +
//4 - B,C,E - C не знал, завтык
//5 - F - B,D,E не знал
//6 - A,B,E - A,E не знал
//7 - D +
//8 - C,D,E - A не знал,завтык
//9 - A,C,D - A не знал
//10 - B,C,F - C,F не знал
//11 - B,D,E,F - C,E,G не знал
//12 - B,C +
//13 - A,C,E +
//14 - A,B - E не знал
//15 - E - A не знал
//16 - D,F +
//17 - E - A,B,D,G не знал!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//18 - A,B,C,E,F - B,D,E не знал
//19 - B,C,D +
//20 - C,E - A,C не знал
//21 - A,C,E - A,C не знал
//22 - C - E не знал!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//23 - E - H не знал
//-17(74%)
//ObjectInputStream is top level class, may be a wrapper for InputStream
//Console.readPassword() return char[] but not String because Java puts all String to reusable pool and this is not secure
//Console HAS ONLY readLine() and readPassword() methods, but HAS NOT read(), readString()
//Writer has append() throws IOException methods
//ObjectInputStream.readObject() throws ClassNotFoundException
//NOT all-thread should be marked Serializable, everything may or not may be marked depending on reasons
//a File object may refer to a path that does not exist within the file system
//ONLY ONE requirement for class serialization/deserialization is to implement Serializable interface, creating static serialVersionUID is optional
//File methods: renameTo() - moves a file, mkdir() mkdirs() - create dir(s), use read/write techniques to copy a file
//java.io classes are: Reader/Writer, FileReader/Writer, BufferedReader/Writer, PrintWriter. AND NOT PrintReader
//abstract, concrete or final class can be marked as Serializable
//high level classes are: ObjectInput/OutputStream, PrintStream, PrintWriter,
//NOT high level classes are: FileWriter, FileInputStream, OutputStream
//Console defines 2 output methods: format(), printf(), Can use console.writer().println() to write some data
//java.nio.DirectoryStream is not java.io class
//java.io.PipedOutputStream can be connected to a piped input stream to create a communications pipe. Attempting to use both objects from a single thread is not recommended as it may deadlock the thread.
//reasons to use a character streams over a byte stream: 1.more convenient code syntax when working with String data; 2. automatic character encoding
//serialUID is NOT serialVersionUID and not used by serialization mechanism.
//upon deserialization, the default initialization and constructor will be skipped
//Java will call the constructor for the first non-serializable no-argument parent class during deserialization, skipping any constructors and default initializations for serializable classes in between.
//Not all java.io support mark() operation (if do not runtime exception can me thrown), without calling markSupported() on the stream, the result is unknown until runtime
//the reset() operation puts the stream back in the position before the mark() was called