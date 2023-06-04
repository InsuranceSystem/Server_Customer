

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import ListImpl.CounselApplicationListImpl;
import ListImpl.CounselListImpl;
import ListImpl.CustomerListImpl;
import ListImpl.FamilyHistoryListImpl;
import Interface.CounselApplicationList;
import Interface.CounselList;
import Interface.CustomerList;
import Interface.FamilyHistoryList;





public class CustomerServer extends UnicastRemoteObject{
	private static final long serialVersionUID = 1L;
	
	
	protected CustomerServer() throws RemoteException {
		super();
	}
	
	public static void main(String[] args) throws Exception {
		try {
			System.setProperty("java.security.policy", "policy.txt");
			System.setSecurityManager(null);	

			
			
			
			
			
			CustomerList customerList = new CustomerListImpl();
			CustomerList stub1 = (CustomerList) UnicastRemoteObject.exportObject(customerList, 0);
			Registry registry1 = LocateRegistry.createRegistry(1305);
		    registry1.rebind("CustomerList", stub1);
			
			FamilyHistoryList familyHistoryList = new FamilyHistoryListImpl();
			FamilyHistoryList stub2 = (FamilyHistoryList) UnicastRemoteObject.exportObject(familyHistoryList, 0);
			Registry registry2 = LocateRegistry.createRegistry(1306);
		    registry2.rebind("FamilyHistoryList", stub2);
			
			CounselList counselList = new CounselListImpl();
			CounselList stub3 = (CounselList) UnicastRemoteObject.exportObject(counselList, 0);
			Registry registry3 = LocateRegistry.createRegistry(1307);
		    registry3.rebind("CounselList", stub3);
			
			CounselApplicationList counselApplicationList = new CounselApplicationListImpl();
			CounselApplicationList stub4 = (CounselApplicationList) UnicastRemoteObject.exportObject(counselApplicationList, 0);

	        Registry registry4 = LocateRegistry.createRegistry(1308);
	        registry4.rebind("CounselApplicationList", stub4);
	

		       
		        
		        // SurveyList 객체 등록
	
		      
			
		       
		        
		        // SurveyList 객체 등록
	
			

			System.out.println("Customer Server is ready !!!");		
	
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();	
		}
	}

	
}
