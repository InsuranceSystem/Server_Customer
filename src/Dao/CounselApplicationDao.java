package Dao;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import Exception.DaoException;
import Interface.CounselApplication;

public class CounselApplicationDao extends Dao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public CounselApplicationDao() {
		try {
			super.connect();
		} catch (Exception e) {
			System.out.println("데이터베이스 연결에 실패했습니다." + e.getMessage());
			System.out.println("DAO Exception 발생한 메서드: " + ((DaoException) e).getDaoMethodName());
		}
	}

	public void create(CounselApplication counselApplication) throws DaoException {
		String query = "INSERT INTO counselApplication (category, counselID, customerID, dateOfFirst, dateOfSecond, requirement) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, counselApplication.getCategory());
			statement.setString(2, counselApplication.getCounselID());
			statement.setString(3, counselApplication.getCustomerID());
			statement.setObject(4, counselApplication.getDateOfFirst());
			statement.setObject(5, counselApplication.getDateOfSecond());
			statement.setString(6, counselApplication.getRequirement());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("CounselApplication 생성에 실패했습니다.", "create");
		}
	}

	public ResultSet retrieveByCustomerID(String customerID) throws DaoException {
		String query = "SELECT * FROM counselApplication WHERE customerID = ?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, customerID);
			return statement.executeQuery();
		} catch (SQLException e) {
			throw new DaoException("customerID로 CounselApplication 조회에 실패했습니다.", "retrieveByCustomerID");
		}
	}

	public ArrayList<CounselApplication> retrieveAll() throws DaoException {
		String query = "SELECT * FROM counselApplication";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			ArrayList<CounselApplication> counselApplicationList = new ArrayList<>();
			while (resultSet.next()) {
				CounselApplication counselApplication = new CounselApplication();
				counselApplication.setCategory(resultSet.getString("category"));
				counselApplication.setCounselID(resultSet.getString("counselID"));
				counselApplication.setCustomerID(resultSet.getString("customerID"));
				counselApplication.setDateOfFirst(resultSet.getObject("dateOfFirst", LocalDate.class));
				counselApplication.setDateOfSecond(resultSet.getObject("dateOfSecond", LocalDate.class));
				counselApplication.setRequirement(resultSet.getString("requirement"));
				counselApplicationList.add(counselApplication);
			}
			return counselApplicationList;
		} catch (SQLException e) {
			throw new DaoException("CounselApplication 전체 조회에 실패했습니다.", "retrieveAll");
		}
	}

	public void deleteByCustomerId(String customerID) throws DaoException {
		String query = "DELETE FROM counselApplication WHERE customerID = ?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, customerID);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("customerID로 CounselApplication 삭제에 실패했습니다.", "deleteByCustomerId");
		}
	}

	public void deleteAll() throws DaoException {
		String query = "DELETE FROM counselApplication";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("CounselApplication 전체 삭제에 실패했습니다.", "deleteAll");
		}
	}

	public void update(CounselApplication counselApplication) throws DaoException {
		String query = 
				"UPDATE counselApplication "
				+ "SET counselID = ?, category = ?, customerID = ?, "
				+ "dateOfFirst = ?, dateOfSecond = ?, requirement = ? "
				+ "WHERE customerID = ?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, counselApplication.getCounselID());
			statement.setString(2, counselApplication.getCategory());
			statement.setString(3, counselApplication.getCustomerID());
			statement.setObject(4, counselApplication.getDateOfFirst());
			statement.setObject(5, counselApplication.getDateOfSecond());
			statement.setString(6, counselApplication.getRequirement());
			statement.setString(7, counselApplication.getCustomerID());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("CounselApplication 업데이트에 실패했습니다.", "update");
		}
	}
}