package com.eclipserunner;

import static com.eclipserunner.Messages.Message_uncategorized;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.IRunnerModel;
import com.eclipserunner.model.RunnerModelProvider;
import com.eclipserunner.model.impl.CategoryNode;
import com.eclipserunner.model.impl.LaunchNode;

/**
 * Helper class for saving and loading plugin state.
 *
 * @author bary
 */
public class RunnerStateExternalizer {

	private static final String XML_VERSION_ATTR    = "version";
	private static final String XML_VERSION_VALUE   = "1.0";

	private static final String VERSION_ATTR        = "version";
	private static final String VERSION_VALUE       = "1.0.0";

	private static final String NAME_ATTR           = "name";
	private static final String BOOKMARK_ATTR       = "bookmark";
	private static final String DEFAULT_LAUNCH_MODE_ATTR       = "defaultLaunchMode";
	private static final String NODE_EXPANDED_ATTR  = "expanded";

	private static final String ROOT_NODE_NAME      = "runner";
	private static final String CATEGORY_NODE_NAME  = "category";
	private static final String LAUNCH_NODE_NAME    = "launchConfiguration";

	/**
	 * Load plugin state from given file.
	 *
	 * @param file Plugin state file.
	 * @throws CoreException
	 */
	public static void readRunnerModelFromFile(File file) throws CoreException {
		if (!file.exists()) {
			throw coreException("File not found '" + file.getAbsolutePath() + "'");
		}
		if (file.length() == 0) {
			throw coreException("File contains no data '" + file.getAbsolutePath() + "'");
		}

		Document runnerModelDocument = createDocumentFromRunnerModelFile(file);
		IRunnerModel runnerModel = getDefaultModel();

		populateRunnerModelFromDocument(runnerModel, runnerModelDocument);
		populateRunnerModelWithRemainingUncategorizedLaunchConfigurations(runnerModel);
	}

