/**
 * FlyPortalDataService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package flyportaldata;

public interface FlyPortalDataService extends javax.xml.rpc.Service {
    public java.lang.String getFlyPortalDataPortAddress();

    public flyportaldata.FlyPortalDataIF getFlyPortalDataPort() throws javax.xml.rpc.ServiceException;

    public flyportaldata.FlyPortalDataIF getFlyPortalDataPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
