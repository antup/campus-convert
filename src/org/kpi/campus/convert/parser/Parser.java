package org.kpi.campus.convert.parser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Class for parsing xml-task file.
 *
 * @author Anton Bidkov
 *
 */
public class Parser {

	@SuppressWarnings("serial")
	public class ParserException extends Exception {
		public ParserException(String message) {
			super(message);
		}
	}

	private Schema schema;

	/**
	 * Create new Parser instance. Required XML Schema file witch include
	 * rules for xml-file.
	 * @param validate XML Schema file for validation.
	 * @throws SAXException if any problem with XML Schema occur.
	 */
	public Parser(URL validate) throws SAXException {
		try {
			String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
			SchemaFactory factory = SchemaFactory.newInstance(language);
			schema = factory.newSchema(validate);
		} catch (SAXException e) {
			throw new SAXException("XML Schema error! " + e.getMessage());
		}
	}

	/**
	 * Method parse xml-task file. It use XML Schema file for validation and 
	 * DOM for parsing.
	 * 
	 * @param task xml-task file for parsing
	 * @param validate XML Schema file for validation task.
	 * 
	 * @return task representation in ArrayList of Database objects.
	 * @throws ParserException if any error will be found in xml-task file.
	 * @throws ParserConfigurationException if a DocumentBuilder cannot be created.
	 * @throws IOException if any I/O problems occur with task or validate files.
	 */
	public ArrayList<Database> parseTask(File task) throws
	ParserException, ParserConfigurationException, IOException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);

		boolean valid = true;
		try {
			//current parser may not support XML Schema
			factory.setSchema(schema);
			valid = false;
		} catch (UnsupportedOperationException e) {
			//we will use validator
		}

		DocumentBuilder builder = factory.newDocumentBuilder();

		builder.setErrorHandler(new ParserErrorHandler());

		Document doc = null;
		try {
			doc = builder.parse(task);
		} catch (SAXException e) {
			throw new ParserException(e.getMessage());
		}

		if (valid) {
			//if parser not support XML Schema
			//we use validator
			Validator validator = schema.newValidator();
			try {
				validator.validate(new DOMSource(doc));
			} catch (SAXException e) {
				throw new ParserException(e.getMessage());
			}
		}
		
		Element root = doc.getDocumentElement();

		NodeList dblist = root.getElementsByTagName("database");

		ArrayList<Database> dbs = new ArrayList<Database>();
		for (int i = 0; i < dblist.getLength(); i++) {
			dbs.add(parseDatabase((Element)dblist.item(i)));
		}

		return dbs;
	}

	private Database parseDatabase(Element elem) throws ParserException {
		NodeList convertList = elem.getElementsByTagName("convert");

		String csFrom = elem.getAttribute("csFrom");
		String csTo = elem.getAttribute("csTo");
		DBType dbTypeFrom = null;
		DBType dbTypeTo = null;

		try {
			dbTypeFrom = (DBType) Enum.valueOf(DBType.class, elem.
					getAttribute("dbTypeFrom").toUpperCase());
			dbTypeTo = (DBType) Enum.valueOf(DBType.class, elem.
					getAttribute("dbTypeTo").toUpperCase());	
		} catch (IllegalArgumentException ex) {
			throw new ParserException("Bad xml-file! Bad database type.");
		}
		if (csFrom.isEmpty() || csTo.isEmpty()) {
			throw new ParserException("Bad xml-file! Some attributes not found" +
					" or empty in <database>.");
		}

		Database db = new Database(csFrom, dbTypeFrom, csTo, dbTypeTo);

		for (int i = 0; i < convertList.getLength(); i++) {
			db.addConvert(parseConvert((Element)convertList.item(i)));
		}

		return db;
	}

	private Convert parseConvert(Element elem) throws ParserException {
		NodeList from = elem.getElementsByTagName("tableFrom");
		NodeList to = elem.getElementsByTagName("tableTo");

		String fromName = ((Element)from.item(0)).getAttribute("name");
		String toName = ((Element)to.item(0)).getAttribute("name");

		if (fromName.isEmpty()) {
			throw new ParserException("Bad xml-file! Attribute \"name\" not found" +
				" or empty in <tableFrom>.");
		}
		if (toName.isEmpty()) {
			throw new ParserException("Bad xml-file! Attribute \"name\" not found" +
			" or empty in <tableTo>.");
		}
		
		NodeList fromColumns = ((Element) from.item(0)).getElementsByTagName("column");
		NodeList toColumns = ((Element) to.item(0)).getElementsByTagName("column");
		
		if (fromColumns.getLength() != toColumns.getLength()) {
			throw new ParserException("Bad xml-file! Different number" +
				" <column> in <tableFrom> and <tableTo>.");
		}

		Convert c = new Convert(fromName, toName);

		Column cfrom;
		Column cto;
		for (int i = 0; i < fromColumns.getLength(); i++) {
			Element elFrom = (Element) fromColumns.item(i);
			Element elTo = (Element) toColumns.item(i);
			String colFrom = elFrom.getAttribute("name");
			String colTo = elTo.getAttribute("name");

			if (colFrom.isEmpty() || colTo.isEmpty()) {
				throw new ParserException("Bad xml-file!" +
						" Attribute \"name\" not found in <column>.");
			}

			cfrom = new Column(colFrom, elFrom.getAttribute("modifier"),
					elFrom.getAttribute("type"));
			cto = new Column(colTo, elTo.getAttribute("modifier"),
					elTo.getAttribute("type"));
			c.addColumns(cfrom, cto);
		}

		return c;
	}

	class ParserErrorHandler implements ErrorHandler {

		@Override
		public void warning(SAXParseException exception) throws SAXException {
			System.out.println(exception.toString());
		}
		
		@Override
		public void fatalError(SAXParseException exception) throws SAXException {
			throw new SAXException("Line: " + exception.getLineNumber() + 
					" Column: " + exception.getColumnNumber() + " "
					+ exception.getMessage());
		}
		
		@Override
		public void error(SAXParseException exception) throws SAXException {
			fatalError(exception);
		}	
	}
}