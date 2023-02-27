package model;

public class SinhVien {
	private String sinhVien_ID;
	private String sinhVien_Name;
	private String sinhVien_Mail;
	private String sinhVien_Sdt;
	private String sinhVien_Username;
	private String sinhVien_Password;
	private String room_ID;
	

	
	public String getSinhVien_ID() {
		return sinhVien_ID;
	}



	public String getRoom_ID() {
		return room_ID;
	}



	public void setRoom_ID(String room_ID) {
		this.room_ID = room_ID;
	}



	public void setSinhVien_ID(String sinhVien_ID) {
		this.sinhVien_ID = sinhVien_ID;
	}



	public String getSinhVien_Name() {
		return sinhVien_Name;
	}


	public void setSinhVien_Name(String sinhVien_Name) {
		this.sinhVien_Name = sinhVien_Name;
	}


	public String getSinhVien_Mail() {
		return sinhVien_Mail;
	}



	public void setSinhVien_Mail(String sinhVien_Mail) {
		this.sinhVien_Mail = sinhVien_Mail;
	}





	public String getSinhVien_Sdt() {
		return sinhVien_Sdt;
	}





	public void setSinhVien_Sdt(String sinhVien_Sdt) {
		this.sinhVien_Sdt = sinhVien_Sdt;
	}





	public String getSinhVien_Username() {
		return sinhVien_Username;
	}





	public void setSinhVien_Username(String sinhVien_Username) {
		this.sinhVien_Username = sinhVien_Username;
	}





	public String getSinhVien_Password() {
		return sinhVien_Password;
	}





	public void setSinhVien_Password(String sinhVien_Password) {
		this.sinhVien_Password = sinhVien_Password;
	}





	public SinhVien() {
		super();
	}





	public SinhVien(String sinhVien_ID, String sinhVien_Name, String sinhVien_Mail, String sinhVien_Sdt,
			String sinhVien_Username, String sinhVien_Password) {
		super();
		this.sinhVien_ID = sinhVien_ID;
		this.sinhVien_Name = sinhVien_Name;
		this.sinhVien_Mail = sinhVien_Mail;
		this.sinhVien_Sdt = sinhVien_Sdt;
		this.sinhVien_Username = sinhVien_Username;
		this.sinhVien_Password = sinhVien_Password;
	}
	
	
}
