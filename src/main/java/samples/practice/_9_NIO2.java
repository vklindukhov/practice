package samples.practice;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.list;
import static java.util.stream.Collectors.toList;

//1 D - C
//2 D - B
//3 B +
//4 A +
//5 D - A

public class _9_NIO2 {

    public static final String ABSOLUTE_PATH_TO_GAMES = "C:\\Users\\vasylk\\Projects\\trunk\\ngm\\client\\games";
    public static final String FILE_TO_SEARCH = "config.json";
    public static final String CONTENT_TO_INSERT = "  \"someNewParameterName\": \"someNewParameterValue\"";
    public static final String LAST_LINE_IN_SEARCH_FILE = "}";

    public static void main(String[] args) throws IOException {
        Path path = Paths.get(".");
        Path normalized = path.normalize();
        int nameCount = normalized.getNameCount();
        System.out.println(nameCount);
    }

    private static void symbolicLinks() throws IOException {
        Path path = Paths.get("../");
        Files.find(path, 0, (p, a) -> a.isSymbolicLink())
                .map(p -> p.toString())
                .collect(Collectors.toList())
                .stream()
                .filter(x -> x.endsWith(".txt"))
                .forEach(System.out::println);
    }

    private static void relativize() {
        Path path = Paths.get("/user/.././root", "../kodiacbear.txt");
        path = path.normalize().relativize(Paths.get("/lion"));
        System.out.println(path);
    }

    private static void processGames() throws IOException {
        Path pathToGames = Paths.get(ABSOLUTE_PATH_TO_GAMES);
        System.out.println("Processing ...");
        Map<String, List<Path>> filesPaths = findFilesPaths(pathToGames, FILE_TO_SEARCH);

        System.out.println();
        AtomicInteger updated = new AtomicInteger(0);
        filesPaths.entrySet().stream().parallel()
                .filter(e -> e.getValue().size() == 1)
//                .limit(10)
                .forEach(e -> {
                    Path p = e.getValue().iterator().next();
                    try (/*BufferedReader reader = Files.newBufferedReader(p)) {
                        List<String> lines = new ArrayList<>();
                        String line = null;
                        while ((line = reader.readLine()) != null) lines.add(line);*/
                         Stream<String> linesStream = Files.lines(p)
                    ) {
                        List<String> lines = linesStream.collect(toList());
                        if (!LAST_LINE_IN_SEARCH_FILE.equals(lines.get(lines.size() - 1).trim())) {
                            throw new IOException("WRONG FILE MARKING: LAST LINE IS NOT '" + LAST_LINE_IN_SEARCH_FILE + "'");
                        }
                        lines.set(lines.size() - 2, lines.get(lines.size() - 2) + ",");
                        lines.add(lines.size() - 1, CONTENT_TO_INSERT);
                        Files.write(p, lines, UTF_8);
                        System.out.println("FILE " + p + " IS SUCCESSFULLY UPDATED");
                        updated.getAndIncrement();
                    } catch (IOException cause) {
                        System.err.println("!!!!!!!!!ERRORR!!!!!!!!! - IO_EXCEPTION FOR: " + p.getFileName() + ". " + cause.getMessage());
                    }
                });
        System.out.println("\nUPDATED " + updated + " " + FILE_TO_SEARCH + " FILES");
    }

    private static Map<String, List<Path>> findFilesPaths(Path pathToGames, String searchFileName) throws IOException {
        Predicate<Path> isFileHidden = p -> p.getFileName().toString().startsWith(".");

        int indexOfGameName = 7;
        Map<String, List<Path>> searchFilePaths = new ConcurrentHashMap<>();

        Consumer<Path> processGame = p -> {
            String gameName = p.getName(indexOfGameName).toString();
            searchFilePaths.put(gameName, new ArrayList<>());
            try {
                Files.walk(p)
                        .parallel()
                        .filter(f -> !isDirectory(f) && searchFileName.equals(f.getFileName().toString()))
                        .forEach(f -> {
//                            System.out.println(f.getFileName() + " - " + f.toAbsolutePath());
                            searchFilePaths.get(gameName).add(f);
                        });
            } catch (IOException e) {
                System.err.println("IO_EXCEPTION FOR: " + p.getFileName());
            }
        };


        list(pathToGames).parallel().filter(isFileHidden.negate()).forEach(processGame);

        long foundFilesCount = searchFilePaths.values().stream().map(List::size).reduce(0, (a, b) -> a + b);
        System.out.println("\nFOUND " + foundFilesCount + " " + searchFileName + " FILES IN " + searchFilePaths.entrySet().size() + " GAMES");

        System.out.println("\nGAMES WITHOUT " + searchFileName + " FILE:");
        searchFilePaths.entrySet().stream().filter(e -> e.getValue().isEmpty()).forEach(e -> System.out.println(e.getKey()));

        System.out.println("\nGAMES WITH MULTIPLE " + searchFileName + " FILES:");
        searchFilePaths.entrySet().stream().filter(e -> e.getValue().size() > 1).forEach(e -> {
            System.out.print("\n" + e.getKey() + " - ");
            e.getValue().stream().forEach(p -> System.out.print(p.toString().split(e.getKey())[1] + "   "));
        });
        System.out.println();

        return searchFilePaths;
    }

    private static void filesAttributes() throws IOException {
        Files.readAttributes(Paths.get("README.md"), "dos:*", LinkOption.NOFOLLOW_LINKS).entrySet().forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
    }

