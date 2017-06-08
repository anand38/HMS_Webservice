package com.service;

import java.io.StringReader;
import java.io.StringWriter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
		account=null;
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
	 
	 @Path("/getdetails")
	 @PUT
	 @Consumes({MediaType.APPLICATION_XML})
	 @Produces({MediaType.APPLICATION_XML})
	public String getname(String xml){
		 String n="";
		 String email="";
		 String id="";
		 Document doc = convertStringToDocument(xml);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("candidate");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					email=eElement.getElementsByTagName("email").item(0).getTextContent();
				}
			}
			Account account=new Account();
			try{
			n=account.getName(email);
			id=account.getId(email);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		return getxml(id,n);
	}
	 
	 private String getxml(String id,String name){
		 
		  StringWriter writer=null;
	        try {

	            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

	            // root elements
	            Document doc = docBuilder.newDocument();
	            Element rootElement = doc.createElement("candidate");
	            doc.appendChild(rootElement);

	          
	            Element ID = doc.createElement("id");
	            ID.appendChild(doc.createTextNode(id));
	            rootElement.appendChild(ID);
	            
	            Element NAME = doc.createElement("name");
	            NAME.appendChild(doc.createTextNode(name));
	            rootElement.appendChild(NAME);

	            // write the content into xml file
	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();
	            DOMSource source = new DOMSource(doc);
	            writer=new StringWriter();
	            // Output to console for testing
	            //StreamResult result = new StreamResult(System.out);

	            transformer.transform(source, new StreamResult(writer));

	        } catch (ParserConfigurationException pce) {
	            pce.printStackTrace();
	        } catch (TransformerException tfe) {
	            tfe.printStackTrace();
	        }
	        //System.out.println("XmlString:"+writer.toString());
	        return writer.toString();
	 }
}
