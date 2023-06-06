package Dao;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import Exception.DaoException;
import Interface.Counsel;

public class CounselDao extends Dao implements Serializable{
	public CounselDao() {
		try {
			super.connect();
		} catch (Exception e) {
			System.out.println("데이터베이스 연결에 실패했습니다." + e.getMessage());
			System.out.println("DAO Exception 발생한 메서드: " + ((DaoException) e).getDaoMethodName());
		}
	}

	public void create(Counsel counsel) throws DaoException {
		String query = "INSERT INTO Counsel (counselID, customerID, content, managerName, dateOfCounsel) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, counsel.getCounselID());
			statement.setString(2, counsel.getCustomerID());
			statement.setString(3, (counsel.getContent() == null ? "-" : counsel.getContent()));
			statement.setString(4, (counsel.getManagerName() == null ? "-" : counsel.getManagerName()));
			if (counsel.getDateOfCounsel() == null) statement.setNull(5, java.sql.Types.DATE);
		    else statement.setObject(5, counsel.getDateOfCounsel());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Counsel 생성에 실패했습니다.", "create");
		}
	}

	public ArrayList<Counsel> retrieveAll() throws DaoException {
		String query = "SELECT * FROM Counsel";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			ArrayList<Counsel> counselList = new ArrayList<>();
			while (resultSet.next()) {
				Counsel counsel = new Counsel();
				counsel.setCounselID(resultSet.getString("counselID"));
				counsel.setCustomerID(resultSet.getString("customerID"));
				counsel.setContent(resultSet.getString("content"));
				counsel.setManagerName(resultSet.getString("managerName"));
				counsel.setDateOfCounsel(resultSet.getObject("dateOfCounsel", LocalDate.class));
				counselList.add(counsel);
			}
			return counselList;
		} catch (SQLException e) {
			throw new DaoException("Counsel 전체 조회에 실패했습니다.", "retrieveAll");
		}
	}

	public void deleteByCustomerId(String customerID) throws DaoException {
		String query = "DELETE FROM Counsel WHERE customerID = ?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, customerID);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("customerID로 Counsel 삭제에 실패했습니다.", "deleteByCustomerId");
		}
	}

	public void deleteByCounselId(String counselID) throws DaoException {
		String query = "DELETE FROM Counsel WHERE counselID = ?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, counselID);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("counselID로 Counsel 삭제에 실패했습니다.", "deleteByCounselId");
		}
	}

	public void deleteAll() throws DaoException {
		String query = "DELETE FROM Counsel";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Counsel 전체 삭제에 실패했습니다.", "deleteAll");
		}
	}

//	public void updateCounsel(Counsel updateCounsel) throws DaoException {
//		String query = "UPDATE Counsel SET content = ? WHERE CounselID = ?";
//		try (PreparedStatement statement = connect.prepareStatement(query)) {
//			statement.setString(1, updateCounsel.getContent());
//			statement.setString(2, updateCounsel.getCounselID());
//			statement.executeUpdate();
//		} catch (SQLException e) {
//			throw new DaoException("Counsel 업데이트에 실패했습니다.", "updateCounsel");
//		}
//	}
	public void updateCounsel(Counsel updateCounsel) throws DaoException {
	    String query = "UPDATE Counsel SET customerID = ?, content = ?, managerName = ?, dateOfCounsel = ? WHERE counselID = ?";
	    try (PreparedStatement statement = connect.prepareStatement(query)) {
	        statement.setString(1, updateCounsel.getCustomerID());
	        statement.setString(2, updateCounsel.getContent());
	        statement.setString(3, updateCounsel.getManagerName());
	        statement.setObject(4, updateCounsel.getDateOfCounsel());
	        statement.setString(5, updateCounsel.getCounselID());
	        statement.executeUpdate();
	    } catch (SQLException e) {
	        throw new DaoException("Counsel 업데이트에 실패했습니다.", "updateCounsel");
	    }
	}

	
	
}