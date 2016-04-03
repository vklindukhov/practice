package samples.practice;

//1 B,C
//2 B
//3 D
//4 A - D
//5.1 C
//5.2 A
//6 B
//7 D
//8 A
//9 D/F
//10 B


import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class _6_ExceptionsAssertions {
    public static void main(String[] args) {
    }

    private static void read() throws SQLException {
        try {
            readFromDb();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static void readFromDb() throws SQLException {

    }

    private static void autoClosableSignature() throws IOException {
        try (InputStream stream = new BufferedInputStream(new ByteInputStream(new byte[100], 11))) {
        }
    }

    public static class AutoClosableClass implements AutoCloseable {
        @Override
        public void close() throws Exception {
            throw new Exception();
        }
    }
}

//Chapter 6 Test
//1 - C +
//2 - B - B,C не знал
//3 - C,E - E завтык
//4 - A,B,F - A,B не знал
//5 - G - C не знал
//6 - G +
//7 - C +
//8 - B - B,C не знал
//9 - A,D +
//10 - A,E +
//11 - !!!A!!!,B,D,E - B,D,E
//12 - A,D,E +
//13 - A +
//14 - F + не знал
//15 - B,E +
//16 - A - C не знал
//17 - A - A,B не знал
//18 - C - B не знал, не понял вопроса
//19 - A,B,D - D не знал, завтык, не понял вопроса
//20 - E +
//-10(50%)
//Closable extends AutoClosable. Closable::close() throws IOException, AutoClosable::close() throws Exception
//Using try-with-resources except implement AutoClosable define throws Exception (IOException) in method signature or catch Exception (IOException)
//try-with-resources should contain {}
//during cosing recourse in try-with-resources could be suppressed exception
//java uses  the flags -ea or -enableassertions to turn on assertions. -da or -disableassertions turn off assertions. -ea:Class/package/so on... turns on assertions as well
//assertion syntax is: assert false <: "Text">; or assert (false) <:"Text">;
//assertion should not have side effects e.g. assert a++ > 0;
//RuntimeException: IllegalFormatException, IllegalStateException, MissingResourceException, DateTimeParseException
//in multi catch handling handled exception reference is effectively final and cannot be reassigned
//in catch block AutoClosable::close() throws Exception is already executed
//IOException extends Exception, SQLException extends Exception
//if code in try block does not declare some checked exception then it shall not be caught