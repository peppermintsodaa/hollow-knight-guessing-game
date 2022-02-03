/**
 * TreeReader class to demonstrate XML file reading
 * @author ponbarry
 */

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class GameTreeReader {
	private File xmlFile;

	/**
	 * constructor
	 * @param filename
	 */
	public GameTreeReader(String filename) {
		this.xmlFile = new File (filename);
	}

	/**
	 * buildGameTree
	 * @return GameTree based on XML file.
	 */
	public GameTree<String> buildGameTree() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);
			return parseGameTree(document);
		} catch (SAXException sxe) {
			Exception  x = sxe;
			if (sxe.getException() != null)
				x = sxe.getException();
			x.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	/**
	 * Parses XML Document.
	 * @return GameTree.
	 */
	private GameTree<String> parseGameTree(Document document) {
		Element xmlRootElement = (Element)document.getDocumentElement();
		GameTree<String> dt = new GameTree<String>();

		// assume root element is a question
		dt.setRoot(parseQuestionNode(xmlRootElement));
		return dt;
	}

	/**
	 * Parses a question element. Adopts convention that "yes" user
	 * responses become left child nodes, and "no" user responses become
	 * right child nodes.
	 * @return a GameTreeNode object based on the XML Element passed in.
	 */
	private GameTreeNode<String> parseQuestionNode(Element element) {
	    if (!element.getTagName().equals("question")) {
			System.err.println("Error: expecting question tag");
		}

		String questionText = element.getAttribute("value");
		GameTreeNode<String> questionNode = new GameTreeNode<String>(questionText);

		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i) instanceof Element) {
				Element childNode = (Element)children.item(i);
				String user_response = childNode.getAttribute("value");

				if (user_response.equals("yes")) {
					questionNode.setLeftChild(parseAnswerNode(childNode));
				} else if (user_response.equals("no")) {
					questionNode.setRightChild(parseAnswerNode(childNode));
				} else {
					System.err.println("Error in XML file: not a valid answer");
				}
    	  	}
		}
		return questionNode;
	}

	/**
	 * Parses an answer element.
	 * @return a GameTreeNode object based on the XML Element passed in.
	 */
	private GameTreeNode<String> parseAnswerNode(Element element) {
	    if (!element.getTagName().equals("answer")) {
			System.err.println("Error: expecting answer tag");
		}

		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
	        if ( children.item(i) instanceof Element ) {
	        	Element child = (Element)children.item(i);

	        	//base case: if child is Thing element
	        	if (child.getTagName().equals("thing")) {
	        		return new GameTreeNode<String>(child.getTextContent());

	        	//recursive case: child is a question node
	        	} else if (child.getTagName().equals("question")) {
	        		return parseQuestionNode(child);
	        	} else {
	        		System.err.println("Error: child of answer should be a thing or a question");
	        	}
	        }
		}
		return null;
	}
}
