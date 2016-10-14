package samples.java8;

//1 D - C
//2 C
//3 A
//4 D,E - C,E
//5 B
//6 C
//7 A
//8 E - C

import java.sql.*;

public class _10_JDBC {
    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql:test")) {
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery("select * from table");
            while (resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    System.out.println(resultSet.getObject(columnIndex));
                }
            }
        }
    }
}

//Chapter 10 Test
//1 - B - B,F не знал
//2 - B,C,D - B,C не знал
//3 - A +
//4 - B - E не знал
//5 - C +
//6 - B,D - B,E не знал
//7 - C,D,E,F - C,E,F не знал
//8 - B - E не знал
//9 - B +
//10 - E - C не знал
//11 - D +
//12 - D +
//13 - A - D не знал
//14 - B,F +
//15 - F +
//16 - E +
//17 - B,E - C,D завтык
//18 - E - A не знал
//19 -  - C
//20 - F
//-11(55%)
//Driver and Statement implementations are part of a concrete jar
//only jdbc vendor and database name are required in url (jdbc:mysql:zoo). NOT location, port, connections parameters
//in JDBC 4.0+ jar required a file META-INF/service/java.sql.Driver.java
//Class::forName throws ClassNotFoundException, DriverManager::getConnection throws SQLException
//Connection.createStatement(int resultSetType, int resultSetConcurrency) resultStatement C {TYPE_SCROLL_SENSITIVE, TYPE_SCROLL_INSENSITIVE, TYPE_FORWARD_ONLY}, resultSetConcurrency C {CONCUR_READ_ONLY, CONCUR_UPDATABLE}
//If requested mode from Connection::createStatement is not supported, tte JDBC will downgrade the request to one that is supported
//A Statement automatically closes the open ResultSet when another SQL statement is run, hence using of closed ResultSet throws SQLException at runtime
//ResultSet::getDate returns year, month, day; ReslutSet:getTime returns hours, minutes, seconds; ReslutSet:getTimestamp returns year, month, day, hours, minutes, seconds
//By default statement is not scrollable (TYPE_FORWARD_ONLY), may throw SQLException when used ResultSet::next/previous/relative
//ResultSet::beforeFirst/afterLast meves the cursor to a location of beginning/end the results
//The call to ResultSet:absolute(0) moves the cursor to a location immediately before the results
//If cursor immediately before the results, then absolute(0) returns false (similar for the end)
//if value in absolute/relative is out of the list count, then the cursor moves to the corresponding border