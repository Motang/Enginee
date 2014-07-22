package com.engineer.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableStructure {
	public static void main(String args[]){
		try {
//			Class.forName("org.hsqldb.jdbcDriver");
//			Connection cn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost","sa","");
			//DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection SWT_cn =
		     	DriverManager.getConnection (
		     			"jdbc:oracle:thin:@10.68.49.79:1521:mp2mch"
		     			, "mp2pcf"
		     			, "mp2pcf"
						   );
//			al(getTables(SWT_cn));
			al(getSchemas(SWT_cn));
			System.out.println(" ok!");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public static void al(Object obj){
		System.out.println(obj);
	}
	
	public static List<String> getTables(Connection cn, 
			String catalog, String schemaPattern, 
			String tableNamePattern, String[] types) throws SQLException{
		DatabaseMetaData dmd = cn.getMetaData();
		ResultSet rs = dmd.getTables(catalog, schemaPattern, tableNamePattern, types );	
		List<String> tables = new ArrayList<String>();
		//get all the tables
		while(rs.next()){
			String table_name = rs.getString("TABLE_NAME");
			tables.add(table_name);
		}
		rs.close();
		return tables;
	}
	
	public static List<String> getSchemas(Connection cn) throws SQLException{
        DatabaseMetaData dmd = cn.getMetaData();
		ResultSet rs = dmd.getSchemas();	
		List<String> schemas = new ArrayList<String>();
		//get all the tables
		while(rs.next()){
			String schemas_name = rs.getString("TABLE_SCHEM");
			schemas.add(schemas_name);
		}
		rs.close();
		return schemas;
	}
	
	
	//transfer the int rsColumns.getInt("DATA_TYPE") to String of hibernate types
	public static String getJAVASQLType(int i){
		switch (i){
		    case 0	: return "string";//"NULL"
			case 1	: return "string";//"CHAR"
//			case 2	: return "java.math.BigDecimal";//"NUMERIC"
			case 3	: return "double";//"DECIMAL"
			case 4	: return "long";//"INTEGER"
			case 5	: return "integer";//"SMALLINT"
			case 6	: return "float";//"FLOAT"
			case 7	: return "string";//"REAL"
			case 8	: return "double";//"DOUBLE"
			
			case 12	: return "string";//"VARCHAR"
			case 16	: return "boolean";//"BOOLEAN"
			case 70	: return "string";//"DATALINK"
//			case 91	: return "java.sql.Date";//"DATE"
//			case 92	: return "java.sql.Time";//"TIME"
//			case 93	: return "java.sql.Timestamp";//"TIMESTAMP"
			
			case -1	    : return "string";//"LONGVARCHAR"
			case -2	    : return "binary";//"BINARY"                   ==>hibernate type need to be transfer to Byte[] when generate java code
		    case -3	    : return "binary";//"VARBINARY"                ==>hibernate type need to be transfer to Byte[] when generate java code
		    case -4	    : return "binary";//"LONGVARBINARY"            ==>hibernate type need to be transfer to Byte[] when generate java code
//			case -5	    : return "java.math.BigInteger";//"BIGINT"
			case -6	    : return "short";//"TINYINT"
			case -7	    : return "byte";//"BIT"
//			case -8	    : return "java.sql.RowId";//"ROWID"
			case -9	    : return "string";//"NVARCHAR"
			case -15	: return "string";//"NCHAR"
			case -16	: return "string";//"LONGNVARCHAR"
			case 1111	: return "string";//"OTHER"
			case 2000	: return "string";//"JAVA_OBJECT"
			case 2001	: return "string";//"DISTINCT"
//			case 2002	: return "java.sql.Struct";//"STRUCT"
//			case 2003   : return "java.sql.Array";//"ARRAY"
//			case 2004	: return "java.sql.Blob";//"BLOB"
//			case 2005	: return "java.sql.Clob";//CLOB"
//			case 2006	: return "java.sql.Ref";//"REF"
			case 2009	: return "string";//"SQLXML"
//			case 2011	: return "java.sql.NClob";//"NCLOB"
			default : return "string";
		}
	}
	//Column name
	public static List<String> getColumnName(Connection cn, String table_name) throws SQLException{
		return getColumnInfo(cn, table_name, COLUMN_NAME);
	}
	
	//Column type
	public static List<String> getColumnType(Connection cn, String table_name) throws SQLException{
		return getColumnInfo(cn, table_name, DATA_TYPE);
	}
	//Column size
	public static List<String> getColumnSize(Connection cn, String table_name) throws SQLException{
		return getColumnInfo(cn, table_name, COLUMN_SIZE);
	}
	//Column not null
	public static List<String> getColumnNullable(Connection cn, String table_name) throws SQLException{
		return getColumnInfo(cn, table_name, IS_NULLABLE);
	}
	public static final String COLUMN_NAME = "COLUMN_NAME";
	public static final String IS_NULLABLE = "IS_NULLABLE";
	public static final String COLUMN_SIZE = "COLUMN_SIZE";
	public static final String DATA_TYPE = "DATA_TYPE";
	public static List<String> getColumnInfo(Connection cn, String table_name, String cont) throws SQLException{
		List result = new ArrayList();
		DatabaseMetaData dmd = cn.getMetaData();
		ResultSet rsColumns = dmd.getColumns(null, null, table_name,null );	
		if(COLUMN_NAME.equals(cont)){
			while(rsColumns.next()){
				result.add(rsColumns.getString("COLUMN_NAME"));
			}
		}else if(IS_NULLABLE.equals(cont)){
			while(rsColumns.next()){
				result.add(rsColumns.getString("IS_NULLABLE"));
			}
		}else if(COLUMN_SIZE.equals(cont)){
			while(rsColumns.next()){
				result.add(rsColumns.getInt("COLUMN_SIZE"));
			}
		}else if(DATA_TYPE.equals(cont)){
			while(rsColumns.next()){
				result.add(getJAVASQLType(rsColumns.getInt("DATA_TYPE")));
			}
		}
		return result;
	}
	
	//PKs column collection
	public static List<String> getPK(Connection cn, String table_name) throws SQLException{
		DatabaseMetaData dmd = cn.getMetaData();
		ResultSet rsPrimaryKeys = dmd.getPrimaryKeys(null, null, table_name);
		List<String> primaryKeysNames = new ArrayList<String>();
		while(rsPrimaryKeys.next()){
			primaryKeysNames.add(rsPrimaryKeys.getString("COLUMN_NAME"));
		}
		rsPrimaryKeys.close();
		return primaryKeysNames;
	}

	//FKs column Map foreign table
	public static Map<String, String> getFK(Connection cn, String table_name) throws SQLException{
		DatabaseMetaData dmd = cn.getMetaData();
		ResultSet rsImportedKeys = dmd.getImportedKeys(null, null, table_name);
		Map<String, String> result = new HashMap<String, String>();
		while(rsImportedKeys.next()){
			result.put(rsImportedKeys.getString("FKCOLUMN_NAME"), 
					rsImportedKeys.getString("PKTABLE_NAME"));
		}
		return result;
	}
	
	//Table map which table's which colume refer to this table
//	public static List getReferTable(Connection cn, String table_name) throws SQLException{
//		List result = new ArrayList();
//
//			DatabaseMetaData dmd = cn.getMetaData();
//			dmd.g
//			
//			ResultSet rsImportedKeys = dmd.getImportedKeys(null, null, table_name);
//			Map<String, String> result = new HashMap<String, String>();
//			while(rsImportedKeys.next()){
//				result.put(SCUtil.lowerLetters(rsImportedKeys.getString("FKCOLUMN_NAME")), 
//						SCUtil.lowerLetters(rsImportedKeys.getString("PKTABLE_NAME")));
//			}
//	
//	}
//	
	
	
	//FKs table arraylist
	public static List<String> getFKTable(Connection cn, String table_name) throws SQLException{
		DatabaseMetaData dmd = cn.getMetaData();
		ResultSet rsImportedKeys = dmd.getImportedKeys(null, null, table_name);
		List<String> result = new ArrayList<String>();
		while(rsImportedKeys.next()){
			if(!result.contains(rsImportedKeys.getString("PKTABLE_NAME"))){
				result.add(rsImportedKeys.getString("PKTABLE_NAME"));
			}
		}
		return result;
	}
	
	//Unique Column collection
	public static List<String> getColumnUnique(Connection cn, String table_name) throws SQLException{
		DatabaseMetaData dmd = cn.getMetaData();
		ResultSet rsUniqueColumn = dmd.getIndexInfo(null, null, table_name, true, false);
		List<String> UniqueColumn = new ArrayList<String>();
		while(rsUniqueColumn.next()){
			UniqueColumn.add(rsUniqueColumn.getString("COLUMN_NAME"));
		}
		rsUniqueColumn.close();
		return UniqueColumn;
	}
	
	//un validated
	public File DB2File(Connection cn, String table, String path) throws IOException, SQLException{
		File result = new File(path);
		result.createNewFile();
		
		List cols = TableStructure.getColumnName(cn, table);
		String sql = " insert into " + table ;
		String sql_cols_list = "";
		for(int i = 0;i<cols.size();i++){
			sql_cols_list += cols.get(i) + ",";
		}
		SCUtil.removeLastLetter(sql_cols_list);
		sql +=  " (" + sql_cols_list + " ) values ";
		
		Statement st;
		ResultSet rs1;

		st = cn.createStatement();
		rs1 = st.executeQuery(" select "+sql_cols_list+" from "+table);
		String value_list_temp = "";
		List types = TableStructure.getColumnType(cn, table);
		
		while(rs1.next()){
//			value_list_temp = "";
			for(int i = 0;i<cols.size();i++){
				if("double".equals(types.get(i))
						||"integer".equals(types.get(i))
						||"short".equals(types.get(i))
						||"float".equals(types.get(i))
						||"double".equals(types.get(i))
						||"byte".equals(types.get(i))
						){
					value_list_temp += rs1.getString(i)+",";	
				}else{
					value_list_temp += "'" + rs1.getString(i) + "',";	
				}
			}
		}
		SCUtil.removeLastLetter(value_list_temp);
		sql += "( " + value_list_temp + " )";
		FileUtil.writeFile(sql,path+"/"+table);
		return result;
	}
	
}
