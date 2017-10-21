package controller;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import model.Libro;

/**
 * 
 * Clase que implementa un CRUD básico con XML
 * 
 * @author Lux Ferre
 *
 */
public class DataXML {

	private Document document;
	private String filepath;
	private NodeList mainNode;

	public DataXML() {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			// documento Raiz
			document = implementation.createDocument(null, "bookstore", null);
			document.setXmlVersion("1.0");

		} catch (ParserConfigurationException e) {
		}
		filepath = "E:\\libreria.xml";

	}

	public static void main(String[] args) {

		DataXML dataXML = new DataXML();
		dataXML.buildChildLibrary(new Libro(1, "Terror", "IT", "Stephen King", 2015, 20.000));
		dataXML.buildChildLibrary(new Libro(2, "Terror", "The crow", "Edgar Allan Poe", 1979, 50.000));
		dataXML.buildChildLibrary(new Libro(3, "Terror", "The omen", "NA", 1968, 100.000));
		dataXML.listDocument();
//		 System.out.println("Elimnando");
//		 dataXML.removeNodes(2);
//		 System.out.println("Loading...");
//		 dataXML.listDocument();
//		System.out.println();
//		Libro lib = new Libro(2, "Comedy", "The crow", "Aronofsky", 1989, 50.000);
//		System.out.println("Updating..");
//		dataXML.changeNode(2, lib);
//		System.out.println("New List..");
//		dataXML.listDocument();
		
//		System.out.println("<<<< Objeto Libro >>>>");
//		Libro lib = dataXML.getNodeValue(3);
//		System.out.println(lib.getCategoria());
//		System.out.println(lib.getTitulo());
//		System.out.println(lib.getAutor());
//		System.out.println(lib.getAnio());
//		System.out.println(lib.getPrecio());
		
	}

	/**
	 * 
	 * Create new element node (book)
	 * 
	 * @param libro
	 */
	public void buildChildLibrary(Libro libro1) {

		// construccion de documento XML
		// Creación de elementos
		Element libro = document.createElement("book");
		Element categoria = document.createElement("category");
		Element titulo = document.createElement("title");
		Element autor = document.createElement("author");
		Element anio = document.createElement("year");
		Element price = document.createElement("price");

		document.getDocumentElement().appendChild(libro);

		libro.setAttribute("id", String.valueOf(libro1.getId()));

		libro.appendChild(categoria);
		libro.appendChild(titulo);
		libro.appendChild(autor);
		libro.appendChild(anio);
		libro.appendChild(price);

		Text cat = document.createTextNode(libro1.getCategoria());
		categoria.appendChild(cat);
		Text tit = document.createTextNode(libro1.getTitulo());
		titulo.appendChild(tit);
		Text aut = document.createTextNode(libro1.getAutor());
		autor.appendChild(aut);
		Text year = document.createTextNode(String.valueOf(libro1.getAnio()));
		anio.appendChild(year);
		Text pri = document.createTextNode(String.valueOf(libro1.getPrecio()));
		price.appendChild(pri);

		write();

	}

	/**
	 * 
	 * Get All DOM XML values
	 * 
	 */
	public void listDocument() {

		// Obtiene los nodos hijo de la raiz
		mainNode = document.getDocumentElement().getChildNodes();

		System.out.println("-------  -------  -------");

		for (int i = 0; i < mainNode.getLength(); i++) {

			NodeList p = mainNode.item(i).getChildNodes();

			for (int j = 0; j < p.getLength(); j++) {

				System.out.println(p.item(j).getNodeName() + ": " + p.item(j).getChildNodes().item(0).getNodeValue());
			}
			System.out.println("-------  -------  -------");
		}
	}

	/**
	 * Get object library for XML Document
	 * 
	 * @param element
	 * @return Object Libro
	 */
	public Libro getNodeValue(int element) {

		Libro lib = new Libro();
		NodeList y = getNode(element);

		lib.setCategoria(y.item(0).getChildNodes().item(0).getNodeValue());
		lib.setTitulo(y.item(1).getChildNodes().item(0).getNodeValue());
		lib.setAutor(y.item(2).getChildNodes().item(0).getNodeValue());
		lib.setAnio(Integer.parseInt(y.item(3).getChildNodes().item(0).getNodeValue()));
		lib.setPrecio(Double.parseDouble(y.item(4).getChildNodes().item(0).getNodeValue()));

		return lib;
	}
	
	/**
	 * 
	 * Get node root for attribute
	 * 
	 * @param element
	 * @return
	 */
	public NodeList getNode(int element) {
		
		mainNode = document.getDocumentElement().getChildNodes();

		NodeList node = null;

		for (int i = 0; i < mainNode.getLength(); i++) {

			String id = mainNode.item(i).getAttributes().item(0).getNodeValue();

			if (id.equals(String.valueOf(element))) {

				node = mainNode.item(i).getChildNodes();
			}
		}
		return node;
	}

	/**
	 * Change the text node value of the child element
	 * 
	 * @param element
	 * @param libro
	 */
	public void changeNode(int element, Libro libro) {

		NodeList x = getNode(element);
		x.item(0).getChildNodes().item(0).setNodeValue(libro.getCategoria());
		x.item(1).getChildNodes().item(0).setNodeValue(libro.getTitulo());
		x.item(2).getChildNodes().item(0).setNodeValue(libro.getAutor());
		x.item(3).getChildNodes().item(0).setNodeValue(String.valueOf(libro.getAnio()));
		x.item(4).getChildNodes().item(0).setNodeValue(String.valueOf(libro.getPrecio()));

		// Reescribir documento XML
		write();
	}

	/**
	 * 
	 * Remove a specified node for attributte & its child nodes are also removed
	 * 
	 * @param element
	 */
	public void removeNodes(int element) {

		for (int i = 0; i < mainNode.getLength(); i++) {

			String id = mainNode.item(i).getAttributes().item(0).getNodeValue();

			if (id.equals(String.valueOf(element))) {
				Node nodeRemove = mainNode.item(i);
				mainNode.item(i).getParentNode().removeChild(nodeRemove);
			}
		}
		// Reescribir documento XML
		write();
	}

	/**
	 * Write/rewrite text in document & path specified (txt)
	 * 
	 */
	public void write() {

		try {

			// hay q crearlo al inicio y escribir sobre el
			TransformerFactory transFact = TransformerFactory.newInstance();

			// Formateamos el fichero. Añadimos sangrado y la cabecera de XML
			transFact.setAttribute("indent-number", new Integer(3));
			Transformer trans = transFact.newTransformer();
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

			// Hacemos la transformación
			StringWriter sw = new StringWriter();
			StreamResult sr = new StreamResult(sw);
			DOMSource domSource = new DOMSource(document);
			trans.transform(domSource, sr);

			// cambia la forma en que se escribe en el documento
			PrintWriter writer = new PrintWriter(new FileWriter(filepath));
			writer.println(sw.toString());
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
