package model;

public class Document {

	private String docName;
	private int docID;
	private double score;
	
	public Document(int docID, String docName)
	{
		this.docID = docID;
		this.docName = docName;
		score = 0;
	}
	
	public void setdocName(String docName)
	{
		this.docName = docName;
	}
	
	public void setdocID(int docID)
	{
		this.docID = docID;
	}
	
	public String getdocName()
	{
		return docName;
	}
	
	public int getdocID()
	{
		return docID;
	}
	
	public double getScore()
	{
		return score;
	}
	
	public void setScore(double score)
	{
		this.score = score;
	}
	
}
