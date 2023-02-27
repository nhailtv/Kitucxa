package DAO;

import java.util.ArrayList;

public interface DAOinterface<T> {
	public int Add(T t);

	public int Update(T t);
	
	public int Delete(T t);
	
	public ArrayList<T> SelectAll();
	
	public T SelectByID(T t);
	
	public ArrayList<T> SelectByCondition(String Condition);
}
