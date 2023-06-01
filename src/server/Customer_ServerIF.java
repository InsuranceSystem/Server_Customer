package server;
import Counsel.CounselApplicationList;
import Counsel.CounselList;
import Customer.CustomerList;
import Customer.FamilyHistoryList;

public interface Customer_ServerIF {

	public CustomerList getCustomerList();
	public FamilyHistoryList getFamilyHistoryList();
	public CounselList getCounselList();
	public CounselApplicationList getCounselApplicationList();
}
