package Counsel;
import java.rmi.Remote;
import java.util.ArrayList;

import Dao.CounselApplicationDao;
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
		if(this.CounselApplicationList.add(counselApplication)) {
			int listSize = CounselApplicationList.size();
			CounselApplicationList.get(listSize-1).setCounselID("C" + listSize);
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
	public ArrayList<CounselApplication> retrieve() {
		return null;
	}
	public CounselApplication getCounselApplicationById(String id) {
		for (CounselApplication counselApplication : CounselApplicationList) {
			if (counselApplication.getCustomerID().equals(id)) 
				return counselApplication;
		}
		return null;
	}
}
