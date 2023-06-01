package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Counsel.CounselApplicationListImpl;
import Counsel.CounselListImpl;
import Customer.CustomerListImpl;
import Customer.FamilyHistoryListImpl;
import Counsel.CounselApplicationList;
import Counsel.CounselList;
import Customer.CustomerList;
import Customer.FamilyHistoryList;




public class CustomerServer extends UnicastRemoteObject implements Customer_ServerIF{
	private static final long serialVersionUID = 1L;
	private static CustomerList CustomerList;
	private static FamilyHistoryList FamilyHistoryList;
	private static CounselList CounselList;
	private static CounselApplicationList CounselApplicationList;
	
	protected CustomerServer() throws RemoteException {
		super();
	}
	
	public static void main(String[] args) throws Exception {
		try {
			CustomerServer server = new CustomerServer();
			Naming.rebind("CustomerServer", server);		

			CustomerList = new CustomerListImpl();
			FamilyHistoryList = new FamilyHistoryListImpl();
			CounselList = new CounselListImpl();
			CounselApplicationList = new CounselApplicationListImpl();
					
			System.out.println("Customer Server is ready !!!");		
	
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();	
		}
	}
	@Override
	public CustomerList getCustomerList() {
		return CustomerList;
	}

	@Override
	public FamilyHistoryList getFamilyHistoryList() {
		return FamilyHistoryList;
	}
	@Override
	public CounselList getCounselList() {
		return CounselList;
	}

	@Override
	public CounselApplicationListImpl getCounselApplicationList() {
		return CounselApplicationList;
	}
	
}
