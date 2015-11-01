package reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class DBModel {

    private PreparedStatement ps;
    private DBConnector connector;

    private Connection connection;

    public DBModel() {
        connector = DBConnector.getInstance();
    }

    public void insertWord(String word) {
        String query = "INSERT INTO words (word) VALUES (?)";
        connection = connector.getConnection();
        if (!wordExist(word)) {
            try {
                ps = connection.prepareStatement(query);
                ps.setString(2, word);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException sqlee) {
                    sqlee.printStackTrace();
                }
            }
        }
    }

    public int getLastInsertedId() {
        String query = "SELECT LAST_INSERT_ID();";
        connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }

        return 0;
    }

    public void insertWordFile(int wordId, int fileId) {
        String query = "INSERT INTO wordsfiles (wordId, fileId) VALUES (?, ?)";
        connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, wordId);
            ps.setInt(2, fileId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }

    }

    public boolean wordExist(String word) {
        String query = "SELECT * FROM words WHERE word = ?";
        connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, word);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }

        return false;
    }

    public void inserFile(String name) {
        String query = "INSERT INTO files (fileName) VALUES (?)";
        connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(query);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
    }

    //Gives the filenames that contain the set of search terms
    public Iterator searchDocs(String terms, String exclusions) {
        connection = connector.getConnection();
        
        ArrayList<String> fileNames = new ArrayList();
        String[] words = terms.split(" ");
        String[] excludedWords = exclusions.split(" ");
        String query = "SELECT fileName FROM files WHERE fileId IN ";
        query += "(Select wf.fileId FROM words w, wordsFiles wf "
                + "WHERE w.word = \"" + words[0] + "\" AND w.wordId = wf.wordId)\n";

        if (words.length > 1) {
            for (int i = 1; i < words.length; i++) {
                query += "AND id IN (Select wf.fileId FROM words w, wordsFiles wf "
                        + "WHERE w.word = \"" + words[i] + "\" AND w.wordId = wf.wordId);";
            }
        }
        
        if (excludedWords.length >= 1) {
            for (int i = 1; i < words.length; i++) {
                query += "AND id NOT IN (Select wf.fileId FROM words w, wordsFiles wf "
                        + "WHERE w.word = \"" + excludedWords[i] + "\" AND w.wordId = wf.wordId);";
            }
        }
        
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                fileNames.add(rs.getString("fileName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
        return fileNames.iterator();
    }
}