    private static void basicFileAttributes() throws IOException {
        Path path = Paths.get("README.md");
        BasicFileAttributes basicFileAttributes = Files.readAttributes(
                path,
                BasicFileAttributes.class,
                LinkOption.NOFOLLOW_LINKS
        );
        Method[] declaredMethods = basicFileAttributes.getClass().getDeclaredMethods();

        Arrays.stream(declaredMethods)
                .filter(m -> {
                    int modifiers = m.getModifiers();
                    return isPublic(modifiers) && !isStatic(modifiers) && m.getParameterCount() == 0;
                })
                .forEach(m -> {
                    try {
                        m.setAccessible(true);
                        System.out.println(m.getName() + ": " + m.invoke(basicFileAttributes));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.err.println(m.getName());
                    }
                });
    }

    private static void comparingPaths() {
        Path path1 = Paths.get("README.md");
        Path path2 = Paths.get("C:\\Users\\vasylk\\Projects\\Practice\\README.md");
        System.out.println(path1.getFileName());
        System.out.println(path1.getRoot());
        System.out.println(path1.toAbsolutePath());
        System.out.println(path2);

        System.out.println(path1 == path2);
        System.out.println(path1.equals(path2));
        System.out.println(path1.toAbsolutePath().equals(path2));
        System.out.println(path1.compareTo(path2));
        System.out.println(path1.toAbsolutePath().compareTo(path2));
    }
}


//Chapter 9 Test
//1 - D - F не знал, завтык,
//2 - C,D - B,C не знал
//3 - A - D не знал
//4 - C +
//5 - B,D - B,C,D не знал
//6 - C +
//7 - F +
//8 - E - A не знал
//9 - A,C - B,C не знал
//10 - B,E - C,E не знал
//11 - B - A не знал
//12 - A,F,!?G?! - A,F не знал
//13 - A - B не знал
//14 - A - E не знал
//15 - B,C - D,E,F не знал
//16 - A,E,F - F не знал
//17 - A,?E,F?,G - A,G не знал
//18 - D +
//19 - A,B,C,D,E,F - A,C,E не знал
//20 - D - B не знал
//-16(80%)
//Path.relativize(Path path) constructs a relative path between this path and a given path.
//Path::normalize does not change the current Path object, but instead it returns a new Path object
//Files.isDirectory(Path path) returns false if directory does not exist
//symbolic link can point to a real directory
//Files::deleteIfExists throws DirectoryNotEmptyException if directory has content
//BasicFileAttributes does not have the method setTimes()
//Path::subpath is applied to the absolute path, Path.getName() is applied to the relative path
//symbolic link can be replaced to path of referenced directory
//Files.find(Path,int,BiPredicate<Path,BasicFilesAttributes>,FileVisitOption...)
//Files.isSameFile(Path,Path) checks are they same in term of equals() if false, then checks existence of both path in the file system, relative and absolute to the same file is not equal
//Path::resolve does not normalize any path symbols, returns an absolute path if an argument is an absolute path
//path name can be with dot
//advantage of using Files.lines(): can be run on large files with very little memory available;
//if REPLACE_EXISTING flag is not provided then Files.move() throws an Exception if the target exists
//NOFOLLOW_LINKS flag means that if the source is a symbolic link, the link itself and not the target will be copied at runtime
//ATOMIC_MODE flag means that any process monitoring the file system will not see an incomplete file during the move
//possible to rename file from/to without extension
//Files.move(Path src, Path dst) if src is file/directory then dst will be file/directory as well
//Files.copy(Path src, Path dst, CopyOption...) src will not equal to dst, hence equals() return false
//FileSystems.getDefault().getPath(String path) exists
//Paths.getPath(String path) DOES not exist
//directory is regular and NOT a symbolic link
//Files.find(... int depth ...) if depth is 0 then search will be in top-level directory
//Files::list is most similar to File::listFiles and retrieves the members of the current directory without traversing any subdirectories
//Path::listFiles and Files::files DO NOT exist
//Files::walk and Files::find find recursively traverse a directory tree rather than list contents of the current directory
//Files.(...int depth...) can be similar to File::listFiles if depth is 1
//NIO.2 views advantages to read metadata rather than individually from Files methods: 1) makes fewer round-trip to the file system, 2) can be used to access file system-dependent attributes, 3) often more performant to read multiple attributes
//NIO.2 advantages over legacy File: 1) supports file system-dependent attributes; 2) supports symbolic links; 3) allows to traverse a directory tree directly
//NIO.2 and legacy File: 1) can be used to list all the files within a single directory; 2) can be used to delete files and non-empty directories; 3) can be used to read the last-modified time
//Path::normalize does not covert a relative path into an absolute path
//Path::getNameCount return 1 for current directory ("" or ".".normalize())
//Path has iterator. The first element returned by the iterator represents the name element that is closest to the root in the directory hierarchy, the second element is the next closest, and so on. The last element returned is the name of the file or directory denoted by this path


//    Predicate<Path> isHiddenCheck1 = p -> {
//        try {
//            return isHidden(p);
//        } catch (IOException e) {
//            System.err.println("IO_EXCEPTION for: " + p.getFileName());
//            return false;
//        }
//    };
//
//
//    Predicate<? super Path> isHiddenCheck2 = p -> {
//        Object attribute = null;
//        try {
//            attribute = Files.getAttribute(p, "dos:hidden", LinkOption.NOFOLLOW_LINKS);
//        } catch (IOException e) {
//            System.err.println("IO_EXCEPTION for: " + p.getFileName());
//        }
//        return attribute instanceof Boolean && (Boolean) attribute;
//
//    };

//                        int placeToInsert = content.lastIndexOf('}');
//                        String contentToInsert = ",  \"someNewParameterName\": \"someNewParameterValue\"";
//                        String newContent = content.substring(0, placeToInsert) + contentToInsert + content.substring(placeToInsert);