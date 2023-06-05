package ListImpl;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import Dao.FamilyHistoryDao;
import Exception.DaoException;
import Interface.FamilyHistory;
import Interface.FamilyHistoryList;

public class FamilyHistoryListImpl implements FamilyHistoryList, Remote{

	private FamilyHistoryDao familyHistoryDao;
	private ArrayList<FamilyHistory> familyHistoryList;

	public FamilyHistoryListImpl() throws SQLException, DaoException {
		familyHistoryDao = new FamilyHistoryDao();
		familyHistoryList = familyHistoryDao.retrieveAll();
	}

	public boolean add(FamilyHistory familyHistory) {
		if (this.familyHistoryList.add(familyHistory))
			return true;
		else
			return false;
	}

	public boolean delete(String customerID) {
		for (FamilyHistory familyHistory : this.familyHistoryList) {
			if (familyHistory.getCustomerID().equals(customerID)) {
				if (this.familyHistoryList.remove(familyHistory))
					return true;
				break;
			}
		}
		return false;
	}

	public boolean update(FamilyHistory familyHistory, String customerID) {
		for (FamilyHistory ufamilyHistory : this.familyHistoryList) {
			if (ufamilyHistory.getCustomerID().equals(customerID)) {
				ufamilyHistory.setDiseaseName(familyHistory.getDiseaseName());
				ufamilyHistory.setRelationship(familyHistory.getRelationship());
			}
		}
		return false;
	}

	public ArrayList<FamilyHistory> retrieve() {
		return familyHistoryList;
	}

	public void setRetrieve(ArrayList<FamilyHistory> familyHistoryList) {
		this.familyHistoryList = familyHistoryList;
	}

	public FamilyHistory getFamilyHistoryFromId(String id) throws RemoteException { // 고객 아이디에 맞는 가족력 반환
		   ArrayList<FamilyHistory> familyHistories = familyHistoryList;
		   for(FamilyHistory familyHistory : familyHistories) {
			   if (familyHistory.getCustomerID().equals(id)) {
				   return familyHistory;
			   }
		   }
		   return null;
	   }

    public ArrayList<FamilyHistory> getFamilyHistoryByCID(String customerID) {
		ArrayList<FamilyHistory> familyHistories = new ArrayList<FamilyHistory>();
		for(int i=0;i<this.familyHistoryList.size();i++) {
			if(this.familyHistoryList.get(i).matchCID(customerID))
				familyHistories.add(this.familyHistoryList.get(i));
		}
		return familyHistories;
    }
	public ArrayList<FamilyHistory> getAllFamilyHistoryFromId(String id) throws RemoteException {
		ArrayList<FamilyHistory> matchingFamilyHistories = new ArrayList<FamilyHistory>();

		for (FamilyHistory familyHistory : familyHistoryList) {
			if (familyHistory.getCustomerID().equals(id)) {
				matchingFamilyHistories.add(familyHistory);
			}
		}

		return matchingFamilyHistories;
	}



	
}
