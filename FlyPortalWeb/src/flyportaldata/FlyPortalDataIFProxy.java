package flyportaldata;

public class FlyPortalDataIFProxy implements flyportaldata.FlyPortalDataIF {
  private String _endpoint = null;
  private flyportaldata.FlyPortalDataIF flyPortalDataIF = null;
  
  public FlyPortalDataIFProxy() {
    _initFlyPortalDataIFProxy();
  }
  
  public FlyPortalDataIFProxy(String endpoint) {
    _endpoint = endpoint;
    _initFlyPortalDataIFProxy();
  }
  
  private void _initFlyPortalDataIFProxy() {
    try {
      flyPortalDataIF = (new flyportaldata.FlyPortalDataServiceLocator()).getFlyPortalDataPort();
      if (flyPortalDataIF != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)flyPortalDataIF)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)flyPortalDataIF)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (flyPortalDataIF != null)
      ((javax.xml.rpc.Stub)flyPortalDataIF)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public flyportaldata.FlyPortalDataIF getFlyPortalDataIF() {
    if (flyPortalDataIF == null)
      _initFlyPortalDataIFProxy();
    return flyPortalDataIF;
  }
  
  public java.lang.String[] getAirlines() throws java.rmi.RemoteException{
    if (flyPortalDataIF == null)
      _initFlyPortalDataIFProxy();
    return flyPortalDataIF.getAirlines();
  }
  
  public flyportaldata.Airport[] getAirports() throws java.rmi.RemoteException{
    if (flyPortalDataIF == null)
      _initFlyPortalDataIFProxy();
    return flyPortalDataIF.getAirports();
  }
  
  
}