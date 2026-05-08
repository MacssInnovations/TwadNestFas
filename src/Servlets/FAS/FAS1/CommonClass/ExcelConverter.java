package Servlets.FAS.FAS1.CommonClass;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;

public class ExcelConverter {
	Connection connection;
	ResultSet resultSet;
	ResultSetMetaData result;
	FileWriter writer;
	LoadDriver load = new LoadDriver();	
	
	public void writeInCsvFormat(String query,String fileName) throws SQLException, IOException{         
      //String res="";
      connection = load.getConnection();
      PreparedStatement preparedStatement=connection.prepareStatement(query);
      //Hashtable hashprocess=new Hashtable();
      writer = new FileWriter(fileName);
      ResultSet local_rs=preparedStatement.executeQuery();
      ResultSetMetaData rsmd = local_rs.getMetaData();
      String columnName ="";
      String columnValue="";
      int NumOfCol=rsmd.getColumnCount();
      //res="<response>";
      //int row_count=0;
      for(int i=1;i<=NumOfCol;i++){
    	  columnName = rsmd.getColumnName(i);
          writer.append(columnName);
          writer.append(',');
      }
      writer.append('\n');
      while (local_rs.next()){
    	  //row_count++;          
          for (int i=1;i<=NumOfCol;i++){
              columnName = rsmd.getColumnName(i);     
              //System.out.println("check --> "+local_rs.getString(columnName));
              columnValue = local_rs.getString(columnName);
              writer.append(columnValue);
              writer.append(',');
              //res+=Obj.generateXML(ColumnName,  Obj.isNull(ColumnValue, 1), 1, Obj);
          }
          writer.append('\n');
      }
      //res+="<row_count>"+row_count+"</row_count></response>";
      local_rs.close();
      preparedStatement.close();     
      //return res;
    }

}
