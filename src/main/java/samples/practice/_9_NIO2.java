package samples.practice;

//1
//2
//3
//4
//5

import java.io.BufferedReader;
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

import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.list;


//        System.out.println("GAMES COUNT: " + gamesPaths.size() + "\n");
//todo: create Consumer to do something, in current case it:
//todo: 1. find config.json (implement Visitor pattern)
//todo: 2. parse and add new attributes
//todo: 3. save changes
//        gamesPaths.stream().forEach(gameDir -> System.out.println(gameDir.getFileName()));
//        System.out.println("\nGAMES COUNT: " + gamesPaths.size());


public class _9_NIO2 {

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