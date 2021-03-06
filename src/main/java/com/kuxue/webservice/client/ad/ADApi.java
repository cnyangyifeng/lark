package com.kuxue.webservice.client.ad;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.1
 * 2013-12-11T16:12:44.266+08:00
 * Generated source version: 2.4.1
 * 
 */
@WebServiceClient(name = "ADApi", 
                  wsdlLocation = "http://192.168.1.12:866/ADApi.asmx?wsdl",
                  targetNamespace = "http://tempuri.org/") 
public class ADApi extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://tempuri.org/", "ADApi");
    public final static QName ADApiSoap = new QName("http://tempuri.org/", "ADApiSoap");
    public final static QName ADApiSoap12 = new QName("http://tempuri.org/", "ADApiSoap12");
    static {
        URL url = null;
        try {
            url = new URL("http://192.168.1.12:866/ADApi.asmx?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(ADApi.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://192.168.1.12:866/ADApi.asmx?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ADApi(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ADApi(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ADApi() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns ADApiSoap
     */
    @WebEndpoint(name = "ADApiSoap")
    public ADApiSoap getADApiSoap() {
        return super.getPort(ADApiSoap, ADApiSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ADApiSoap
     */
    @WebEndpoint(name = "ADApiSoap")
    public ADApiSoap getADApiSoap(WebServiceFeature... features) {
        return super.getPort(ADApiSoap, ADApiSoap.class, features);
    }
    /**
     *
     * @return
     *     returns ADApiSoap
     */
    @WebEndpoint(name = "ADApiSoap12")
    public ADApiSoap getADApiSoap12() {
        return super.getPort(ADApiSoap12, ADApiSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ADApiSoap
     */
    @WebEndpoint(name = "ADApiSoap12")
    public ADApiSoap getADApiSoap12(WebServiceFeature... features) {
        return super.getPort(ADApiSoap12, ADApiSoap.class, features);
    }

}