	private static Document createDocumentFromRunnerModelFile(File inputFile) throws CoreException {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(inputFile);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			return builder.parse(inputStream);
		} catch (Exception e) {
			throw coreException("Failed to load runner state");
		} finally {
			closeStream(inputStream);
		}
	}

	private static void populateRunnerModelFromDocument(IRunnerModel runnerModel, Document document) throws CoreException {
		Element runnerNode = document.getDocumentElement();
		String readVersion = runnerNode.getAttribute(VERSION_ATTR);
		if (!readVersion.equals(VERSION_VALUE)) {
			throw coreException("State file version '" + readVersion + "' not supported");
		}

		NodeList categoryNodeList = runnerNode.getElementsByTagName(CATEGORY_NODE_NAME);
		for (int categoryIndex = 0; categoryIndex < categoryNodeList.getLength(); categoryIndex++) {
			Element categoryElement = (Element) categoryNodeList.item(categoryIndex);
			String categoryNodeName = categoryElement.getAttribute(NAME_ATTR);

			ICategoryNode categoryNode = null;
			if (Message_uncategorized.equals(categoryNodeName)) {
				categoryNode = runnerModel.getDefaultCategoryNode();
			}
			else {
				categoryNode = new CategoryNode(categoryNodeName);
				categoryNode.setExpanded(Boolean.valueOf(categoryElement.getAttribute(NODE_EXPANDED_ATTR)));
				runnerModel.addCategoryNode(categoryNode);
			}

			NodeList launchNodeList = categoryElement.getElementsByTagName(LAUNCH_NODE_NAME);
			for (int i = 0; i < launchNodeList.getLength(); i++) {
				Element launchElement = (Element) launchNodeList.item(i);
				ILaunchConfiguration launchConfiguration = findLaunchConfigurationByName(launchElement.getAttribute(NAME_ATTR));

				if (launchConfiguration != null) {
					boolean isBookmarked = Boolean.valueOf(launchElement.getAttribute(BOOKMARK_ATTR));
					
					ILaunchNode launchNode = createLaunchCategory(launchConfiguration, isBookmarked);
					launchNode.setDefaultMode(launchElement.getAttribute(DEFAULT_LAUNCH_MODE_ATTR));
					categoryNode.add(launchNode);
				}
			}
		}
	}

	private static void populateRunnerModelWithRemainingUncategorizedLaunchConfigurations(IRunnerModel runnerModel) throws CoreException {
		Collection<ICategoryNode> categoryNodes = runnerModel.getCategoryNodes();

		// Get all launch configurations
		List<ILaunchConfiguration> launchConfigurations = new ArrayList<ILaunchConfiguration>(Arrays.asList(getLaunchManager().getLaunchConfigurations()));

		// Skip configurations which are already in model
		for(ICategoryNode categoryNode : categoryNodes) {
			for (ILaunchNode launchNode : categoryNode.getLaunchNodes()) {
				for (Iterator<ILaunchConfiguration> launchConfigurationIterator = launchConfigurations.iterator(); launchConfigurationIterator.hasNext();) {
					ILaunchConfiguration launchConfiguration = launchConfigurationIterator.next();
					if (launchConfiguration.equals(launchNode.getLaunchConfiguration())) {
						launchConfigurationIterator.remove();
						break;
					}
				}
			}
		}

		// add remaining configurations
		for(ILaunchConfiguration launchConfiguration : launchConfigurations) {
			ILaunchNode launchNode = new LaunchNode();
			launchNode.setLaunchConfiguration(launchConfiguration);
			runnerModel.addLaunchNode(launchNode);
		}
	}

	/**
	 * Load plugin default state.
	 *
	 * @throws CoreException
	 */
	public static void readDefaultRunnerModel() throws CoreException {
		IRunnerModel runnerModel = getDefaultModel();
		ILaunchManager launchManager = getLaunchManager();
		for (ILaunchConfiguration launchConfiguration : launchManager.getLaunchConfigurations()) {
			LaunchNode launchNode = new LaunchNode();
			launchNode.setLaunchConfiguration(launchConfiguration);
			runnerModel.getDefaultCategoryNode().add(launchNode);
		}
	}

	/**
	 * Save plugin state into given file.
	 *
	 * @param outputFile Plugin state file.
	 * @throws CoreException
	 */
	public static void writeRunnerModelToFile(File outputFile) throws CoreException {
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(outputFile);
			Document document = createDocumentFromRunnerModel(getDefaultModel());
			writeDocumentToStream(document, outputStream);
			outputStream.flush();
		} catch (IOException e) {
			throw coreException("Failed to save runner state");
		} finally {
			closeStream(outputStream);
		}
	}

	private static Document createDocumentFromRunnerModel(IRunnerModel runnerModel) throws CoreException {
		Document document = createDocument();

		Element rootElement = document.createElement(ROOT_NODE_NAME);
		rootElement.setAttribute(VERSION_ATTR, VERSION_VALUE);
		document.appendChild(rootElement);

		appendCategoryNodes(document, rootElement, runnerModel);
		return document;
	}

	private static Document createDocument() throws CoreException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			return documentBuilder.newDocument();
		} catch (ParserConfigurationException e) {
			throw coreException("Failed to create document");
		}
	}

	private static void appendCategoryNodes(Document document, Element rootElement, IRunnerModel runnerModel) {
		for (ICategoryNode categoryNode : runnerModel.getCategoryNodes()) {
			rootElement.appendChild(
				createCategoryElement(categoryNode, document)
			);
		}
	}

	private static Element createCategoryElement(ICategoryNode category, Document document) {
		Element categoryElement = document.createElement(CATEGORY_NODE_NAME);
		categoryElement.setAttribute(NAME_ATTR, category.getName());
		categoryElement.setAttribute(NODE_EXPANDED_ATTR, Boolean.toString(category.isExpanded()));
		for (ILaunchNode launchNode : category.getLaunchNodes()) {
			categoryElement.appendChild(
				createLaunchElement(launchNode, document)
			);
		}
		return categoryElement;
	}

	private static Element createLaunchElement(ILaunchNode launchNode, Document document) {
		Element launchElement = document.createElement(LAUNCH_NODE_NAME);
		ILaunchConfiguration launchConfiguration = launchNode.getLaunchConfiguration();
		launchElement.setAttribute(NAME_ATTR, launchConfiguration.getName());
		launchElement.setAttribute(BOOKMARK_ATTR, Boolean.toString(launchNode.isBookmarked()));
		launchElement.setAttribute(DEFAULT_LAUNCH_MODE_ATTR, launchNode.getDefaultMode());
		return launchElement;
	}

	private static void writeDocumentToStream(Document document, OutputStream outputStream) throws CoreException {
		Source source = new DOMSource(document);
		Result result = new StreamResult(outputStream);
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(XML_VERSION_ATTR, XML_VERSION_VALUE);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
		} catch (TransformerException e) {
			throw coreException("Failed to write document");
		}
	}

	private static ILaunchManager getLaunchManager() {
		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		return launchManager;
	}

	private static ILaunchConfiguration findLaunchConfigurationByName(String name) throws CoreException {
		for (ILaunchConfiguration launchConfiguration : getLaunchManager().getLaunchConfigurations()) {
			if (launchConfiguration.getName().equals(name)) {
				return launchConfiguration;
			}
		}
		return null;
	}

	private static ILaunchNode createLaunchCategory(ILaunchConfiguration launchConfiguration, boolean isBookmarked) {
		LaunchNode launchNode = new LaunchNode();
		launchNode.setLaunchConfiguration(launchConfiguration);
		launchNode.setBookmarked(isBookmarked);
		return launchNode;
	}

	private static CoreException coreException(String message) throws CoreException {
		return new CoreException(new Status(IStatus.ERROR, RunnerPlugin.PLUGIN_ID, message));
	}

	private static void closeStream(Closeable closable) {
		if (closable != null) {
			try {
				closable.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}

	private static IRunnerModel getDefaultModel() {
		return RunnerModelProvider.getInstance().getDefaultModel();
	}

}
