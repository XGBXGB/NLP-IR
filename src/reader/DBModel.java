package reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import model.Document;

public class DBModel {

    private PreparedStatement ps;
    private DBConnector connector;

    private Connection connection;

    public DBModel() {
        connector = DBConnector.getInstance();
        connection = connector.getConnection();
    }

    public void insertWord(String word) {
        String subQuery = "SELECT * FROM words WHERE word = \""+word+"\";";
        String query = "INSERT INTO words (word) VALUES (?)";
        if (!wordExist(word)) {
            try {
                ps = connection.prepareStatement(subQuery);
                ResultSet rs = ps.executeQuery();
                if(!rs.next()){
                    ps = connection.prepareStatement(query);
                    ps.setString(1, word);
                    ps.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    //Returns true if wordid and fileid is already in wordfiles table
    public boolean checkWordFiles(String word, String fileName) {
        String query;
            try {
                query = "SELECT * FROM wordsfiles WHERE wordId = (SELECT wordId FROM words WHERE word = ?)  AND fileId = (SELECT fileId FROM files WHERE fileName = ?)";
                ps = connection.prepareStatement(query);
                ps.setString(1, word);
                ps.setString(2, fileName);
                
                ResultSet rs = ps.executeQuery();
                if(rs.next())
                    return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return false;
    }

    public int getLastInsertedId() {
        String query = "SELECT LAST_INSERT_ID();";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 

        return 0;
    }

    public void insertWordFile(String word, double tf, String fileName) {
        String subQuery;
        
        String query = "INSERT INTO wordsfiles (wordId, fileId, termFreq) VALUES (?, ?, ?)";
        try {
            subQuery = "SELECT wordId FROM words WHERE word = \""+word+"\";";
            ps = connection.prepareStatement(subQuery);
            ResultSet wID = ps.executeQuery();
            wID.next();
            int wordId = wID.getInt(1);
            
            subQuery = "SELECT fileId FROM files WHERE fileName = \""+fileName+"\"";
            ps = connection.prepareStatement(subQuery);
            ResultSet fID = ps.executeQuery();
            fID.next();
            int fileId = fID.getInt(1);
            ps = connection.prepareStatement(query);
            ps.setInt(1, wordId);
            ps.setInt(2, fileId);
            ps.setDouble(3, tf);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean wordExist(String word) {
        String query = "SELECT * FROM words WHERE word = ?";
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
        } 
        
        return false;
    }

    public void insertFile(String name) {
        String query = "INSERT INTO files (fileName) VALUES (?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
    
    public int getNumOfDocsOfWord(int id){
        String query = "SELECT COUNT(*) FROM wordsFiles WHERE wordId = "+id;
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return 0;
    }
    
    public int getNumOfDocs(){
        String query = "SELECT COUNT(*) FROM files";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return 0;
    }
    
    public void updateIDF(){
        String query = "SELECT wordId FROM words ";
        final int numOfDocs = getNumOfDocs();
        double idf;
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("wordId: "+rs.getInt("wordId"));
                System.out.println("numOfDocs: "+numOfDocs);
                System.out.println("numOfDocsOfWord: "+getNumOfDocsOfWord(rs.getInt("wordId")));
                idf = (Math.log((double)numOfDocs/(double)getNumOfDocsOfWord(rs.getInt("wordId"))));
                System.out.println("Resulting idf: "+idf);
                query = "UPDATE words SET inverseDocFreq = "+idf+" WHERE wordId = "+rs.getInt("wordId");
                ps = connection.prepareStatement(query);
                ps.execute();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    
    public void updateScore(){
        String query = "SELECT * FROM wordsfiles ";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            String score;
            while (rs.next()) {
                score = "(SELECT inverseDocFreq FROM words WHERE wordId = "+rs.getInt("wordId")+")*"+rs.getDouble("termFreq");
                query = "UPDATE wordsfiles SET score = "+score+" WHERE wordId = "+rs.getInt("wordId")+" AND fileId = "+rs.getInt("fileId");
                ps = connection.prepareStatement(query);
                ps.execute();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }

    //Gives the filenames that contain the set of search terms
    public Iterator searchDocs(String terms, String exclusions) {
        
       // ArrayList<String> fileNames = new ArrayList();
        ArrayList<Document> docs = new ArrayList<Document>();
    	String[] words = terms.split(" ");
        String[] excludedWords = exclusions.split(" ");
        String query = "SELECT * FROM files WHERE fileId IN ";
        query += "(Select wf.fileId FROM words w, wordsFiles wf "
                + "WHERE w.word = \"" + words[0] + "\" AND w.wordId = wf.wordId ORDER BY score DESC)\n";

        if (words.length > 1) {
            for (int i = 1; i < words.length; i++) {
                query += "AND fileId IN (Select wf.fileId FROM words w, wordsFiles wf "
                        + "WHERE w.word = \"" + words[i] + "\" AND w.wordId = wf.wordId);";
            }
        }
        
        if (excludedWords.length >= 1) {
        	System.out.println("Went in excluded");
            for (int i = 0; i < excludedWords.length; i++) {
                query += "AND fileId NOT IN (Select wf.fileId FROM words w, wordsFiles wf "
                        + "WHERE w.word = \"" + excludedWords[i] + "\" AND w.wordId = wf.wordId);";

            }
        }
        
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //fileNames.add(rs.getString("fileName"));
            	docs.add(new Document(rs.getInt("fileId"), rs.getString("fileName")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return docs.iterator();
    }
}
