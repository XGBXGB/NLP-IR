package model;

import java.util.ArrayList;

import org.w3c.dom.css.DocumentCSS;
import org.w3c.dom.stylesheets.DocumentStyle;

public class Term {

	private String term;
	private ArrayList<Document> documents;
	
	public Term(String term)
	{
		this.term = term;
		documents = new ArrayList<Document>();
	}
	
	public Document getDocument(int x)
	{
		return documents.get(x);
	}
	
	public String getTerm()
	{
		return term;
	}
	
	public ArrayList<Document> getDocuments()
	{
		return documents;
	}
}
