package com.erms.models;

public class Receipt {
	private int id;

	private int employeeId;

	private int managerId;

	private String employeeName;

	private String manager;

	private float amount;

	private String status;

	private String description;

	private String title;

	private byte[] receipt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getManager() {
		return manager;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte[] getReceipt() {
		return receipt;
	}

	public void setReceipt(byte[] receipt) {
		this.receipt = receipt;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getManagerName() {
		// TODO Auto-generated method stub
		return manager;
	}

	public int getManagerId() {
		// TODO Auto-generated method stub
		return managerId;
	}

	public void setManagerId(int managerId2) {
		// TODO Auto-generated method stub
		this.managerId = managerId2;
	}

}
