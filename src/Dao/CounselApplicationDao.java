package Dao;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import Counsel.CounselApplication;
	public class CounselApplicationDao extends Dao{
		public CounselApplicationDao() {
			try {
				super.connect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void create(CounselApplication counselApplication) throws Exception {
			//쿼리 제조
			String query = "insert into counselApplication (category, counselID,customerID, dateOfFirst,dateOfSecond,requirement ) values ( " +
					" '" + counselApplication.getCategory()+ "', " +
					" '" + counselApplication.getCounselID()+ "', " +
					" '" + counselApplication.getCustomerID()+ "', " +
					" '" + counselApplication.getDateOfFirst()+ "', " +
					" '" + counselApplication.getDateOfSecond()+ "', " +
					" '" + counselApplication.getRequirement() + "')";
			super.create(query);
		}

		public ResultSet retrieveByCustomerID(String CustomerID) throws Exception {
			//쿼리 제조
			String query = "select * from CounselApplication where CustomerID ="+CustomerID+";";
			return super.retrieve(query);
		}
		
		public ArrayList<CounselApplication> retrieveAll() throws Exception {
			String query = "select * from CounselApplication;";
			ResultSet results = super.retrieve(query);
	        ArrayList<CounselApplication> CounselApplicationList = new ArrayList<CounselApplication>();
	        CounselApplication counselApplication;
	        while (results.next()){
	        	counselApplication = new CounselApplication(); 
	        	counselApplication.setCategory(results.getString("category"));
	        	counselApplication.setCounselID(results.getString("counselID"));
	        	counselApplication.setCustomerID(results.getString("customerID"));
	        	String dateString = resultSet.getString("dateOfFirst");
				LocalDate dateOfFirst = LocalDate.parse(dateString);
				counselApplication.setDateOfFirst(dateOfFirst);
				String dateString2 = resultSet.getString("dateOfSecond");
				LocalDate dateOfSecond = LocalDate.parse(dateString2);
				counselApplication.setDateOfSecond(dateOfSecond);
	        	counselApplication.setRequirement("requirement");
	        	CounselApplicationList.add(counselApplication);
	        }  
			return CounselApplicationList;
		}

		public void deleteByCustomerId(String CustomerID) throws Exception {
			//쿼리 제조
			String query = "DELETE FROM CounselApplication WHERE customerID="+CustomerID+";";
			super.delete(query);
		}
		public void deleteAll() throws Exception {
			//쿼리 제조
			String query = "DELETE FROM CounselApplication;";
			super.delete(query);
		}

		public void update(CounselApplication counselApplication) throws Exception {
			String query =  "UPDATE counselApplication SET counselID  = '"+counselApplication.getCounselID()+"',category ='"+counselApplication.getCategory()+"',customerID ='"+counselApplication.getCustomerID()+"',dateOfFirst ='"+counselApplication.getDateOfFirst()+"',dateOfSecond ='"+counselApplication.getDateOfSecond()+"',requirement='"+counselApplication.getRequirement()+"';";									 
			super.update(query);
		}

	}


