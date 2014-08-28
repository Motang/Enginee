package com.engineer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class DBUtil {
	public static void main(String args[]) throws Exception{
//		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
//		Connection cn =
//	     	DriverManager.getConnection (
//	     			"jdbc:oracle:thin:@10.68.49.79:1521:mp2mch"
//	     			, "mp2pcf"
//	     			, "mp2pcf"
//					   );
		
//		Connection cn =
//	     	DriverManager.getConnection (
//	     			"jdbc:oracle:thin:@10.68.0.25:1521:mp2shy"
//	     			, "mp2shy"
//	     			, "mp2shy"
//					   );
//		Connection cn =
//	     	DriverManager.getConnection (
//	     			"jdbc:sqlserver://10.68.13.135:1433;databaseName=MP2TEST2CN"
//	     			, "MP2TEST2CN"
//	     			, "mp2test2cn"
//					   );
// 
		
		
		
		
		/*
		 * select *
from poreceiv a,purreq b,poheader c,acct d 
where a.seqnum=b.seqnum(+) and a.closedate=b.closedate(+) and a.ponum=c.ponum 
and a.releasenum=c.releasenum and a.closedate=c.closedate 
and a.purchasingcenterid=c.purchasingcenterid and b.acctcode=d.acctcode(+)
and a.receivernum is not null and b.vendorid is not null
and a.TRANSACTIONDATE>= to_date(20100101,'YYYYMMDD') 
and a.TRANSACTIONDATE<= to_date(20101231,'YYYYMMDD') 
and d.ACCTCODE like ('3220.%')
		 */
//		String sql = "select * " +
//"from poreceiv a,purreq b,poheader c,acct d  " +
//"where a.seqnum=b.seqnum(+) and a.closedate=b.closedate(+) and a.ponum=c.ponum  " +
//"and a.releasenum=c.releasenum and a.closedate=c.closedate  " +
//"and a.purchasingcenterid=c.purchasingcenterid and b.acctcode=d.acctcode(+) " +
//"and a.receivernum is not null and b.vendorid is not null " +
//"and a.TRANSACTIONDATE>= to_date(20090101,'YYYYMMDD')  " +
//"and a.TRANSACTIONDATE<= to_date(20091231,'YYYYMMDD')  " ;
//"and d.ACCTCODE like ('3220.%')";
		
//		String sql = "select * from issrec where ISSUEDATE >= to_date(20100101,'YYYYMMDD') " +
//		" and ISSUEDATE <= to_date(20101231,'YYYYMMDD')  ";
		
		
		//m.userid, m.AUDITDATE,m.AUDITTIME, o.TABLENAME, o.FIELDNAME, m.TYPEOFCHANGE from Audvalo o, Audmst m 
//		String sql = " select  m.userid, m.AUDITDATE,m.AUDITTIME, o.TABLENAME, o.FIELDNAME, m.TYPEOFCHANGE from Audvalo o, Audmst m " +
//		"where m.CURKEYID = o.CURKEYID  " +
//		"and o.TABLENAME  = 'PO'  " +
//		"and m.AUDITDATE >= to_date(20100101,'YYYYMMDD') "+
//		"and m.AUDITDATE <= to_date(20101231,'YYYYMMDD')  "+
//		" " ;
//		
		
//        String sql = "select * from vendor v" +
//        		" where v.vendorid not in ( select distinct i.vendorid from invvend i ) " +
//        		" and v.vendorbranchid not in ( select distinct i.vendorbranchid from invvend i ) " +
//      
//        		" ";
//		String sql = "select * from vendor v where " +
//				"(v.vendorid) in ( select distinct i.vendorid  from purreq i ) "+
//                "and (v.vendorbranchid) in ( select distinct i.vendorbranchid from purreq i ) ";

//		String sql = "select * from vendor where UPDATESTAMP > to_date('20101215','YYYYMMDD')";
//		String sql = "select pr.seqnum, pr.closedate, in.itemnum,in.description,in.notes " +
//				" from purreq pr, invy in, invvend inv, manufact mf," +
//				" stock stk, warehouse wh" +
//				" where pr.itemnum = in.itemnum " +
//				" and in.itemnum = inv.itemnum" +
//				" and inv.manufactid = mf.manufactid" +
//				" and stk.itemnum = in.itemnum" +
//				" and stk.warehouseid = wh.warehouseid" +
//				" ";
//		String sql = "select * from audtbl";
//		String sql = "select * from vendlist where UPDATESTAMP > to_date('20101215','YYYYMMDD')";	
//		String sql = "select * from issrec where rownum <1000";
//		String sql = "select * from invy,invvend where invy.itemnum=invvend.itemnum";

//		String sql = " select * from purreq,poheader where purreq.ponum = poheader.ponum " +
//				" and poheader.closedate > to_date('20110101','YYYYMMDD')";
		
//		String sql = "select * from purreq where purreq.ponum is null";
		/*
		String sql = "" +

"		     select " +
"		     poheader.potype, " +
"		     poheader.exchangerate, " +
"		     poheader.mp2currency, " +
"		     poreceivpurreqacct.costcenter, " +
"		     poreceivpurreqacct.vendorid, " +
"		     poreceivpurreqacct.descriptiononpo, " +
"		     poreceivpurreqacct.QTYRECEIVED, " +
//--    poreceivpurreqacct.CUSGLCLASS, 
"		     poreceivpurreqacct.receivernum, " +
"		     poreceivpurreqacct.linenumber, " +
"		     poreceivpurreqacct.tranSactiondate, " +
"		     poreceivpurreqacct.ponum, " +
"		     poreceivpurreqacct.datereceived, " +
"		     poreceivpurreqacct.receiptnum, " +
"		     poreceivpurreqacct.itemnum, " +
"		     poreceivpurreqacct.adjustedunitcost  " +
"		     from ( " +
"		               select  " +
"		               purreqacct.costcenter, " +
"		               purreqacct.vendorid, " +
"		               purreqacct.descriptiononpo, " +
"		               purreqacct.QTYRECEIVED, " +
//--              purreqacct.CUSGLCLASS, 
"		               poreceiv.receivernum, " +
"		               poreceiv.linenumber, " +
"		    		   poreceiv.tranSactiondate, " +
"		    		   poreceiv.ponum, " +
"		    		   poreceiv.datereceived, " +
"		    		   poreceiv.receiptnum, " +
"		    		   poreceiv.itemnum, " +
"		    		   poreceiv.adjustedunitcost, " +
"		    		   poreceiv.releasenum, " +
"		               poreceiv.closedate, " +
"		               poreceiv.purchasingcenterid " +
"		               from poreceiv left join   " +
"	                        (select " +
"		     				     purreq.ponum, " +
"		    				     purreq.seqnum,  " +
"		                         purreq.closedate, " +
"		    			         purreq.costcenter,  " +
"		                         purreq.vendorid, " +
"		     			         purreq.descriptiononpo, " +
"		     			 	     purreq.QTYRECEIVED, " +
"		    				     purreq.purchasingcenterid   " +
//--				 	       acct.CUSGLCLASS 
"		                         from purreq left join acct   " +
"		                         on purreq.acctcode = acct.acctcode ) purreqacct    " +
"		                on poreceiv.closedate = purreqacct.closedate   " +
"		                AND poreceiv.seqnum = purreqacct.seqnum " +
"		                AND poreceiv.ponum = purreqacct.ponum " +
"		     ) poreceivpurreqacct join poheader   " +
"		     on poreceivpurreqacct.ponum = poheader.ponum   " +
"		     AND poreceivpurreqacct.releasenum = poheader.releasenum   " +
"		     AND poreceivpurreqacct.closedate = poheader.closedate  " +
"		     AND poreceivpurreqacct.purchasingcenterid = poheader.purchasingcenterid   " 
;*/
		
	
//		Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
//        // set connection properties
//        String databaseUrl = "jdbc:db2://10.131.24.150:50000/CLM_TEST";
//        // get connection
//        Connection cn = DriverManager.getConnection(
//        		databaseUrl, "admin", "Passw0rd");
//        // execute a query
//      
// if(!cn.isClosed()){
//	 System.out.println("!cn.isClosed()="+(!cn.isClosed()));
//	 Statement statement = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE );
//
//
////	 System.out.println(TableStructure.getTables(cn));
//	 ResultSet rs = statement.executeQuery(" select * FROM CLAIM_MAIN ");
//	 while(rs.next()){
//		 System.out.println(rs.getString(0)+""+rs.getString(1));
//	 }
//	 rs.close();
//	 statement.close();
// }

//		Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
//		Connection cn = DriverManager.getConnection (
//				 "jdbc:microsoft:sqlserver://10.131.20.39:1433;DatabaseName=CIRCAudit"
//				 , "sa"
//				 , "cigna"
//				);	
//		Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
//		Connection cn = DriverManager.getConnection (
//				 "jdbc:microsoft:sqlserver://10.131.20.39:1433;DatabaseName=CN_ODS_SHADOW"
//				 , "sa"
//				 , "cigna"
//				);	
		Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
		Connection cn = DriverManager.getConnection (
				 "jdbc:microsoft:sqlserver://CNITES02:1433;DatabaseName=CIRCAudit"
				 , "test_user"
				 , "test_user"
				);
//		importByEXL2 ( cn, "C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\agt_code中介机构信息表_updated on 27 Jul.xls", 
//				"agt_code", 10,  519, 1);
		importByEXL2 ( cn, "C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\Plan_infor险种代码?20120730.xls", 
				"plan_info", 16,  680, 1);
		/*
		 * 请把您系统里面所用的险种代码表?
		 * 中介机构信息表?
		 * 员工信\
		 * 银保专管员信息表发给我，谢谢?
		 */
//		String sql_plan_info  = " select * from plan_info ";
//		String sql_agt_code   = " select * from agt_code ";
//		String sql_staff_info = " select * from staff_info ";
//		String sql_Banc_Speci_Info = " select * from Banc_Speci_Info ";
//		String sql_claim_report = " select * from claim_report ";
//		String sql_claim_main = " select * from claim_main ";
//		doSQL2EXL(cn, "C:\\0810\\sql_plan_info.xls", sql_plan_info);
//		doSQL2EXL(cn, "C:\\0810\\sql_agt_code.xls", sql_agt_code);
//		doSQL2EXL(cn, "C:\\0810\\sql_staff_info.xls", sql_staff_info);
//		doSQL2EXL(cn, "C:\\0810\\sql_Banc_Speci_Info.xls", sql_Banc_Speci_Info);
//		doSQL2EXL(cn, "C:\\0810\\sql_claim_report.xls", sql_claim_report);
//		doSQL2EXL(cn, "C:\\0810\\sql_claim_main.xls", sql_claim_main);
//		Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
//		Connection cn = DriverManager.getConnection (
//				 "jdbc:microsoft:sqlserver://10.131.20.62:1433;DatabaseName=ca1_nb_prod"
//				 , "nb_system"
//				 , "nb_system"
//				);
//		importByEXL2 ( cn, "C:\\Documents and Settings\\b3wang\\桌面\\新建文件?(3)\\副本plancode(PA提额) 0808 去除金卡.xls", "nb_sponsor_plancode_info ", 9,  91, 1);
//		importByEXL2 ( cn, "C:\\plancode(FW PA调额).xls", "nb_sponsor_plancode_info", 9,  11, 1);
		/*

//1   JHKJ002    缴费信息? 应缴日期、到账日期和保单生效日期都在规定时间区间以前    18  
String T1_CGHB="select * from CGHB_Prem_info where Payab_Date < '2012-01-01' and  Gained_Date < '2012-01-01' and Eff_Date <  '2012-01-01'";
doSQL2EXL(cn, "C:\\0726\\T1_CGHB.xls", T1_CGHB);
String T1_CICAP="select * from CICAP_Prem_info where Payab_Date < '2012-01-01' and  Gained_Date < '2012-01-01' and Eff_Date <  '2012-01-01'";
doSQL2EXL(cn, "C:\\0726\\T1_CICAP.xls", T1_CICAP);
String T1_IPMI="select * from IPMI_Prem_info where Payab_Date < '2012-01-01' and  Gained_Date < '2012-01-01' and Eff_Date <  '2012-01-01'";
doSQL2EXL(cn, "C:\\0726\\T1_IPMI.xls", T1_IPMI);System.out.println("====>1");
//2   JHKJ002    缴费信息? 应缴日期、到账日期和保单生效日期都在规定时间区间末以? 45  
String T2_CGHB="select * from CGHB_Prem_info where Payab_Date > '2012-03-31 23:59:59' and Gained_Date > '2012-03-31 23:59:59' and  Eff_Date > '2012-03-31 23:59:59'";
doSQL2EXL(cn, "C:\\0726\\T2_CGHB.xls", T2_CGHB);
String T2_CICAP="select * from CICAP_Prem_info where Payab_Date > '2012-03-31 23:59:59' and Gained_Date > '2012-03-31 23:59:59' and  Eff_Date > '2012-03-31 23:59:59'";
doSQL2EXL(cn, "C:\\0726\\T2_CICAP.xls", T2_CICAP);
String T2_IPMI="select * from IPMI_Prem_info where Payab_Date > '2012-03-31 23:59:59' and Gained_Date > '2012-03-31 23:59:59' and  Eff_Date > '2012-03-31 23:59:59'";
doSQL2EXL(cn, "C:\\0726\\T2_IPMI.xls", T2_IPMI);System.out.println("====>1");
//3   JHKJ005    报案信息? 险种代码在险种代码表中不存在或为?195 
String T3_CGHB="select * from CGHB_claim_report  ct where ct.Plan_Code is null or not exists(select distinct  1 from Plan_info where ct.Plan_Code = Plan_info.Plan_Code) ";
doSQL2EXL(cn, "C:\\0726\\T3_CGHB.xls", T3_CGHB);
String T3_IPMI="select * from IPMI_claim_report  ct where ct.Plan_Code is null or not exists(select distinct  1 from Plan_info where ct.Plan_Code = Plan_info.Plan_Code) ";
doSQL2EXL(cn, "C:\\0726\\T3_IPMI.xls", T3_IPMI);
String T3_CICAP="select * from NCS_claim_report  ct where ct.Plan_Code is null or not exists(select distinct  1 from Plan_info where ct.Plan_Code = Plan_info.Plan_Code) ";
doSQL2EXL(cn, "C:\\0726\\T3_CICAP.xls", T3_CICAP);System.out.println("====>1");
//4   JHWZ005    新保单信息表- ?渠道为代理或经纪业务时，中介机构代码在中介机构信息表中不存在或为?96  
String T4_CGHB="select * from CGHB_Pol_Main where (Agt_Code is null or Agt_Code = '' or not exists(select distinct  1 from agt_code where CGHB_Pol_Main.Agt_Code = agt_code.Agt_Code)) and Busi_Src_Type in('220','221','229','230','300')";
doSQL2EXL(cn, "C:\\0726\\T4_CGHB.xls", T4_CGHB);
String T4_CICAP="select * from CICAP_Pol_Main where (Agt_Code is null or Agt_Code = '' or not exists(select distinct  1 from agt_code where CICAP_Pol_Main.Agt_Code = agt_code.Agt_Code)) and Busi_Src_Type in('220','221','229','230','300')";
doSQL2EXL(cn, "C:\\0726\\T4_CICAP.xls", T4_CICAP);
String T4_IPMI="select * from IPMI_Pol_Main where (Agt_Code is null or Agt_Code = '' or not exists(select distinct  1 from agt_code where IPMI_Pol_Main.Agt_Code = agt_code.Agt_Code)) and Busi_Src_Type in('220','221','229','230','300')";
doSQL2EXL(cn, "C:\\0726\\T4_IPMI.xls", T4_IPMI);System.out.println("====>1");
//5   JHWZ005    新保单信息表- 中介机构代码在中介机构信息表中不存在 99  
String T5_CGHB="select * from CGHB_Pol_Main where Agt_Code is not null and Agt_Code != '' and not exists ( select distinct  Agt_Code from agt_code where CGHB_Pol_Main.Agt_Code = AGT_CODE.AGT_CODE)";
doSQL2EXL(cn, "C:\\0726\\T5_CGHB.xls", T5_CGHB);
String T5_CICAP="select * from CICAP_Pol_Main where Agt_Code is not null and Agt_Code != '' and not exists ( select distinct  Agt_Code from agt_code where CICAP_Pol_Main.Agt_Code = AGT_CODE.AGT_CODE)";
doSQL2EXL(cn, "C:\\0726\\T5_CICAP.xls", T5_CICAP);
String T5_IPMI="select * from IPMI_Pol_Main where Agt_Code is not null and Agt_Code != '' and not exists ( select distinct  Agt_Code from agt_code where IPMI_Pol_Main.Agt_Code = AGT_CODE.AGT_CODE)";
doSQL2EXL(cn, "C:\\0726\\T5_IPMI.xls", T5_IPMI);System.out.println("====>1");
//6   JHWZ006    缴费信息? 中介机构代码在中介机构信息表中不存在   234 
String T6_CGHB="select * from CGHB_Prem_info where Agt_Code is not null and Agt_Code != ''  and not exists( select distinct  Agt_Code from agt_code where CGHB_Prem_info.Agt_Code = AGT_CODE.AGT_CODE) ";
doSQL2EXL(cn, "C:\\0726\\T6_CGHB.xls", T6_CGHB);
String T6_CICAP="select * from CICAP_Prem_info where Agt_Code is not null and Agt_Code != ''  and not exists( select distinct  Agt_Code from agt_code where CICAP_Prem_info.Agt_Code = AGT_CODE.AGT_CODE) ";
doSQL2EXL(cn, "C:\\0726\\T6_CICAP.xls", T6_CICAP);
String T6_IPMI="select * from IPMI_Prem_info where Agt_Code is not null and Agt_Code != ''  and not exists( select distinct  Agt_Code from agt_code where IPMI_Prem_info.Agt_Code = AGT_CODE.AGT_CODE) ";
doSQL2EXL(cn, "C:\\0726\\T6_IPMI.xls", T6_IPMI);System.out.println("====>1");
//7   JHWZ006    缴费信息? 营销员代码在营销员信息表中不存在  372585 
//select distinct  Salesman_No from Prem_Info where Salesman_No is not null and Salesman_No != '' and not exists( select distinct  Salesman_No from Salesman_info where PREM_INFO.Salesman_No = SALESMAN_INFO.SALESMAN_NO) 
//8   JHWZ006    缴费信息? 银保专管员代码在银保专管员信息表中不存在   473 
String T8_CGHB="select * from CGHB_Prem_info where Speciman_No is not null and Speciman_No != '' and not exists( select distinct 1 Banc_Speci_No from Banc_Speci_Info where CGHB_Prem_info.Speciman_No = BANC_SPECI_INFO.BANC_SPECI_NO)";
doSQL2EXL(cn, "C:\\0726\\T8_CGHB.xls", T8_CGHB);
String T8_CICAP="select * from CICAP_Prem_info where Speciman_No is not null and Speciman_No != '' and not exists( select distinct 1 Banc_Speci_No from Banc_Speci_Info where CICAP_Prem_info.Speciman_No = BANC_SPECI_INFO.BANC_SPECI_NO)";
doSQL2EXL(cn, "C:\\0726\\T8_CICAP.xls", T8_CICAP);
String T8_IPMI="select * from IPMI_Prem_info where Speciman_No is not null and Speciman_No != '' and not exists( select distinct 1 Banc_Speci_No from Banc_Speci_Info where IPMI_Prem_info.Speciman_No = BANC_SPECI_INFO.BANC_SPECI_NO)";
doSQL2EXL(cn, "C:\\0726\\T8_IPMI.xls", T8_IPMI);System.out.println("====>1");
//9   JHWZ006    缴费信息? 员工代码若不为空：员工代码在员工信息表中不存在?应付员工绩效凭证号在财务凭证信息表中不存在或为空、员工直接绩效比例小于等于零   353172 
String T9_CGHB="select * from CGHB_Prem_info where Staff_No is not null and Staff_No != '' and not exists(select distinct  1 from Staff_Info where CGHB_Prem_info.Staff_No = Staff_Info.Staff_No) ";
doSQL2CSVByRows(cn, "C:\\0726\\T9_CGHB.xls", T9_CGHB,20000);
String T9_CICAP="select * from CICAP_Prem_info where Staff_No is not null and Staff_No != '' and not exists(select distinct  1 from Staff_Info where CICAP_Prem_info.Staff_No = Staff_Info.Staff_No) ";
doSQL2CSVByRows(cn, "C:\\0726\\T9_CICAP.xls", T9_CICAP,20000);
String T9_IPMI="select * from IPMI_Prem_info where Staff_No is not null and Staff_No != '' and not exists(select distinct  1 from Staff_Info where IPMI_Prem_info.Staff_No = Staff_Info.Staff_No) ";
doSQL2CSVByRows(cn, "C:\\0726\\T9_IPMI.xls", T9_IPMI,20000);System.out.println("====>1");
//10  JHWZ006    缴费信息? 新单期缴保费收入年期类型在新单期缴保费年期代码表中不存在或为?  249 
String T10_CGHB="select * from CGHB_Prem_info where not exists( select distinct  1 from New_Business_Info where CGHB_Prem_info.New_busi_Code = NEW_BUSINESS_INFO.P)";
doSQL2EXL(cn, "C:\\0726\\T10_CGHB.xls", T10_CGHB);
String T10_CICAP="select * from CICAP_Prem_info where not exists( select distinct  1 from New_Business_Info where CICAP_Prem_info.New_busi_Code = NEW_BUSINESS_INFO.P)";
doSQL2EXL(cn, "C:\\0726\\T10_CICAP.xls", T10_CICAP);
String T10_IPMI="select * from IPMI_Prem_info where not exists( select distinct  1 from New_Business_Info where IPMI_Prem_info.New_busi_Code = NEW_BUSINESS_INFO.P)";
doSQL2EXL(cn, "C:\\0726\\T10_IPMI.xls", T10_IPMI);System.out.println("====>1");
//11  JHWZ007    批单信息? 险种代码在险种代码表中不存在或为?33535  
String T11_CICAP="select * from CICAP_Endo_Fee where not exists( select distinct  1 from Plan_info where CICAP_Endo_Fee.Plan_Code = PLAN_INFO.PLAN_CODE) ";
doSQL2CSVByRows(cn, "C:\\0726\\T11_CICAP.xls", T11_CICAP,20000);
String T11_IPMI="select * from IPMI_Endo_Fee where not exists( select distinct  1 from Plan_info where IPMI_Endo_Fee.Plan_Code = PLAN_INFO.PLAN_CODE) ";
doSQL2CSVByRows(cn, "C:\\0726\\T11_IPMI.xls", T11_IPMI,20000);System.out.println("====>1");
//12  JHWZ007    批单信息? 中介机构代码在中介机构信息表中不存在   396 
String T12_CICAP="select * from CICAP_Endo_Fee where Agt_Code is not null and Agt_Code != '' and not exists( select distinct  Agt_Code from agt_code where CICAP_Endo_Fee.Agt_Code = AGT_CODE.AGT_CODE)";
doSQL2EXL(cn, "C:\\0726\\T12_CICAP.xls", T12_CICAP);
String T12_IPMI="select * from IPMI_Endo_Fee where Agt_Code is not null and Agt_Code != '' and not exists( select distinct  Agt_Code from agt_code where IPMI_Endo_Fee.Agt_Code = AGT_CODE.AGT_CODE)";
doSQL2EXL(cn, "C:\\0726\\T12_IPMI.xls", T12_IPMI);System.out.println("====>1");
//13  JHWZ007    批单信息? 营销员代码在营销员信息表中不存在  1488713    
//select * from Endo_Fee where Salesman_No is not null and Salesman_No != '' and not exists ( select distinct  1 from Salesman_info where Endo_Fee.Salesman_No = SALESMAN_INFO.SALESMAN_NO)
//14  JHWZ007    批单信息? 银保专管员代码在银保专管员信息表中不存在   5294   
String T14_CICAP="select * from CICAP_Endo_Fee where Speciman_No is not null and Speciman_No != '' and not exists( select distinct  1 from Banc_Speci_Info where CICAP_Endo_Fee.Speciman_No = BANC_SPECI_INFO.BANC_SPECI_NO)";
doSQL2EXL(cn, "C:\\0726\\T14_CICAP.xls", T14_CICAP);
String T14_IPMI="select * from IPMI_Endo_Fee where Speciman_No is not null and Speciman_No != '' and not exists( select distinct  1 from Banc_Speci_Info where IPMI_Endo_Fee.Speciman_No = BANC_SPECI_INFO.BANC_SPECI_NO)";
doSQL2EXL(cn, "C:\\0726\\T14_IPMI.xls", T14_IPMI);System.out.println("====>1");
//15  JHWZ007    批单信息? 员工代码在员工信息表中不存在  71104  
String T15_CICAP="select * from  CICAP_Endo_Fee where Staff_No is not null and Staff_No != '' and not exists( select distinct  1 from Staff_Info where CICAP_Endo_Fee.Staff_No = STAFF_INFO.STAFF_NO)";
doSQL2CSVByRows(cn, "C:\\0726\\T15_CICAP.xls", T15_CICAP,20000);
String T15_IPMI="select * from  IPMI_Endo_Fee where Staff_No is not null and Staff_No != '' and not exists( select distinct  1 from Staff_Info where IPMI_Endo_Fee.Staff_No = STAFF_INFO.STAFF_NO)";
doSQL2CSVByRows(cn, "C:\\0726\\T15_IPMI.xls", T15_IPMI,20000);System.out.println("====>1");
//16  JHWZ007    批单信息? 投保日期、保单生效日期?发生日期（批改日期）、批单生效日期为?  47  
String T16_CICAP="select * from  CICAP_Endo_Fee where App_Date is null or Pol_Eff_Date is null or Edr_Eff_Date is null or Proc_Date is null ";
doSQL2EXL(cn, "C:\\0726\\T16_CICAP.xls", T16_CICAP);
String T16_IPMI="select * from  IPMI_Endo_Fee where App_Date is null or Pol_Eff_Date is null or Edr_Eff_Date is null or Proc_Date is null ";
doSQL2EXL(cn, "C:\\0726\\T16_IPMI.xls", T16_IPMI);System.out.println("====>1");
//17  JHWZ007    批单信息? 批文、投保人证件号码、被保人名称为空   1488698    (批文除外)
String T17_CICAP="select * from  CICAP_Endo_Fee where App_Idcard_No is null or InsName is null ";
doSQL2CSVByRows(cn, "C:\\0726\\T17_CICAP.xls", T17_CICAP,20000);
String T17_IPMI="select * from  IPMI_Endo_Fee where  App_Idcard_No is null or InsName is null ";
doSQL2CSVByRows(cn, "C:\\0726\\T17_IPMI.xls", T17_IPMI,20000);System.out.println("====>1");
//18  JHWZ007    批单信息? 非现金收款方式的对方银行账号为空  1488698    （另，修改程序）
//select * from  Endo_Fee where (Bank_account_code is null or Bank_account_code='') and CollectPay_Way_Code != '1'
//19  JHWZ009    报案信息? 机构代码在分支机构信息表中不存在或为?195 
String T19_CGHB="select * from CGHB_claim_report where not exists (select distinct  Branch_Code from Branch_Info where CGHB_claim_report.Branch_Code = BRANCH_INFO.BRANCH_CODE)";
doSQL2EXL(cn, "C:\\0726\\T19_CGHB.xls", T19_CGHB);
String T19_IPMI="select * from IPMI_claim_report where not exists (select distinct  Branch_Code from Branch_Info where IPMI_claim_report.Branch_Code = BRANCH_INFO.BRANCH_CODE)";
doSQL2EXL(cn, "C:\\0726\\T19_IPMI.xls", T19_IPMI);
String T19_CICAP="select * from NCS_claim_report where not exists (select distinct  Branch_Code from Branch_Info where NCS_claim_report.Branch_Code = BRANCH_INFO.BRANCH_CODE)";
doSQL2EXL(cn, "C:\\0726\\T19_CICAP.xls", T19_CICAP);System.out.println("====>1");
//20  JHWZ010    赔案信息? 机构代码在分支机构信息表中不存在或为?125 
String T20_CGHB="select * from CGHB_claim_report where not exists( select distinct  1 from Branch_Info where CGHB_claim_report.Branch_Code = BRANCH_INFO.BRANCH_CODE)";
doSQL2EXL(cn, "C:\\0726\\T20_CGHB.xls", T20_CGHB);
String T20_IPMI="select * from IPMI_claim_report where not exists( select distinct  1 from Branch_Info where IPMI_claim_report.Branch_Code = BRANCH_INFO.BRANCH_CODE)";
doSQL2EXL(cn, "C:\\0726\\T20_IPMI.xls", T20_IPMI);
String T20_CICAP="select * from NCS_claim_report where not exists( select distinct  1 from Branch_Info where NCS_claim_report.Branch_Code = BRANCH_INFO.BRANCH_CODE)";
doSQL2EXL(cn, "C:\\0726\\T20_CICAP.xls", T20_CICAP);System.out.println("====>1");
//21  JHWZ010    赔案信息? 到账日期在取数区间内的应付赔款凭证号，在财务凭证表中不存? 6029   
//select * from claim_main where Gained_Date between '2012-01-01'and '2012-03-31 23:59:59' and Payab_Amt_cnvt is not null and Payab_Amt_cnvt != 0 and not exists( select distinct  1 from Voucher_Info where CLAIM_MAIN.Payab_Vou_Code = VOUCHER_INFO.VOUCHER_CODE)
//22  JHWZ010    赔案信息? 到账日期在取数区间内的实付赔款凭证号，在财务凭证表中不存? 6029   
//select * from claim_main where Gained_Date between '2012-01-01'and '2012-03-31 23:59:59' and Paid_Amt_cnvt is not null and Paid_Amt_cnvt != 0 and not exists( select distinct  1  from Voucher_Info where CLAIM_MAIN.Paid_Vou_Code = VOUCHER_INFO.VOUCHER_CODE)
//23  JHWZ010    赔案信息? ?渠道为直?，员工代码为?1894   
String T23_CGHB="select * from CGHB_claim_main where (Staff_No is null or Staff_No='') and Busi_Src_Type like ('1%')";
doSQL2EXL(cn, "C:\\0726\\T23_CGHB.xls", T23_CGHB);
String T23_IPMI="select * from IPMI_claim_main where (Staff_No is null or Staff_No='') and Busi_Src_Type like ('1%')";
doSQL2EXL(cn, "C:\\0726\\T23_IPMI.xls", T23_IPMI);
String T23_CICAP="select * from NCS_claim_main where (Staff_No is null or Staff_No='') and Busi_Src_Type like ('1%')";
doSQL2EXL(cn, "C:\\0726\\T23_CICAP.xls", T23_CICAP);System.out.println("====>1");
//24  JHWZ010    赔案信息? 非现金收款方式的对方银行账号为空  619 
String T24_CGHB="select * from CGHB_claim_main where (Pay_account_code is null or Pay_account_code='') and CollectPay_Code != '1'";
doSQL2EXL(cn, "C:\\0726\\T24_CGHB.xls", T24_CGHB);
String T24_IPMI="select * from IPMI_claim_main where (Pay_account_code is null or Pay_account_code='') and CollectPay_Code != '1'";
doSQL2EXL(cn, "C:\\0726\\T24_IPMI.xls", T24_IPMI);
String T24_CICAP="select * from NCS_claim_main where (Pay_account_code is null or Pay_account_code='') and CollectPay_Code != '1'";
doSQL2EXL(cn, "C:\\0726\\T24_CICAP.xls", T24_CICAP);System.out.println("====>1");
//25  JHWZ011    单证信息? 单证代码在单证代码表中不存在或为?160000 
//select * from Bill_Info where not exists( select distinct  1 from Bill_Code_Info where BILL_INFO.Bill_Code = BILL_CODE_INFO.BILL_CODE)
//26  JHWZ011    单证信息? 使用机构代码在分支机构信息表中不存在或为?5535332    
//select * from Bill_Info where not exists( select distinct  1 from Branch_Info  where BILL_INFO.Branch_Code = BRANCH_INFO.BRANCH_CODE)
//27  JHWZ012    财务凭证信息? 没有期初余额记录  1   
String T27="select * from voucher_info where Is_Original = 'Y'";
doSQL2EXL(cn, "C:\\0726\\T27.xls", T27);
//28  JHWZ012    险种代码? 停止?日期存在空?,或日期关系不正确  5   
String T28="select * from plan_info where StopDate is null or Startdate < stopdate";
doSQL2EXL(cn, "C:\\0726\\T28.xls", T28);
//29  JHWZ012    中介机构信息? 获得许可证日期?许可证到期日期?签约日期、协议到期日存在空?,或日期关系不正确 444 
String T29="select * from AGT_CODE where  STARTDATE IS NULL OR STARTDATE>=ENDDATE OR ENDDATE IS NULL OR SIGNDATE = '1900-01-01' OR QUITDATE ='1900-01-01' OR SIGNDATE IS NULL OR QUITDATE IS NULL ";
doSQL2EXL(cn, "C:\\0726\\T29.xls", T29);System.out.println("====>1");
//30  JHWZ012    分支机构信息? 负责人代码在员工信息表中不存在或为空   13  
//select * from Branch_Info  where not exists( select distinct  1 from Staff_Info where BRANCH_INFO.Leader_Code = STAFF_INFO.STAFF_NO)
//31  JHWZ012    分支机构信息? 经营区域代码不是GB/T2260规定的行政区划代码或为空  2   
String T31="select * from Branch_Info where len(Branch_area_code)!=6";
doSQL2EXL(cn, "C:\\0726\\T31.xls", T31);System.out.println("====>1");
*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*20120808
//		1	JHKJ002	缴费信息? 应缴日期、到账日期和保单生效日期都在规定时间区间以前	58	
String T01_CGHB  = " select * from CGHB_Prem_info where Payab_Date < '2012-01-01' and  Gained_Date < '2012-01-01' and Eff_Date <  '2012-01-01' ";
doSQL2EXL(cn, "C:\\0808\\T01_CGHB.xls", T01_CGHB);
String T01_CICAP = " select * from CICAP_Prem_info where Payab_Date < '2012-01-01' and  Gained_Date < '2012-01-01' and Eff_Date <  '2012-01-01' ";
doSQL2EXL(cn, "C:\\0808\\T01_CICAP.xls", T01_CICAP);
String T01_IPMI  = " select * from IPMI_Prem_info where Payab_Date < '2012-01-01' and  Gained_Date < '2012-01-01' and Eff_Date <  '2012-01-01' ";
doSQL2EXL(cn, "C:\\0808\\T01_IPMI.xls", T01_IPMI);
//		2	JHKJ002	缴费信息? 应缴日期、到账日期和保单生效日期都在规定时间区间末以?1	
String T02_CGHB  = " select * from CGHB_Prem_info where Payab_Date > '2012-03-31 23:59:59' and Gained_Date > '2012-03-31 23:59:59' and  Eff_Date > '2012-03-31 23:59:59' ";
doSQL2EXL(cn, "C:\\0808\\T02_CGHB.xls", T02_CGHB);
String T02_CICAP = " select * from CICAP_Prem_info where Payab_Date > '2012-03-31 23:59:59' and Gained_Date > '2012-03-31 23:59:59' and  Eff_Date > '2012-03-31 23:59:59' ";
doSQL2EXL(cn, "C:\\0808\\T02_CICAP.xls", T02_CICAP);
String T02_IPMI  = " select * from IPMI_Prem_info where Payab_Date > '2012-03-31 23:59:59' and Gained_Date > '2012-03-31 23:59:59' and  Eff_Date > '2012-03-31 23:59:59' ";
doSQL2EXL(cn, "C:\\0808\\T02_IPMI.xls", T02_IPMI);
//		3	JHKJ005	报案信息? 险种代码在险种代码表中不存在或为?159	
String T03_CGHB  = " select * from CGHB_claim_report  ct where ct.Plan_Code is null or not exists(select 1 from Plan_info where ct.Plan_Code = Plan_info.Plan_Code)  ";
doSQL2EXL(cn, "C:\\0808\\T03_CGHB.xls", T03_CGHB);

String T03_NCS= " select * from NCS_claim_report  ct where ct.Plan_Code is null or not exists(select 1 from Plan_info where ct.Plan_Code = Plan_info.Plan_Code)  ";
doSQL2EXL(cn, "C:\\0808\\T03_CICAP.xls", T03_NCS);
String T03_IPMI  = " select * from IPMI_claim_report  ct where ct.Plan_Code is null or not exists(select 1 from Plan_info where ct.Plan_Code = Plan_info.Plan_Code)  ";
doSQL2EXL(cn, "C:\\0808\\T03_IPMI.xls", T03_IPMI);
//		4	JHKJ006	赔案信息? 报案日期、实付日期?结案日期三?大?在取数区间以?1	
String T04_CGHB  = " select * from CGHB_claim_main where End_Date<'2012-01-01' ";
doSQL2EXL(cn, "C:\\0808\\T04_CGHB.xls", T04_CGHB);

String T04_NCS = " select * from NCS_claim_main where End_Date<'2012-01-01' ";
doSQL2EXL(cn, "C:\\0808\\T04_CICAP.xls", T04_NCS);
String T04_IPMI  = " select * from IPMI_claim_main where End_Date<'2012-01-01' ";
doSQL2EXL(cn, "C:\\0808\\T04_IPMI.xls", T04_IPMI);
//		5	JHKJ006	赔案信息? 报案日期在取数区间末以后	1	
String T05_CGHB  = " select * from CGHB_claim_main where Docu_Date>'2012-03-31 23:59:59' ";
doSQL2EXL(cn, "C:\\0808\\T05_CGHB.xls", T05_CGHB);

String T05_NCS = " select * from NCS_claim_main where Docu_Date>'2012-03-31 23:59:59' ";
doSQL2EXL(cn, "C:\\0808\\T05_CICAP.xls", T05_NCS);
String T05_IPMI  = " select * from IPMI_claim_main where Docu_Date>'2012-03-31 23:59:59' ";
doSQL2EXL(cn, "C:\\0808\\T05_IPMI.xls", T05_IPMI);


//		6	JHKJ006	赔案信息?收付款方式为2?时：对方银行帐号为空或不符合银行帐号?特征（如存在数字、位数大?等）	2	
String T06_CGHB  = " select *  from CGHB_claim_main WHERE CGHB_claim_main.CollectPay_Code IN('2','5') and (CGHB_claim_main.Pay_account_code IS NULL OR LEN(CGHB_claim_main.Pay_account_code) <=5 or CGHB_claim_main.Pay_account_code not like '%[0-9]%') ";
doSQL2EXL(cn, "C:\\0808\\T06_CGHB.xls", T06_CGHB);
String T06_NCS = " select *  from NCS_claim_main WHERE NCS_claim_main.CollectPay_Code IN('2','5') and (NCS_claim_main.Pay_account_code IS NULL OR LEN(NCS_claim_main.Pay_account_code) <=5 or NCS_claim_main.Pay_account_code not like '%[0-9]%') ";
doSQL2EXL(cn, "C:\\0808\\T06_CICAP.xls", T06_NCS);
String T06_IPMI  = " select *  from IPMI_claim_main WHERE IPMI_claim_main.CollectPay_Code IN('2','5') and (IPMI_claim_main.Pay_account_code IS NULL OR LEN(IPMI_claim_main.Pay_account_code) <=5 or IPMI_claim_main.Pay_account_code not like '%[0-9]%') ";
doSQL2EXL(cn, "C:\\0808\\T06_IPMI.xls", T06_IPMI);

//		7	JHWZ005	新保单信息表- ?渠道为代理或经纪业务时，中介机构代码在中介机构信息表中不存在或为?96	
String T07_CGHB = " select * from CGHB_Pol_Main where (Agt_Code is null or Agt_Code = '' or not exists(select 1 from agt_code where CGHB_Pol_Main.Agt_Code = agt_code.Agt_Code)) and Busi_Src_Type in('220','221','229','230','300') ";
doSQL2EXL(cn, "C:\\0808\\T07_CGHB.xls", T07_CGHB);
String T07_CICAP = " select * from CICAP_Pol_Main where (Agt_Code is null or Agt_Code = '' or not exists(select 1 from agt_code where CICAP_Pol_Main.Agt_Code = agt_code.Agt_Code)) and Busi_Src_Type in('220','221','229','230','300') ";
doSQL2EXL(cn, "C:\\0808\\T07_CICAP.xls", T07_CICAP);
String T07_IPMI  = " select * from IPMI_Pol_Main where (Agt_Code is null or Agt_Code = '' or not exists(select 1 from agt_code where IPMI_Pol_Main.Agt_Code = agt_code.Agt_Code)) and Busi_Src_Type in('220','221','229','230','300') ";
doSQL2EXL(cn, "C:\\0808\\T07_IPMI.xls", T07_IPMI);
//		8	JHWZ005	新保单信息表- 中介机构代码在中介机构信息表中不存在	99	
String T08_CGHB = " select * from CGHB_Pol_Main where Agt_Code is not null and Agt_Code != '' and not exists ( select Agt_Code from agt_code where CGHB_Pol_Main.Agt_Code = AGT_CODE.AGT_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T08_CGHB.xls", T08_CGHB);
String T08_CICAP = " select * from CICAP_Pol_Main where Agt_Code is not null and Agt_Code != '' and not exists ( select Agt_Code from agt_code where CICAP_Pol_Main.Agt_Code = AGT_CODE.AGT_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T08_CICAP.xls", T08_CICAP);
String T08_IPMI  = " select * from IPMI_Pol_Main where Agt_Code is not null and Agt_Code != '' and not exists ( select Agt_Code from agt_code where IPMI_Pol_Main.Agt_Code = AGT_CODE.AGT_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T08_IPMI.xls", T08_IPMI);
//		9	JHWZ006	缴费信息? 投保人证件类型在证件类型代码表中不存在或为空	15	
String T09_CGHB  = " select * from CGHB_Prem_Info where not exists( select P from Id_Card_Type where CGHB_Prem_Info.App_Idcard_Type = ID_CARD_TYPE.P) ";
doSQL2EXL(cn, "C:\\0808\\T09_CGHB.xls", T09_CGHB);
String T09_CICAP = " select * from CICAP_Prem_Info where not exists( select P from Id_Card_Type where CICAP_Prem_Info.App_Idcard_Type = ID_CARD_TYPE.P) ";
doSQL2EXL(cn, "C:\\0808\\T09_CICAP.xls", T09_CICAP);
String T09_IPMI  = " select * from IPMI_Prem_Info where not exists( select P from Id_Card_Type where IPMI_Prem_Info.App_Idcard_Type = ID_CARD_TYPE.P) ";
doSQL2EXL(cn, "C:\\0808\\T09_IPMI.xls", T09_IPMI);
//		10	JHWZ006	缴费信息? 缴费类型代码在缴?领取类型代码基表中不存在或为?182	
String T10_CGHB  = " select * from CGHB_Prem_Info where not exists ( select P from Prem_Annuity_Type  where CGHB_Prem_Info.Prem_Type = PREM_ANNUITY_TYPE.P) ";
doSQL2EXL(cn, "C:\\0808\\T10_CGHB.xls", T10_CGHB);
String T10_CICAP = " select * from CICAP_Prem_Info where not exists ( select P from Prem_Annuity_Type  where CICAP_Prem_Info.Prem_Type = PREM_ANNUITY_TYPE.P) ";
doSQL2EXL(cn, "C:\\0808\\T10_CICAP.xls", T10_CICAP);
String T10_IPMI  = " select * from IPMI_Prem_Info where not exists ( select P from Prem_Annuity_Type  where IPMI_Prem_Info.Prem_Type = PREM_ANNUITY_TYPE.P) ";
doSQL2EXL(cn, "C:\\0808\\T10_IPMI.xls", T10_IPMI);
//		11	JHWZ006	缴费信息? 被保险人证件类型在证件类型代码表中不存在	127	
String T11_CGHB  = " select * from CGHB_Prem_Info where Ins_Idcard_Type is not null and Ins_Idcard_Type != '' and not exists( select P from Id_Card_Type where CGHB_Prem_Info.Ins_Idcard_Type = ID_CARD_TYPE.P)  ";
doSQL2EXL(cn, "C:\\0808\\T11_CGHB.xls", T11_CGHB);
String T11_CICAP = " select * from CICAP_Prem_Info where Ins_Idcard_Type is not null and Ins_Idcard_Type != '' and not exists( select P from Id_Card_Type where CICAP_Prem_Info.Ins_Idcard_Type = ID_CARD_TYPE.P)  ";
doSQL2EXL(cn, "C:\\0808\\T11_CICAP.xls", T11_CICAP);
String T11_IPMI  = " select * from IPMI_Prem_Info where Ins_Idcard_Type is not null and Ins_Idcard_Type != '' and not exists( select P from Id_Card_Type where IPMI_Prem_Info.Ins_Idcard_Type = ID_CARD_TYPE.P)  ";
doSQL2EXL(cn, "C:\\0808\\T11_IPMI.xls", T11_IPMI);

//		12	JHWZ006	缴费信息? 中介机构代码在中介机构信息表中不存在	95	
String T12_CGHB  = " select * from CGHB_Prem_Info where Agt_Code is not null and Agt_Code != ''  and not exists( select Agt_Code from agt_code where CGHB_Prem_Info.Agt_Code = AGT_CODE.AGT_CODE)  ";
doSQL2EXL(cn, "C:\\0808\\T12_CGHB.xls", T12_CGHB);
String T12_CICAP = " select * from CICAP_Prem_Info where Agt_Code is not null and Agt_Code != ''  and not exists( select Agt_Code from agt_code where CICAP_Prem_Info.Agt_Code = AGT_CODE.AGT_CODE)  ";
doSQL2EXL(cn, "C:\\0808\\T12_CICAP.xls", T12_CICAP);
String T12_IPMI  = " select * from IPMI_Prem_Info where Agt_Code is not null and Agt_Code != ''  and not exists( select Agt_Code from agt_code where IPMI_Prem_Info.Agt_Code = AGT_CODE.AGT_CODE)  ";
doSQL2EXL(cn, "C:\\0808\\T12_IPMI.xls", T12_IPMI);
//		13	JHWZ006	缴费信息? 营销员代码在营销员信息表中不存在	372533	
//String T13 = " select * from Prem_Info where Salesman_No is not null and Salesman_No != '' and not exists( select Salesman_No from Salesman_info where PREM_INFO.Salesman_No = SALESMAN_INFO.SALESMAN_NO)  ";
//		14	JHWZ006	缴费信息? 员工代码若不为空：员工代码在员工信息表中不存在?应付员工绩效凭证号在财务凭证信息表中不存在或为空、员工直接绩效比例小于等于零	353153	
//String T14 = " select * from Prem_Info where Staff_No is not null and Staff_No != '' and (not exists(select 1 from Staff_Info where Prem_Info.Staff_No = Staff_Info.Staff_No) or (not exists(select 1 from Voucher_Info where Prem_Info.PayabBonus_Vou_Code = Voucher_Info.Voucher_Code) or PayabBonus_Vou_Code is null or PayabBonus_Vou_Code = '') or Staff_Bonus_Rate <= 0) ";
//		15	JHWZ006	缴费信息? 收付款方式在收付款方式代码基表中不存在或为空	25	

String T15_CGHB  = " select * from CGHB_Prem_Info where not exists( select P from CollectPay_Code where CGHB_Prem_Info.CollectPay_Code = COLLECTPAY_CODE.P) ";
doSQL2EXL(cn, "C:\\0808\\T15_CGHB.xls", T15_CGHB);
String T15_CICAP = " select * from CICAP_Prem_Info where not exists( select P from CollectPay_Code where CICAP_Prem_Info.CollectPay_Code = COLLECTPAY_CODE.P) ";
doSQL2EXL(cn, "C:\\0808\\T15_CICAP.xls", T15_CICAP);
String T15_IPMI  = " select * from IPMI_Prem_Info where not exists( select P from CollectPay_Code where IPMI_Prem_Info.CollectPay_Code = COLLECTPAY_CODE.P) ";
doSQL2EXL(cn, "C:\\0808\\T15_IPMI.xls", T15_IPMI);
//		16	JHWZ007	批单信息? 险种代码在险种代码表中不存在或为?33535	
String T16_CICAP = " select * from CICAP_Endo_Fee where not exists( select 1 from Plan_info where CICAP_Endo_Fee.Plan_Code = PLAN_INFO.PLAN_CODE)  ";
doSQL2EXL(cn, "C:\\0808\\T16_CICAP.xls", T16_CICAP);
String T16_IPMI  = " select * from IPMI_Endo_Fee where not exists( select 1 from Plan_info where IPMI_Endo_Fee.Plan_Code = PLAN_INFO.PLAN_CODE)  ";
doSQL2EXL(cn, "C:\\0808\\T16_IPMI.xls", T16_IPMI);

//		17	JHWZ007	批单信息? 中介机构代码在中介机构信息表中不存在	396	
String T17_CICAP = " select * from CICAP_Endo_Fee where Agt_Code is not null and Agt_Code != '' and not exists( select Agt_Code from agt_code where CICAP_Endo_Fee.Agt_Code = AGT_CODE.AGT_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T17_CICAP.xls", T17_CICAP);
String T17_IPMI  = " select * from IPMI_Endo_Fee where Agt_Code is not null and Agt_Code != '' and not exists( select Agt_Code from agt_code where IPMI_Endo_Fee.Agt_Code = AGT_CODE.AGT_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T17_IPMI.xls", T17_IPMI);
//		18	JHWZ007	批单信息? 营销员代码在营销员信息表中不存在	1488713	
//String T18 = " select * from Endo_Fee where Salesman_No is not null and Salesman_No != '' and not exists ( select 1 from Salesman_info where ENDO_FEE.Salesman_No = SALESMAN_INFO.SALESMAN_NO) ";
//		19	JHWZ007	批单信息? 银保专管员代码在银保专管员信息表中不存在	927	
String T19_CICAP = " select * from CICAP_Endo_Fee where Speciman_No is not null and Speciman_No != '' and not exists( select 1 from Banc_Speci_Info where CICAP_Endo_Fee.Speciman_No = BANC_SPECI_INFO.BANC_SPECI_NO) ";
doSQL2EXL(cn, "C:\\0808\\T19_CICAP.xls", T19_CICAP);
String T19_IPMI  = " select * from IPMI_Endo_Fee where Speciman_No is not null and Speciman_No != '' and not exists( select 1 from Banc_Speci_Info where IPMI_Endo_Fee.Speciman_No = BANC_SPECI_INFO.BANC_SPECI_NO) ";
doSQL2EXL(cn, "C:\\0808\\T19_IPMI.xls", T19_IPMI);
//		20	JHWZ007	批单信息? 员工代码在员工信息表中不存在	70234	
String T20_CICAP = " select * from CICAP_Endo_Fee where Staff_No is not null and Staff_No != '' and not exists( select 1 from Staff_Info where CICAP_Endo_Fee.Staff_No = STAFF_INFO.STAFF_NO) ";
doSQL2EXL(cn, "C:\\0808\\T20_CICAP.xls", T20_CICAP);
String T20_IPMI  = " select * from IPMI_Endo_Fee where Staff_No is not null and Staff_No != '' and not exists( select 1 from Staff_Info where IPMI_Endo_Fee.Staff_No = STAFF_INFO.STAFF_NO) ";
doSQL2EXL(cn, "C:\\0808\\T20_IPMI.xls", T20_IPMI);
//		21	JHWZ007	批单信息? 投保日期、保单生效日期?发生日期（批改日期）、批单生效日期为?47	
String T21_CICAP = " select * from  CICAP_Endo_Fee where App_Date is null or Pol_Eff_Date is null or Edr_Eff_Date is null or Proc_Date is null  ";
doSQL2EXL(cn, "C:\\0808\\T21_CICAP.xls", T21_CICAP);
String T21_IPMI  = " select * from  IPMI_Endo_Fee where App_Date is null or Pol_Eff_Date is null or Edr_Eff_Date is null or Proc_Date is null  ";
doSQL2EXL(cn, "C:\\0808\\T21_IPMI.xls", T21_IPMI);
//		22	JHWZ007	批单信息? 批文、投保人证件号码、被保人名称为空	1488698	 
//String T22 = " select * from  Endo_Fee where Endo_Content is null or App_Idcard_No is null or InsName is null  ";
//		23	JHWZ007	批单信息? 非现金收款方式的对方银行账号为空	1488698	
//String T23 = " select * from Endo_Fee where (Bank_account_code is null or Bank_account_code='') and CollectPay_Way_Code != '1' ";
//		24	JHWZ009	报案信息? 机构代码在分支机构信息表中不存在或为?159	
String T24_CGHB  = " select * from CGHB_claim_report where not exists (select Branch_Code from Branch_Info where CGHB_claim_report.Branch_Code = BRANCH_INFO.BRANCH_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T24_CGHB.xls", T24_CGHB);
String T24_NCS = " select * from NCS_claim_report where not exists (select Branch_Code from Branch_Info where NCS_claim_report.Branch_Code = BRANCH_INFO.BRANCH_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T24_CICAP.xls", T24_NCS);
String T24_IPMI  = " select * from IPMI_claim_report where not exists (select Branch_Code from Branch_Info where IPMI_claim_report.Branch_Code = BRANCH_INFO.BRANCH_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T24_IPMI.xls", T24_IPMI);
//		25	JHWZ010	赔案信息? 险种代码在险种代码表中不存在或为?52	
String T25_CGHB  = " select * from CGHB_claim_main where not exists( select 1 from Plan_info where CGHB_claim_main.Plan_Code = PLAN_INFO.PLAN_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T25_CGHB.xls", T25_CGHB);
String T25_NCS = " select * from NCS_claim_main where not exists( select 1 from Plan_info where NCS_claim_main.Plan_Code = PLAN_INFO.PLAN_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T25_CICAP.xls", T25_NCS);
String T25_IPMI  = " select * from IPMI_claim_main where not exists( select 1 from Plan_info where IPMI_claim_main.Plan_Code = PLAN_INFO.PLAN_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T25_IPMI.xls", T25_IPMI);
//		26	JHWZ010	赔案信息? 机构代码在分支机构信息表中不存在或为?171	
String T26_CGHB  = " select * from CGHB_claim_main where not exists( select 1 from Branch_Info where CGHB_claim_main.Branch_Code = BRANCH_INFO.BRANCH_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T26_CGHB.xls", T26_CGHB);
String T26_NCS = " select * from NCS_claim_main where not exists( select 1 from Branch_Info where NCS_claim_main.Branch_Code = BRANCH_INFO.BRANCH_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T26_CICAP.xls", T26_NCS);
String T26_IPMI  = " select * from IPMI_claim_main where not exists( select 1 from Branch_Info where IPMI_claim_main.Branch_Code = BRANCH_INFO.BRANCH_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T26_IPMI.xls", T26_IPMI);
//		27	JHWZ010	赔案信息? 投保人证件类型在证件类型代码表中不存在或为空	1	
String T27_CGHB  = " select * from CGHB_claim_main where not exists( select P from Id_Card_Type where CGHB_claim_main.App_Idcard_Type = ID_CARD_TYPE.P) ";
doSQL2EXL(cn, "C:\\0808\\T27_CGHB.xls", T27_CGHB);
String T27_NCS = " select * from NCS_claim_main where not exists( select P from Id_Card_Type where NCS_claim_main.App_Idcard_Type = ID_CARD_TYPE.P) ";
doSQL2EXL(cn, "C:\\0808\\T27_CICAP.xls", T27_NCS);
String T27_IPMI  = " select * from IPMI_claim_main where not exists( select P from Id_Card_Type where IPMI_claim_main.App_Idcard_Type = ID_CARD_TYPE.P) ";
doSQL2EXL(cn, "C:\\0808\\T27_IPMI.xls", T27_IPMI);
//		28	JHWZ010	赔案信息? 领取人证件类型在证件类型代码表中不存在或为空	1	
String T28_CGHB  = " select * from CGHB_claim_main where Payab_Amt_cnvt != 0 and not exists ( select P from Id_Card_Type where CGHB_claim_main.Paid_Idcard_Type = ID_CARD_TYPE.P) ";
doSQL2EXL(cn, "C:\\0808\\T28_CGHB.xls", T28_CGHB);
String T28_NCS = " select * from NCS_claim_main where Payab_Amt_cnvt != 0 and not exists ( select P from Id_Card_Type where NCS_claim_main.Paid_Idcard_Type = ID_CARD_TYPE.P) ";
doSQL2EXL(cn, "C:\\0808\\T28_CICAP.xls", T28_NCS);
String T28_IPMI  = " select * from IPMI_claim_main where Payab_Amt_cnvt != 0 and not exists ( select P from Id_Card_Type where IPMI_claim_main.Paid_Idcard_Type = ID_CARD_TYPE.P) ";
doSQL2EXL(cn, "C:\\0808\\T28_IPMI.xls", T28_IPMI);
//		29	JHWZ010	赔案信息? 中介机构代码在中介机构信息表中不存在	171	
String T29_CGHB  = " select * from CGHB_claim_main where Agt_Code is not null and Agt_Code != ''  and not exists( select 1 from agt_code where CGHB_claim_main.Agt_Code = AGT_CODE.AGT_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T29_CGHB.xls", T29_CGHB);
String T29_NCS = " select * from NCS_claim_main where Agt_Code is not null and Agt_Code != ''  and not exists( select 1 from agt_code where NCS_claim_main.Agt_Code = AGT_CODE.AGT_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T29_CICAP.xls", T29_NCS);
String T29_IPMI  = " select * from IPMI_claim_main where Agt_Code is not null and Agt_Code != ''  and not exists( select 1 from agt_code where IPMI_claim_main.Agt_Code = AGT_CODE.AGT_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T29_IPMI.xls", T29_IPMI);
//		30	JHWZ010	赔案信息? 员工代码在员工信息表中不存在	1	
String T30_CGHB  = " select * from CGHB_claim_main where Staff_No is not null and Staff_No != ''  and not exists( select 1 from Staff_Info where CGHB_claim_main.Staff_No = STAFF_INFO.STAFF_NO) ";
doSQL2EXL(cn, "C:\\0808\\T30_CGHB.xls", T30_CGHB);
String T30_NCS = " select * from NCS_claim_main where Staff_No is not null and Staff_No != ''  and not exists( select 1 from Staff_Info where NCS_claim_main.Staff_No = STAFF_INFO.STAFF_NO) ";
doSQL2EXL(cn, "C:\\0808\\T30_CICAP.xls", T30_NCS);
String T30_IPMI  = " select * from IPMI_claim_main where Staff_No is not null and Staff_No != ''  and not exists( select 1 from Staff_Info where IPMI_claim_main.Staff_No = STAFF_INFO.STAFF_NO) ";
doSQL2EXL(cn, "C:\\0808\\T30_IPMI.xls", T30_IPMI);
//		31	JHWZ010	赔案信息? 到账日期在取数区间内的应付赔款凭证号，在财务凭证表中不存?6030	
//String T31 = " select * from claim_main where Gained_Date between '2012-01-01'and '2012-03-31 23:59:59' and Payab_Amt_cnvt is not null and Payab_Amt_cnvt != 0 and not exists( select 1 from Voucher_Info where CLAIM_MAIN.Payab_Vou_Code = VOUCHER_INFO.VOUCHER_CODE) ";
//		32	JHWZ010	赔案信息? 到账日期在取数区间内的实付赔款凭证号，在财务凭证表中不存?6030	
//String T32 = " select * from claim_main where Gained_Date between '2012-01-01'and '2012-03-31 23:59:59' and Paid_Amt_cnvt is not null and Paid_Amt_cnvt != 0 and not exists( select 1  from Voucher_Info where CLAIM_MAIN.Paid_Vou_Code = VOUCHER_INFO.VOUCHER_CODE) ";
//		33	JHWZ010	赔案信息? ?渠道为直?，员工代码为?2000	
String T33_CGHB  = " select * from CGHB_claim_main where (Staff_No is null or Staff_No='') and Busi_Src_Type like ('1%') ";
doSQL2EXL(cn, "C:\\0808\\T33_CGHB.xls", T33_CGHB);
String T33_NCS = " select * from NCS_claim_main where (Staff_No is null or Staff_No='') and Busi_Src_Type like ('1%') ";
doSQL2EXL(cn, "C:\\0808\\T33_CICAP.xls", T33_NCS);
String T33_IPMI  = " select * from IPMI_claim_main where (Staff_No is null or Staff_No='') and Busi_Src_Type like ('1%') ";
doSQL2EXL(cn, "C:\\0808\\T33_IPMI.xls", T33_IPMI);
//		34	JHWZ010	赔案信息? ?渠道为银行邮政代理时，银保专管员代码为空	14	
String T34_CGHB  = " select * from CGHB_claim_main where (Speciman_No is null or Speciman_No='') and Busi_Src_Type = '221' ";
doSQL2EXL(cn, "C:\\0808\\T34_CGHB.xls", T34_CGHB);
String T34_NCS = " select * from NCS_claim_main where (Speciman_No is null or Speciman_No='') and Busi_Src_Type = '221' ";
doSQL2EXL(cn, "C:\\0808\\T34_CICAP.xls", T34_NCS);
String T34_IPMI  = " select * from IPMI_claim_main where (Speciman_No is null or Speciman_No='') and Busi_Src_Type = '221' ";
doSQL2EXL(cn, "C:\\0808\\T34_IPMI.xls", T34_IPMI);
//		35	JHWZ010	赔案信息? 非现金收款方式的对方银行账号为空	988	
String T35_CGHB  = " select * from CGHB_claim_main where (Pay_account_code is null or Pay_account_code='') and CollectPay_Code != '1' ";
doSQL2EXL(cn, "C:\\0808\\T35_CGHB.xls", T35_CGHB);
String T35_NCS = " select * from NCS_claim_main where (Pay_account_code is null or Pay_account_code='') and CollectPay_Code != '1' ";
doSQL2EXL(cn, "C:\\0808\\T35_CICAP.xls", T35_NCS);
String T35_IPMI  = " select * from IPMI_claim_main where (Pay_account_code is null or Pay_account_code='') and CollectPay_Code != '1' ";
doSQL2EXL(cn, "C:\\0808\\T35_IPMI.xls", T35_IPMI);
//		36	JHWZ011	单证信息? 使用机构代码在分支机构信息表中不存在或为?3200833	
//String T36 = " select * from Bill_Info where not exists( select 1 from Branch_Info  where BILL_INFO.Branch_Code = BRANCH_INFO.BRANCH_CODE) ";
//		37	JHWZ012	财务凭证信息? 没有期初余额记录	1	
String T37 = " select * from voucher_info where Is_Original = 'Y' ";
doSQL2EXL(cn, "C:\\0808\\T37.xls", T37);
//		38	JHWZ012	险种代码? 停止?日期存在空?,或日期关系不正确	5	
String T38="select * from plan_info where StopDate is null or Startdate < stopdate";
doSQL2EXL(cn, "C:\\0808\\T38.xls", T38);
//		39	JHWZ012	中介机构信息? 获得许可证日期?许可证到期日期?签约日期、协议到期日存在空?,或日期关系不正确	444	
String T39 = " select * from AGT_CODE where  STARTDATE IS NULL OR STARTDATE>=ENDDATE OR ENDDATE IS NULL OR SIGNDATE = '1900-01-01' OR QUITDATE ='1900-01-01' OR SIGNDATE IS NULL OR QUITDATE IS NULL  ";
doSQL2EXL(cn, "C:\\0808\\T39.xls", T39);
//		40	JHWZ012	员工信息? 证件类型在证件类型代码表中不存在或为?1	
String T40 = " select * from Staff_Info  where not exists( select 1 from Id_Card_Type where STAFF_INFO.Id_type = ID_CARD_TYPE.P) ";
doSQL2EXL(cn, "C:\\0808\\T40.xls", T40);
//		41	JHWZ012	员工信息? ?分支机构在分支机构信息表中不存在或为?5	
String T41 = " select * from Staff_Info  where not exists( select 1 from Branch_info where STAFF_INFO.Depart_info = BRANCH_INFO.BRANCH_CODE) ";
doSQL2EXL(cn, "C:\\0808\\T41.xls", T41);
//		42	JHWZ012	银保专管员信息表- 学历在学历代码基表中不存在或为空	754	
String T42 = " select * from Banc_Speci_Info  where not exists( select 1 from Edu_Degree_Code where BANC_SPECI_INFO.Edu_Degree = EDU_DEGREE_CODE.P) ";
doSQL2EXL(cn, "C:\\0808\\T42.xls", T42);
//		43	JHWZ012	银保专管员信息表- ?分支机构在分支机构信息表中不存在或为?754	
String T43 = " select * from Banc_Speci_Info  where not exists( select 1 from Branch_info where BRANCH_INFO.BRANCH_CODE = BANC_SPECI_INFO.Branch) ";
doSQL2EXL(cn, "C:\\0808\\T43.xls", T43);
//		44	JHWZ012	分支机构信息? 经营区域代码不是GB/T2260规定的行政区划代码或为空	2	
		
*/

		
		

		//		1	JHKJ002	缴费信息? 应缴日期、到账日期和保单生效日期都在规定时间区间以前	26	
String T01 = "select * from Prem_info where Payab_Date < '2012-01-01' and  Gained_Date < '2012-01-01' and Eff_Date <  '2012-01-01'";

		//		2	JHWZ005	新保单信息表- ?渠道为代理或经纪业务时，中介机构代码在中介机构信息表中不存在或为?4	
String T02 = "select * from Pol_Main where (Agt_Code is null or Agt_Code = '' or not exists(select 1 from agt_code where Pol_Main.Agt_Code = agt_code.Agt_Code)) and Busi_Src_Type in('220','221','229','230','300')";

		//		3	JHWZ005	新保单信息表- 中介机构代码在中介机构信息表中不存在	7	
String T03 = "select * from Pol_Main where Agt_Code is not null and Agt_Code != '' and not exists ( select Agt_Code from agt_code where POL_MAIN.Agt_Code = AGT_CODE.AGT_CODE)";

		//		4	JHWZ006	缴费信息? 被保险人证件类型在证件类型代码表中不存在	112	
String T04 = "select * from Prem_Info where Ins_Idcard_Type is not null and Ins_Idcard_Type != '' and not exists( select P from Id_Card_Type where PREM_INFO.Ins_Idcard_Type = ID_CARD_TYPE.P) ";

		//		10	JHWZ007	批单信息? 投保日期、保单生效日期?发生日期（批改日期）、批单生效日期为?47	
String T10 = "select * from  Endo_Fee where App_Date is null or Pol_Eff_Date is null or Edr_Eff_Date is null or Proc_Date is null ";

		//		13	JHWZ012	财务凭证信息? 没有期初余额记录	1	
String T13 = "select * from voucher_info where Is_Original = 'Y'";

		//		14	JHWZ012	险种代码? 停止?日期存在空?,或日期关系不正确	5	
String T14 = "select * from voucher_info where Is_Original = 'Y'";

		//		15	JHWZ012	中介机构信息? 获得许可证日期?许可证到期日期?签约日期、协议到期日存在空?,或日期关系不正确	500	
String T15 = "select * from AGT_CODE where  STARTDATE IS NULL OR STARTDATE>=ENDDATE OR ENDDATE IS NULL OR SIGNDATE = '1900-01-01' OR QUITDATE ='1900-01-01' OR SIGNDATE IS NULL OR QUITDATE IS NULL ";

		//		16	JHWZ012	中介机构信息? 中介机构类别在中介机构类别基表中不存在或为空	1	
String T16 = "select * from agt_code  where not exists( select A from agt_org_type_tbl where AGT_CODE.agt_org_type = AGT_ORG_TYPE_TBL.A)";

		//		17	JHWZ012	员工信息? 证件类型在证件类型代码表中不存在或为?1	
String T17 = "select * from Staff_Info  where not exists( select 1 from Id_Card_Type where STAFF_INFO.Id_type = ID_CARD_TYPE.P)";

		//		18	JHWZ012	员工信息? ?分支机构在分支机构信息表中不存在或为?5	
String T18 = "select * from Staff_Info  where not exists( select 1 from Branch_info where STAFF_INFO.Depart_info = BRANCH_INFO.BRANCH_CODE)";

		//		19	JHWZ012	银保专管员信息表- 学历在学历代码基表中不存在或为空	754	
String T19 = "select * from Banc_Speci_Info  where not exists( select 1 from Edu_Degree_Code where BANC_SPECI_INFO.Edu_Degree = EDU_DEGREE_CODE.P)";

		//		20	JHWZ012	银保专管员信息表- ?分支机构在分支机构信息表中不存在或为?754	
String T20 = "select * from Banc_Speci_Info  where not exists( select 1 from Branch_info where BRANCH_INFO.BRANCH_CODE = BANC_SPECI_INFO.Branch)";

		//		21	JHWZ012	分支机构信息? 经营区域代码不是GB/T2260规定的行政区划代码或为空	2	
		
		
		
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\20120731\\副本Branch_Info分支机构信息?0730.xls","Branch_Info", 6, 1, 14);
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\20120731\\副本Staff_info_new_leader.xls","Staff_Info", 13, 1, 6);
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\PremInfo1.xls","Ipmi_Prem_info", 52, 1, 16);
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\Prem_Info2.xls","CGHB_Prem_info", 52, 1, 183);
		
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\Plan_infor险种代码?20120718.xls","Plan_info", 16, 1, 673);
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\agt_code中介机构信息?(3).xls","Agt_Code", 10, 1, 468);
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\CGHB_Agt_Code.xls","Agt_Code", 10, 1, 24);
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\副本Banc_Speci_Info银保专管员信息表.xls","Banc_Speci_Info",15, 1,755);
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\副本信诺资料CMBYC补充.xls","staff_info","2","0",20);
//		importByEXL (cn, "C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\Plan_infor险种代码?20120718.xls", "Plan_info",16, 1, 673) ;
//		importByEXL(cn,"C:\\agt_code.xls","agt_code","1","0",433);
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\ipmi\\claim_main.xls","ipmi_claim_main","1","0",2);
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\ipmi\\claim_report.xls","ipmi_claim_report","1","0",2);
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\ipmi\\pol_main.xls","ipmi_pol_main",40,"1",17);
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\ipmi\\endo_fee.xls","ipmi_endo_fee",42,"1",18);
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\ipmi\\prem_info.xls","ipmi_prem_info",52,"1",45);
//		importByCSV (cn, "C:\\Documents and Settings\\b3wang\\桌面\\circipro\\CIRC.csv", "ipro_pol_main",new String[]{"PolNO","revisit_first_DATE","revisit_type","revisit_FLAG","revisit_DATE"},704725);
//		importByEXL(cn,"c:\\Staff_info.xls","staff_info","1","0",12481);
//		importByEXL(cn,"c:\\Banc_Speci_Info.xls","Banc_Speci_Info","1","0",213);
//		importByEXL(cn,"c:\\Plan_infor_dul.xls","Plan_info","1","15",608);
		
		//CGHB.zip
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\CGHB4_2012_01-03\\Agt_Code.xls","Agt_code","1","0",27);
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\CGHB4_2012_01-03\\Claim_Main.xls","claim_main","1",6794);
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\CGHB4_2012_01-03\\Claim_Report.xls","claim_report","1",19195);
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\CGHB4_2012_01-03\\Prem_Info.xls","prem_info","1",250);
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\CIRC\\CGHB4_2012_01-03\\Pol_Main.xls","pol_main","1",84);
//		
		
		
//		Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
//		Connection cn = DriverManager.getConnection (
//				 "jdbc:microsoft:sqlserver://PHDBSQBFS01\\PHDBSQBFS01:1433;DatabaseName=cmcprod"
//				 , "BFS_Dev_Harold"
//				 , "BFS_Dev_Harold"
//				);
//		String sql = 
//		" select * from LZCARD   "+
//		" where" +
//		" CERTIFYCODE='10103001' and lower(CERTIFYVERSION) in ('v1.0','v2.0','v3.0')   "+
//		" or CERTIFYCODE='10103002' and lower(CERTIFYVERSION) in ('v1.0','v2.0','v3.0','v4.0','v5.0','v6.0','v7.0','v8.0','v9.0')   "+
//		" or CERTIFYCODE='10103003' and lower(CERTIFYVERSION) in ('v1.0','v2.0','v3.0','v4.0')   "+
//		" or CERTIFYCODE='10103004' and lower(CERTIFYVERSION) in ('v1.0','v2.0')   "+
//		" or CERTIFYCODE='10103006' and lower(CERTIFYVERSION) in ('v1.0','v2.0')   " +
//		" or " +
//		" CERTIFYCODE='10205004' and lower(CERTIFYVERSION) in ('v1.0','v2.0','v3.0') "
//		;
//		doSQL2EXL(cn, "C:\\lzcard\\lzcard_supply.xls", sql);
		
		

		
		
		//importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\manual files_updated\\CIRC_ipro_pol_main.xls","ipro_pol_main");
//		doSQL2EXL(cn,"c:\\ipro_pol_main.xls","select * from ipro_pol_main");
		
		
//		Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
//		Connection cn = DriverManager.getConnection (
//				 "jdbc:microsoft:sqlserver://10.131.24.31:1433;DatabaseName=cmcprod"
//				 , "BFS_Dev_Harold"
//				 , "Hh123456"
//				);
//		
		
		
		
		
		
		
		
//		String sql1 = "select * from agt_org_type_tbl";
//		String sql2 = "select * from amt_type_tbl";
//		String sql3 = "select * from Prem_Annuity_Type";
//		String sql4 = "select * from POS_Type_Tbl";
//		String sql5 = "select * from plan_period_tbl";
//		String sql6 = "select * from Plan_Type_tbl";
//		String sql7 = "select * from Busi_Src_Type_Tbl";
//		String sql8 = "select * from Cur_tbl";
//		String sql9 = "select * from Accommodate_cause_tbl";
//		String sql10 = "select * from Plan_Design_tbl";
//		String sql11 = "select * from Id_Card_Type";
//		String sql12 = "select * from Surrender_cause_Info";
//		String sql13 = "select * from Relationship_code";
//		String sql14 = "select * from New_business_Info";
//		String sql15 = "select * from Edu_Degree_Code";
//		String sql16 = "select * from Account_Sort";
//		String sql17 = "select * from CollectPay_Code";
//		String sql18 = "select * from Bill_Type_Code";
//		String sql19 = "select * from Bill_Status_Code";
//		String sql20 = "select * from Coverage_type_code_tbl";
//		doSQL2EXL(cn, "C:\\dz\\agt_org_type_tbl.xls", sql1);
//		importByEXL(cn,"C:\\dz\\agt_org_type_tbl.xls", "agt_org_type_tbl");
//		doSQL2EXL(cn, "C:\\dz\\amt_type_tbl.xls", sql2);
//		importByEXL(cn,"C:\\dz\\amt_type_tbl.xls", "amt_type_tbl");
//		doSQL2EXL(cn, "C:\\dz\\Prem_Annuity_Type.xls", sql3);
//		importByEXL(cn,"C:\\dz\\Prem_Annuity_Type.xls", "Prem_Annuity_Type");
//		doSQL2EXL(cn, "C:\\dz\\POS_Type_Tbl.xls", sql4);
//		importByEXL(cn,"C:\\dz\\POS_Type_Tbl.xls", "POS_Type_Tbl");
//		doSQL2EXL(cn, "C:\\dz\\plan_period_tbl.xls", sql5);
//		importByEXL(cn,"C:\\dz\\plan_period_tbl.xls", "plan_period_tbl");
//		doSQL2EXL(cn, "C:\\dz\\Plan_Type_tbl.xls", sql6);
//		importByEXL(cn,"C:\\dz\\Plan_Type_tbl.xls", "Plan_Type_tbl");
//		doSQL2EXL(cn, "C:\\dz\\Busi_Src_Type_Tbl.xls", sql7);
//		importByEXL(cn,"C:\\dz\\Busi_Src_Type_Tbl.xls", "Busi_Src_Type_Tbl");
//		doSQL2EXL(cn, "C:\\dz\\Cur_tbl.xls", sql8);
//		importByEXL(cn,"C:\\dz\\Cur_tbl.xls", "Cur_tbl");
//		doSQL2EXL(cn, "C:\\dz\\Accommodate_cause_tbl.xls", sql9);
//		importByEXL(cn,"C:\\dz\\Accommodate_cause_tbl.xls", "Accommodate_cause_tbl");
//		doSQL2EXL(cn, "C:\\dz\\Plan_Design_tbl.xls", sql10);
//		importByEXL(cn,"C:\\dz\\Plan_Design_tbl.xls", "Plan_Design_tbl");
//		doSQL2EXL(cn, "C:\\dz\\Id_Card_Type.xls", sql11);
//		importByEXL(cn,"C:\\dz\\Id_Card_Type.xls", "Id_Card_Type");
//		doSQL2EXL(cn, "C:\\dz\\Surrender_cause_Info.xls", sql12);
//		importByEXL(cn,"C:\\dz\\Surrender_cause_Info.xls", "Surrender_cause_Info");
//		doSQL2EXL(cn, "C:\\dz\\Relationship_code.xls", sql13);
//		importByEXL(cn,"C:\\dz\\Relationship_code.xls", "Relationship_code");
//		doSQL2EXL(cn, "C:\\dz\\New_business_Info.xls", sql14);
//		importByEXL(cn,"C:\\dz\\New_business_Info.xls", "New_business_Info");
//		doSQL2EXL(cn, "C:\\dz\\Edu_Degree_Code.xls", sql15);
//		importByEXL(cn,"C:\\dz\\Edu_Degree_Code.xls", "Edu_Degree_Code");
//		doSQL2EXL(cn, "C:\\dz\\Account_Sort.xls", sql16);
//		importByEXL(cn,"C:\\dz\\Account_Sort.xls", "Account_Sort");
//		doSQL2EXL(cn, "C:\\dz\\CollectPay_Code.xls", sql17);
//		importByEXL(cn,"C:\\dz\\CollectPay_Code.xls", "CollectPay_Code");
//		doSQL2EXL(cn, "C:\\dz\\Bill_Type_Code.xls", sql18);
//		importByEXL(cn,"C:\\dz\\Bill_Type_Code.xls", "Bill_Type_Code");
//		doSQL2EXL(cn, "C:\\dz\\Bill_Status_Code.xls", sql19);
//		importByEXL(cn,"C:\\dz\\Bill_Status_Code.xls", "Bill_Status_Code");
//		doSQL2EXL(cn, "C:\\dz\\Coverage_type_code_tbl.xls", sql20);
//		importByEXL(cn,"C:\\dz\\Coverage_type_code_tbl.xls", "Coverage_type_code_tbl");
		
		
		//unformat excel to formated excel begin
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\manual files_updated\\manual files_updated\\agt_code中介机构信息?xls", 
//				"agt_code",new String[]{"Agt_Code","Agt_Name","Agt_Address","agt_org_type","agt_busi_num","StartDate","EndDate","SignDate","QuitDate","IsULQulifd"});
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\manual files_updated\\manual files_updated\\Banc_Speci_Info银保专管员信息表.xls",
//				"Banc_Speci_Info",new String[]{"Banc_Speci_No","Banc_Speci_Name","Sex","Id_type","Idno","quano","JoinDate","LeaveDate","Edu_Degree","Accreditation","Branch","Rank","Source","IsCompliant","IsULQulifd"});
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\manual files_updated\\manual files_updated\\Branch_Info分支机构信息?xls", 
//				"Branch_Info",new String[]{"Branch_Code","Branch_ID","Branch_Name","Branch_Address	","Branch_Level","UP_Branch_Code","Branch_Busi_num","StartDate","EndDate","Leader_Code","Branch_area_code"});
//		importByEXL(cn,"C:\\Documents and Settings\\b3wang\\桌面\\manual files_updated\\manual files_updated\\Staff info员工信息?xls", 
//				"Staff_info",new String[]{"Staff_No","Staff_Name","Sex","Id_type","Idno","quano","JoinDate","LeaveDate","Edu_Degree","Accreditation","Depart_info","Is_Leader","Staff_Rank"});
//		String sql1 = "select Agt_Code,Agt_Name,Agt_Address,agt_org_type,agt_busi_num,StartDate,EndDate,SignDate,QuitDate,IsULQulifd from agt_code ";
//		String sql2 = "select Banc_Speci_No,Banc_Speci_Name,Sex,Id_type,Idno,quano,JoinDate,LeaveDate,Edu_Degree,Accreditation,Branch,Rank,Source,IsCompliant,IsULQulifd from Banc_Speci_Info ";
//		String sql3 = "select Branch_Code,Branch_ID,Branch_Name,Branch_Address	,Branch_Level,UP_Branch_Code,Branch_Busi_num,StartDate,EndDate,Leader_Code,Branch_area_code from  Branch_Info ";
//		String sql4 = "select Staff_No,Staff_Name,Sex,Id_type,Idno,quano,JoinDate,LeaveDate,Edu_Degree,Accreditation,Depart_info,Is_Leader,Staff_Rank from Staff_info ";
//		doSQL2EXL(cn, "C:\\dz\\agt_code.xls", sql1);
//		doSQL2EXL(cn, "C:\\dz\\Banc_Speci_Info.xls", sql2);
//		doSQL2EXL(cn, "C:\\dz\\Branch_Info.xls", sql3);
//		doSQL2EXL(cn, "C:\\dz\\Staff_info.xls", sql4);
		//unformat excel to formated excel end
		
		
		
		
//		program_code	campaign_code	sponsor_campaign_code	package_code	po_prefix	sponsor_code	split_desc	city	campaign_status	campaign_start_date	campaign_end_date	campaign_measure_day

		//importByEXL(cn,"c:\\May.xls", "nb_cicap_campaign_info_temp", new String[]{"program_code","campaign_code","sponsor_campaign_code","package_code","po_prefix","sponsor_code","split_desc","city","campaign_status","campaign_start_date","campaign_end_date","campaign_measure_day"});

 
//		doSQL2EXL(cn,"C:\\cric\\23200083.xls","select * from bill_code_info where bill_code='23200083'");
//		doSQL2EXL(cn,"C:\\cric\\bill_code_info.xls","select * from bill_code_info");
//		doSQL2EXL(cn,"C:\\cric\\23200083_x.xls","select * from bill_info where bill_code like '23200083%'");
//		doSQL2EXL(cn,"C:\\cric\\23200083_certifycode.xls","select * from lmcertifydes where certifycode='23200083'");
//
		
//		doSQL2EXL(cn,"C:\\cric\\01_claim_report_Plan_Code.csv","select * from dbo.claim_report  ct 	where ct.Plan_Code is null");
//		doSQL2CSV(cn,"C:\\cric\\01.csv","select * from Pol_Main  where App_Date < '2012-01-01' and Eff_Date < '2012-01-01'");
//		doSQL2CSV(cn,"C:\\cric\\02.csv","select * from Pol_Main  where app_date > '2012-03-31 23:59:59' and eff_date > '2012-03-31 23:59:59'");
//		doSQL2CSV(cn,"C:\\cric\\03.csv","select * from Prem_info where Payab_Date < '2012-01-01' and  Gained_Date < '2012-01-01' and Eff_Date <  '2012-01-01'");
//		doSQL2CSV(cn,"C:\\cric\\04.csv","select * from Prem_info where Payab_Date > '2012-03-31 23:59:59' and Gained_Date > '2012-03-31 23:59:59' and  Eff_Date > '2012-03-31 23:59:59'");
//		doSQL2CSV(cn,"C:\\cric\\05.csv","select * from claim_report where Docu_Date>'2012-03-31 23:59:59'");
//		doSQL2CSV(cn,"C:\\cric\\06.csv","select * from claim_report  ct where ct.Plan_Code is null or not exists(select 1 from Plan_info where ct.Plan_Code = Plan_info.Plan_Code )");
//		doSQL2CSV(cn,"C:\\cric\\07.csv","select * from claim_report  ct where ct.Plan_Code is null or not exists(select 1 from Plan_info where ct.Plan_Code = Plan_info.Plan_Code) ");
//		doSQL2CSV(cn,"C:\\cric\\08.csv","select * from claim_main where End_Date<'2012-01-01'");
//		doSQL2CSV(cn,"C:\\cric\\09.csv","select * from claim_main where Docu_Date>'2012-03-31 23:59:59'");
//		doSQL2CSV(cn,"C:\\cric\\10.csv","select * from claim_main WHERE claim_main.CollectPay_Code IN('2','5') and (claim_main.Pay_account_code IS NULL OR LEN(claim_main.Pay_account_code) <=5 or claim_main.Pay_account_code not like '%[0-9]%')");
//		doSQL2CSV(cn,"C:\\cric\\11.csv","select * from Pol_Main where not exists(select 1 from Branch_Info where Pol_Main.Branch_Code = Branch_Info.Branch_Code)");
//		doSQL2CSV(cn,"C:\\cric\\12.csv","select * from Pol_Main where Ins_Idcard_Type is not null and Ins_Idcard_Type != '' and not exists( select P from Id_Card_Type where Pol_Main.Ins_Idcard_Type = Id_Card_Type.P)");
//		doSQL2CSV(cn,"C:\\cric\\13.csv","select * from Pol_Main where not exists( select 1 from Relationship_code where Pol_Main.App_Ins_Relation= Relationship_code.p) ");
//		doSQL2CSV(cn,"C:\\cric\\14.csv","select * from Pol_Main where (Agt_Code is null or Agt_Code = '' or not exists(select 1 from agt_code where Pol_Main.Agt_Code = agt_code.Agt_Code)) and Busi_Src_Type in('220','221','229','230','300')");
//		doSQL2CSV(cn,"C:\\cric\\15.csv","select * from Pol_Main where Agt_Code is not null and Agt_Code != '' and not exists ( select Agt_Code from agt_code where POL_MAIN.Agt_Code = AGT_CODE.AGT_CODE)");
//		doSQL2CSV(cn,"C:\\cric\\16.csv","select * from Prem_Info where Ins_Idcard_Type is not null and Ins_Idcard_Type != '' and not exists( select P from Id_Card_Type where PREM_INFO.Ins_Idcard_Type = ID_CARD_TYPE.P) ");
//		doSQL2CSV(cn,"C:\\cric\\17.csv","select * from Prem_Info where Agt_Code is not null and Agt_Code != ''  and not exists( select Agt_Code from agt_code where PREM_INFO.Agt_Code = AGT_CODE.AGT_CODE) ");
//		doSQL2CSV(cn,"C:\\cric\\18.csv","select * from Prem_Info where Salesman_No is not null and Salesman_No != '' and not exists( select Salesman_No from Salesman_info where PREM_INFO.Salesman_No = SALESMAN_INFO.SALESMAN_NO) ");
//		doSQL2CSV(cn,"C:\\cric\\19.csv","select * from Prem_Info where Speciman_No is not null and Speciman_No != '' and not exists( select Banc_Speci_No from Banc_Speci_Info where PREM_INFO.Speciman_No = BANC_SPECI_INFO.BANC_SPECI_NO)");
//		doSQL2CSVByRows(cn,"C:\\cric\\20.csv","select * from Prem_Info where Staff_No is not null and Staff_No != '' and (not exists(select 1 from Staff_Info where Prem_Info.Staff_No = Staff_Info.Staff_No) or (not exists(select 1 from Voucher_Info where Prem_Info.PayabBonus_Vou_Code = Voucher_Info.Voucher_Code) or PayabBonus_Vou_Code is null or PayabBonus_Vou_Code = '') or Staff_Bonus_Rate <= 0)",10000);
//		doSQL2CSV(cn,"C:\\cric\\21.csv","select * from Prem_Info where not exists( select 1 from New_Business_Info where PREM_INFO.New_busi_Code = NEW_BUSINESS_INFO.P)");
//		doSQL2CSVByRows(cn,"C:\\cric\\22.csv","select * from Endo_Fee where not exists( select 1 from Plan_info where ENDO_FEE.Plan_Code = PLAN_INFO.PLAN_CODE) ",10000);
//		doSQL2CSV(cn,"C:\\cric\\23.csv","select * from Endo_Fee where not exists( select 1 from Id_Card_Type where ENDO_FEE.App_Idcard_Type = ID_CARD_TYPE.P) ");
//		doSQL2CSV(cn,"C:\\cric\\24.csv","select * from Endo_Fee where not exists( select 1 from POS_Type_Tbl where ENDO_FEE.POS_Type = POS_TYPE_TBL.PO)");
//		doSQL2CSV(cn,"C:\\cric\\25.csv","select * from Endo_Fee where not exists( select 1 from Busi_Src_Type_Tbl where ENDO_FEE.Busi_Src_Type = BUSI_SRC_TYPE_TBL.B)");
//		doSQL2CSV(cn,"C:\\cric\\26.csv","select * from Endo_Fee where not exists( select 1 from Id_Card_Type  where ENDO_FEE.Ins_Idcard_Type = ID_CARD_TYPE.P)");
//		doSQL2CSV(cn,"C:\\cric\\27.csv","select * from Endo_Fee where Agt_Code is not null and Agt_Code != '' and not exists( select Agt_Code from agt_code where ENDO_FEE.Agt_Code = AGT_CODE.AGT_CODE)");
//		doSQL2CSVByRows(cn,"C:\\cric\\28.csv","select * from Endo_Fee where Salesman_No is not null and Salesman_No != '' and not exists ( select 1 from Salesman_info where ENDO_FEE.Salesman_No = SALESMAN_INFO.SALESMAN_NO)",10000);
//		doSQL2CSV(cn,"C:\\cric\\29.csv","select * from Endo_Fee where Speciman_No is not null and Speciman_No != '' and not exists( select 1 from Banc_Speci_Info where ENDO_FEE.Speciman_No = BANC_SPECI_INFO.BANC_SPECI_NO)");
//		doSQL2CSVByRows(cn,"C:\\cric\\30.csv","select * from Endo_Fee where Staff_No is not null and Staff_No != '' and not exists( select 1 from Staff_Info where ENDO_FEE.Staff_No = STAFF_INFO.STAFF_NO)",10000);
		
		
		
		
		
		cn.close();
//-------------------------------------------------------------------
//Map map = new HashMap();
//map.put("id", 12345);
//User user = new User();
//user.setId(1234);
//User entity = new User();
//entity.setUsername("sdsd");
//entity.setUserpwd("ssss");
//System.out.println(((User)Map2Object( map, User.class)).getId());
//System.out.println(Object2Map(user));
//System.out.println(((User)initObjbyMap(map,user)).getId());
//System.out.println(getDeleteByObject(user));
//System.out.println(getUpdateByObject(user,entity));
	}
	
	public static List<Map> getData(Connection cn, String sql) throws SQLException{
		if(cn == null || sql == null || sql.trim().length()==0 || cn.isClosed())return null;
	
		List result = new ArrayList();
		Statement statement = cn.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		while(rs.next()){
			Map line = new HashMap();
			for(int i=0;i<rsmd.getColumnCount();i++){
				line.put(rsmd.getColumnName(i), rs.getObject(rsmd.getColumnName(i)));
			}
			result.add(line);
		}
		rs.close();
		return result;
	}
	
	//get the table which have data in a database
	public static List getTablesWithData(Connection cn) throws SQLException{
		List alltables = TableStructure.getTables(cn, null, null, null, new String[]{"TABLE"});
		for(int i=1; i<alltables.size(); i++){
			String tablename = (String) alltables.get(i);
			Statement st = cn.createStatement();
			ResultSet rs;
			try {
				rs = st.executeQuery("Select count(*) from "+tablename);
				rs.next();
				if(rs.getInt(1)==0){
					alltables.remove(i);
				}
				rs.close();
				st.close();
			} catch (SQLException e) {
				System.out.println(tablename);
				alltables.remove(i);
				e.printStackTrace();
			}
		}
		return alltables;
	}
	
	//get the table which have data in a database
	public static List getTablesWithOutData(Connection cn) throws SQLException{
		List result = new ArrayList();
		List alltables = TableStructure.getTables(cn, null, null, null, new String[]{"TABLE"});
		for(int i=1; i<alltables.size(); i++){
			String tablename = (String) alltables.get(i);
			Statement st = cn.createStatement();
			ResultSet rs;
			try {
				rs = st.executeQuery("Select count(*) from "+tablename);
				rs.next();
				if(rs.getInt(1)==0){
					result.add(tablename);
				}
				rs.close();
				st.close();
			} catch (SQLException e) {
				System.out.println(tablename);
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static void importByCSV (Connection cn, String path, String table,String[] cols,long from )throws SQLException, BiffException, IOException, RowsExceededException, WriteException{
		File file = new File(path);
		if(!file.exists()){  
			throw new IOException("can't find file");
		}
		BufferedReader br = new BufferedReader(new FileReader(path));
		String sql = " insert into "+table+" ";
		int column_index = 0;
		String temp = "";
		StringBuffer result=new StringBuffer();
		StringBuffer column_list = new StringBuffer();
		System.out.println(cols);
		for(int i=0;i<cols.length;i++){
			column_list.append( cols[i] + ",");
		}

		sql += " ( "+column_list.toString().substring(0, column_list.length()-1)+" ) values ";
		System.out.println(sql);


		int row_num = 0;
		String line = "";
		StringBuffer value_list ;
		while((line=br.readLine())!=null){
			row_num++;
			if(row_num <from){
				continue;
			}
			temp = "";
			column_index = 0;
			String[] values = line.split(",");
			String[] values_update = line.split(",");
			if(values.length<5){
				values = new String[5];
			}
			for(int i=0;i<5;i++){
				if(i<values_update.length){
					values[i] = values_update[i];
				}else{
					values[i] = "";
				}				
			}
			value_list=new StringBuffer("");
			while(column_index<cols.length){
					value_list.append( "N'" + values[column_index].replaceAll("\"", "") + "',");
					column_index++;
			}
			
			
			temp = sql + " ( "+value_list.toString().substring(0, value_list.length()-1) + " ) \n";
			result.append(temp);
			System.out.println(row_num+" ==> "+temp);
			cn.createStatement().execute(temp);
		}
		FileUtil.writeFile(result.toString(),"c:\\"+table+".sql");
		br.close();
	}
	public static void importByCSV (Connection cn, String path, String table,String[] cols)throws SQLException, BiffException, IOException, RowsExceededException, WriteException{
		File file = new File(path);
		if(!file.exists()){  
			throw new IOException("can't find file");
		}
		BufferedReader br = new BufferedReader(new FileReader(path));
		String sql = " insert into "+table+" ";
		int column_index = 0;
		String temp = "";
		StringBuffer result=new StringBuffer();
		StringBuffer column_list = new StringBuffer();
		System.out.println(cols);
		for(int i=0;i<cols.length;i++){
			column_list.append( cols[i] + ",");
		}

		sql += " ( "+column_list.toString().substring(0, column_list.length()-1)+" ) values ";
		System.out.println(sql);


		int row_num = 0;
		String line = "";
		StringBuffer value_list ;
		while((line=br.readLine())!=null){
			row_num++;
			temp = "";
			column_index = 0;
			String[] values = line.split(",");
			String[] values_update = line.split(",");
			if(values.length<5){
				values = new String[5];
			}
			for(int i=0;i<5;i++){
				if(i<values_update.length){
					values[i] = values_update[i];
				}else{
					values[i] = "";
				}				
			}
			value_list=new StringBuffer("");
			while(column_index<cols.length){
					value_list.append( "N'" + values[column_index] + "',");
					column_index++;
			}
			
			
			temp = sql + " ( "+value_list.toString().substring(0, value_list.length()-1) + " ) \n";
			result.append(temp);
			System.out.println(row_num+" ==> "+temp);
			cn.createStatement().execute(temp);
		}
		FileUtil.writeFile(result.toString(),"c:\\"+table+".sql");
		br.close();
	}
	
	
	//notDuplicatedCol use for a whole insert bacth
	public static void importByEXL (Connection cn, String path, String table, String  from, String notDuplicatedCol, int rownum) throws IOException, BiffException, SQLException{
		File file = new File(path);
		if(!file.exists()){  
			throw new IOException("can't find file");
		}
		Workbook wb = Workbook.getWorkbook(file); 
		Sheet sheet = wb.getSheet(0);
		String sql = " insert into "+table+" ";
		int column_num = 0;
		String temp = "";
		StringBuffer result=new StringBuffer();
		StringBuffer column_list = new StringBuffer();
		String[] cols = new String[sheet.getColumns()];
		for(int i=0;i<cols.length;i++){
			cols[i] = sheet.getCell(i, 0).getContents();
		}
		System.out.println(cols);
		for(int i=0;i<cols.length;i++){
			column_list.append( cols[i] + ",");
		}
		sql += " ( "+column_list.toString().substring(0, column_list.length()-1)+" ) values ";
		List notduplicatedlist = new ArrayList();
		int row_num = Integer.parseInt(from);
		boolean isJump = false;
		StringBuffer value_list ;
		while(row_num<rownum){
			isJump = false;
			//filter for duplicated line by notduplicatedCol
			
			if(notDuplicatedCol!=null){
				String notDuplicatedCol_value = sheet.getCell(Integer.parseInt(notDuplicatedCol),row_num).getContents();
				if(!notduplicatedlist.contains(notDuplicatedCol_value)){
					notduplicatedlist.add(notDuplicatedCol_value);
				}else{
					isJump = true;
				}
			}
			if(!isJump){
				//build insert sql
				temp = "";
				column_num = 0;
				value_list=new StringBuffer("");
				while(column_num<cols.length){
					temp = sheet.getCell(column_num,row_num).getContents();
					value_list.append( "N'" + temp.trim().replaceAll("\\'", " ") + "',");
					column_num++;
				}
				temp = sql + " ( "+value_list.toString().substring(0, value_list.length()-1) + " ) \n";
				result.append(temp);
				System.out.println(row_num+" ==> "+temp);
//				cn.createStatement().execute(temp);
			}
			row_num++;
		}
		FileUtil.writeFile(result.toString(),"c:\\"+table+".sql");
		wb.close();
	}
	
	public static void importByEXL2 (Connection cn, String path, String table, int col_count,  int rownum, int from) throws IOException, BiffException, SQLException{
		File file = new File(path);
		if(!file.exists()){  
			throw new IOException("can't find file");
		}
		Workbook wb = Workbook.getWorkbook(file); 
		Sheet sheet = wb.getSheet(0);
		String sql = " insert into "+table+" ";
		int column_num = 0;
		String temp = "";
		StringBuffer result=new StringBuffer();
		StringBuffer column_list = new StringBuffer();
		String[] cols = new String[col_count];
		for(int i=0;i<cols.length;i++){
			cols[i] = sheet.getCell(i, 0).getContents();
		}
		System.out.println(cols);
		for(int i=0;i<cols.length;i++){
			column_list.append( cols[i] + ",");
		}
		sql += " ( "+column_list.toString().substring(0, column_list.length()-1)+" ) values ";

		int row_num = from;

		StringBuffer value_list ;
		while(row_num<rownum){
			
				//build insert sql
				temp = "";
				column_num = 0;
				value_list=new StringBuffer("");
				while(column_num<cols.length){
					temp = sheet.getCell(column_num,row_num).getContents();
					value_list.append( "N'" + temp.trim().replaceAll("\\'", " ") + "',");
					column_num++;
				}
				temp = sql + " ( "+value_list.toString().substring(0, value_list.length()-1) + " ) \n";
				result.append(temp);
				System.out.println(row_num+" ==> "+temp);
//				cn.createStatement().execute(temp);
			
			row_num++;
		}
		FileUtil.writeFile(result.toString(),"c:\\"+table+".sql");
		wb.close();
	}
	
	public static void importByEXL (Connection cn, String path, String table,int col_count, int from, String notDuplicatedCol, int rownum) throws IOException, BiffException, SQLException{
		File file = new File(path);
		if(!file.exists()){  
			throw new IOException("can't find file");
		}
		Workbook wb = Workbook.getWorkbook(file); 
		Sheet sheet = wb.getSheet(0);
		String sql = " insert into "+table+" ";
		int column_num = 0;
		String temp = "";
		StringBuffer result=new StringBuffer();
		StringBuffer column_list = new StringBuffer();
		String[] cols = new String[col_count];
		for(int i=0;i<cols.length;i++){
			cols[i] = sheet.getCell(i, 0).getContents();
		}
		System.out.println(cols);
		for(int i=0;i<cols.length;i++){
			column_list.append( cols[i] + ",");
		}
		sql += " ( "+column_list.toString().substring(0, column_list.length()-1)+" ) values ";
		List notduplicatedlist = new ArrayList();
		int row_num = from;
		boolean isJump = false;
		StringBuffer value_list ;
		while(row_num<rownum){
			isJump = false;
			//filter for duplicated line by notduplicatedCol
			
			if(notDuplicatedCol!=null){
				String notDuplicatedCol_value = sheet.getCell(Integer.parseInt(notDuplicatedCol),row_num).getContents();
				if(!notduplicatedlist.contains(notDuplicatedCol_value)){
					notduplicatedlist.add(notDuplicatedCol_value);
				}else{
					isJump = true;
				}
			}
			if(!isJump){
				//build insert sql
				temp = "";
				column_num = 0;
				value_list=new StringBuffer("");
				while(column_num<cols.length){
					temp = sheet.getCell(column_num,row_num).getContents();
					value_list.append( "N'" + temp.trim().replaceAll("\\'", " ") + "',");
					column_num++;
				}
				temp = sql + " ( "+value_list.toString().substring(0, value_list.length()-1) + " ) \n";
				result.append(temp);
				System.out.println(row_num+" ==> "+temp);
//				cn.createStatement().execute(temp);
			}
			row_num++;
		}
		FileUtil.writeFile(result.toString(),"c:\\"+table+".sql");
		wb.close();
	}
	
	
	
	
	public static void importByEXL (Connection cn, String path, String table, String from, int rownum) throws IOException, BiffException, SQLException{
		importByEXL ( cn,  path,  table,  from, null,rownum);
	}
	public static void importByEXL (Connection cn, String path, String table,int rownum)throws SQLException, BiffException, IOException, RowsExceededException, WriteException{
		importByEXL ( cn,  path,  table,  "1", null,rownum);
	}
	//unover get insert into table by excel content(first line is column name, from second line is the data)
	public static void importByEXL (Connection cn, String path, String table, String[] cols)throws SQLException, BiffException, IOException, RowsExceededException, WriteException{
		File file = new File(path);
		if(!file.exists()){  
			throw new IOException("can't find file");
		}
		
		
		Workbook wb = Workbook.getWorkbook(file); 

		Sheet sheet = wb.getSheet(0);
		String sql = " insert into "+table+" ";
		int column_num = 0;
		String temp = "";
		StringBuffer result=new StringBuffer();
		StringBuffer column_list = new StringBuffer();
		for(int i=0;i<cols.length;i++){
			column_list.append( cols[i] + ",");
		}

		sql += " ( "+column_list.toString().substring(0, column_list.length()-1)+" ) values ";
		System.out.println(sql);


		int row_num = 1;
		
		StringBuffer value_list ;
		while(row_num<sheet.getRows()){
			temp = "";
			column_num = 0;
			value_list=new StringBuffer("");;
			while(column_num<cols.length){
				
//				String column_type = (String)ColumnType.get(column_num);
//				if("integer".equals(column_type)
//						||"short".equals(column_type)
//						||"double".equals(column_type)
//						||"float".equals(column_type)
//						||"boolean".equals(column_type)
//						||"binary".equals(column_type)
//						||"byte".equals(column_type)
//						){
//					temp = sheet.getCell(column_num,row_num).getContents();
//					value_list.append( temp + ",");
//					column_num++;
//				}else{
					temp = sheet.getCell(column_num,row_num).getContents();
					value_list.append( "N'" + temp + "',");
					column_num++;
//				}
				
			}
			row_num++;
			
			temp = sql + " ( "+value_list.toString().substring(0, value_list.length()-1) + " ) \n";
			result.append(temp);
			System.out.println(row_num+" ==> "+temp);
			cn.createStatement().execute(temp);
		}
		FileUtil.writeFile(result.toString(),"c:\\"+table+".sql");
		wb.close();
	}
	
	
	
	
	public static void importByEXL (Connection cn, String path, String table, String[] cols,int from)throws SQLException, BiffException, IOException, RowsExceededException, WriteException{
		File file = new File(path);
		if(!file.exists()){  
			throw new IOException("can't find file");
		}
		
		
		Workbook wb = Workbook.getWorkbook(file); 

		Sheet sheet = wb.getSheet(0);
		String sql = " insert into "+table+" ";
		int column_num = 0;
		String temp = "";
		StringBuffer result=new StringBuffer();
		StringBuffer column_list = new StringBuffer();
		for(int i=0;i<cols.length;i++){
			column_list.append( cols[i] + ",");
		}

		sql += " ( "+column_list.toString().substring(0, column_list.length()-1)+" ) values ";
		System.out.println(sql);

		int row_num = 1;
		if(from<1){
			row_num = 1;
		}else{
			row_num = from;
		}
		
		StringBuffer value_list ;
		while(row_num<sheet.getRows()){
			temp = "";
			column_num = 0;
			value_list=new StringBuffer("");;
			while(column_num<cols.length){
				
//				String column_type = (String)ColumnType.get(column_num);
//				if("integer".equals(column_type)
//						||"short".equals(column_type)
//						||"double".equals(column_type)
//						||"float".equals(column_type)
//						||"boolean".equals(column_type)
//						||"binary".equals(column_type)
//						||"byte".equals(column_type)
//						){
//					temp = sheet.getCell(column_num,row_num).getContents();
//					value_list.append( temp + ",");
//					column_num++;
//				}else{
					temp = sheet.getCell(column_num,row_num).getContents();
					value_list.append( "N'" + temp + "',");
					column_num++;
//				}
				
			}
			row_num++;
			
			temp = sql + " ( "+value_list.toString().substring(0, value_list.length()-1) + " ) \n";
			result.append(temp);
			System.out.println(row_num+" ==> "+temp);
			cn.createStatement().execute(temp);
		}
		FileUtil.writeFile(result.toString(),"c:\\"+table+".sql");
		wb.close();
	}
	
	public static void doSQL2CSV(Connection cn, String path, String sql) throws SQLException, IOException {
        Long starttimepoint = System.currentTimeMillis();
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		int cols_count = rs.getMetaData().getColumnCount();
		List cols = new ArrayList();
		for(int i=1; i<cols_count+1; i++){
			cols.add(rs.getMetaData().getColumnName(i));
		}
		StringBuffer sb = new StringBuffer();
		String header = "";
		for(int j=0; j<cols.size(); j++){
			header+=cols.get(j).toString()+",";
		}
		sb.append(header);
		sb.append("\r\n");
		while(rs.next()){
			for(int j=0; j<cols.size(); j++){
				sb.append(rs.getString(cols.get(j).toString())+",");
			}
			sb.append("\r\n");
		}
		FileUtil.writeFile(sb.toString(), path,"UTF-8");
	
		rs.close();
		st.close();
		Long endtimepoint = System.currentTimeMillis();
		System.out.println(path+":"+((endtimepoint-starttimepoint)/1000)+"s");
	}
	public static void doSQL2CSVByRows(Connection cn, String path, String sql,long rownum) throws SQLException, IOException {
        Long starttimepoint = System.currentTimeMillis();
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		int cols_count = rs.getMetaData().getColumnCount();
		List cols = new ArrayList();
		for(int i=1; i<cols_count+1; i++){
			cols.add(rs.getMetaData().getColumnName(i));
		}
		StringBuffer sb = new StringBuffer();
		String header = "";
		for(int j=0; j<cols.size(); j++){
			header+=cols.get(j).toString()+",";
		}
		sb.append(header);
		sb.append("\r\n");
		long rows = 0;
		while(rs.next()){
			rows++;
			for(int j=0; j<cols.size(); j++){
				sb.append(rs.getString(cols.get(j).toString())+",");
			}
			if(rows%rownum==0){
				FileUtil.writeFile(sb.toString(), path.substring(0,path.lastIndexOf("."))+"_"+rows+".csv","UTF-8");
				sb = new StringBuffer();
				sb.append(header);
				sb.append("\r\n");
			}
			sb.append("\r\n");
		}
		FileUtil.writeFile(sb.toString(), path.substring(0,path.lastIndexOf("."))+"_"+rows+".csv","UTF-8");
	
		rs.close();
		st.close();
		Long endtimepoint = System.currentTimeMillis();
		System.out.println(path+":"+((endtimepoint-starttimepoint)/1000)+"s");
	}
	
	public static void doSQL2EXL(Connection cn, String path, String sql) throws SQLException, BiffException, IOException, RowsExceededException, WriteException{
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		int cols_count = rs.getMetaData().getColumnCount();
		List cols = new ArrayList();
		for(int i=1; i<cols_count+1; i++){
			cols.add(rs.getMetaData().getColumnName(i));
		}
		
		File file = new File(path);
		if(!file.exists()){  
			file.createNewFile();
		}

		WritableWorkbook book = Workbook.createWorkbook(file); 
		WritableSheet sheet = book.createSheet(sql, 0);
		int colindex = 0;
		for(int j=0; j<cols.size(); j++){
			sheet.addCell(new  jxl.write.Label(colindex++, 0, cols.get(j).toString()));
		}
		
		int i = 1;
		while(rs.next()){

			colindex = 0;
			System.out.println(i);
			for(int j=0; j<cols.size(); j++){
				try{
				sheet.addCell(new  jxl.write.Label(colindex++, i, rs.getString(cols.get(j).toString())));
				}catch(java.sql.SQLException e){
					continue;
				}
			}
			i++;
			
		}
		book.write();
		book.close();

	}

	public static String getSelectByObject(Object entity) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Class obj_class = entity.getClass();
		Method[] methods = obj_class.getMethods();
		String sql = "select ";
		String where = " where ";
		for(int i=0;i<methods.length;i++){
			if(methods[i].getName().indexOf("get")!=-1){
				if(!"Class".equals(methods[i].getName().replace("get", ""))){
					sql+=SCUtil.lowerFirstLetter(methods[i].getName().replace("get", "")) + ",";
					if (methods[i].invoke(entity)!=null){
						if(methods[i].invoke(entity) instanceof String){
							where+=SCUtil.lowerFirstLetter(methods[i].getName().replace("get", "")) + "='" + methods[i].invoke(entity).toString() + "' and ";
						}else{
							where+=SCUtil.lowerFirstLetter(methods[i].getName().replace("get", "")) + "=" + methods[i].invoke(entity).toString() + " and ";
						}
					}
				}
			}
		}
		return sql.substring(0,sql.length()-1) 
		+ " from " 
		+ (obj_class.getName().indexOf(".")!=-1 ? 
				  obj_class.getName().substring(obj_class.getName().lastIndexOf(".")+1,obj_class.getName().length())
				: obj_class.getName() )
		+ " "
		+ (where.equals(" where ") ?
				""
				: where.substring(0,where.length()-4));
	}
	
	public static String getDeleteByObject(Object entity) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		String select = getSelectByObject(entity);
		Class obj_class = entity.getClass();
		return 
			"delete from "+ (obj_class.getName().indexOf(".")!=-1 ? 
					obj_class.getName().substring(obj_class.getName().lastIndexOf(".")+1,obj_class.getName().length())
					: obj_class.getName() )
			+ " " 
			+ (select.indexOf("where")==-1 ?//where statement
					""
					: select.substring(select.indexOf("where"),select.length()));
	}
	
	public static String getUpdateByObject(Object condition, Object entity) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		String select = getSelectByObject(condition);
		Class obj_class = entity.getClass();
		String update = "update "+(obj_class.getName().indexOf(".")!=-1 ? 
				obj_class.getName().substring(obj_class.getName().lastIndexOf(".")+1,obj_class.getName().length())
				: obj_class.getName() );
		Method[] methods = obj_class.getMethods();
		String set = " set ";
		for(int i=0;i<methods.length;i++){
			if(methods[i].getName().indexOf("get")!=-1){
				if(!"Class".equals(methods[i].getName().replace("get", ""))){
					if (methods[i].invoke(entity)!=null){
						if(methods[i].invoke(entity) instanceof String){
							set+=SCUtil.lowerFirstLetter(methods[i].getName().replace("get", "")) + "='" + methods[i].invoke(entity).toString() + "', ";
						}else{
							set+=SCUtil.lowerFirstLetter(methods[i].getName().replace("get", "")) + "=" + methods[i].invoke(entity).toString() + ", ";
						}
					}
				}
			}
		}
		set = set.substring(0,set.length()-2);
		return 
			update 
			+ set 
			+  " "	
			+ (select.indexOf("where")==-1 ?//where statement
				""
				: select.substring(select.indexOf("where"),select.length()));
	}
	
	public static void importByEXL (Connection cn, String path, String table,int col_count, int from, int rownum) throws IOException, BiffException, SQLException{
		File file = new File(path);
		if(!file.exists()){  
			throw new IOException("can't find file");
		}
		Workbook wb = Workbook.getWorkbook(file); 
		Sheet sheet = wb.getSheet(0);
		String sql = " insert into "+table+" ";
		int column_num = 0;
		String temp = "";
		StringBuffer result=new StringBuffer();
		StringBuffer column_list = new StringBuffer();
		String[] cols = new String[col_count];
		for(int i=0;i<cols.length;i++){
			cols[i] = sheet.getCell(i, 0).getContents();
		}
		System.out.println(cols);
		for(int i=0;i<cols.length;i++){
			column_list.append( cols[i] + ",");
		}
		sql += " ( "+column_list.toString().substring(0, column_list.length()-1)+" ) values ";
		List notduplicatedlist = new ArrayList();
		int row_num = from;
		boolean isJump = false;
		StringBuffer value_list ;
		while(row_num<rownum){
			isJump = false;
			//filter for duplicated line by notduplicatedCol
			
			
				//build insert sql
				temp = "";
				column_num = 0;
				value_list=new StringBuffer("");
				while(column_num<cols.length){
					temp = sheet.getCell(column_num,row_num).getContents();
					value_list.append( "N'" + temp.trim().replaceAll("\\'", " ") + "',");
					column_num++;
				}
				temp = sql + " ( "+value_list.toString().substring(0, value_list.length()-1) + " ) \n";
				result.append(temp);
				System.out.println(row_num+" ==> "+temp);
				cn.createStatement().execute(temp);
			
			row_num++;
		}
		FileUtil.writeFile(result.toString(),"c:\\"+table+".sql");
		wb.close();
	}
	
	public static String getInsertByObject(Object entity) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Class obj_class = entity.getClass();
		Method[] methods = obj_class.getMethods();
		String insert = "insert into "+(obj_class.getName().indexOf(".")!=-1 ? 
				  obj_class.getName().substring(obj_class.getName().lastIndexOf(".")+1,obj_class.getName().length())
					: obj_class.getName() )+"(";
		String values = " values(";
		for(int i=0;i<methods.length;i++){
			if(methods[i].getName().indexOf("get")!=-1){
				if(!"Class".equals(methods[i].getName().replace("get", ""))){
					if (methods[i].invoke(entity)!=null){
						insert+=SCUtil.lowerFirstLetter(methods[i].getName().replace("get", ""))+",";
						if(methods[i].invoke(entity) instanceof String){
							values+="'"+methods[i].invoke(entity).toString()+"',";
						}else{
							values+=""+methods[i].invoke(entity).toString()+",";
						}
					}
				}
			}
		}
		return insert.substring(0,insert.length()-1)+")" + values.substring(0,values.length()-1)+")";
	}
	
	public static String getInsertFromMap(Map map, String table){
		String insert = "insert into "+table+"(";
		Iterator iterator = map.keySet().iterator();
		String value="";
		while(iterator.hasNext()){
			insert+=iterator.next()+",";
			value+="N'"+map.get(iterator.next())+"',";//fit for sqlserver
		}
		insert = insert.substring(0,insert.length()-1)+")values("+value.substring(0,value.length()-1)+")";
		return insert;
	}

	public static void tablesSync(Connection cn1,Connection cn2,String table1,String table2) throws SQLException{
		List result = getData(cn1,table1);
		Statement statement = cn2.createStatement();
		cn2.setAutoCommit(false);
		for(int i=0;i<result.size();i++){
			statement.execute(DBUtil.getInsertFromMap((Map)result.get(i),table2));
		}
		cn2.commit();
		cn2.setAutoCommit(true);
	}
	
	public static String table2Select(
			Connection cn, 
			String table, 
			String selectName, 
			String valueCol, 
			String textCol) throws SQLException{
		StringBuffer result = new StringBuffer();
		String sql = "select "+valueCol+", "+textCol+" from "+table;
		Statement statement = cn.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		result.append("<select name=\""+selectName+"\">");
		while(rs.next()){
			result.append("<option value=\""+rs.getString(valueCol)+"\">"+rs.getString(textCol)+"</option>");
		}
		result.append("</select>");
		return result.toString();
	}
	
	
		public static List sql2List(Connection cn, String sql, Class cls) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SQLException, InstantiationException{
			List result = new ArrayList();
			
			Statement statement = cn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()){
				Object item = cls.newInstance();
				Method[] methods = cls.getMethods();
				for(int i=0;i<methods.length;i++){
					if(methods[i].getName().indexOf("set")!=-1){
						methods[i].invoke(item, rs.getString(methods[i].getName().replace("set", "")));
					}
				}
				result.add(item);
			}
			return result;
		}
		
		public static List query(Connection cn, String sql) throws SQLException{
			Statement statement = cn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			List result = new ArrayList();
			while(rs.next()){
				int column = 0;
				Map line = new HashMap();
				while(column<rs.getMetaData().getColumnCount()){
					String col_name = rs.getMetaData().getColumnClassName(column);
					line.put(col_name, rs.getObject(column));
					column++;
				}
				result.add(line);
			}
			statement.close();
			rs.close();
			return result;
		}
