package samples.practice;

//1
//2
//3
//4
//5

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


//        System.out.println("GAMES COUNT: " + gamesPaths.size() + "\n");
//todo: create Consumer to do something, in current case it:
//todo: 1. find config.json (implement Visitor pattern)
//todo: 2. parse and add new attributes
//todo: 3. save changes
//        gamesPaths.stream().forEach(gameDir -> System.out.println(gameDir.getFileName()));
//        System.out.println("\nGAMES COUNT: " + gamesPaths.size());


public class _9_NIO2 {

    public static final String ABSOLUTE_PATH_TO_GAMES = "C:\\Users\\vasylk\\Projects\\trunk\\ngm\\client\\games";
    public static final String FILE_TO_SEARCH = "config.json";
    public static final String CONTENT_TO_INSERT = "  \"someNewParameterName\": \"someNewParameterValue\"";
    public static final String LAST_LINE_IN_SEARCH_FILE = "}";

    public static void main(String[] args) throws IOException {
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
//1 -
//2 -
//3 -
//4 -
//5 -
//6 -
//7 -
//8 -
//9 -
//10 -
//11 -
//12 -
//13 -
//14 -
//15 -
//16 -
//17 -
//18 -
//19 -
//20 -
//21 -
//22 -
//23 -
//-(%)


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