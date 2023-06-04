package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Exception.DaoException;
import Interface.FamilyHistory;

public class FamilyHistoryDao extends Dao {
	public FamilyHistoryDao() {
		try {
			super.connect();
		} catch (Exception e) {
			System.out.println("데이터베이스 연결에 실패했습니다." + e.getMessage());
			System.out.println("DAO Exception 발생한 메서드: " + ((DaoException) e).getDaoMethodName());
		}
	}

	public void create(FamilyHistory familyHistory) throws DaoException {
		String query = "INSERT INTO FamilyHistory (customerID, diseaseName, relationship) VALUES (?, ?, ?)";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, familyHistory.getCustomerID());
			statement.setString(2, familyHistory.getDiseaseName());
			statement.setString(3, familyHistory.getRelationship());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("FamilyHistory 생성에 실패했습니다.", "create");
		}
	}

	public ResultSet retrieveByCustomerID(String customerID) throws DaoException {
		String query = "SELECT * FROM FamilyHistory WHERE customerID = ?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, customerID);
			return statement.executeQuery();
		} catch (SQLException e) {
			throw new DaoException("customerID로 FamilyHistory 조회에 실패했습니다.", "retrieveByCustomerID");
		}
	}

	public void deleteByCustomerId(String customerID) throws DaoException {
		String query = "DELETE FROM FamilyHistory WHERE customerID = ?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, customerID);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("customerID로 FamilyHistory 삭제에 실패했습니다.", "deleteByCustomerId");
		}
	}

	public void deleteAll() throws DaoException {
		String query = "DELETE FROM FamilyHistory";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("FamilyHistory 전체 삭제에 실패했습니다.", "deleteAll");
		}
	}

	public ArrayList<FamilyHistory> retrieveAll() throws DaoException {
		String query = "SELECT * FROM FamilyHistory";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			ArrayList<FamilyHistory> familyHistories = new ArrayList<>();
			while (resultSet.next()) {
				FamilyHistory familyHistory = new FamilyHistory();
				familyHistory.setCustomerID(resultSet.getString("customerID"));
				familyHistory.setDiseaseName(resultSet.getString("diseaseName"));
				familyHistory.setRelationship(resultSet.getString("relationship"));
				familyHistories.add(familyHistory);
			}
			return familyHistories;
		} catch (SQLException e) {
			throw new DaoException("FamilyHistory 전체 조회에 실패했습니다.", "retrieveAll");
		}
	}
}