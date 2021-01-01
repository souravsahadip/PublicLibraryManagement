package public_library_management;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dipcenation
 */
public class OracleJDBC {

    Connection c = null;
    Statement stmt = null;
    ResultSet r;

    boolean replacelibrarian(int dlid, int lid) {
        try {
            String proc = new String("");
            proc = "{call replace_librarian(?, ?)}";

            CallableStatement cst = c.prepareCall(proc);
            cst.setInt(1, dlid);
            cst.setInt(2, lid);
            cst.execute();

        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;

    }

    boolean insertEntry(String[] s, String[] type, String table) {
        try {
            int i = 0;
            String proc = new String("");
            proc = "{call insert_into_" + table + "(";
            for (i = 1; i <= s.length; i++) {

                proc += "?";
                if (i < s.length) {
                    proc += ",";
                }
            }
            proc += ")}";
            System.out.println("Procedure: " + proc);
            CallableStatement cst = c.prepareCall(proc);
            System.out.println("l:" + s.length);
            // cst.setInt(9, 3005);

            for (i = 1; i <= s.length; i++) {
                if (type[i - 1].equals("number")) {
                    cst.setInt(i, Integer.parseInt(s[i - 1]));

                } else if (type[i - 1].equals("varchar2")) {
                    System.out.println("entered");
                    cst.setString(i, s[i - 1]);

                }

            }
            // cst.setInt(9, 2000);
            // System.out.println(""+query);
            cst.execute();
            // stmt.(query);

            // return true ;
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    void createConnection() {
        try {
            c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:oracl", "MYLIBRARY", "1234");
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    boolean checkUser(String email, String password, boolean isMember) {
        try {
            stmt = c.createStatement();
            String query = new String();
            if (isMember == true) {
                query = "select email,PASS_WORD\n" + "from MEMBERS\n" + "where email='" + email + "' and pass_word='"
                        + password + "'";
            } else {
                query = "select email,PASS_WORD\n" + "from Librarian\n" + "where email='" + email + "' and pass_word='"
                        + password + "'";
            }
            r = stmt.executeQuery(query);

            if (!r.next()) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    boolean insertintobook2(String[] s) {
        try {
            String maxid = new String();
            stmt = c.createStatement();
            maxid = "SELECT Max(Book_ID) FROM Book";

            r = stmt.executeQuery(maxid);
            int max = 0;
            while (r.next()) {
                max = r.getInt(1);
                System.out.println("" + r.getString(1));
            }
            System.out.println(max);
            String insert = "insert into book(book_id ,book_name,publisher_id,language,date_of_publication ,genre,no_of_copies)"
                    + " VALUES (" + Integer.toString(max + 1);
            for (int i = 0; i < s.length; i++) {
                // s[i]= "aa" ;
                if (i == 3) {
                    insert = insert + ",TO_DATE('" + s[i] + "','dd/mm/yyyy')";
                } else if (i == 6 || i == 1) {
                    insert = insert + "," + s[i] + "";
                } else if (i != 5 && i != 1 && i != 6) {
                    insert = insert + ",'" + s[i] + "'";
                }
            }

            insert = insert + ")";
            System.out.println("string: " + insert);
            r = stmt.executeQuery(insert);

        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
        // return true ;
    }

    boolean insertintoissue(String s0, String s1, String s2) {
        try {
            String query = new String();
            query = "insert into issue(issue_id,issue_date,member_ID,Book_ID,LIBRARIAN_id,required_return_date)\n"
                    + " values ((select nvl(max(issue_id), 0) from issue)+1,TO_DATE(SYSDATE,'dd/mm/yyyy')," + s0 + ","
                    + s1 + "," + s2 + "," + "TO_DATE(SYSDATE+10,'dd/mm/yyyy'))";
            System.out.println(query);
            stmt = c.createStatement();
            r = stmt.executeQuery(query);

        } catch (SQLException ex) {

            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    boolean insertintobook(String[] s) {
        try {
            String maxid = new String();
            stmt = c.createStatement();
            maxid = "SELECT Max(Book_ID) FROM Book";

            r = stmt.executeQuery(maxid);
            int max = 0;
            while (r.next()) {
                max = r.getInt(1);
                System.out.println("" + r.getString(1));
            }
            System.out.println(max);
            String insert = "insert into book(book_id ,book_name,publisher_id,language,date_of_publication ,genre,prequel ,no_of_copies)"
                    + " VALUES (" + Integer.toString(max + 1);
            for (int i = 0; i < s.length; i++) {
                // s[i]= "aa" ;
                if (i == 3) {
                    insert = insert + ",TO_DATE('" + s[i] + "','dd/mm/yyyy')";
                } else if (i == 6 || i == 1 || i == 5) {
                    insert = insert + "," + s[i] + "";
                } else if (i != 5 && i != 1 && i != 6) {
                    insert = insert + ",'" + s[i] + "'";
                }
            }

            insert = insert + ")";
            System.out.println("string: " + insert);
            r = stmt.executeQuery(insert);

        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
        // return true ;
    }

    boolean updateinfo(String valupdate, String valcol, String condition, String conditioncol, String table) {
        try {
            String query = new String();
            query = "Update " + table + "\n Set " + valcol + " = ";
            if (valupdate.matches("[0-9]+") == false) {
                query = query + "'" + valupdate + "' \n where ";
            } else {
                query = query + valupdate + "\n where ";
            }
            query = query + conditioncol + " = ";
            if (condition.matches("[0-9]+") == false) {
                query = query + "'" + condition + "'";
            } else {
                query = query + condition;
            }

            System.out.println(query);
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            System.out.println("executed");

        } catch (Exception ex) {

            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
        return true;
    }

    boolean deletefromcontain(String bookid, String shelfid) {
        try {
            String query = new String();
            query = "Delete \n From Contain \n Where book_id = " + bookid + " and " + "shelf_id = " + shelfid;
            System.out.println(query);
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            System.out.println("executed");

        } catch (Exception ex) {

            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
        return true;
    }

    boolean deletefromwriter(String bookid, String writerid) {
        try {
            String query = new String();
            query = "Delete \n From Writer \n Where book_id = " + bookid + " and " + "author_id = " + writerid;
            System.out.println(query);
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            System.out.println("executed");

        } catch (Exception ex) {

            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
        return true;
    }

    boolean createMember(String[] s) {
        try {

            String maxid = new String();
            stmt = c.createStatement();
            maxid = "SELECT Max(MEMBER_ID) FROM Members";

            r = stmt.executeQuery(maxid);
            int max = 0;
            while (r.next()) {
                max = r.getInt(1);
                System.out.println("" + r.getString(1));
            }
            System.out.println(max);
            String insert = "insert into Members(MEMBER_id ,member_name,address,BirthDate,email ,occupation,contact_no,pass_word,gender)"
                    + " VALUES (" + Integer.toString(max + 1);
            for (int i = 0; i < s.length; i++) {
                // s[i]= "aa" ;
                if (i == 2) {
                    insert = insert + ",TO_DATE('" + s[i] + "','dd/mm/yyyy')";
                } else {
                    insert = insert + ",'" + s[i] + "'";
                }
            }

            insert = insert + ")";
            System.out.println("string: " + insert);
            r = stmt.executeQuery(insert);

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    ResultSet retrievemembers() {
        try {
            stmt = c.createStatement();
            String query;
            query = "Select* from members";
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    ResultSet retrieveFromTable(String Table) {
        try {
            stmt = c.createStatement();
            String query;
            query = "Select* from " + Table;
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    ResultSet retrieveBookInfo(String bookName) {
        try {
            stmt = c.createStatement();
            String query;
            query = "Select BOOK_NAME,LANGUAGE,DATE_OF_PUBLICATION,GENRE,PREQUEL from book where book_name= '"
                    + bookName + "'";
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    ResultSet retrieveAuthorName(String bookName) {
        try {
            stmt = c.createStatement();
            String query;
            query = "select author_name\n" + "from AUTHOR\n" + "where author_id = (select author_id\n"
                    + " from WRITER\n" + "where book_id=(select book_id from book  where book_name= '" + bookName
                    + "'))";
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    ResultSet retrievePublisherName(String bookName) {
        try {
            stmt = c.createStatement();
            String query;
            query = "select PUBLICATION_Name\n" + "from book b join PUBLICATION p\n"
                    + "on(b.publisher_id=p.PUBLICATION_id)\n" + "where b.book_name='" + bookName + "'";

            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    ResultSet retrieveBookList() {
        try {
            stmt = c.createStatement();
            String query;
            query = "Select book_name from book";

            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    ResultSet retrieveBookCount() {
        try {
            stmt = c.createStatement();
            String query;
            query = "Select count(*) from book";
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    ResultSet retrievePrequel(String bookname) {
        try {
            stmt = c.createStatement();
            String query;
            query = "select B2.BOOK_NAME from book b1 join book b2\n" + "on(b1.PREQUEL=B2.BOOK_ID) where B1.BOOK_NAME='"
                    + bookname + "'";
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    ResultSet retrieveInfo(String dataType, String s, String Tablename) {
        try {
            // Class.forName("oracle.jdbc.driver.OracleDriver");

            // c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:globaldb","Library","123456789");
            stmt = c.createStatement();

            String query = "select* from " + Tablename + " where " + dataType + " = ";
            if (s.matches("[0-9]+") == true) {
                query = query + s;
            } else {
                query = query + "'" + s + "'";
            }
            System.out.println(query);
            r = stmt.executeQuery(query);
            System.out.println("in query\n");
            // try {
            // while (r.next())
            // {
            // System.out.println(r.getString(i+1));
            // i++;
            // }
        } catch (Exception e) {
            System.out.println("error in query");
        }
        return r;
    }

    ResultSet retrievecontainInfo(String bookid, String shelfid) {
        try {
            String query = new String();
            query = "Select* \n From Contain \n Where book_id = " + bookid + " and " + "shelf_id = " + shelfid;
            System.out.println(query);
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            System.out.println("executed");

        } catch (Exception ex) {

            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);

            // return false;
        }
        return r;
    }

    boolean sendBorrowRequest(String[] insert) {
        try {
            String query = new String();
            query = "Select Max (Request_id) from Book_Request";
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            while (r.next()) {
                // max = r.getInt(i);
                System.out.println("entered");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    ResultSet RetrieveInfo(String column_name, String val, String table) {
        // boolean isunique = false;
        try {
            String query = "Select* from " + table + " where " + column_name + " = '" + val + "'";

            stmt = c.createStatement();
            r = stmt.executeQuery(query);

        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;

    }

    //
    ResultSet searchinTable(String name, String column, String Table) {
        try {
            String query = "Select* from " + Table + " where " + column + " Like '%" + name + "%'";
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            // while (r.next())
            // {
            // System.out.println(""+r.getString("Member_name"));
            // }

        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    boolean checkUniqueEntry(String column_name, String website, String table) {
        boolean isunique = false;
        try {
            String query = "Select* from " + table + " where " + column_name + " = '" + website + "'";

            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            if (r.next() == false) {
                isunique = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

        return isunique;
    }

    // check if the place for shelf is occupied or not
    boolean checkIsOccupied(String floor_no, String row, String column) {
        boolean isoccupied = false;
        try {
            String query = "Select* from shelf  where floor_no = " + floor_no + " and row_no = " + row
                    + " and column_no = " + column;

            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            if (r.next() == false) {
                isoccupied = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

        return isoccupied;
    }

    boolean isValidEntry(String column_name, String entry, String table) {
        try {
            String query = "Select* from " + table + " where " + column_name + " ='" + entry + "'";
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            if (r.next()) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