//		public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
//			// TODO Auto-generated method stub
//			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
//			Connection cn = DriverManager.getConnection (
//					 "jdbc:microsoft:sqlserver://CNITES02:1433;DatabaseName=esales"
//					 , "test_user"
//					 , "test_user"
//					);	
//			DBBackupUtil.DB2File(cn, "region", "c:\\region_export111.sql");
//			
//			cn.close();
//		}
		
		//generate insert sql for a table
		public static File Table2File(Connection cn, String table, String path) throws IOException, SQLException{
			File result = new File(path);
			result.createNewFile();
			List cols = TableStructure.getColumnName(cn, table);
			String sqlinsert = " insert into " + table ;
			String sql_cols_list = "";
			for(int i = 0;i<cols.size();i++){
				sql_cols_list += cols.get(i) + ",";
			}
			System.out.println(cols);
			sql_cols_list = sql_cols_list.substring(0,sql_cols_list.length()-1);
			sqlinsert +=  " (" + sql_cols_list + " ) values ";

			Statement st;
			ResultSet rs1;

			st = cn.createStatement();
			rs1 = st.executeQuery(" select "+sql_cols_list+" from "+table);
			String value_list_temp = "";
			List types = TableStructure.getColumnType(cn, table);
			StringBuffer sql =new StringBuffer();
			while(rs1.next()){
				value_list_temp = "";
				for(int i = 0;i<cols.size();i++){
					if("double".equals(types.get(i))
							||"integer".equals(types.get(i))
							||"short".equals(types.get(i))
							||"float".equals(types.get(i))
							||"double".equals(types.get(i))
							||"byte".equals(types.get(i))
							){
						value_list_temp += rs1.getString(cols.get(i).toString())+",";	
					}else{
						value_list_temp += "N'" + rs1.getString(cols.get(i).toString()) + "',";	
					}
				}
				value_list_temp = value_list_temp.substring(0,value_list_temp.length()-1);
				sql.append( sqlinsert+"( " + value_list_temp + " )\r\n");
				System.out.println(sqlinsert+"( " + value_list_temp + " )\r\n");
			}

			FileUtil.writeFile(sql.toString(),path);
			return result;
		}
}
