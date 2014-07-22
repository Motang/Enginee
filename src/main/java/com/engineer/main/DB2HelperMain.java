package com.engineer.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

public class DB2HelperMain {
	public static void main(String[] args) throws Exception{
		int i = generateDocumentClass();
		//int i = generateDocumentIndex();
		
		System.err.println("result="+i);
		
	}

	private static int generateDocumentIndex() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String sql = "INSERT INTO DMS_DOCUMENT_INDEX (DIX_ID,DIX_DCL_ID,DIX_SYMBOLIC_NAME,DIX_LABEL,DIX_TYPE,DIX_FORMAT,DIX_LENGTH,DIX_CREATE_ID,DIX_MODIFY_ID,DIX_MANDATORY,DIX_CREATE_DATE,DIX_MODIFY_DATE,DIX_LEVEL,DIX_DEFAULT_VALUE,DIX_LUG_ID,DIX_SEQ) " +
				//"VALUES ('s' /*not nullable*/,'s' /*not nullable*/,'s' /*not nullable*/,'s','s' /*not nullable*/,'s',0,'s' /*not nullable*/,'s',0 /*not nullable*/,{ts '2012-10-31 16:48:41.183000'} /*not nullable*/,{ts '2012-10-31 16:48:41.183000'},'s','s','s',0);" +
				"VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s);";
		String astr = "'%s'";
		Connection cn = getConn();
		PreparedStatement statement = cn.prepareStatement("select * from dms_document_index");
		ResultSet result = statement.executeQuery();
		int i = 0;
		while (result.next()) {
			i++;
			String sqlStr = String.format(sql
					,String.format(astr, result.getString(1))
					,String.format(astr, result.getString(2))
					,String.format(astr, result.getString(3))
					,StringUtils.isEmpty(result.getString(4))?"NULL":String.format(astr, result.getString(4))
					,String.format(astr, result.getString(5))
					,StringUtils.isEmpty(result.getString(6))?"NULL":String.format(astr, result.getString(6))
					,result.getInt(7)
					,String.format(astr, result.getString(8))
					,StringUtils.isEmpty(result.getString(9))?"NULL":String.format(astr, result.getString(9))
					,StringUtils.isEmpty(result.getString(10))?"NULL":String.format(astr, result.getString(10))
					,result.getTimestamp(11)==null?"NULL":String.format(astr, dateFormat.format(result.getTimestamp(11)))
					,result.getTimestamp(12)==null?"NULL":String.format(astr, dateFormat.format(result.getTimestamp(12)))
					,StringUtils.isEmpty(result.getString(13))?"NULL":String.format(astr, result.getString(13))
					,StringUtils.isEmpty(result.getString(14))?"NULL":String.format(astr, result.getString(14))
					,StringUtils.isEmpty(result.getString(15))?"NULL":String.format(astr, result.getString(15))
					,result.getInt(16)
			);
			
			//System.out.println("1>>>>"+result.getTimestamp(6));
			//System.out.println("2>>>>"+result.getDate(6));
			System.out.println(sqlStr);
		}
		
		result.close();
		statement.close();
		cn.close();
		return i;
	}

	private static int generateDocumentClass() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String sql = "INSERT INTO DMS_DOCUMENT_CLASS (DCL_ID,DCL_SYMBOLIC_NAME,DCL_CREATE_ID,DCL_MODIFY_ID,DCL_LABEL,DCL_CREATE_DATE,DCL_MODIFY_DATE) " +
				"VALUES (%s,%s,%s,%s,%s,%s,%s);";
		String astr = "'%s'";
		Connection cn = getConn();
		PreparedStatement statement = cn.prepareStatement("select * from DMS_DOCUMENT_CLASS");
		ResultSet result = statement.executeQuery();
		int i = 0;
		while (result.next()) {
			String sqlStr = String.format(sql
					,String.format(astr, result.getString(1))
					,String.format(astr, result.getString(2))
					,StringUtils.isEmpty(result.getString(3))?"NULL":String.format(astr, result.getString(3))
					,StringUtils.isEmpty(result.getString(4))?"NULL":String.format(astr, result.getString(4))
					,StringUtils.isEmpty(result.getString(5))?"NULL":String.format(astr, result.getString(5))
					,result.getTimestamp(6)==null?"NULL":String.format(astr, dateFormat.format(result.getTimestamp(6)))
					,result.getTimestamp(7)==null?"NULL":String.format(astr, dateFormat.format(result.getTimestamp(7))));
			
			//System.out.println("1>>>>"+result.getTimestamp(6));
			//System.out.println("2>>>>"+result.getDate(6));
			i++;
			System.out.println(sqlStr);
		}
		
		result.close();
		statement.close();
		cn.close();
		
		return i;
	}

	private static Connection getConn() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		Connection cn = DriverManager.getConnection (
				 "jdbc:db2://sziy0088:50000/CIGNADB"
				 , "focal"
				 , "Aa123456"
				);
		return cn;
	}
}
