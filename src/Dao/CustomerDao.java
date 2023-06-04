package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Exception.DaoException;
import Interface.Customer;
import Interface.Customer.EGender;
import Interface.CustomerList;

public class CustomerDao extends Dao {
	public CustomerDao() throws Exception {

		try {
			super.connect();
		} catch (Exception e) {
			System.out.println("데이터베이스 연결에 실패했습니다." + e.getMessage());
			System.out.println("DAO Exception 발생한 메서드: " + ((DaoException) e).getDaoMethodName());
		}
	}

	public void create(Customer customer) throws DaoException {
		String query = "INSERT INTO Customer (customerID, customerName, job, pnumber, birth, eGender, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, customer.getCustomerID());
			statement.setString(2, customer.getCustomerName());
			statement.setString(3, customer.getJob());
			statement.setString(4, customer.getPnumber());
			statement.setString(5, customer.getBirth());
			statement.setString(6, customer.getEGender().getGenderStr());
			statement.setString(7, customer.getAddress());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Customer 생성에 실패했습니다.", "create");
		}
	}

	public ArrayList<Customer> retrieveAll() throws DaoException {
		String query = "SELECT * FROM Customer";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			ArrayList<Customer> customerList = new ArrayList<>();
			while (resultSet.next()) {
				Customer customer = new Customer();
				customer.setCustomerID(resultSet.getString("customerID"));
				customer.setCustomerName(resultSet.getString("customerName"));
				customer.setJob(resultSet.getString("job"));
				customer.setPnumber(resultSet.getString("pnumber"));
				customer.setBirth(resultSet.getString("birth"));
				for (EGender gender : EGender.values()) {
					if (gender.getGenderStr().equals(resultSet.getString("eGender")))
						customer.setEGender(gender);
				}
				customer.setAddress(resultSet.getString("address"));
				customerList.add(customer);
			}
			return customerList;
		} catch (SQLException e) {
			throw new DaoException("Customer 전체 조회에 실패했습니다.", "retrieveAll");
		}
	}

	public Customer retrieveByCustomerID(String customerID) throws DaoException {
		String query = "SELECT * FROM Customer WHERE customerID = ?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, customerID);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Customer customer = new Customer();
				customer.setCustomerID(resultSet.getString("customerID"));
				customer.setCustomerName(resultSet.getString("customerName"));
				customer.setJob(resultSet.getString("job"));
				customer.setPnumber(resultSet.getString("pnumber"));
				customer.setBirth(resultSet.getString("birth"));
				for (EGender gender : EGender.values()) {
					if (gender.getGenderStr().equals(resultSet.getString("eGender")))
						customer.setEGender(gender);
				}
				customer.setAddress(resultSet.getString("address"));
				return customer;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new DaoException("customerID로 Customer 조회에 실패했습니다.", "retrieveByCustomerID");
		}
	}

	public void update(Customer customer) throws DaoException {
		String query = "UPDATE Customer SET customerName = ?, birth = ?, eGender = ?, pnumber = ?, job = ?, address = ? WHERE customerID = ?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, customer.getCustomerName());
			statement.setString(2, customer.getBirth());
			statement.setString(3, customer.getEGender().getGenderStr());
			statement.setString(4, customer.getPnumber());
			statement.setString(5, customer.getJob());
			statement.setString(6, customer.getAddress());
			statement.setString(7, customer.getCustomerID());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Customer 업데이트에 실패했습니다.", "update");
		}
	}

	public void deleteAll() throws DaoException {
		String query = "DELETE FROM Customer";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Customer 전체 삭제에 실패했습니다.", "deleteAll");
		}
	}

	public void deleteById(String customerID) throws DaoException {
		String query = "DELETE FROM Customer WHERE customerID = ?";
		try (PreparedStatement statement = connect.prepareStatement(query)) {
			statement.setString(1, customerID);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("customerID로 Customer 삭제에 실패했습니다.", "deleteById");
		}
	}
}