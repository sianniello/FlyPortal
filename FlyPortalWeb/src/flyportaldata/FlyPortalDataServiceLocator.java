/**
 * FlyPortalDataServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package flyportaldata;

public class FlyPortalDataServiceLocator extends org.apache.axis.client.Service implements flyportaldata.FlyPortalDataService {

    public FlyPortalDataServiceLocator() {
    }


    public FlyPortalDataServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FlyPortalDataServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FlyPortalDataPort
    private java.lang.String FlyPortalDataPort_address = "http://stefano-pc:8080/FlyPortalDataWebService/FlyPortalDataService";

    public java.lang.String getFlyPortalDataPortAddress() {
        return FlyPortalDataPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FlyPortalDataPortWSDDServiceName = "FlyPortalDataPort";

    public java.lang.String getFlyPortalDataPortWSDDServiceName() {
        return FlyPortalDataPortWSDDServiceName;
    }

    public void setFlyPortalDataPortWSDDServiceName(java.lang.String name) {
        FlyPortalDataPortWSDDServiceName = name;
    }

    public flyportaldata.FlyPortalDataIF getFlyPortalDataPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FlyPortalDataPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFlyPortalDataPort(endpoint);
    }

    public flyportaldata.FlyPortalDataIF getFlyPortalDataPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            flyportaldata.FlyPortalDataPortBindingStub _stub = new flyportaldata.FlyPortalDataPortBindingStub(portAddress, this);
            _stub.setPortName(getFlyPortalDataPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFlyPortalDataPortEndpointAddress(java.lang.String address) {
        FlyPortalDataPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (flyportaldata.FlyPortalDataIF.class.isAssignableFrom(serviceEndpointInterface)) {
                flyportaldata.FlyPortalDataPortBindingStub _stub = new flyportaldata.FlyPortalDataPortBindingStub(new java.net.URL(FlyPortalDataPort_address), this);
                _stub.setPortName(getFlyPortalDataPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("FlyPortalDataPort".equals(inputPortName)) {
            return getFlyPortalDataPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://flyportaldata/", "FlyPortalDataService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://flyportaldata/", "FlyPortalDataPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FlyPortalDataPort".equals(portName)) {
            setFlyPortalDataPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
