package it.unipr.ce.dsg.nam4j.utils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import it.unipr.ce.dsg.dao.MysqlDAO;

public class MysqlConnDB {

	MysqlDAO mysqlDAO = null;
	
	String db_username = "marcomuro";
	String db_password = "P3X-984";
	String db_address  = "jdbc:mysql://localhost/prologKnowledge"; 
	
	public MysqlConnDB(){
		 this.mysqlDAO = new MysqlDAO();
	}

	public MysqlDAO getMysqlDAO() {
		return mysqlDAO;
	}

	public void setMysqlDAO(MysqlDAO mysqlDAO) {
		this.mysqlDAO = mysqlDAO;
	}
	
	public void connectToDB(){
		mysqlDAO.connectToDatabase(db_address,db_username,db_password);
	}
	
	public void prepareQuery(String queryStructure){
		mysqlDAO.prepareStatement(queryStructure);
	}
	
	public void setDataQuery(int numOfValues, ArrayList<Object> values) throws Exception{
		
		for(int i = 0; i < numOfValues; i++){
			
			if(values.get(i) instanceof String)
				mysqlDAO.setStringValue(i+1, (String) values.get(i));
			else if (values.get(i) instanceof Integer)
				mysqlDAO.setIntValue(i+1, (Integer) values.get(i));
			else if (values.get(i) instanceof Boolean)
				mysqlDAO.setBooleanValue(i+1, (Boolean) values.get(i));
			else if (values.get(i) instanceof Date)
				mysqlDAO.setDateValue(i+1, (Date) values.get(i));
			else if (values.get(i) instanceof Double)
				mysqlDAO.setDoubleValue(i+1, (Double) values.get(i));
			else throw new Exception("unknow data type");
			}
		
	}
	
	public ResultSet executeSelectQuery(){
		return mysqlDAO.executeSqlSelectPrst();
	}
	
	public ArrayList<Object> executeSelectQuerywithMappedResult() throws SQLException{
		ArrayList<Object> results = new ArrayList<Object>();
		ResultSet resultsFromDB =  mysqlDAO.executeSqlSelectPrst();
		
		ResultSetMetaData rsmd = resultsFromDB.getMetaData();
		int cc = rsmd.getColumnCount();
		
//		System.out.println("num columns: " + cc);
//		for (int j = 1; j <= cc; j++) {
//			System.out.println("column name: " + rsmd.getColumnName(j));
//		}
		
		resultsFromDB.last();
		int i = resultsFromDB.getRow();
//		System.out.println("num rows: " + i);
		if (i > 0) { 
			resultsFromDB.beforeFirst(); 
			while (resultsFromDB.next()) {
				for (int j = 1; j <= cc; j++)
					if (resultsFromDB.getObject(j) != null){
//						System.out.print(resultsFromDB.getObject(j).toString() + " ");
						results.add(resultsFromDB.getObject(j));
					}
					else{
						//System.out.print("NULL ");
						results.add(new String("NULL"));
					}
//				System.out.print("\n");
			} 
		}

		
		return results;
	}
	
	public int executeInsertQuery(){
		return mysqlDAO.executeSqlInsertPrst();
	}
	
	public int executeDeleteQuery(){
		return mysqlDAO.executeSqlDeletePrst();
	}
	
}
