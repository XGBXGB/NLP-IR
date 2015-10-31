package reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBModel {
	private PreparedStatement ps;
	private DBConnector connector;
	
	private Connection connection;
	
	public DBModel() {
		connector = DBConnector.getInstance();
		connection = connector.getConnection();
	}
	
	public void insertWord(String word) {
		String query = "INSERT INTO words (word) VALUES (?)";
		
		if(!wordExist(word)) {
			try {
				ps = connection.prepareStatement(query);
				ps.setString(2, word);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getLastInsertedId() {
		String query = "SELECT LAST_INSERT_ID();";
		try {
			ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public void insertWordFile(int wordId, int fileId) {
		String query = "INSERT INTO wordsfiles (wordId, fileId) VALUES (?, ?)";
		try {
			ps = connection.prepareStatement(query);
			ps.setInt(1, wordId);
			ps.setInt(2, fileId);
			ps.executeUpdate();
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
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void inserFile(String name) {
		String query = "INSERT INTO files (fileName) VALUES (?)";
			try {
				ps = connection.prepareStatement(query);
				ps.setString(2, name);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
