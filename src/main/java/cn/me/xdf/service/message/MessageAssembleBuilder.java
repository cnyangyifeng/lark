package cn.me.xdf.service.message;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.internal.util.xml.Origin;
import org.hibernate.internal.util.xml.OriginImpl;
import org.hibernate.internal.util.xml.XmlDocument;
import org.hibernate.internal.util.xml.XmlDocumentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.xml.sax.InputSource;


public class MessageAssembleBuilder implements ResourceLoaderAware{

	private static final Logger LOGGER = LoggerFactory
            .getLogger(MessageAssembleBuilder.class);
    private Map<String, String> messages;
	
    /**
     * 资源文件路径
     */
    private String[] fileName = new String[0];
    
    private ResourceLoader resourceLoader;
    
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

	public Map<String, String> getMessages() {
		return messages;
	}

	public void setMessages(Map<String, String> messages) {
		this.messages = messages;
	}

	public void setFileName(String[] fileName) {
        this.fileName = fileName;
    }

	public void init() throws IOException {
		 messages = new ConcurrentHashMap<String, String>();
	     boolean flag = this.resourceLoader instanceof ResourcePatternResolver;
	     if (flag) {
	         Resource[] resources = ((ResourcePatternResolver) this.resourceLoader)
	                 .getResources(fileName[0]);
	         buildMap(resources);
	     } else {
	         Resource resource = resourceLoader.getResource(fileName[0]);
	         buildMap(resource);
	     }
	}
	
	private void buildMap(Resource[] resources) throws IOException {
	     if (resources == null) {
	         return;
	     }
	     for (Resource resource : resources) {
	         buildMap(resource);
	     }
	 }
	
    private void buildMap(Resource resource) {
        InputSource inputSource = null;
        try {
            inputSource = new InputSource(resource.getInputStream());

            XmlDocument metadataXml = readMappingDocument(inputSource,
                    new OriginImpl("file", resource.getFilename()));
                final Document doc = metadataXml.getDocumentTree();
                final Element statement = doc.getRootElement();
                Iterator<?> rootChildren = statement
                        .elementIterator();
                while (rootChildren.hasNext()) {
                    final Element element = (Element) rootChildren.next();
                    final String elementName = element.getName();
                    if ("message".equals(elementName)) {
                    	 messages.put(element.attribute("name").getText(), element.getText());
                    }
                }
        } catch (Exception e) {
            LOGGER.error(e.toString());
            //throw new RuntimeException(e);
            e.printStackTrace();
        } finally {
            if (inputSource != null && inputSource.getByteStream() != null) {
                try {
                    inputSource.getByteStream().close();
                } catch (IOException e) {
                    LOGGER.error(e.toString());
                    throw new RuntimeException(e);
                }
            }
        }

    }
    
    public XmlDocument readMappingDocument(InputSource source, Origin origin) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(source);
            return new XmlDocumentImpl(document, origin.getType(), origin.getName());
        } catch (Exception orm2Problem) {
            orm2Problem.printStackTrace();
        }
        return null;
    }
}
