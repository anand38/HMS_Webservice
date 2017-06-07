package com.service;

import java.io.StringReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.model.Account;

@Path("/login_service")
public class Login_service {

	@Path("/getloginstatus")
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes({MediaType.APPLICATION_XML})
	public String gelogintstatus(String xml){
		String email="";
		String password=""; 
		Document doc = convertStringToDocument(xml);
		doc.getDocumentElement().normalize();
		
		NodeList nList = doc.getElementsByTagName("candidate");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				email=eElement.getElementsByTagName("email").item(0).getTextContent();
				password=eElement.getElementsByTagName("password").item(0).getTextContent();
			}
		}
		Account account=new Account();
		boolean status=false;
		try{
		status=account.check_user(email, password);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(status){
			return "success";
		}
		return "failed";
	}
	
	
	 private static Document convertStringToDocument(String xmlStr) {
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	        DocumentBuilder builder;  
	        try  
	        {  
	            builder = factory.newDocumentBuilder();  
	            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
	            return doc;
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } 
	        return null;
	    }
	 
	 @Path("/getsome")
	 @GET
	 @Produces(MediaType.TEXT_PLAIN)
	 public String getsome(){
		 return "hello world";
	 }
	
}
