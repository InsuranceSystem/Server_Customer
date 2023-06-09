package ListImpl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Dao.CustomerDao;
import Exception.DaoException;
import Interface.Contract;
import Interface.Customer;
import Interface.CustomerList;

public class CustomerListImpl implements CustomerList, Remote {
	// 만기계약대상자/미납대상자/부활대상자 enum
	public enum TargetType {
		EXPIRED_CONTRACTS, UNPAID_CUSTOMERS, RESURRECT_CANDIDATES
	}

	private ArrayList<Customer> customerList;
	private ArrayList<Customer> expiredContracts; // 만기계약 대상자 리스트
	private ArrayList<Customer> unpaidCustomers; // 미납대상자 리스트
	private ArrayList<Customer> resurrectCandidates; // 부활대상자 리스트
//		private ContractListImpl contractList;
	private CustomerDao customerDao;

	public CustomerListImpl() throws Exception {
		this.customerDao = new CustomerDao();
		this.customerList = customerDao.retrieveAll();
	}
	@Override
	public ArrayList<Customer> getResurrectCandidates(boolean resurrection, List<Contract> contractList)
			throws RemoteException {
		resurrectCandidates = new ArrayList<Customer>();
	    HashMap<String, Boolean> customerMap = new HashMap<String, Boolean>(); // 중복 호출 방지를 위한 맵

	    for (Customer customer : customerList) {
	        if (customerMap.containsKey(customer.getCustomerID())) {
	            continue;
	        }

	        for (Contract contract : contractList) {
	            if (customer.getCustomerID().equals(contract.getCustomerID())) {
	                if (contract.isResurrection() == resurrection) {
	                    resurrectCandidates.add(customer);
	                    customerMap.put(customer.getCustomerID(), true);
	                    break;
	                }
	            }
	        }
	    }
	    return resurrectCandidates;
	}

	@Override
	public ArrayList<Customer> getExpiredContracts(boolean maturity, List<Contract> contractList)
			throws RemoteException {
		expiredContracts = new ArrayList<Customer>(); // 만기계약 리스트
		HashMap<String, Boolean> customerMap = new HashMap<String, Boolean>(); // 중복 호출 방지를 위한 맵

		for (Customer customer : customerList) {
			if (customerMap.containsKey(customer.getCustomerID())) {
				continue; // 이미 출력된 고객이므로 중복 호출 방지
			}

			for (Contract contract : contractList) {
				if (customer.getCustomerID().equals(contract.getCustomerID())) {
					if (contract.isMaturity()) {
						// 새로 만듬 -> 1 3 4
						// 1 3 4 -> 1 2 3 4
						expiredContracts.add(customer);
						customerMap.put(customer.getCustomerID(), true); // 고객 ID를 맵에 추가
						break; // 해당 고객의 계약이 만기되었으므로 다음 고객으로 넘어감
					}
				}
			}
		}
		return expiredContracts;
	}

	public boolean add(Customer customer) {
		if (this.customerList.add(customer))
			return true;
		else
			return false;
	}

	public void setRetrieve(ArrayList<Customer> customerList) {
		this.customerList = customerList;
	}

	// 6. retrieveCustomer
	public Customer retrieveCustomer(String customerID) {
		// 지정된 ID를 가진 고객 찾기
		for (Customer customer : customerList) {
			if (customer.getCustomerID().equals(customerID)) {
				return customer;
			}
		}
		// Customer가 없을 때
		return null;
	}

	// 이름 & 휴대전화로 고객 정보 찾기
	public String getCustomerIdFromNameAndPH(String customerName, String customerPH) {
		for (Customer customer : customerList) {
			if (customer.getCustomerName().equals(customerName) && customer.getPnumber().equals(customerPH)) {
				return customer.getCustomerID();
			}
		}
		return null;

	}

	public Customer retrieveCustomerFromResurrect(String customerID) {
		for (Customer customer : resurrectCandidates) {
			if (customer.getCustomerID().equals(customerID)) {
				return customer;
			}
		}
		return null;
	}

	public Customer retrieveCustomerFromExpired(String customerID) {
		for (Customer customer : expiredContracts) {
			if (customer.getCustomerID().equals(customerID)) {
				return customer;
			}
		}
		return null;
	}
	@Override
	public boolean deleteResurrectCandidatesCustomer(Customer customer) throws RemoteException {
		return resurrectCandidates.remove(customer);
	}
	@Override
	public boolean deleteExpiredCustomer(Customer customer) throws RemoteException {
		return expiredContracts.remove(customer);
	}
//
//		public boolean deleteUnpaidCustomer(Customer customer) { // 미납 대상자에서 제외
//			return unpaidCustomers.remove(customer);
//		}

//		public static List<Contract> getContractFromCustomerId(String id, ContractListImpl contractListImpl)
//				throws Exception {
//			List<Contract> selectedContracts = new ArrayList<Contract>();
//			ArrayList<Contract> contracts = contractListImpl.retrieve();
//			for (Contract contract : contracts) {
//				if (contract.getCustomerID().equals(id)) {
//					selectedContracts.add(contract);
//				}
//			}
//			// 여기서 한 사람당 계약 정보가 둘 이상일 때 첫 번째 계약만 값을 받아옴
//			return selectedContracts;
//		}

	public Customer getCustomerByID(String customerID) {
		for (int i = 0; i < this.customerList.size(); i++) {
			Customer customer = (Customer) this.customerList.get(i);
			if (customer.matchId(customerID))
				return customer;
		}
		return null;
	}

	public ArrayList<Customer> retrieve() {
		return customerList;
	}

	public boolean delete(String customerID) {
		for (Customer customer : this.customerList) {
			if (customer.getCustomerID().equals(customerID)) {
				if (this.customerList.remove(customer))
					return true;
				break;
			}
		}
		return false;
	}

	public boolean update(Customer newCustomer, String customerID) throws RemoteException, DaoException {
		for (Customer customer : customerList) {
			if (customer.getCustomerID().equals(customerID)) {
				customer.setAddress(newCustomer.getAddress());
				customer.setBirth(newCustomer.getBirth());
				customer.setCustomerName(newCustomer.getCustomerName());
				customer.setEGender(newCustomer.getEGender());
				customer.setJob(newCustomer.getJob());
				customer.setPnumber(newCustomer.getPnumber());
				System.out.println(customer);
				customerDao.update(customer);
			}
		}
		return true;
	}

	public Customer retrieveCustomerFromUnpaid(String customerID) {
		for (Customer customer : unpaidCustomers) {
			if (customer.getCustomerID().equals(customerID)) {
				return customer;
			}
		}
		return null;
	}
}
