package ListImpl;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Interface.CounselApplication;
import Dao.CounselApplicationDao;
import Exception.DaoException;
import Interface.CounselApplicationList;


public class CounselApplicationListImpl implements CounselApplicationList, Remote{

	private ArrayList<CounselApplication> CounselApplicationList;
	private CounselApplicationDao counselApplicationDao;

	public CounselApplicationListImpl() throws Exception {
		this.counselApplicationDao = new CounselApplicationDao();
		this.CounselApplicationList = counselApplicationDao.retrieveAll();
	}
	

	@Override
	public boolean add(CounselApplication counselApplication) throws Exception {
		int listSize = CounselApplicationList.size();
		counselApplication.setCounselID("C" + (listSize+1));
		if(this.CounselApplicationList.add(counselApplication)) {
			counselApplicationDao.create(counselApplication);
			return true;
		}
		else return false;
	}

	@Override
	public boolean delete(String counselID) {
		return false;
	}

	@Override
	public boolean update(CounselApplication counselApplication, String counselID) {
		return false;
	}

	@Override
	public ArrayList<CounselApplication> retrieve()throws RemoteException {
		return CounselApplicationList;
	}
	@Override
	public CounselApplication getCounselApplicationById(String id) throws RemoteException {
		for (CounselApplication counselApplication : CounselApplicationList) {
			if (counselApplication.getCounselID().equals(id)) 
				return counselApplication;
		}
		return null;
	}
	@Override
	public CounselApplication getCounselApplicationByCustomerId(String id) throws RemoteException {
		for (CounselApplication counselApplication : CounselApplicationList) {
			if (counselApplication.matchIdWithCustomer(id))
				return counselApplication;
		}
		return null;
	}
}
