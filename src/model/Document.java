package model;

public class Document {

	private String docName;
	private int docID;
	
	public Document(int docID, String docName)
	{
		this.docID = docID;
		this.docName = docName;
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
	
}
