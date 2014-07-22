package com.engineer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelUtil {
	public static void main(String args[]) throws RowsExceededException, BiffException, WriteException, IOException, SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException{
//		unionAllExcel("D:\\New Folder\\cc1\\",
//				"D:\\New Folder\\cc1.csv",
//				1,
//				new String[]{"Campaign Code","Prospect ID","Name","Gender","DOB","Mobile","HomePhone","OfficePhone","WhereCallingFrom","EmailAddress","CorrespondenceAddress","IQ_ID","Running No","Activity","Submission_Date","Remark"});
//		unionAllExcel("D:\\New Folder\\cc3\\",
//				"D:\\New Folder\\cc3.csv",
//				1,
//				new String[]{"Campaign Code","Prospect ID","Name","Gender","DOB","Mobile","HomePhone","OfficePhone","WhereCallingFrom","EmailAddress","CorrespondenceAddress","IQ_ID","Running No","Activity","Submission_Date","Remark"});
//		unionAllExcel("D:\\New Folder\\cc4\\",
//				"D:\\New Folder\\cc4.csv",
//				1,
//				new String[]{"Campaign Code","Prospect ID","Name","Gender","DOB","Mobile","HomePhone","OfficePhone","WhereCallingFrom","EmailAddress","CorrespondenceAddress","IQ_ID","Running No","Activity","Submission_Date","Remark"});
//		unionAllExcel("D:\\New Folder\\ccz\\",
//				"D:\\New Folder\\ccz.csv",
//				1,
//				new String[]{"Campaign Code","Prospect ID","Name","Gender","DOB","Mobile","HomePhone","OfficePhone","WhereCallingFrom","EmailAddress","CorrespondenceAddress","IQ_ID","Running No","Activity","Submission_Date","Remark"});
//		unionAllExcel("D:\\New Folder\\cc\\",
//				"D:\\New Folder\\cc.csv",
//				1,
//				new String[]{"Campaign Code","Prospect ID","Name","Gender","DOB","Mobile","HomePhone","OfficePhone","WhereCallingFrom","EmailAddress","CorrespondenceAddress","IQ_ID","Running No","Activity","Submission_Date","Remark"});
		/*
		 * 
update lzcard set sendoutcom='A8606', sendoutdepartcode='8606-admin' 
                  where CERTIFYCODE='10103001' AND CERTIFYVERSION='v2.0' AND STARTNO>='2011010651' AND ENDNO<='2011010670'
update lzcard set where CERTIFYCODE='10103001' AND CERTIFYVERSION='v2.0' AND STARTNO>='2011010651' AND ENDNO<='2011010670' 
update lzcardtrack set sendoutcom='A8606', sendoutdepartcode='8606-admin'
where 
CERTIFYCODE='10103001' AND
CERTIFYVERSION='v2.0' AND
STARTNO>='2011010651' AND
ENDNO<='2011010670'

		 */
		
//		generateSQLByXls_upfate2();
//		generateSQLByXls();
//		generateSQLByXls_update("C:\\wfu.xls",484,"C:\\wfu_update.sql");
//		generateSQLByXls_select("C:\\wfu.xls",484,"C:\\wfu_select.sql");
//		generateSQLByXls_backupselect("C:\\wfu.xls",484,"C:\\wfu_backup.sql");

//		unionAllExcel("C:\\Documents and Settings\\b3wang\\桌面\\New Folder (2)\\New Folder (2)\\",
//				"D:\\New Folder\\cc4.csv",
//				1,
//				new String[]{"Campaign Code","Prospect ID","Name","Gender","DOB","Mobile","HomePhone","OfficePhone","WhereCallingFrom","EmailAddress","CorrespondenceAddress","IQ_ID","Running No","Activity","Submission_Date","Remark"});
		
		
//		List result = getDuplicatedLine("D:\\New Folder\\cc4.csv");
//		for(int i = 0;i<result.size();i++){
//			System.out.println(result.get(i));
//		}
		
//		Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
//		Connection cn = (Connection) DriverManager.getConnection (
//				 "jdbc:microsoft:sqlserver://10.131.24.31:1433;DatabaseName=cmcprod"
//				 , "BFS_Dev_Harold"
//				 , "Hh123456"
//				);
//		generateSupplyInsert(cn,"C:\\wfu_2.xls",264,"C:\\wfu_supply_2.sql",0);
//		cn.close();
		
//		Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
//		Connection cn = (Connection) DriverManager.getConnection (
//				 "jdbc:microsoft:sqlserver://PHDBSQBFS01.cmc-xinnuo.com\\PHDBSQBFS01:1433;DatabaseName=cmcprod"
//				 , "BFS_Dev_Harold"
//				 , "Hh123456"
//				);
//		generateSupplyInsert(cn,"C:\\wfu_2.xls",264,"C:\\wfu_supply_2.sql",0);
//		generateJSSupplyInsert(cn,"C:\\wfu_js.xls",582,"C:\\wfu_supply_js.sql",0);
		generateOccupation("c:\\occupation.xls",1540,"c:\\occupation.sql",2);
//		cn.close();
	}
	/*
	 * update lzcard set SendOutDepartCode =null,ReceiveDepartCode = '8607-bankinsurance' 
where CERTIFYCODE='10103002' 
AND lower(CERTIFYVERSION)=lower('v2.0') 
and startno>='2012018082' and endno<='2012018082'





update lzcardtrack set receivecom = 'A8607' , sendoutcom='A8607' 
,SendOutDepartCode = '8607-bankinsurance',
ReceiveDepartCode = '8607-bankinsurance' 
where CERTIFYCODE='10103002'
AND lower(CERTIFYVERSION)=lower('v2.0') 
and startno>='2012018082' and endno<='2012018082'

	 * */
	public static void generateOccupation(String path, long count,String pathTo, long from ) throws BiffException, IOException{
		StringBuffer content = new StringBuffer();
		
		for(int i=0;i<count;i++){
			String[] line = get1Line(i, path);
			String value="";
			if(line[0].trim().length()!=0){
				value = line[0]+" - "+line[1]+" - "+line[3];
			}else{
				value = line[1]+" - "+line[3];
			}
			System.out.println(value);
			content.append(" insert into occupation(value,text)values(N'"+value+"',N'"+line[2]+"') \n");
		}
		FileUtil.writeFile(content.toString(),pathTo);
		
	}
	public static void generateJSSupplyInsert(Connection cn, String path, int count,String pathTo, long from ) throws BiffException, IOException, SQLException{
		StringBuffer content = new StringBuffer();
		int total = 0;
		String SERIALNO =  "99999999999999997";
		ResultSet rs = null;
		ResultSet rs2 = null;
		for(int i=1;i<count;i++){
			if(i<from)continue;
			String[] line = get1Line(i,path);
			String sql = "select count(*) as c from lzcardtrack "+getWhereOnlyByNo(line);
			String sql2 = "select count(*) as c from lzcard "+getWhereOnlyByNo(line);
			Statement statement = cn.createStatement();
			Statement statement2 = cn.createStatement();
			rs = statement.executeQuery(sql);
			rs2 = statement2.executeQuery(sql2);
			rs.next();
			rs2.next();
//			if(rs.getInt("c")<rs2.getInt("c")
//				&& rs.getInt("c")==0 
//			){
				System.out.println(i +  "select '"+line[4]+"' no,'lzcard'      tbl,'' serialno,lzcard.* from lzcard "+getWhereOnlyByNo(line)+" union all\n");
				System.out.println(i +  "select '"+line[4]+"' no,'lzcardtrack' tbl,lzcardtrack.*        from lzcardtrack "+getWhereOnlyByNo(line)+" union all\n");
//				content.append("insert into lzcardtrack (SERIALNO,CERTIFYCODE,CERTIFYVERSION,SUBCODE,STARTNO,ENDNO,SENDOUTCOM,SENDOUTDEPARTCODE,SENDOUTUSERCODE,RECEIVECOM,RECEIVEDEPARTCODE,RECEIVEUSERCODE,SUMCOUNT,PREM,AMNT,HANDLER,HANDLEDATE,CVALIDATE,CINVALIDATE,TAKEBACKNO,STATEFLAG,OPERATORFLAG,REASON,REREASON,STATE,PRTNO,APPLYNO,OPERATOR,MAKEDATE,MAKETIME,FINANCENO,POLICYNO,PREMIUM,POLICYER,DEPUTY,DEPUTYNO,SERVICEOFF,POLICYADD,COMMDIRE,STANDBYFLAG1,STANDBYFLAG2,STANDBYFLAG3) \n" +
//						       "select '"+(SERIALNO+SCUtil.lstr(total+"", 3, "0"))+"',CERTIFYCODE,CERTIFYVERSION,SUBCODE,STARTNO,ENDNO,SENDOUTCOM,SENDOUTDEPARTCODE,SENDOUTUSERCODE,RECEIVECOM,RECEIVEDEPARTCODE,RECEIVEUSERCODE,SUMCOUNT,PREM,AMNT,HANDLER,HANDLEDATE,CVALIDATE,CINVALIDATE,TAKEBACKNO,STATEFLAG,OPERATORFLAG,REASON,REREASON,STATE,PRTNO,APPLYNO,OPERATOR,MAKEDATE,MAKETIME,FINANCENO,POLICYNO,PREMIUM,POLICYER,DEPUTY,DEPUTYNO,SERVICEOFF,POLICYADD,COMMDIRE,STANDBYFLAG1,STANDBYFLAG2,STANDBYFLAG3 from lzcard \n"+getWhere(line)+"\n" +
//						       "update lzcardtrack " +getSet_supplyTrack(line,m,n)+" \n"+getWhere(line)+"\n"+
//						       "update lzcard      " +getSet_supplyLzcard(line,m,n)+" \n"+getWhere(line)+"\n\n\n"
//				);
				content.append(
						"select '"+line[4]+"' no,'lzcard'      tbl,'' serialno,lzcard.* from lzcard      "+getWhereOnlyByNo(line)+" union all\n"+
						"select '"+line[4]+"' no,'lzcardtrack' tbl,lzcardtrack.*        from lzcardtrack "+getWhereOnlyByNo(line)+" union all\n"
					       );
			
				
				total++;
//			}			
		}
		rs.close();
		rs2.close();
		FileUtil.writeFile(content.toString(),pathTo);
		System.out.println("total="+total);
				
	}
	public static void generateSupplyInsert(Connection cn, String path, int count,String pathTo, long from ) throws BiffException, IOException, SQLException{
		StringBuffer content = new StringBuffer();
		int total = 0;
		String SERIALNO =  "99999999999999998";
		ResultSet rs = null;
		ResultSet rs2 = null;
		for(int i=1;i<count;i++){
			if(i<from)continue;
			String[] line = get1Line(i,path);
			String sql = "select count(*) as c from lzcardtrack "+getWhere(line);
			String sql2 = "select count(*) as c from lzcard "+getWhere(line);
			Statement statement = cn.createStatement();
			Statement statement2 = cn.createStatement();
			rs = statement.executeQuery(sql);
			rs2 = statement2.executeQuery(sql2);
			rs.next();
			rs2.next();
			if(rs.getInt("c")<rs2.getInt("c")
				&& rs.getInt("c")==0 
			){
				System.out.println(i);

				content.append("insert into lzcardtrack (SERIALNO,CERTIFYCODE,CERTIFYVERSION,SUBCODE,STARTNO,ENDNO,SENDOUTCOM,SENDOUTDEPARTCODE,SENDOUTUSERCODE,RECEIVECOM,RECEIVEDEPARTCODE,RECEIVEUSERCODE,SUMCOUNT,PREM,AMNT,HANDLER,HANDLEDATE,CVALIDATE,CINVALIDATE,TAKEBACKNO,STATEFLAG,OPERATORFLAG,REASON,REREASON,STATE,PRTNO,APPLYNO,OPERATOR,MAKEDATE,MAKETIME,FINANCENO,POLICYNO,PREMIUM,POLICYER,DEPUTY,DEPUTYNO,SERVICEOFF,POLICYADD,COMMDIRE,STANDBYFLAG1,STANDBYFLAG2,STANDBYFLAG3) \n" +
						       "select '"+(SERIALNO+SCUtil.lstr(total+"", 3, "0"))+"',CERTIFYCODE,CERTIFYVERSION,SUBCODE,STARTNO,ENDNO,SENDOUTCOM,SENDOUTDEPARTCODE,SENDOUTUSERCODE,RECEIVECOM,RECEIVEDEPARTCODE,RECEIVEUSERCODE,SUMCOUNT,PREM,AMNT,HANDLER,HANDLEDATE,CVALIDATE,CINVALIDATE,TAKEBACKNO,STATEFLAG,OPERATORFLAG,REASON,REREASON,STATE,PRTNO,APPLYNO,OPERATOR,MAKEDATE,MAKETIME,FINANCENO,POLICYNO,PREMIUM,POLICYER,DEPUTY,DEPUTYNO,SERVICEOFF,POLICYADD,COMMDIRE,STANDBYFLAG1,STANDBYFLAG2,STANDBYFLAG3 from lzcard \n"+getWhere(line)+"\n" +
						       "update lzcardtrack " +getSet_supplyTrack(line,m,n)+" \n"+getWhere(line)+"\n"+
						       "update lzcard      " +getSet_supplyLzcard(line,m,n)+" \n"+getWhere(line)+"\n\n\n"
				);
//				content.append(
//					       "select '"+line[3]+"','"+line[4]+"',count(*),'lzcard' from lzcard "+getWhere(line)+" union all\n"+
//					       "select '"+line[3]+"','"+line[4]+"',count(*),'lzcardtrack' from lzcardtrack "+getWhere(line)+" union all\n"
//					       );
			
				
				total++;
			}			
		}
		rs.close();
		rs2.close();
		FileUtil.writeFile(content.toString(),pathTo);
		System.out.println("total="+total);
				
	}
	public static String  getSet_supplyTrack(String str[],Map m,Map n){
		if(str[16].trim().length()!=0){
			return "set sendoutcom='"+n.get(str[14].trim())+"'"
			+", sendoutdepartcode='"+m.get(str[16].trim())+"' "
			+", receivecom='"+n.get(str[14].trim())+"'"
			+", receivedepartcode='"+m.get(str[16].trim())+"' ";
		}else{
			return "set receivecom='"+n.get(str[14].trim())+"'"
			+", receivedepartcode='"+m.get(str[18].trim())+"' "
			+", sendoutcom='"+n.get(str[14].trim())+"'"
			+", sendoutdepartcode='"+m.get(str[18].trim())+"' ";
		}
	}
	public static String  getSet_supplyLzcard(String str[],Map m,Map n){
		if(str[16].trim().length()!=0){
			return "set sendoutcom = null "
			+", sendoutdepartcode = null "
			+", receivecom = '"+n.get(str[14].trim())+"'"
			+", receivedepartcode = '"+m.get(str[16].trim())+"' ";
		}else{
			return "set receivecom = null "
			+", receivedepartcode = null "
			+", sendoutcom = '"+n.get(str[14].trim())+"'"
			+", sendoutdepartcode = '"+m.get(str[18].trim())+"' ";
		}
	}
	public static void generateSQLByXls_select(String path, int count,String pathTo) throws BiffException, IOException{
		
		StringBuffer content = new StringBuffer();
		for(int i=1;i<count;i++){
			content.append(" select 'lzcard',STARTNO,ENDNO,sendoutcom,sendoutdepartcode,receivecom,receivedepartcode,'"+i+"' from lzcard "+getWhere(get1Line(i,path))+"\n union all \n");
			
		}
		for(int i=1;i<count;i++){
			content.append(" select 'lzcardtrack',STARTNO,ENDNO,sendoutcom,sendoutdepartcode,receivecom,receivedepartcode,'"+i+"' from lzcardtrack "+getWhere(get1Line(i,path))+"\n union all \n");
		}
		FileUtil.writeFile(content.toString(),pathTo);
	}
	
	public static void generateSQLByXls_update(String path,int count ,String pathTo) throws BiffException, IOException{
		StringBuffer content = new StringBuffer();

			for(int i=1;i<count;i++){//backup
				String[] line = get1Line(i,path);
				content.append(" update LZCARD      "+getSet(line,m,n)+" "+getWhere(line)+"\n");
				content.append(" update LZCARDTRACK "+getSet(line,m,n)+" "+getWhere(line)+"\n");
			}
		FileUtil.writeFile(content.toString(),pathTo);
	}

	public static void generateSQLByXls_backupselect(String path, int count ,String pathTo) throws BiffException, IOException{
		StringBuffer content = new StringBuffer();
		for(int i=1;i<count;i++){
			content.append(" select * into lzcard_20120521 from lzcard "+getWhere(get1Line(i,path))+" \n");
			
		}
		for(int i=1;i<count;i++){
			content.append(" select * into lzcardtrack_20120521 from lzcardtrack "+getWhere(get1Line(i,path))+" \n");
		}
		FileUtil.writeFile(content.toString(),pathTo);
	}
	
	public static String getSet(String str[],Map m,Map n) {
		if(str[16].trim().length()!=0){
			return "set sendoutcom='"+n.get(str[14].trim())+"'"
			+", sendoutdepartcode='"+m.get(str[16].trim())+"' "
			+", receivecom=NULL "
			+", receivedepartcode=NULL ";
		}else{
			return "set receivecom='"+n.get(str[14].trim())+"'"
			+", receivedepartcode='"+m.get(str[18].trim())+"' "
			+", sendoutcom=NULL "
			+", sendoutdepartcode=NULL ";
		}
	}
	
	public static String getWhere(String[] line){
		 return  " WHERE CERTIFYCODE='"+line[0]+"'"
			 + " AND lower(CERTIFYVERSION)=lower('"+line[2]+"')"
			 + " AND STARTNO>='"+line[3]+"'"
			 + " AND ENDNO<='"+line[4]+"'\n " 
			 ;
	}
	public static String getWhereOnlyByNo(String[] line){
		 return  " WHERE STARTNO>='"+line[3]+"'"
			 + " AND ENDNO<='"+line[4]+"'\n " 
			 ;
	}

	
	
	
	
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------
	
	
	
	
	
	
	
	
	
	public static String[] get1Line(int line, String input)
			throws BiffException, IOException {
		if (line < 0)
			return null;
		Workbook wb = Workbook.getWorkbook(new File(input));
		Sheet sheet = wb.getSheet(0);
		String[] result = new String[sheet.getColumns()];
		for (int i = 0; i < result.length; i++) {
			result[i] = sheet.getCell(i, line).getContents();
		}
		return result;
	}

	public static String[] get1Col(int col, String input) throws BiffException,
			IOException {
		if (col < 0)
			return null;
		Workbook wb = Workbook.getWorkbook(new File(input));
		Sheet sheet = wb.getSheet(0);
		String[] result = new String[sheet.getRows()];
		for (int i = 0; i < result.length; i++) {
			result[i] = sheet.getCell(col, i).getContents();
		}
		wb.close();
		return result;
	}

	public static String get1Cell(int line, int col, String input)
			throws BiffException, IOException {
		if (line < 0)
			return null;
		if (col < 0)
			return null;
		Workbook wb = Workbook.getWorkbook(new File(input));
		Sheet sheet = wb.getSheet(0);
		String result = sheet.getCell(col, line).getContents();
		wb.close();
		return result;
	}

	public static void unionAllExcel(String input_directory, String output, int fromRow,
			String[] cols) throws BiffException, IOException,
			RowsExceededException, WriteException {
		if(input_directory==null || input_directory.trim().length()==0||
				output==null || output.trim().length()==0||fromRow<0||cols==null){
			return;
		}
		System.out.println(input_directory + "==>" + output);
		File inputpath = new File(input_directory);
		File outputfile = new File(output);
		File[] files = inputpath.listFiles();
		int column_num = 0;
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < files.length; i++) {
			Workbook wb = Workbook.getWorkbook(files[i]);
			Sheet sheet = wb.getSheet(0);
			int row_num = fromRow;
			while (row_num < sheet.getRows()) {
				column_num = 0;
				boolean isEnter = true;// enter control
				while (column_num < cols.length) {
					String temp = sheet.getCell(column_num, row_num)
							.getContents();
//					if (column_num == 0 && !temp.trim().equals("1GIGN11CA1")
//							&& !temp.trim().equals("1GIGZ11CA2")) {// filter
//						isEnter = false;// enter control
//						break;// filter
//					}// filter
					content.append(temp + ",");
					column_num++;
				}
//				if (isEnter) {// enter control
//					content.append("\n");
//					isEnter = true;// enter control
//				}// enter control
				content.append("\n");
				row_num++;
			}
			wb.close();
		}

		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(
				outputfile), "GBK");
		fw.write(content.toString());
		fw.close();
	}
	
	public static List getDuplicatedLine(String file) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(new File(file)));
		String line = "";
		Map unique = new HashMap();
		List result = new ArrayList();
		while((line = br.readLine())!=null){
			String line_s[] = line.split(",");
			if(!unique.containsKey(line_s[0]+line_s[5])){
				unique.put(line_s[0]+line_s[5], line);
			}else{
				result.add(line);
			}
		}
		br.close();
		return result;
	}
	public static Map m = new HashMap();
	static{
	m.put("招商信诺人寿总公司行政部","86-admin");
	m.put("招商信诺人寿总公司银行保险销售部","86-bankinsurance"  );
	m.put("招商信诺人寿总公司银保市场部","86-bankmarket");
	m.put("招商信诺人寿总公司财务部","86-finance");
	m.put("招商信诺人寿总公司IT部","86-it");
	m.put("招商信诺人寿总公司保单管理部","86-policyadm");
	m.put("招商信诺人寿北京分公司行政部","8601-admin");
	m.put("招商信诺人寿北京分公司银行保险销售部","8601-bankinsurance");
	m.put("招商信诺人寿上海分公司行政部","8602-admin");
	m.put("招商信诺人寿上海分公司银行保险销售部","8602-bankinsurance");
	m.put("招商信诺人寿上海分公司分公司SPDB销售部","8602-spdb");
	m.put("招商信诺人寿广州营销部行政部","8603-admin");
	m.put("招商信诺人寿广州营销部银行保险销售部","8603-bankinsurance");
	m.put("招商信诺人寿浙江分公司行政部","8604-admin");
	m.put("招商信诺人寿浙江分公司银行保险销售部","8604-bankinsurance");
	m.put("招商信诺人寿江苏分公司行政部","8605-admin");
	m.put("招商信诺人寿江苏分公司银行保险销售部","8605-bankinsurance");
	m.put("招商信诺人寿四川分公司行政部","8606-admin");
	m.put("招商信诺人寿四川分公司银行保险销售部","8606-bankinsurance");
	m.put("招商信诺人寿湖北分公司行政部","8607-admin");
	m.put("招商信诺人寿湖北分公司银行保险销售部","8607-bankinsurance");
	m.put("招商信诺人寿山东分公司行政部","8608-admin");
	m.put("招商信诺人寿山东分公司银行保险销售部","8608-bankinsurance");
	m.put("招商信诺人寿辽宁分公司行政部","8609-admin");
	m.put("招商信诺人寿辽宁分公司银行保险销售部","8609-bankinsurance");
	m.put("招商信诺人寿烟台营销服务部银行保险销售部","8610-bankinsurance");
	m.put("招商信诺青岛中心支公司银行保险销售部","8611-bankinsurance");
	}
	public static Map n= new HashMap();
	static{
	n.put("北京","A8601");
	n.put("上海","A8602");
	n.put("广东","A8603");
	n.put("浙江","A8604");
	n.put("江苏","A8605");
	n.put("四川","A8606");
	n.put("湖北","A8607");
	n.put("深圳","A86");
	}

}
