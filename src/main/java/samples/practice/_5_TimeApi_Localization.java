package samples.practice;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.time.Month.MARCH;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.DECADES;
import static java.util.Locale.*;

//Time API
//1 - C - D
//2 - C
//3 - E
//4 - E
//5 - B

//Localization
//1 B
//2 F
//3 E
//4 B
//5 A - C
//6 C

public class _5_TimeApi_Localization {
    public static void main(String[] args) {

    }

    private static void formatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
//        System.out.println(formatter.format(LocalDate.now()));
//        System.out.println(formatter.format(LocalTime.now()));
        System.out.println(formatter.format(LocalDateTime.now()));
        System.out.println(formatter.format(ZonedDateTime.now()));
    }

    private static void locales() {
        Locale.setDefault(ENGLISH);
        Locale[] availableLocales = Locale.getAvailableLocales();
        System.out.println("Available Locales: " + availableLocales.length);
        Arrays.stream(availableLocales)
                .filter(l -> !l.getCountry().isEmpty())
                .sorted((l1, l2) -> l1.getDisplayCountry().compareTo(l2.getDisplayCountry()))
                .forEach(l -> System.out.println(
                        l.getCountry() + "      - " + l.getDisplayCountry()
                        + "         - " + l.getDisplayLanguage() + "        - " + l.getDisplayName()
                ));
        System.out.println(GERMANY.getDisplayCountry(GERMAN));
        System.out.println(GERMANY.getDisplayCountry(ENGLISH));
    }

    private static void formattedZonedDateTime() {
        System.out.println(LocalDate.of(2016, MARCH, 17));

        System.out.println(LocalTime.now());

        System.out.println(LocalDateTime.now());

        System.out.println(Instant.now());

        System.out.println(Period.between(LocalDate.now(), LocalDate.now().minus(10, DAYS)));

        System.out.println(Duration.between(LocalDateTime.now().plus(5, DECADES), LocalDateTime.now()));

        System.out.println(ZoneId.systemDefault());

        System.out.println(ZoneOffset.systemDefault());

        System.out.println(ZonedDateTime.now());

        System.out.println(DateTimeFormatter.ofPattern("yyyy MMM dd hh:mm:ss:SS").format(LocalDateTime.now()));
    }

    private static void basics() {
    }
}

//Chapter 5 Test
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
