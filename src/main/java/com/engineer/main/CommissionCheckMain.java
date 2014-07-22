package com.engineer.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.engineer.util.FileUtil;

public class CommissionCheckMain {
	private static String StartDateTime = "2012-01-01";
	private static String EndDateTime = "2012-03-01";
	
	//1	JHKJ002	缴费信息表- 应缴日期、到账日期和保单生效日期都在规定时间区间以前
	private static String SQL_JHKJ002 = 
	"select                                                       "
		+"PolNo as 保单号码 ,                                               "
		+"CertNo as 分单号码 ,                                              "
		+"GP_Type as 个团标志 ,                                             "
		+"Branch_Code as 分支机构代码 ,                                       "
		+"Prpl_No as 投保单号码 ,                                            "
		+"App_Name as 投保人名称 ,                                           "
		+"App_Idcard_Type as 投保人证件类型 ,                                  "
		+"App_Idcard_No as 投保人证件号码 ,                                    "
		+"InsName as 被保人名称 ,                                            "
		+"Ins_Idcard_Type as 被保险人证件类型 ,                                 "
		+"Ins_Idcard_No as 被保险人证件号码 ,                                   "
		+"App_Date as 投保日期 ,                                            "
		+"Eff_Date as 生效日期 ,                                            "
		+"BRNo as 险种序号 ,                                                "
		+"Plan_Code as 险种代码 ,                                           "
		+"Sum_Ins as 保额 ,                                               "
		+"Sum_Ins_Death as 死亡保额 ,                                       "
		+"Amt_Type as 收付类型 ,                                            "
		+"Period as [保险期限（月）] ,                                         "
		+"Prem_Term as [缴费期限（月）] ,                                      "
		+"CurNo as 保费币种 ,                                               "
		+"Payab_Date as [应缴日期（应收日期）] ,                                  "
		+"Payab_Prem_cnvt as 应收保费折合人民币 ,                                "
		+"PayabCol_Vou_Cod as 应收保费凭证号 ,                                 "
		+"Gained_Date as [到账日期（实收日期）] ,                                 "
		+"Tot_Prem_cnvt as 实收保费合计折合人民币 ,                                "
		+"PreCol_Vou_Cod as 预收保费凭证号 ,                                   "
		+"ActCol_Vou_Cod as 实收保费凭证号 ,                                   "
		+"Prem_Invo_Code as 保费发票号 ,                                     "
		+"CollectPay_Code as 收付款方式 ,                                    "
		+"Bank_account_code as 对方银行账号 ,                                 "
		+"Check_code as 对方结算号 ,                                         "
		+"Prem_Psns as 缴费人数 ,                                           "
		+"New_busi_Code as 新单期缴保费收入年期类型 ,                               "
		+"Prem_Type as 缴费类型 ,                                           "
		+"Busi_Src_Type as 销售渠道 ,                                       "
		+"Agt_Code as 中介机构代码 ,                                          "
		+"Proc_Rate as 手续费比例 ,                                          "
		+"PayabProc_Vou_Cod as 应付手续费凭证号 ,                               "
		+"ActProc_Vou_Code as 实付手续费凭证号 ,                                "
		+"Salesman_No as 营销员代码 ,                                        "
		+"Com_Rate as 直接佣金比例 ,                                          "
		+"PayabCom_Vou_Cod as 应付佣金凭证号 ,                                 "
		+"ActCom_Vou_Code as 实付佣金凭证号 ,                                  "
		+"Speciman_No as 银保专管员代码 ,                                      "
		+"Spc_Bonus_Rat as 专管员直接绩效比例 ,                                  "
		+"Spc_PayabBonus_Vou_Code as 应付专管员绩效凭证号 ,                       "
		+"Special_ActBonus_Vou_Code as [实付专管员绩效凭证号或流水号/中间码],            "
		+"Staff_No as 员工代码 ,                                            "
		+"Staff_Bonus_Rate as 员工直接绩效比例 ,                                "
		+"PayabBonus_Vou_Code as 应付员工绩效凭证号 ,                            "
		+"ActBonus_Vou_Code as 实付员工绩效凭证号                                "
		+" from %1$s "
		+" where convert(datetime, Payab_Date, 101) < '" + StartDateTime +"' "
				+"and convert(datetime, Gained_Date, 101) < '" + StartDateTime +"' "
				+"and convert(datetime, Eff_Date, 101) <  '" + StartDateTime +"' ";
	//2	JHWZ005	新保单信息表- 销售渠道为代理或经纪业务时，中介机构代码在中介机构信息表中不存在或为空
	private static String SQL_JHWZ005 = 
		"select "
		+"PolNo as 保单号码 ,"
		+"CertNo as 分单号码 ,"
		+"Pol_Prt_Code as 保单印刷流水号 ,"
		+"Prpl_No as 投保单号码 ,"
		+"Prpl_Prt_Code as 投保单印刷号 ,"
		+"Post_Code as 投保人住所邮编 ,"
		+"Branch_Code as 分支机构代码 ,"
		+"GP_Type as 个团标志 ,"
		+"App_Name as 投保人名称 ,"
		+"App_Idcard_Type as 投保人证件类型 ,"
		+"App_Idcard_No as 投保人证件号码 ,"
		+"App_Income as [投保人收入（万）] ,"
		+"InsName as 被保险人名称 ,"
		+"Ins_Idcard_Type as 被保险人证件类型 ,"
		+"Ins_Idcard_No as 被保险人证件号码 ,"
		+"App_Ins_Relation as 投保人与被保险人关系 ,"
		+"BeneName as 受益人姓名 ,"
		+"Sum_Ins as 总保额 ,"
		+"Sum_Ins_Death as 死亡保额 ,"
		+"App_Date as 投保日期 ,"
		+"Input_Date as 录入日期 ,"
		+"Underwr_date as 核保通过日期 ,"
		+"Eff_Date as [生效日期（保险责任起期）] ,"
		+"Recp_date as 签收保单回执日期 ,"
		+"Revisit_date as 新单回访成功日期 ,"
		+"Matu_Date as 满期日期 ,"
		+"Conta_Name as 缴费联系人姓名 ,"
		+"Conta_Tel as 缴费联系人电话 ,"
		+"Busi_Src_Type as 销售渠道 ,"
		+"Agt_Code as 中介机构代码 ,"
		+"Salesman_No as 营销员代码 ,"
		+"Banc_Speci_No as 银保专管员代码 ,"
		+"Staff_No as 员工代码 , "
		+"Extra_Memo as 特别约定 ,"
		+"Reins_Type as 分保类型  "
		+" from %1$s "
		+" where (Agt_Code is null or Agt_Code = '' or not exists(select 1 from agt_code where Agt_Code = %1$s.Agt_Code)) " 
		+" and Busi_Src_Type in('220','221','229','230','300')";
	
	//3	JHWZ005	新保单信息表- 中介机构代码在中介机构信息表中不存在
	private static String SQL_JHWZ005_b = 
		"select "
		+"PolNo as 保单号码 ,"
		+"CertNo as 分单号码 ,"
		+"Pol_Prt_Code as 保单印刷流水号 ,"
		+"Prpl_No as 投保单号码 ,"
		+"Prpl_Prt_Code as 投保单印刷号 ,"
		+"Post_Code as 投保人住所邮编 ,"
		+"Branch_Code as 分支机构代码 ,"
		+"GP_Type as 个团标志 ,"
		+"App_Name as 投保人名称 ,"
		+"App_Idcard_Type as 投保人证件类型 ,"
		+"App_Idcard_No as 投保人证件号码 ,"
		+"App_Income as [投保人收入（万）] ,"
		+"InsName as 被保险人名称 ,"
		+"Ins_Idcard_Type as 被保险人证件类型 ,"
		+"Ins_Idcard_No as 被保险人证件号码 ,"
		+"App_Ins_Relation as 投保人与被保险人关系 ,"
		+"BeneName as 受益人姓名 ,"
		+"Sum_Ins as 总保额 ,"
		+"Sum_Ins_Death as 死亡保额 ,"
		+"App_Date as 投保日期 ,"
		+"Input_Date as 录入日期 ,"
		+"Underwr_date as 核保通过日期 ,"
		+"Eff_Date as [生效日期（保险责任起期）] ,"
		+"Recp_date as 签收保单回执日期 ,"
		+"Revisit_date as 新单回访成功日期 ,"
		+"Matu_Date as 满期日期 ,"
		+"Conta_Name as 缴费联系人姓名 ,"
		+"Conta_Tel as 缴费联系人电话 ,"
		+"Busi_Src_Type as 销售渠道 ,"
		+"Agt_Code as 中介机构代码 ,"
		+"Salesman_No as 营销员代码 ,"
		+"Banc_Speci_No as 银保专管员代码 ,"
		+"Staff_No as 员工代码 , "
		+"Extra_Memo as 特别约定 ,"
		+"Reins_Type as 分保类型  "
		+ "from %1$s where Agt_Code is not null and Agt_Code != '' and not exists ( select Agt_Code from agt_code where Agt_Code = %1$s.AGT_CODE)";
	
	//JHWZ006	缴费信息表- 被保险人证件类型在证件类型代码表中不存在
	private static String SQL_JHWZ006 = "select "
		+"PolNo as 保单号码 ,                                                 "
		+"CertNo as 分单号码 ,                                                "
		+"GP_Type as 个团标志 ,                                               "
		+"Branch_Code as 分支机构代码 ,                                         "
		+"Prpl_No as 投保单号码 ,                                              "
		+"App_Name as 投保人名称 ,                                             "
		+"App_Idcard_Type as 投保人证件类型 ,                                    "
		+"App_Idcard_No as 投保人证件号码 ,                                      "
		+"InsName as 被保人名称 ,                                              "
		+"Ins_Idcard_Type as 被保险人证件类型 ,                                   "
		+"Ins_Idcard_No as 被保险人证件号码 ,                                     "
		+"App_Date as 投保日期 ,                                              "
		+"Eff_Date as 生效日期 ,                                              "
		+"BRNo as 险种序号 ,                                                  "
		+"Plan_Code as 险种代码 ,                                             "
		+"Sum_Ins as 保额 ,                                                 "
		+"Sum_Ins_Death as 死亡保额 ,                                         "
		+"Amt_Type as 收付类型 ,                                              "
		+"Period as [保险期限（月）] ,                                           "
		+"Prem_Term as [缴费期限（月）] ,                                        "
		+"CurNo as 保费币种 ,                                                 "
		+"Payab_Date as [应缴日期（应收日期）] ,                                    "
		+"Payab_Prem_cnvt as 应收保费折合人民币 ,                                  "
		+"PayabCol_Vou_Cod as 应收保费凭证号 ,                                   "
		+"Gained_Date as [到账日期（实收日期）] ,                                   "
		+"Tot_Prem_cnvt as 实收保费合计折合人民币 ,                                  "
		+"PreCol_Vou_Cod as 预收保费凭证号 ,                                     "
		+"ActCol_Vou_Cod as 实收保费凭证号 ,                                     "
		+"Prem_Invo_Code as 保费发票号 ,                                       "
		+"CollectPay_Code as 收付款方式 ,                                      "
		+"Bank_account_code as 对方银行账号 ,                                   "
		+"Check_code as 对方结算号 ,                                           "
		+"Prem_Psns as 缴费人数 ,                                             "
		+"New_busi_Code as 新单期缴保费收入年期类型 ,                                 "
		+"Prem_Type as 缴费类型 ,                                             "
		+"Busi_Src_Type as 销售渠道 ,                                         "
		+"Agt_Code as 中介机构代码 ,                                            "
		+"Proc_Rate as 手续费比例 ,                                            "
		+"PayabProc_Vou_Cod as 应付手续费凭证号 ,                                 "
		+"ActProc_Vou_Code as 实付手续费凭证号 ,                                  "
		+"Salesman_No as 营销员代码 ,                                          "
		+"Com_Rate as 直接佣金比例 ,                                            "
		+"PayabCom_Vou_Cod as 应付佣金凭证号 ,                                   "
		+"ActCom_Vou_Code as 实付佣金凭证号 ,                                    "
		+"Speciman_No as 银保专管员代码 ,                                        "
		+"Spc_Bonus_Rat as 专管员直接绩效比例 ,                                    "
		+"Spc_PayabBonus_Vou_Code as 应付专管员绩效凭证号 ,                         "
		+"Special_ActBonus_Vou_Code as [实付专管员绩效凭证号或流水号/中间码] ,             "
		+"Staff_No as 员工代码 ,                                              "
		+"Staff_Bonus_Rate as 员工直接绩效比例 ,                                  "
		+"PayabBonus_Vou_Code as 应付员工绩效凭证号 ,                              "
		+"ActBonus_Vou_Code as 实付员工绩效凭证号                                  "
		+"from %1$s where Ins_Idcard_Type is not null and Ins_Idcard_Type != '' and not exists( select P from Id_Card_Type a where %1$s.Ins_Idcard_Type = a.P)";
	//10	JHWZ007	批单信息表- 投保日期、保单生效日期、发生日期（批改日期）、批单生效日期为空
	private static String SQL_JHWZ007 = "select "
		+"EntNo as 批单号 ,                                 "
		+"EntPrintCode as 批单印刷流水号 ,                      "
		+"GP_Type as 个团标志 ,                              "
		+"Branch_Code as 分支机构代码 ,                        "
		+"PolNo as 保单号 ,                                 "
		+"CertNo as 分单号 ,                                "
		+"Prpl_No as 投保单号码 ,                             "
		+"App_Name as 投保人名称 ,                            "
		+"App_Idcard_Type as 投保人证件类型 ,                   "
		+"App_Idcard_No as 投保人证件号码 ,                     "
		+"InsName as 被保人名称 ,                             "
		+"Ins_Idcard_Type as 被保险人证件类型 ,                  "
		+"Ins_Idcard_No as 被保险人证件号码 ,                    "
		+"App_Date as 投保日期 ,                             "
		+"Pol_Eff_Date as 保单生效日期 ,                       "
		+"BRNo as 险种序号 ,                                 "
		+"Plan_Code as 险种代码 ,                            "
		+"Period as [保险期限(月)] ,                          "
		+"Prem_Type as 缴费类型 ,                            "
		+"Prem_Term as [缴费期限(月)] ,                       "
		+"Busi_Src_Type as 销售渠道 ,                        "
		+"Agt_Code as 中介机构代码 ,                           "
		+"Salesman_No as 营销员代码 ,                         "
		+"Speciman_No as 银保专管员代码 ,                       "
		+"Staff_No as 员工代码 ,                             "
		+"POS_Type as 批改类型 ,                             "
		+"Amt_Type as 收付类型 ,                             "
		+"Sum_Ins as 批改后保额 ,                             "
		+"Proc_Date as [发生日期（批改日期）] ,                    "
		+"Edr_Eff_Date as 批改生效日期 ,                       "
		+"CurNo as 币种 ,                                  "
		+"Payab_Date as [应付/应收日期] ,                      "
		+"Amt_payab_cnvt as [应付/应收金额折合人民币] ,             "
		+"Endo_Vou_Code as [应付/应收财务凭证号] ,                "
		+"Gained_pay_Date as [实付/实收日期] ,                 "
		+"Amt_Incured_cnvt as [实付/实收金额折合人民币] ,           "
		+"ActEndo_Vou_Code as [实付/实收财务凭证号] ,             "
		+"CollectPay_Way_Code as 收付款方式 ,                 "
		+"Bank_account_code as 对方银行账号 ,                  "
		+"Check_code as 对方结算号 ,                          "
		+"Surrender_cause as 退保原因 ,                      "
		+"Endo_Content as 批文                             "
		+"from  %1$s where App_Date is null or Pol_Eff_Date is null or Edr_Eff_Date is null or Proc_Date is null ";
	
	//13     JHWZ012 财务凭证信息表- 没有期初余额记录
	private static String SQL_JHWZ012 = "select "
		+"Voucher_Date as 记账日期 ,           "
		+"Branch_Info as 分支机构 ,            "
		+"Voucher_Code as 记账凭证号 ,          "
		+"Entry_SN as 分录号 ,                "
		+"Account_Code as 科目代码 ,           "
		+"G_Account_Code as 总账科目代码 ,       "
		+"CurNo as 币种 ,                    "
		+"Debit_Sum as [借方金额（原币）] ,        "
		+"Debit_Sum_RMB as 借方金额折合人民币 ,     "
		+"Credit_Sum as [贷方金额（原币）] ,       "
		+"Credit_Sum_RMB as 贷方金额折合人民币 ,    "
		+"Brief as 摘要 ,                    "
		+"CollectPay_Code as 收付款方式 ,       "
		+"Bank_Acc as 银行账号 ,               "
		+"Check_ID as 结算号 ,                "
		+"Oppo_Bank_Acc as 对方银行帐号 ,        "
		+"Oppo_Check_ID as 对方结算号 ,         "
		+"Proposal_Code as 投保单号 ,          "
		+"PolNo as 保单号 ,                   "
		+"EntNo as 批单号 ,                   "
		+"CaseNo as 案件号 ,                  "
		+"Plan_Code as 险种代码 ,              "
		+"Profit_center as [利润中心/渠道] ,     "
		+"Department as 部门 ,               "
		+"Personal as 经办人代码 ,              "
		+"Is_Original as 是否期初余额            "
		+"from %1$s where Is_Original = 'Y'";
	//JHKJ006	赔案信息表-收付款方式为2、5时：对方银行帐号为空或不符合银行帐号一般特征（如存在数字、位数大于5等）	
	static String SQL_JHKJ006 = "select                                                 "
		+"CaseNo AS 立案号 ,                                     "
		+"PaySeq AS 支付次数 ,                                   "
		+"PolNo AS 保单号 ,                                      "
		+"CertNo AS 分单号 ,                                     "
		+"ListNo AS 清单号 ,                                     "
		+"Branch_Code AS 分支机构代码 ,                          "
		+"GP_Type AS 个团标志 ,                                  "
		+"Prpl_No AS 投保单号码 ,                                "
		+"App_name AS 投保人名称 ,                               "
		+"App_Idcard_Type AS 投保人证件类型 ,                    "
		+"App_Idcard_No AS 投保人证件号码 ,                      "
		+"Insurd_name AS 被保险人名称 ,                          "
		+"Ins_Idcard_Type AS 被保险人证件类型 ,                  "
		+"Ins_Idcard_No AS 被保险人证件号码 ,                    "
		+"BeneName AS 受益人姓名 ,                               "
		+"App_Date AS 投保日期 ,                                 "
		+"Pol_Eff_Date AS 保单生效日期 ,                         "
		+"BrNo AS 险种序号 ,                                     "
		+"Plan_Code AS 险种代码 ,                                "
		+"Busi_Src_Type AS 销售渠道 ,                            "
		+"Agt_Code AS 中介机构代码 ,                             "
		+"Salesman_No AS 营销员代码 ,                            "
		+"Speciman_No AS 银保专管员代码 ,                        "
		+"Staff_No AS 员工代码 ,                                 "
		+"c_rpt_no AS 报案号 ,                                   "
		+"Docu_Date AS 报案日期 ,                                "
		+"Case_Date AS 立案日期 ,                                "
		+"Coverage_Code AS 责任类别代码 ,                        "
		+"Reckon_Loss AS 估损金额 ,                              "
		+"Check_Date AS 核赔通过日期 ,                           "
		+"End_Date AS 结案日期 ,                                 "
		+"Paid_Name AS 领取人姓名 ,                              "
		+"Paid_Idcard_Type AS 领取人证件类型 ,                   "
		+"Paid_Idcard_No AS 领取人证件号码 ,                     "
		+"Amt_Type AS 收付类型 ,                                 "
		+"Gained_Date AS [付讫日期（实付日期）] ,                "
		+"CurNo AS 币种 ,                                        "
		+"Payab_Amt_cnvt AS [核赔给付额/应付赔款支出折合人民币] ,"
		+"Payab_Vou_Code AS 应付赔款凭证号 ,                     "
		+"Paid_Amt_cnvt AS 实付赔款支出折合人民币 ,              "
		+"Paid_Vou_Code AS 实付赔款凭证号 ,                      "
		+"CollectPay_Code AS 付款方式 ,                          "
		+"Pay_account_code AS 对方银行账号 ,                     "
		+"Pay_check_no AS 结算号 ,                               "
		+"Reje_amt AS 拒赔金额 ,                                 "
		+"Check_Fee AS 查勘费用 ,                                "
		+"accommodate_cause AS 通融赔付原因 ,                    "
		+"N_Share_Sum AS 摊回比例分保赔款 ,                      "
		+"N_Share_Sum_n AS 摊回非比例分保赔款                    " 
		+"from %1$s WHERE CollectPay_Code IN('2','5') and (Pay_account_code IS NULL OR LEN(Pay_account_code) <=5 )";
	
	//JHKJ006	赔案信息表-付讫日期、实付赔款支出折合人民币不等于零时：实付赔款凭证号、领取人姓名、领取人证件类型、领取人证件号码存在为空
	static String SQL_JHKJ006_2 = "select     "
	+"CaseNo AS 立案号 ,                                   "
	+"PaySeq AS 支付次数 ,                                 "
	+"PolNo AS 保单号 ,                                    "
	+"CertNo AS 分单号 ,                                   "
	+"ListNo AS 清单号 ,                                   "
	+"Branch_Code AS 分支机构代码 ,                        "
	+"GP_Type AS 个团标志 ,                                "
	+"Prpl_No AS 投保单号码 ,                              "
	+"App_name AS 投保人名称 ,                             "
	+"App_Idcard_Type AS 投保人证件类型 ,                  "
	+"App_Idcard_No AS 投保人证件号码 ,                    "
	+"Insurd_name AS 被保险人名称 ,                        "
	+"Ins_Idcard_Type AS 被保险人证件类型 ,                "
	+"Ins_Idcard_No AS 被保险人证件号码 ,                  "
	+"BeneName AS 受益人姓名 ,                             "
	+"App_Date AS 投保日期 ,                               "
	+"Pol_Eff_Date AS 保单生效日期 ,                       "
	+"BrNo AS 险种序号 ,                                   "
	+"Plan_Code AS 险种代码 ,                              "
	+"Busi_Src_Type AS 销售渠道 ,                          "
	+"Agt_Code AS 中介机构代码 ,                           "
	+"Salesman_No AS 营销员代码 ,                          "
	+"Speciman_No AS 银保专管员代码 ,                      "
	+"Staff_No AS 员工代码 ,                               "
	+"c_rpt_no AS 报案号 ,                                 "
	+"Docu_Date AS 报案日期 ,                              "
	+"Case_Date AS 立案日期 ,                              "
	+"Coverage_Code AS 责任类别代码 ,                      "
	+"Reckon_Loss AS 估损金额 ,                            "
	+"Check_Date AS 核赔通过日期 ,                         "
	+"End_Date AS 结案日期 ,                               "
	+"Paid_Name AS 领取人姓名 ,                            "
	+"Paid_Idcard_Type AS 领取人证件类型 ,                 "
	+"Paid_Idcard_No AS 领取人证件号码 ,                   "
	+"Amt_Type AS 收付类型 ,                               "
	+"Gained_Date AS [付讫日期（实付日期）] ,              "
	+"CurNo AS 币种 ,                                      "
	+"Payab_Amt_cnvt AS [核赔给付额/应付赔款支出折合人民币],"
	+"Payab_Vou_Code AS 应付赔款凭证号 ,                   "
	+"Paid_Amt_cnvt AS 实付赔款支出折合人民币 ,            "
	+"Paid_Vou_Code AS 实付赔款凭证号 ,                    "
	+"CollectPay_Code AS 付款方式 ,                        "
	+"Pay_account_code AS 对方银行账号 ,                   "
	+"Pay_check_no AS 结算号 ,                             "
	+"Reje_amt AS 拒赔金额 ,                               "
	+"Check_Fee AS 查勘费用 ,                              "
	+"accommodate_cause AS 通融赔付原因 ,                  "
	+"N_Share_Sum AS 摊回比例分保赔款 ,                    "
	+"N_Share_Sum_n AS 摊回非比例分保赔款                  "
	+ "from %1$s WHERE (Gained_Date is not null and Paid_Amt_cnvt is not null) and (Paid_Vou_Code is null or Paid_Name is null or Paid_Idcard_No is null) ";
	
	// JHWZ005 新保单信息表- 被保险人证件类型在证件类型代码表中不存在
	static String SQL_JHWZ005_a = "select "
		+"pol_main.PolNo as 保单号码 ,                       "
		+"pol_main.CertNo as 分单号码 ,                      "
		+"pol_main.Pol_Prt_Code as 保单印刷流水号 ,          "
		+"pol_main.Prpl_No as 投保单号码 ,                   "
		+"pol_main.Prpl_Prt_Code as 投保单印刷号 ,           "
		+"pol_main.Post_Code as 投保人住所邮编 ,             "
		+"pol_main.Branch_Code as 分支机构代码 ,             "
		+"pol_main.GP_Type as 个团标志 ,                     "
		+"pol_main.App_Name as 投保人名称 ,                  "
		+"pol_main.App_Idcard_Type as 投保人证件类型 ,       "
		+"pol_main.App_Idcard_No as 投保人证件号码 ,         "
		+"pol_main.App_Income as [投保人收入（万）] ,        "
		+"pol_main.InsName as 被保险人名称 ,                 "
		+"pol_main.Ins_Idcard_Type as 被保险人证件类型 ,     "
		+"pol_main.Ins_Idcard_No as 被保险人证件号码 ,       "
		+"pol_main.App_Ins_Relation as 投保人与被保险人关系 ,"
		+"pol_main.BeneName as 受益人姓名 ,                  "
		+"pol_main.Sum_Ins as 总保额 ,                       "
		+"pol_main.Sum_Ins_Death as 死亡保额 ,               "
		+"pol_main.App_Date as 投保日期 ,                    "
		+"pol_main.Input_Date as 录入日期 ,                  "
		+"pol_main.Underwr_date as 核保通过日期 ,            "
		+"pol_main.Eff_Date as [生效日期（保险责任起期）] ,  "
		+"pol_main.Recp_date as 签收保单回执日期 ,           "
		+"pol_main.Revisit_date as 新单回访成功日期 ,        "
		+"pol_main.Matu_Date as 满期日期 ,                   "
		+"pol_main.Conta_Name as 缴费联系人姓名 ,            "
		+"pol_main.Conta_Tel as 缴费联系人电话 ,             "
		+"pol_main.Busi_Src_Type as 销售渠道 ,               "
		+"pol_main.Agt_Code as 中介机构代码 ,                "
		+"pol_main.Salesman_No as 营销员代码 ,               "
		+"pol_main.Banc_Speci_No as 银保专管员代码 ,         "
		+"pol_main.Staff_No as 员工代码 ,                    "
		+"pol_main.Extra_Memo as 特别约定 ,                  "
		+"pol_main.Reins_Type as 分保类型                    "
		+"from %1$s pol_main where pol_main.Ins_Idcard_Type is not null and pol_main.Ins_Idcard_Type != '' and not exists( select P from Id_Card_Type where pol_main.Ins_Idcard_Type = P)";
	//JHWZ005	新保单信息表- 投保人与被保险人关系在人员关系代码基表中不存在或为空
	static String SQL_JHWZ005_c = "select "
		+"PolNo as 保单号码 ,                       "                                                                                                      
		+"CertNo as 分单号码 ,                      "                                                                                                      
		+"Pol_Prt_Code as 保单印刷流水号 ,          "                                                                                                      
		+"Prpl_No as 投保单号码 ,                   "                                                                                                      
		+"Prpl_Prt_Code as 投保单印刷号 ,           "                                                                                                      
		+"Post_Code as 投保人住所邮编 ,             "                                                                                                      
		+"Branch_Code as 分支机构代码 ,             "                                                                                                      
		+"GP_Type as 个团标志 ,                     "                                                                                                      
		+"App_Name as 投保人名称 ,                  "                                                                                                      
		+"App_Idcard_Type as 投保人证件类型 ,       "                                                                                                      
		+"App_Idcard_No as 投保人证件号码 ,         "                                                                                                      
		+"App_Income as [投保人收入（万）] ,        "                                                                                                      
		+"InsName as 被保险人名称 ,                 "                                                                                                      
		+"Ins_Idcard_Type as 被保险人证件类型 ,     "                                                                                                      
		+"Ins_Idcard_No as 被保险人证件号码 ,       "                                                                                                      
		+"App_Ins_Relation as 投保人与被保险人关系 ,"                                                                                                      
		+"BeneName as 受益人姓名 ,                  "                                                                                                      
		+"Sum_Ins as 总保额 ,                       "                                                                                                      
		+"Sum_Ins_Death as 死亡保额 ,               "                                                                                                      
		+"App_Date as 投保日期 ,                    "                                                                                                      
		+"Input_Date as 录入日期 ,                  "                                                                                                      
		+"Underwr_date as 核保通过日期 ,            "                                                                                                      
		+"Eff_Date as [生效日期（保险责任起期）] ,  "                                                                                                      
		+"Recp_date as 签收保单回执日期 ,           "                                                                                                      
		+"Revisit_date as 新单回访成功日期 ,        "                                                                                                      
		+"Matu_Date as 满期日期 ,                   "                                                                                                      
		+"Conta_Name as 缴费联系人姓名 ,            "                                                                                                      
		+"Conta_Tel as 缴费联系人电话 ,             "                                                                                                      
		+"Busi_Src_Type as 销售渠道 ,               "                                                                                                      
		+"Agt_Code as 中介机构代码 ,                "                                                                                                      
		+"Salesman_No as 营销员代码 ,               "                                                                                                      
		+"Banc_Speci_No as 银保专管员代码 ,         "                                                                                                      
		+"Staff_No as 员工代码 ,                    "                                                                                                      
		+"Extra_Memo as 特别约定 ,                  "                                                                                                      
		+"Reins_Type as 分保类型                    "                                                                                                      
		+" from %1$s where not exists( select 1 from Relationship_code where App_Ins_Relation= Relationship_code.p)";
	//JHWZ005	新保单信息表- 个团标志为个险时投保人收入为空或为零	
	static String SQL_JHWZ005_d = "select "
	+"pol_main.PolNo as 保单号码 ,                          "
	+"pol_main.CertNo as 分单号码 ,                         "
	+"pol_main.Pol_Prt_Code as 保单印刷流水号 ,             "
	+"pol_main.Prpl_No as 投保单号码 ,                      "
	+"pol_main.Prpl_Prt_Code as 投保单印刷号 ,              "
	+"pol_main.Post_Code as 投保人住所邮编 ,                "
	+"pol_main.Branch_Code as 分支机构代码 ,                "
	+"pol_main.GP_Type as 个团标志 ,                        "
	+"pol_main.App_Name as 投保人名称 ,                     "
	+"pol_main.App_Idcard_Type as 投保人证件类型 ,          "
	+"pol_main.App_Idcard_No as 投保人证件号码 ,            "
	+"pol_main.App_Income as [投保人收入（万）] ,           "
	+"pol_main.InsName as 被保险人名称 ,                    "
	+"pol_main.Ins_Idcard_Type as 被保险人证件类型 ,        "
	+"pol_main.Ins_Idcard_No as 被保险人证件号码 ,          "
	+"pol_main.App_Ins_Relation as 投保人与被保险人关系 ,   "
	+"pol_main.BeneName as 受益人姓名 ,                     "
	+"pol_main.Sum_Ins as 总保额 ,                          "
	+"pol_main.Sum_Ins_Death as 死亡保额 ,                  "
	+"pol_main.App_Date as 投保日期 ,                       "
	+"pol_main.Input_Date as 录入日期 ,                     "
	+"pol_main.Underwr_date as 核保通过日期 ,               "
	+"pol_main.Eff_Date as [生效日期（保险责任起期）] ,     "
	+"pol_main.Recp_date as 签收保单回执日期 ,              "
	+"pol_main.Revisit_date as 新单回访成功日期 ,           "
	+"pol_main.Matu_Date as 满期日期 ,                      "
	+"pol_main.Conta_Name as 缴费联系人姓名 ,               "
	+"pol_main.Conta_Tel as 缴费联系人电话 ,                "
	+"pol_main.Busi_Src_Type as 销售渠道 ,                  "
	+"pol_main.Agt_Code as 中介机构代码 ,                   "
	+"pol_main.Salesman_No as 营销员代码 ,                  "
	+"pol_main.Banc_Speci_No as 银保专管员代码 ,            "
	+"pol_main.Staff_No as 员工代码 ,                       "
	+"pol_main.Extra_Memo as 特别约定 ,                     "
	+"pol_main.Reins_Type as 分保类型                       "
	+"  from %1$s pol_main where (App_Income is null or App_Income = 0) and GP_Type = 'P'";
	//JHWZ005	新保单信息表- 员工代码在员工信息表中不存在
	static String SQL_JHWZ005_e = "select "
	+"pol_main.PolNo as 保单号码 ,                         "
	+"pol_main.CertNo as 分单号码 ,                        "
	+"pol_main.Pol_Prt_Code as 保单印刷流水号 ,            "
	+"pol_main.Prpl_No as 投保单号码 ,                     "
	+"pol_main.Prpl_Prt_Code as 投保单印刷号 ,             "
	+"pol_main.Post_Code as 投保人住所邮编 ,               "
	+"pol_main.Branch_Code as 分支机构代码 ,               "
	+"pol_main.GP_Type as 个团标志 ,                       "
	+"pol_main.App_Name as 投保人名称 ,                    "
	+"pol_main.App_Idcard_Type as 投保人证件类型 ,         "
	+"pol_main.App_Idcard_No as 投保人证件号码 ,           "
	+"pol_main.App_Income as [投保人收入（万）] ,          "
	+"pol_main.InsName as 被保险人名称 ,                   "
	+"pol_main.Ins_Idcard_Type as 被保险人证件类型 ,       "
	+"pol_main.Ins_Idcard_No as 被保险人证件号码 ,         "
	+"pol_main.App_Ins_Relation as 投保人与被保险人关系 ,  "
	+"pol_main.BeneName as 受益人姓名 ,                    "
	+"pol_main.Sum_Ins as 总保额 ,                         "
	+"pol_main.Sum_Ins_Death as 死亡保额 ,                 "
	+"pol_main.App_Date as 投保日期 ,                      "
	+"pol_main.Input_Date as 录入日期 ,                    "
	+"pol_main.Underwr_date as 核保通过日期 ,              "
	+"pol_main.Eff_Date as [生效日期（保险责任起期）] ,    "
	+"pol_main.Recp_date as 签收保单回执日期 ,             "
	+"pol_main.Revisit_date as 新单回访成功日期 ,          "
	+"pol_main.Matu_Date as 满期日期 ,                     "
	+"pol_main.Conta_Name as 缴费联系人姓名 ,              "
	+"pol_main.Conta_Tel as 缴费联系人电话 ,               "
	+"pol_main.Busi_Src_Type as 销售渠道 ,                 "
	+"pol_main.Agt_Code as 中介机构代码 ,                  "
	+"pol_main.Salesman_No as 营销员代码 ,                 "
	+"pol_main.Banc_Speci_No as 银保专管员代码 ,           "
	+"pol_main.Staff_No as 员工代码 ,                      "
	+"pol_main.Extra_Memo as 特别约定 ,                    "
	+"pol_main.Reins_Type as 分保类型                      "
	+" from %1$s Pol_Main where Staff_No is not null and Staff_No != '' and not exists( select Staff_No from Staff_Info where POL_MAIN.Staff_No = STAFF_INFO.STAFF_NO)";
	//JHWZ005	新保单信息表- 投保人联系地址为空或不满足地址的一般性定义(字符串长度小于5)
	static String SQL_JHWZ005_f = "select "
		+"pol_main.PolNo as 保单号码 ,                         "
		+"pol_main.CertNo as 分单号码 ,                        "
		+"pol_main.Pol_Prt_Code as 保单印刷流水号 ,            "
		+"pol_main.Prpl_No as 投保单号码 ,                     "
		+"pol_main.Prpl_Prt_Code as 投保单印刷号 ,             "
		+"pol_main.Post_Code as 投保人住所邮编 ,               "
		+"pol_main.Branch_Code as 分支机构代码 ,               "
		+"pol_main.GP_Type as 个团标志 ,                       "
		+"pol_main.App_Name as 投保人名称 ,                    "
		+"pol_main.App_addr as 投保人地址 ,                    "
		+"pol_main.App_Idcard_Type as 投保人证件类型 ,         "
		+"pol_main.App_Idcard_No as 投保人证件号码 ,           "
		+"pol_main.App_Income as [投保人收入（万）] ,          "
		+"pol_main.InsName as 被保险人名称 ,                   "
		+"pol_main.Ins_Idcard_Type as 被保险人证件类型 ,       "
		+"pol_main.Ins_Idcard_No as 被保险人证件号码 ,         "
		+"pol_main.App_Ins_Relation as 投保人与被保险人关系 ,  "
		+"pol_main.BeneName as 受益人姓名 ,                    "
		+"pol_main.Sum_Ins as 总保额 ,                         "
		+"pol_main.Sum_Ins_Death as 死亡保额 ,                 "
		+"pol_main.App_Date as 投保日期 ,                      "
		+"pol_main.Input_Date as 录入日期 ,                    "
		+"pol_main.Underwr_date as 核保通过日期 ,              "
		+"pol_main.Eff_Date as [生效日期（保险责任起期）] ,    "
		+"pol_main.Recp_date as 签收保单回执日期 ,             "
		+"pol_main.Revisit_date as 新单回访成功日期 ,          "
		+"pol_main.Matu_Date as 满期日期 ,                     "
		+"pol_main.Conta_Name as 缴费联系人姓名 ,              "
		+"pol_main.Conta_Tel as 缴费联系人电话 ,               "
		+"pol_main.Busi_Src_Type as 销售渠道 ,                 "
		+"pol_main.Agt_Code as 中介机构代码 ,                  "
		+"pol_main.Salesman_No as 营销员代码 ,                 "
		+"pol_main.Banc_Speci_No as 银保专管员代码 ,           "
		+"pol_main.Staff_No as 员工代码 ,                      "
		+"pol_main.Extra_Memo as 特别约定 ,                    "
		+"pol_main.Reins_Type as 分保类型                      "
		+" from %1$s Pol_Main where App_addr is null or App_addr = '' or len(App_addr) < 5 ";
	
	//JHWZ005	新保单信息表- 个险新单(个团标志为P)的产品设计类型代码前三位为202(新型产品)或销售渠道代码为120(电销)时：新单首次回访日期为9999-01-01或回访方式、回访成功标志、签收保单回执日期、新单回访成功日期为空
	static String SQL_JHWZ005_g = "select "
		+"pol_main.PolNo as 保单号码 ,                         "
		+"pol_main.CertNo as 分单号码 ,                        "
		+"pol_main.Pol_Prt_Code as 保单印刷流水号 ,            "
		+"pol_main.Prpl_No as 投保单号码 ,                     "
		+"pol_main.Prpl_Prt_Code as 投保单印刷号 ,             "
		+"pol_main.Post_Code as 投保人住所邮编 ,               "
		+"pol_main.Branch_Code as 分支机构代码 ,               "
		+"pol_main.GP_Type as 个团标志 ,                       "
		+"pol_main.App_Name as 投保人名称 ,                    "
		+"pol_main.App_Idcard_Type as 投保人证件类型 ,         "
		+"pol_main.App_Idcard_No as 投保人证件号码 ,           "
		+"pol_main.App_Income as [投保人收入（万）] ,          "
		+"pol_main.InsName as 被保险人名称 ,                   "
		+"pol_main.Ins_Idcard_Type as 被保险人证件类型 ,       "
		+"pol_main.Ins_Idcard_No as 被保险人证件号码 ,         "
		+"pol_main.App_Ins_Relation as 投保人与被保险人关系 ,  "
		+"pol_main.BeneName as 受益人姓名 ,                    "
		+"pol_main.Sum_Ins as 总保额 ,                         "
		+"pol_main.Sum_Ins_Death as 死亡保额 ,                 "
		+"pol_main.App_Date as 投保日期 ,                      "
		+"pol_main.Input_Date as 录入日期 ,                    "
		+"pol_main.Underwr_date as 核保通过日期 ,              "
		+"pol_main.Eff_Date as [生效日期（保险责任起期）] ,    "
		+"pol_main.Recp_date as 签收保单回执日期 ,             "
		+"pol_main.Revisit_date as 新单回访成功日期 ,          "
		+"pol_main.Matu_Date as 满期日期 ,                     "
		+"pol_main.Conta_Name as 缴费联系人姓名 ,              "
		+"pol_main.Conta_Tel as 缴费联系人电话 ,               "
		+"pol_main.Busi_Src_Type as 销售渠道 ,                 "
		+"pol_main.Agt_Code as 中介机构代码 ,                  "
		+"pol_main.Salesman_No as 营销员代码 ,                 "
		+"pol_main.Banc_Speci_No as 银保专管员代码 ,           "
		+"pol_main.Staff_No as 员工代码 ,                      "
		+"pol_main.Extra_Memo as 特别约定 ,                    "
		+"pol_main.Reins_Type as 分保类型                      "
		+" from %1$s Pol_Main, Prem_Info, Plan_Info where Pol_Main.PolNo=Prem_Info.PolNo and Pol_Main.CertNo=Prem_Info.CertNo and Prem_Info.Plan_Code=Plan_Info.Plan_Code and Pol_Main.GP_Type = 'P' and (Plan_Info.Design_type like ('202%%') or Pol_Main.Busi_Src_Type = '120') and (Pol_Main.Revisit_first_date = '9999-01-01' or Pol_Main.Revisit_type is null or Pol_Main.Revisit_type = '' or Pol_Main.Revisit_flag is null or Pol_Main.Revisit_flag = '' or Pol_Main.Recp_date is null or Pol_Main.Revisit_date is null)";
		
	//JHWZ005 新保单信息表- 回访成功标志为0(回访成功)时：签收保单回执日期、新单回访成功日期为9999-01-01
	static String SQL_JHWZ005_h = "select "
		+"pol_main.PolNo as 保单号码 ,                            "
		+"pol_main.CertNo as 分单号码 ,                           "
		+"pol_main.Pol_Prt_Code as 保单印刷流水号 ,               "
		+"pol_main.Prpl_No as 投保单号码 ,                        "
		+"pol_main.Prpl_Prt_Code as 投保单印刷号 ,                "
		+"pol_main.Post_Code as 投保人住所邮编 ,                  "
		+"pol_main.Branch_Code as 分支机构代码 ,                  "
		+"pol_main.GP_Type as 个团标志 ,                          "
		+"pol_main.App_Name as 投保人名称 ,                       "
		+"pol_main.App_Idcard_Type as 投保人证件类型 ,            "
		+"pol_main.App_Idcard_No as 投保人证件号码 ,              "
		+"pol_main.App_Income as [投保人收入（万）] ,             "
		+"pol_main.InsName as 被保险人名称 ,                      "
		+"pol_main.Ins_Idcard_Type as 被保险人证件类型 ,          "
		+"pol_main.Ins_Idcard_No as 被保险人证件号码 ,            "
		+"pol_main.App_Ins_Relation as 投保人与被保险人关系 ,     "
		+"pol_main.BeneName as 受益人姓名 ,                       "
		+"pol_main.Sum_Ins as 总保额 ,                            "
		+"pol_main.Sum_Ins_Death as 死亡保额 ,                    "
		+"pol_main.App_Date as 投保日期 ,                         "
		+"pol_main.Input_Date as 录入日期 ,                       "
		+"pol_main.Underwr_date as 核保通过日期 ,                 "
		+"pol_main.Eff_Date as [生效日期（保险责任起期）] ,       "
		+"pol_main.Recp_date as 签收保单回执日期 ,                "
		+"pol_main.Revisit_date as 新单回访成功日期 ,             "
		+"pol_main.Matu_Date as 满期日期 ,                        "
		+"pol_main.Conta_Name as 缴费联系人姓名 ,                 "
		+"pol_main.Conta_Tel as 缴费联系人电话 ,                  "
		+"pol_main.Busi_Src_Type as 销售渠道 ,                    "
		+"pol_main.Agt_Code as 中介机构代码 ,                     "
		+"pol_main.Salesman_No as 营销员代码 ,                    "
		+"pol_main.Banc_Speci_No as 银保专管员代码 ,              "
		+"pol_main.Staff_No as 员工代码 ,                         "
		+"pol_main.Extra_Memo as 特别约定 ,                       "
		+"pol_main.Reins_Type as 分保类型                         "
		+" from %1$s Pol_Main where Revisit_flag = '0' and (Recp_date = '9999-01-01' or Revisit_date = '9999-01-01')";
		
	//14	JHWZ012	险种代码表- 停止销售日期存在空值,或日期关系不正确
	static String SQL_JHWZ012_a = "select * from %s where StopDate is null or Startdate > StopDate";
	//15	JHWZ012	中介机构信息表- 获得许可证日期、许可证到期日期、签约日期、协议到期日存在空值,或日期关系不正确
	static String SQL_JHWZ012_b = "select "
		+"Agt_Code AS 中介机构代码 ,               "
		+"Agt_Name AS 中介机构名称 ,               "
		+"Agt_Address AS 中介机构地址 ,            "
		+"agt_org_type AS 中介机构类别 ,           "
		+"agt_busi_num AS 经营保险中介业务许可证号 ,     "
		+"StartDate AS 获得中介许可证日期 ,           "
		+"EndDate AS 中介许可证到期日 ,              "
		+"SignDate AS 签约日期 ,                 "
		+"QuitDate AS 协议到期日或解约日 ,            "
		+"IsULQulifd AS 是否有投连销售资格            "
		+"from %1$s where STARTDATE IS NULL OR STARTDATE>=ENDDATE OR ENDDATE IS NULL OR SIGNDATE = '1900-01-01' OR QUITDATE ='1900-01-01' OR SIGNDATE IS NULL OR QUITDATE IS NULL" ;
	//16     JHWZ012 中介机构信息表- 中介机构类别在中介机构类别基表中不存在或为空
	static String SQL_JHWZ012_c = "select "
		+"Agt_Code AS 中介机构代码 ,               "
		+"Agt_Name AS 中介机构名称 ,               "
		+"Agt_Address AS 中介机构地址 ,            "
		+"agt_org_type AS 中介机构类别 ,           "
		+"agt_busi_num AS 经营保险中介业务许可证号 ,     "
		+"StartDate AS 获得中介许可证日期 ,           "
		+"EndDate AS 中介许可证到期日 ,              "
		+"SignDate AS 签约日期 ,                 "
		+"QuitDate AS 协议到期日或解约日 ,            "
		+"IsULQulifd AS 是否有投连销售资格            "
		+"from %1$s where not exists( select A from agt_org_type_tbl where %1$s.agt_org_type = A)" ;
	//17	JHWZ012	员工信息表- 证件类型在证件类型代码表中不存在或为空
	static String SQL_JHWZ012_d = "select "
		+"Staff_No as 员工代码 ,        "
		+"Staff_Name as 员工姓名 ,      "
		+"Sex as 员工性别 ,             "
		+"Id_type as 证件类型 ,         "
		+"Idno as 证件号码 ,            "
		+"quano as 代理资格证号 ,         "
		+"JoinDate as 入司日期 ,        "
		+"LeaveDate as 离司日期 ,       "
		+"Edu_Degree as 学历 ,        "
		+"Accreditation as 专业技术资格 , "
		+"Depart_info as 所属分支机构 ,   "
		+"Is_Leader as 是否高管 ,       "
		+"Staff_Rank as 职位          "
		+"from %1$s where not exists( select 1 from Id_Card_Type where %1$s.Id_type = P)";
	//18	JHWZ012	员工信息表- 所属分支机构在分支机构信息表中不存在或为空
	static String SQL_JHWZ012_e = "select "
		+"Staff_No as 员工代码 ,        "
		+"Staff_Name as 员工姓名 ,      "
		+"Sex as 员工性别 ,             "
		+"Id_type as 证件类型 ,         "
		+"Idno as 证件号码 ,            "
		+"quano as 代理资格证号 ,         "
		+"JoinDate as 入司日期 ,        "
		+"LeaveDate as 离司日期 ,       "
		+"Edu_Degree as 学历 ,        "
		+"Accreditation as 专业技术资格 , "
		+"Depart_info as 所属分支机构 ,   "
		+"Is_Leader as 是否高管 ,       "
		+"Staff_Rank as 职位          "
		+"from %1$s  where not exists( select 1 from Branch_info where %1$s.Depart_info = BRANCH_CODE)";
	//19	JHWZ012	银保专管员信息表- 学历在学历代码基表中不存在或为空
	static String SQL_JHWZ012_f = "select "
		+"Banc_Speci_No as 专管员代码 ,     "
		+"Banc_Speci_Name as 专管员姓名 ,    "
		+"Sex as 专管员性别 ,               "
		+"Id_type as 证件类型 ,            "
		+"Idno as 证件号码 ,               "
		+"quano as 代理资格证号 ,            "
		+"JoinDate as 入司日期 ,           "
		+"LeaveDate as 离司日期 ,          "
		+"Edu_Degree as 学历 ,           "
		+"Accreditation as 专业技术资格 ,    "
		+"Branch as 所属分支机构 ,           "
		+"Rank as 职位 ,                 "
		+"Source as 来源 ,               "
		+"IsCompliant as 是否有违规违纪记录 ,    "
		+"IsULQulifd as 是否有投连销售资格      "
		+"from %1$s  where not exists( select 1 from Edu_Degree_Code where %1$s.Edu_Degree = EDU_DEGREE_CODE.P)";
	//20	JHWZ012	银保专管员信息表- 所属分支机构在分支机构信息表中不存在或为空
	static String SQL_JHWZ012_g = "select "
		+"Banc_Speci_No as 专管员代码 ,     "
		+"Banc_Speci_Name as 专管员姓名 ,    "
		+"Sex as 专管员性别 ,               "
		+"Id_type as 证件类型 ,            "
		+"Idno as 证件号码 ,               "
		+"quano as 代理资格证号 ,            "
		+"JoinDate as 入司日期 ,           "
		+"LeaveDate as 离司日期 ,          "
		+"Edu_Degree as 学历 ,           "
		+"Accreditation as 专业技术资格 ,    "
		+"Branch as 所属分支机构 ,           "
		+"Rank as 职位 ,                 "
		+"Source as 来源 ,               "
		+"IsCompliant as 是否有违规违纪记录 ,    "
		+"IsULQulifd as 是否有投连销售资格      "
		+"from %1$s  where not exists( select 1 from Branch_info where BRANCH_INFO.BRANCH_CODE = %1$s.Branch)";
	//21	JHWZ012	分支机构信息表- 经营区域代码不是GB/T2260规定的行政区划代码或为空
	static String SQL_JHWZ012_h = "select * from %1$s where len(Branch_area_code)!=6";
	//JHWZ006	缴费信息表- 险种代码在险种代码表中不存在或为空
	static String SQL_JHWZ006_a = "select "
		+"prem_info.PolNo as 保单号码 ,                                                  "
		+"prem_info.CertNo as 分单号码 ,                                                 "
		+"prem_info.GP_Type as 个团标志 ,                                                "
		+"prem_info.Branch_Code as 分支机构代码 ,                                        "
		+"prem_info.Prpl_No as 投保单号码 ,                                              "
		+"prem_info.App_Name as 投保人名称 ,                                             "
		+"prem_info.App_Idcard_Type as 投保人证件类型 ,                                  "
		+"prem_info.App_Idcard_No as 投保人证件号码 ,                                    "
		+"prem_info.InsName as 被保人名称 ,                                              "
		+"prem_info.Ins_Idcard_Type as 被保险人证件类型 ,                                "
		+"prem_info.Ins_Idcard_No as 被保险人证件号码 ,                                  "
		+"prem_info.App_Date as 投保日期 ,                                               "
		+"prem_info.Eff_Date as 生效日期 ,                                               "
		+"prem_info.BRNo as 险种序号 ,                                                   "
		+"prem_info.Plan_Code as 险种代码 ,                                              "
		+"prem_info.Sum_Ins as 保额 ,                                                    "
		+"prem_info.Sum_Ins_Death as 死亡保额 ,                                          "
		+"prem_info.Amt_Type as 收付类型 ,                                               "
		+"prem_info.Period as [保险期限（月）] ,                                         "
		+"prem_info.Prem_Term as [缴费期限（月）] ,                                      "
		+"prem_info.CurNo as 保费币种 ,                                                  "
		+"prem_info.Payab_Date as [应缴日期（应收日期）] ,                               "
		+"prem_info.Payab_Prem_cnvt as 应收保费折合人民币 ,                              "
		+"prem_info.PayabCol_Vou_Cod as 应收保费凭证号 ,                                 "
		+"prem_info.Gained_Date as [到账日期（实收日期）] ,                              "
		+"prem_info.Tot_Prem_cnvt as 实收保费合计折合人民币 ,                            "
		+"prem_info.PreCol_Vou_Cod as 预收保费凭证号 ,                                   "
		+"prem_info.ActCol_Vou_Cod as 实收保费凭证号 ,                                   "
		+"prem_info.Prem_Invo_Code as 保费发票号 ,                                       "
		+"prem_info.CollectPay_Code as 收付款方式 ,                                      "
		+"prem_info.Bank_account_code as 对方银行账号 ,                                  "
		+"prem_info.Check_code as 对方结算号 ,                                           "
		+"prem_info.Prem_Psns as 缴费人数 ,                                              "
		+"prem_info.New_busi_Code as 新单期缴保费收入年期类型 ,                          "
		+"prem_info.Prem_Type as 缴费类型 ,                                              "
		+"prem_info.Busi_Src_Type as 销售渠道 ,                                          "
		+"prem_info.Agt_Code as 中介机构代码 ,                                           "
		+"prem_info.Proc_Rate as 手续费比例 ,                                            "
		+"prem_info.PayabProc_Vou_Cod as 应付手续费凭证号 ,                              "
		+"prem_info.ActProc_Vou_Code as 实付手续费凭证号 ,                               "
		+"prem_info.Salesman_No as 营销员代码 ,                                          "
		+"prem_info.Com_Rate as 直接佣金比例 ,                                           "
		+"prem_info.PayabCom_Vou_Cod as 应付佣金凭证号 ,                                 "
		+"prem_info.ActCom_Vou_Code as 实付佣金凭证号 ,                                  "
		+"prem_info.Speciman_No as 银保专管员代码 ,                                      "
		+"prem_info.Spc_Bonus_Rat as 专管员直接绩效比例 ,                                "
		+"prem_info.Spc_PayabBonus_Vou_Code as 应付专管员绩效凭证号 ,                    "
		+"prem_info.Special_ActBonus_Vou_Code as [实付专管员绩效凭证号或流水号/中间码] , "
		+"prem_info.Staff_No as 员工代码 ,                                               "
		+"prem_info.Staff_Bonus_Rate as 员工直接绩效比例 ,                               "
		+"prem_info.PayabBonus_Vou_Code as 应付员工绩效凭证号 ,                          "
		+"prem_info.ActBonus_Vou_Code as 实付员工绩效凭证号                              "
		+" from %1$s Prem_Info where not exists( select Plan_Code from Plan_info where PREM_INFO.Plan_Code= PLAN_INFO.PLAN_CODE)";
	
	//JHWZ006	缴费信息表- 员工代码若不为空：员工代码在员工信息表中不存在、应付员工绩效凭证号在财务凭证信息表中不存在或为空、员工直接绩效比例小于等于零
	static String SQL_JHWZ006_b = "select "
		+"prem_info.PolNo as 保单号码 ,                                                 "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.CertNo as 分单号码 ,                                                "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.GP_Type as 个团标志 ,                                               "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Branch_Code as 分支机构代码 ,                                       "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Prpl_No as 投保单号码 ,                                             "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.App_Name as 投保人名称 ,                                            "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.App_Idcard_Type as 投保人证件类型 ,                                 "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.App_Idcard_No as 投保人证件号码 ,                                   "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.InsName as 被保人名称 ,                                             "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Ins_Idcard_Type as 被保险人证件类型 ,                               "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Ins_Idcard_No as 被保险人证件号码 ,                                 "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.App_Date as 投保日期 ,                                              "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Eff_Date as 生效日期 ,                                              "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.BRNo as 险种序号 ,                                                  "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Plan_Code as 险种代码 ,                                             "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Sum_Ins as 保额 ,                                                   "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Sum_Ins_Death as 死亡保额 ,                                         "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Amt_Type as 收付类型 ,                                              "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Period as [保险期限（月）] ,                                        "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Prem_Term as [缴费期限（月）] ,                                     "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.CurNo as 保费币种 ,                                                 "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Payab_Date as [应缴日期（应收日期）] ,                              "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Payab_Prem_cnvt as 应收保费折合人民币 ,                             "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.PayabCol_Vou_Cod as 应收保费凭证号 ,                                "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Gained_Date as [到账日期（实收日期）] ,                             "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Tot_Prem_cnvt as 实收保费合计折合人民币 ,                           "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.PreCol_Vou_Cod as 预收保费凭证号 ,                                  "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.ActCol_Vou_Cod as 实收保费凭证号 ,                                  "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Prem_Invo_Code as 保费发票号 ,                                      "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.CollectPay_Code as 收付款方式 ,                                     "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Bank_account_code as 对方银行账号 ,                                 "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Check_code as 对方结算号 ,                                          "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Prem_Psns as 缴费人数 ,                                             "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.New_busi_Code as 新单期缴保费收入年期类型 ,                         "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Prem_Type as 缴费类型 ,                                             "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Busi_Src_Type as 销售渠道 ,                                         "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Agt_Code as 中介机构代码 ,                                          "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Proc_Rate as 手续费比例 ,                                           "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.PayabProc_Vou_Cod as 应付手续费凭证号 ,                             "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.ActProc_Vou_Code as 实付手续费凭证号 ,                              "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Salesman_No as 营销员代码 ,                                         "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Com_Rate as 直接佣金比例 ,                                          "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.PayabCom_Vou_Cod as 应付佣金凭证号 ,                                "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.ActCom_Vou_Code as 实付佣金凭证号 ,                                 "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Speciman_No as 银保专管员代码 ,                                     "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Spc_Bonus_Rat as 专管员直接绩效比例 ,                               "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Spc_PayabBonus_Vou_Code as 应付专管员绩效凭证号 ,                   "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Special_ActBonus_Vou_Code as [实付专管员绩效凭证号或流水号/中间码] ,"                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Staff_No as 员工代码 ,                                              "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.Staff_Bonus_Rate as 员工直接绩效比例 ,                              "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.PayabBonus_Vou_Code as 应付员工绩效凭证号 ,                         "                                                                                                                                                                                                                                                                                                                              
		+"prem_info.ActBonus_Vou_Code as 实付员工绩效凭证号                             "                                                                                                                                                                                                                                                                                                                              
		+" from %1$s Prem_Info where Staff_No is not null and Staff_No != '' and (not exists(select 1 from Staff_Info where Prem_Info.Staff_No = Staff_Info.Staff_No) or (not exists(select 1 from Voucher_Info where Prem_Info.PayabBonus_Vou_Code = Voucher_Info.Voucher_Code) or PayabBonus_Vou_Code is null or PayabBonus_Vou_Code = '') or Staff_Bonus_Rate <= 0)";
	
	static String SQL_ddfdfdfs="select "
		+"endo_fee.EntNo as 批单号 ,                              "
		+"endo_fee.EntPrintCode as 批单印刷流水号 ,               "
		+"endo_fee.GP_Type as 个团标志 ,                          "
		+"endo_fee.Branch_Code as 分支机构代码 ,                  "
		+"endo_fee.PolNo as 保单号 ,                              "
		+"endo_fee.CertNo as 分单号 ,                             "
		+"endo_fee.Prpl_No as 投保单号码 ,                        "
		+"endo_fee.App_Name as 投保人名称 ,                       "
		+"endo_fee.App_Idcard_Type as 投保人证件类型 ,            "
		+"endo_fee.App_Idcard_No as 投保人证件号码 ,              "
		+"endo_fee.InsName as 被保人名称 ,                        "
		+"endo_fee.Ins_Idcard_Type as 被保险人证件类型 ,          "
		+"endo_fee.Ins_Idcard_No as 被保险人证件号码 ,            "
		+"endo_fee.App_Date as 投保日期 ,                         "
		+"endo_fee.Pol_Eff_Date as 保单生效日期 ,                 "
		+"endo_fee.BRNo as 险种序号 ,                             "
		+"endo_fee.Plan_Code as 险种代码 ,                        "
		+"endo_fee.Period as [保险期限(月)] ,                     "
		+"endo_fee.Prem_Type as 缴费类型 ,                        "
		+"endo_fee.Prem_Term as [缴费期限(月)] ,                  "
		+"endo_fee.Busi_Src_Type as 销售渠道 ,                    "
		+"endo_fee.Agt_Code as 中介机构代码 ,                     "
		+"endo_fee.Salesman_No as 营销员代码 ,                    "
		+"endo_fee.Speciman_No as 银保专管员代码 ,                "
		+"endo_fee.Staff_No as 员工代码 ,                         "
		+"endo_fee.POS_Type as 批改类型 ,                         "
		+"endo_fee.Amt_Type as 收付类型 ,                         "
		+"endo_fee.Sum_Ins as 批改后保额 ,                        "
		+"endo_fee.Proc_Date as [发生日期（批改日期）] ,          "
		+"endo_fee.Edr_Eff_Date as 批改生效日期 ,                 "
		+"endo_fee.CurNo as 币种 ,                                "
		+"endo_fee.Payab_Date as [应付/应收日期] ,                "
		+"endo_fee.Amt_payab_cnvt as [应付/应收金额折合人民币] ,  "
		+"endo_fee.Endo_Vou_Code as [应付/应收财务凭证号] ,       "
		+"endo_fee.Gained_pay_Date as [实付/实收日期] ,           "
		+"endo_fee.Amt_Incured_cnvt as [实付/实收金额折合人民币] ,"
		+"endo_fee.ActEndo_Vou_Code as [实付/实收财务凭证号] ,    "
		+"endo_fee.CollectPay_Way_Code as 收付款方式 ,            "
		+"endo_fee.Bank_account_code as 对方银行账号 ,            "
		+"endo_fee.Check_code as 对方结算号 ,                     "
		+"endo_fee.Surrender_cause as 退保原因 ,                  "
		+"endo_fee.Endo_Content as 批文                           "
		+" from %1$s Endo_Fee where not exists( select 1 from POS_Type_Tbl where ENDO_FEE.POS_Type = POS_TYPE_TBL.PO)";
	//JHWZ007	批单信息表- 险种代码在险种代码表中不存在或为空
	static String SQL_JHWZ007_a = "select "
		+"endo_fee.EntNo as 批单号 ,                               "                                                                                   
		+"endo_fee.EntPrintCode as 批单印刷流水号 ,                "                                                                                   
		+"endo_fee.GP_Type as 个团标志 ,                           "                                                                                   
		+"endo_fee.Branch_Code as 分支机构代码 ,                   "                                                                                   
		+"endo_fee.PolNo as 保单号 ,                               "                                                                                   
		+"endo_fee.CertNo as 分单号 ,                              "                                                                                   
		+"endo_fee.Prpl_No as 投保单号码 ,                         "                                                                                   
		+"endo_fee.App_Name as 投保人名称 ,                        "                                                                                   
		+"endo_fee.App_Idcard_Type as 投保人证件类型 ,             "                                                                                   
		+"endo_fee.App_Idcard_No as 投保人证件号码 ,               "                                                                                   
		+"endo_fee.InsName as 被保人名称 ,                         "                                                                                   
		+"endo_fee.Ins_Idcard_Type as 被保险人证件类型 ,           "                                                                                   
		+"endo_fee.Ins_Idcard_No as 被保险人证件号码 ,             "                                                                                   
		+"endo_fee.App_Date as 投保日期 ,                          "                                                                                   
		+"endo_fee.Pol_Eff_Date as 保单生效日期 ,                  "                                                                                   
		+"endo_fee.BRNo as 险种序号 ,                              "                                                                                   
		+"endo_fee.Plan_Code as 险种代码 ,                         "                                                                                   
		+"endo_fee.Period as [保险期限(月)] ,                      "                                                                                   
		+"endo_fee.Prem_Type as 缴费类型 ,                         "                                                                                   
		+"endo_fee.Prem_Term as [缴费期限(月)] ,                   "                                                                                   
		+"endo_fee.Busi_Src_Type as 销售渠道 ,                     "                                                                                   
		+"endo_fee.Agt_Code as 中介机构代码 ,                      "                                                                                   
		+"endo_fee.Salesman_No as 营销员代码 ,                     "                                                                                   
		+"endo_fee.Speciman_No as 银保专管员代码 ,                 "                                                                                   
		+"endo_fee.Staff_No as 员工代码 ,                          "                                                                                   
		+"endo_fee.POS_Type as 批改类型 ,                          "                                                                                   
		+"endo_fee.Amt_Type as 收付类型 ,                          "                                                                                   
		+"endo_fee.Sum_Ins as 批改后保额 ,                         "                                                                                   
		+"endo_fee.Proc_Date as [发生日期（批改日期）] ,           "                                                                                   
		+"endo_fee.Edr_Eff_Date as 批改生效日期 ,                  "                                                                                   
		+"endo_fee.CurNo as 币种 ,                                 "                                                                                   
		+"endo_fee.Payab_Date as [应付/应收日期] ,                 "                                                                                   
		+"endo_fee.Amt_payab_cnvt as [应付/应收金额折合人民币] ,   "                                                                                   
		+"endo_fee.Endo_Vou_Code as [应付/应收财务凭证号] ,        "                                                                                   
		+"endo_fee.Gained_pay_Date as [实付/实收日期] ,            "                                                                                   
		+"endo_fee.Amt_Incured_cnvt as [实付/实收金额折合人民币] , "                                                                                   
		+"endo_fee.ActEndo_Vou_Code as [实付/实收财务凭证号] ,     "                                                                                   
		+"endo_fee.CollectPay_Way_Code as 收付款方式 ,             "                                                                                   
		+"endo_fee.Bank_account_code as 对方银行账号 ,             "                                                                                   
		+"endo_fee.Check_code as 对方结算号 ,                      "                                                                                   
		+"endo_fee.Surrender_cause as 退保原因 ,                   "                                                                                   
		+"endo_fee.Endo_Content as 批文                            "                                                                                   
		+" from %1$s Endo_Fee where not exists( select 1 from Plan_info where ENDO_FEE.Plan_Code = PLAN_INFO.PLAN_CODE)" ;
	//JHWZ007	批单信息表- 员工代码在员工信息表中不存在	
	static String SQL_JHWZ007_b = "select "
		+"endo_fee.EntNo as 批单号 ,                              "
		+"endo_fee.EntPrintCode as 批单印刷流水号 ,               "
		+"endo_fee.GP_Type as 个团标志 ,                          "
		+"endo_fee.Branch_Code as 分支机构代码 ,                  "
		+"endo_fee.PolNo as 保单号 ,                              "
		+"endo_fee.CertNo as 分单号 ,                             "
		+"endo_fee.Prpl_No as 投保单号码 ,                        "
		+"endo_fee.App_Name as 投保人名称 ,                       "
		+"endo_fee.App_Idcard_Type as 投保人证件类型 ,            "
		+"endo_fee.App_Idcard_No as 投保人证件号码 ,              "
		+"endo_fee.InsName as 被保人名称 ,                        "
		+"endo_fee.Ins_Idcard_Type as 被保险人证件类型 ,          "
		+"endo_fee.Ins_Idcard_No as 被保险人证件号码 ,            "
		+"endo_fee.App_Date as 投保日期 ,                         "
		+"endo_fee.Pol_Eff_Date as 保单生效日期 ,                 "
		+"endo_fee.BRNo as 险种序号 ,                             "
		+"endo_fee.Plan_Code as 险种代码 ,                        "
		+"endo_fee.Period as [保险期限(月)] ,                     "
		+"endo_fee.Prem_Type as 缴费类型 ,                        "
		+"endo_fee.Prem_Term as [缴费期限(月)] ,                  "
		+"endo_fee.Busi_Src_Type as 销售渠道 ,                    "
		+"endo_fee.Agt_Code as 中介机构代码 ,                     "
		+"endo_fee.Salesman_No as 营销员代码 ,                    "
		+"endo_fee.Speciman_No as 银保专管员代码 ,                "
		+"endo_fee.Staff_No as 员工代码 ,                         "
		+"endo_fee.POS_Type as 批改类型 ,                         "
		+"endo_fee.Amt_Type as 收付类型 ,                         "
		+"endo_fee.Sum_Ins as 批改后保额 ,                        "
		+"endo_fee.Proc_Date as [发生日期（批改日期）] ,          "
		+"endo_fee.Edr_Eff_Date as 批改生效日期 ,                 "
		+"endo_fee.CurNo as 币种 ,                                "
		+"endo_fee.Payab_Date as [应付/应收日期] ,                "
		+"endo_fee.Amt_payab_cnvt as [应付/应收金额折合人民币] ,  "
		+"endo_fee.Endo_Vou_Code as [应付/应收财务凭证号] ,       "
		+"endo_fee.Gained_pay_Date as [实付/实收日期] ,           "
		+"endo_fee.Amt_Incured_cnvt as [实付/实收金额折合人民币] ,"
		+"endo_fee.ActEndo_Vou_Code as [实付/实收财务凭证号] ,    "
		+"endo_fee.CollectPay_Way_Code as 收付款方式 ,            "
		+"endo_fee.Bank_account_code as 对方银行账号 ,            "
		+"endo_fee.Check_code as 对方结算号 ,                     "
		+"endo_fee.Surrender_cause as 退保原因 ,                  "
		+"endo_fee.Endo_Content as 批文                           " 
		+" from %1$s Endo_Fee where Staff_No is not null and Staff_No != '' and not exists( select 1 from Staff_Info where ENDO_FEE.Staff_No = STAFF_INFO.STAFF_NO)";
	
	//JHWZ007	批单信息表- 投保日期、保单生效日期、发生日期（批改日期）、批单生效日期为空
	static String SQL_JHWZ007_c = "select "
		+"endo_fee.EntNo as 批单号 ,                                 "
		+"endo_fee.EntPrintCode as 批单印刷流水号 ,                  "
		+"endo_fee.GP_Type as 个团标志 ,                             "
		+"endo_fee.Branch_Code as 分支机构代码 ,                     "
		+"endo_fee.PolNo as 保单号 ,                                 "
		+"endo_fee.CertNo as 分单号 ,                                "
		+"endo_fee.Prpl_No as 投保单号码 ,                           "
		+"endo_fee.App_Name as 投保人名称 ,                          "
		+"endo_fee.App_Idcard_Type as 投保人证件类型 ,               "
		+"endo_fee.App_Idcard_No as 投保人证件号码 ,                 "
		+"endo_fee.InsName as 被保人名称 ,                           "
		+"endo_fee.Ins_Idcard_Type as 被保险人证件类型 ,             "
		+"endo_fee.Ins_Idcard_No as 被保险人证件号码 ,               "
		+"endo_fee.App_Date as 投保日期 ,                            "
		+"endo_fee.Pol_Eff_Date as 保单生效日期 ,                    "
		+"endo_fee.BRNo as 险种序号 ,                                "
		+"endo_fee.Plan_Code as 险种代码 ,                           "
		+"endo_fee.Period as [保险期限(月)] ,                        "
		+"endo_fee.Prem_Type as 缴费类型 ,                           "
		+"endo_fee.Prem_Term as [缴费期限(月)] ,                     "
		+"endo_fee.Busi_Src_Type as 销售渠道 ,                       "
		+"endo_fee.Agt_Code as 中介机构代码 ,                        "
		+"endo_fee.Salesman_No as 营销员代码 ,                       "
		+"endo_fee.Speciman_No as 银保专管员代码 ,                   "
		+"endo_fee.Staff_No as 员工代码 ,                            "
		+"endo_fee.POS_Type as 批改类型 ,                            "
		+"endo_fee.Amt_Type as 收付类型 ,                            "
		+"endo_fee.Sum_Ins as 批改后保额 ,                           "
		+"endo_fee.Proc_Date as [发生日期（批改日期）] ,             "
		+"endo_fee.Edr_Eff_Date as 批改生效日期 ,                    "
		+"endo_fee.CurNo as 币种 ,                                   "
		+"endo_fee.Payab_Date as [应付/应收日期] ,                   "
		+"endo_fee.Amt_payab_cnvt as [应付/应收金额折合人民币] ,     "
		+"endo_fee.Endo_Vou_Code as [应付/应收财务凭证号] ,          "
		+"endo_fee.Gained_pay_Date as [实付/实收日期] ,              "
		+"endo_fee.Amt_Incured_cnvt as [实付/实收金额折合人民币] ,   "
		+"endo_fee.ActEndo_Vou_Code as [实付/实收财务凭证号] ,       "
		+"endo_fee.CollectPay_Way_Code as 收付款方式 ,               "
		+"endo_fee.Bank_account_code as 对方银行账号 ,               "
		+"endo_fee.Check_code as 对方结算号 ,                        "
		+"endo_fee.Surrender_cause as 退保原因 ,                     "
		+"endo_fee.Endo_Content as 批文                              "
		+" from  %1$s Endo_Fee where App_Date is null or Pol_Eff_Date is null or Edr_Eff_Date is null or Proc_Date is null ";
	//JHWZ007	批单信息表- 批文、投保人证件号码、被保人名称为空 89316
	static String SQL_JHWZ007_d = "select "
		+"endo_fee.EntNo as 批单号 ,                                "
		+"endo_fee.EntPrintCode as 批单印刷流水号 ,                 "
		+"endo_fee.GP_Type as 个团标志 ,                            "
		+"endo_fee.Branch_Code as 分支机构代码 ,                    "
		+"endo_fee.PolNo as 保单号 ,                                "
		+"endo_fee.CertNo as 分单号 ,                               "
		+"endo_fee.Prpl_No as 投保单号码 ,                          "
		+"endo_fee.App_Name as 投保人名称 ,                         "
		+"endo_fee.App_Idcard_Type as 投保人证件类型 ,              "
		+"endo_fee.App_Idcard_No as 投保人证件号码 ,                "
		+"endo_fee.InsName as 被保人名称 ,                          "
		+"endo_fee.Ins_Idcard_Type as 被保险人证件类型 ,            "
		+"endo_fee.Ins_Idcard_No as 被保险人证件号码 ,              "
		+"endo_fee.App_Date as 投保日期 ,                           "
		+"endo_fee.Pol_Eff_Date as 保单生效日期 ,                   "
		+"endo_fee.BRNo as 险种序号 ,                               "
		+"endo_fee.Plan_Code as 险种代码 ,                          "
		+"endo_fee.Period as [保险期限(月)] ,                       "
		+"endo_fee.Prem_Type as 缴费类型 ,                          "
		+"endo_fee.Prem_Term as [缴费期限(月)] ,                    "
		+"endo_fee.Busi_Src_Type as 销售渠道 ,                      "
		+"endo_fee.Agt_Code as 中介机构代码 ,                       "
		+"endo_fee.Salesman_No as 营销员代码 ,                      "
		+"endo_fee.Speciman_No as 银保专管员代码 ,                  "
		+"endo_fee.Staff_No as 员工代码 ,                           "
		+"endo_fee.POS_Type as 批改类型 ,                           "
		+"endo_fee.Amt_Type as 收付类型 ,                           "
		+"endo_fee.Sum_Ins as 批改后保额 ,                          "
		+"endo_fee.Proc_Date as [发生日期（批改日期）] ,            "
		+"endo_fee.Edr_Eff_Date as 批改生效日期 ,                   "
		+"endo_fee.CurNo as 币种 ,                                  "
		+"endo_fee.Payab_Date as [应付/应收日期] ,                  "
		+"endo_fee.Amt_payab_cnvt as [应付/应收金额折合人民币] ,    "
		+"endo_fee.Endo_Vou_Code as [应付/应收财务凭证号] ,         "
		+"endo_fee.Gained_pay_Date as [实付/实收日期] ,             "
		+"endo_fee.Amt_Incured_cnvt as [实付/实收金额折合人民币] ,  "
		+"endo_fee.ActEndo_Vou_Code as [实付/实收财务凭证号] ,      "
		+"endo_fee.CollectPay_Way_Code as 收付款方式 ,              "
		+"endo_fee.Bank_account_code as 对方银行账号 ,              "
		+"endo_fee.Check_code as 对方结算号 ,                       "
		+"endo_fee.Surrender_cause as 退保原因 ,                    "
		+"endo_fee.Endo_Content as 批文                             "
		+" from %1$s Endo_Fee where Endo_Content is null or App_Idcard_No is null or InsName is null ";
	//JHWZ007	批单信息表- 非现金收款方式的对方银行账号为空	89316	
	static String SQL_JHWZ007_e = "select "
		+"endo_fee.EntNo as 批单号 ,                                "
		+"endo_fee.EntPrintCode as 批单印刷流水号 ,                 "
		+"endo_fee.GP_Type as 个团标志 ,                            "
		+"endo_fee.Branch_Code as 分支机构代码 ,                    "
		+"endo_fee.PolNo as 保单号 ,                                "
		+"endo_fee.CertNo as 分单号 ,                               "
		+"endo_fee.Prpl_No as 投保单号码 ,                          "
		+"endo_fee.App_Name as 投保人名称 ,                         "
		+"endo_fee.App_Idcard_Type as 投保人证件类型 ,              "
		+"endo_fee.App_Idcard_No as 投保人证件号码 ,                "
		+"endo_fee.InsName as 被保人名称 ,                          "
		+"endo_fee.Ins_Idcard_Type as 被保险人证件类型 ,            "
		+"endo_fee.Ins_Idcard_No as 被保险人证件号码 ,              "
		+"endo_fee.App_Date as 投保日期 ,                           "
		+"endo_fee.Pol_Eff_Date as 保单生效日期 ,                   "
		+"endo_fee.BRNo as 险种序号 ,                               "
		+"endo_fee.Plan_Code as 险种代码 ,                          "
		+"endo_fee.Period as [保险期限(月)] ,                       "
		+"endo_fee.Prem_Type as 缴费类型 ,                          "
		+"endo_fee.Prem_Term as [缴费期限(月)] ,                    "
		+"endo_fee.Busi_Src_Type as 销售渠道 ,                      "
		+"endo_fee.Agt_Code as 中介机构代码 ,                       "
		+"endo_fee.Salesman_No as 营销员代码 ,                      "
		+"endo_fee.Speciman_No as 银保专管员代码 ,                  "
		+"endo_fee.Staff_No as 员工代码 ,                           "
		+"endo_fee.POS_Type as 批改类型 ,                           "
		+"endo_fee.Amt_Type as 收付类型 ,                           "
		+"endo_fee.Sum_Ins as 批改后保额 ,                          "
		+"endo_fee.Proc_Date as [发生日期（批改日期）] ,            "
		+"endo_fee.Edr_Eff_Date as 批改生效日期 ,                   "
		+"endo_fee.CurNo as 币种 ,                                  "
		+"endo_fee.Payab_Date as [应付/应收日期] ,                  "
		+"endo_fee.Amt_payab_cnvt as [应付/应收金额折合人民币] ,    "
		+"endo_fee.Endo_Vou_Code as [应付/应收财务凭证号] ,         "
		+"endo_fee.Gained_pay_Date as [实付/实收日期] ,             "
		+"endo_fee.Amt_Incured_cnvt as [实付/实收金额折合人民币] ,  "
		+"endo_fee.ActEndo_Vou_Code as [实付/实收财务凭证号] ,      "
		+"endo_fee.CollectPay_Way_Code as 收付款方式 ,              "
		+"endo_fee.Bank_account_code as 对方银行账号 ,              "
		+"endo_fee.Check_code as 对方结算号 ,                       "
		+"endo_fee.Surrender_cause as 退保原因 ,                    "
		+"endo_fee.Endo_Content as 批文                             "
		+" from %1$s Endo_Fee where (Bank_account_code is null or Bank_account_code='') and CollectPay_Way_Code != '1'";
	
	//JHWZ010	赔案信息表- 员工代码在员工信息表中不存在	6	
	static String SQL_JHWZ010_a = "select "
		+"claim_main.CaseNo as 立案号 ,                                         "
		+"claim_main.PaySeq as 支付次数 ,                                       "
		+"claim_main.PolNo as 保单号 ,                                          "
		+"claim_main.CertNo as 分单号 ,                                         "
		+"claim_main.ListNo as 清单号 ,                                         "
		+"claim_main.Branch_Code as 分支机构代码 ,                              "
		+"claim_main.GP_Type as 个团标志 ,                                      "
		+"claim_main.Prpl_No as 投保单号码 ,                                    "
		+"claim_main.App_name as 投保人名称 ,                                   "
		+"claim_main.App_Idcard_Type as 投保人证件类型 ,                        "
		+"claim_main.App_Idcard_No as 投保人证件号码 ,                          "
		+"claim_main.Insurd_name as 被保险人名称 ,                              "
		+"claim_main.Ins_Idcard_Type as 被保险人证件类型 ,                      "
		+"claim_main.Ins_Idcard_No as 被保险人证件号码 ,                        "
		+"claim_main.BeneName as 受益人姓名 ,                                   "
		+"claim_main.App_Date as 投保日期 ,                                     "
		+"claim_main.Pol_Eff_Date as 保单生效日期 ,                             "
		+"claim_main.BrNo as 险种序号 ,                                         "
		+"claim_main.Plan_Code as 险种代码 ,                                    "
		+"claim_main.Busi_Src_Type as 销售渠道 ,                                "
		+"claim_main.Agt_Code as 中介机构代码 ,                                 "
		+"claim_main.Salesman_No as 营销员代码 ,                                "
		+"claim_main.Speciman_No as 银保专管员代码 ,                            "
		+"claim_main.Staff_No as 员工代码 ,                                     "
		+"claim_main.c_rpt_no as 报案号 ,                                       "
		+"claim_main.Docu_Date as 报案日期 ,                                    "
		+"claim_main.Case_Date as 立案日期 ,                                    "
		+"claim_main.Reckon_Loss as 估损金额 ,                                  "
		+"claim_main.Check_Date as 核赔通过日期 ,                               "
		+"claim_main.End_Date as 结案日期 ,                                     "
		+"claim_main.Paid_Name as 领取人姓名 ,                                  "
		+"claim_main.Paid_Idcard_Type as 领取人证件类型 ,                       "
		+"claim_main.Paid_Idcard_No as 领取人证件号码 ,                         "
		+"claim_main.Amt_Type as 收付类型 ,                                     "
		+"claim_main.Gained_Date as [付讫日期（实付日期）] ,                    "
		+"claim_main.CurNo as 币种 ,                                            "
		+"claim_main.Payab_Amt_cnvt as [核赔给付额/应付赔款支出折合人民币] ,    "
		+"claim_main.Payab_Vou_Code as 应付赔款凭证号 ,                         "
		+"claim_main.Paid_Amt_cnvt as 实付赔款支出折合人民币 ,                  "
		+"claim_main.Paid_Vou_Code as 实付赔款凭证号 ,                          "
		+"claim_main.CollectPay_Code as 付款方式 ,                              "
		+"claim_main.Pay_account_code as 对方银行账号 ,                         "
		+"claim_main.Pay_check_no as 结算号 ,                                   "
		+"claim_main.Reje_amt as 拒赔金额 ,                                     "
		+"claim_main.Check_Fee as 查勘费用 ,                                    "
		+"claim_main.accommodate_cause as 通融赔付原因 ,                        "
		+"claim_main.N_Share_Sum as 摊回比例分保赔款 ,                          "
		+"claim_main.N_Share_Sum_n as 摊回非比例分保赔款                        " 
		+" from %1$s claim_main where Staff_No is not null and Staff_No != ''  and not exists( select 1 from Staff_Info where CLAIM_MAIN.Staff_No = STAFF_INFO.STAFF_NO)";
		
	//JHWZ010	赔案信息表- 到账日期在取数区间内的实付赔款凭证号，在财务凭证表中不存在	6
	static String SQL_JHWZ010_b = "select "
	+"claim_main.CaseNo as 立案号 ,                                        "                                                                                                                                                                                                                   
	+"claim_main.PaySeq as 支付次数 ,                                      "                                                                                                                                                                                                                   
	+"claim_main.PolNo as 保单号 ,                                         "                                                                                                                                                                                                                   
	+"claim_main.CertNo as 分单号 ,                                        "                                                                                                                                                                                                                   
	+"claim_main.ListNo as 清单号 ,                                        "                                                                                                                                                                                                                   
	+"claim_main.Branch_Code as 分支机构代码 ,                             "                                                                                                                                                                                                                   
	+"claim_main.GP_Type as 个团标志 ,                                     "                                                                                                                                                                                                                   
	+"claim_main.Prpl_No as 投保单号码 ,                                   "                                                                                                                                                                                                                   
	+"claim_main.App_name as 投保人名称 ,                                  "                                                                                                                                                                                                                   
	+"claim_main.App_Idcard_Type as 投保人证件类型 ,                       "                                                                                                                                                                                                                   
	+"claim_main.App_Idcard_No as 投保人证件号码 ,                         "                                                                                                                                                                                                                   
	+"claim_main.Insurd_name as 被保险人名称 ,                             "                                                                                                                                                                                                                   
	+"claim_main.Ins_Idcard_Type as 被保险人证件类型 ,                     "                                                                                                                                                                                                                   
	+"claim_main.Ins_Idcard_No as 被保险人证件号码 ,                       "                                                                                                                                                                                                                   
	+"claim_main.BeneName as 受益人姓名 ,                                  "                                                                                                                                                                                                                   
	+"claim_main.App_Date as 投保日期 ,                                    "                                                                                                                                                                                                                   
	+"claim_main.Pol_Eff_Date as 保单生效日期 ,                            "                                                                                                                                                                                                                   
	+"claim_main.BrNo as 险种序号 ,                                        "                                                                                                                                                                                                                   
	+"claim_main.Plan_Code as 险种代码 ,                                   "                                                                                                                                                                                                                   
	+"claim_main.Busi_Src_Type as 销售渠道 ,                               "                                                                                                                                                                                                                   
	+"claim_main.Agt_Code as 中介机构代码 ,                                "                                                                                                                                                                                                                   
	+"claim_main.Salesman_No as 营销员代码 ,                               "                                                                                                                                                                                                                   
	+"claim_main.Speciman_No as 银保专管员代码 ,                           "                                                                                                                                                                                                                   
	+"claim_main.Staff_No as 员工代码 ,                                    "                                                                                                                                                                                                                   
	+"claim_main.c_rpt_no as 报案号 ,                                      "                                                                                                                                                                                                                   
	+"claim_main.Docu_Date as 报案日期 ,                                   "                                                                                                                                                                                                                   
	+"claim_main.Case_Date as 立案日期 ,                                   "                                                                                                                                                                                                                   
	+"claim_main.Reckon_Loss as 估损金额 ,                                 "                                                                                                                                                                                                                   
	+"claim_main.Check_Date as 核赔通过日期 ,                              "                                                                                                                                                                                                                   
	+"claim_main.End_Date as 结案日期 ,                                    "                                                                                                                                                                                                                   
	+"claim_main.Paid_Name as 领取人姓名 ,                                 "                                                                                                                                                                                                                   
	+"claim_main.Paid_Idcard_Type as 领取人证件类型 ,                      "                                                                                                                                                                                                                   
	+"claim_main.Paid_Idcard_No as 领取人证件号码 ,                        "                                                                                                                                                                                                                   
	+"claim_main.Amt_Type as 收付类型 ,                                    "                                                                                                                                                                                                                   
	+"claim_main.Gained_Date as [付讫日期（实付日期）] ,                   "                                                                                                                                                                                                                   
	+"claim_main.CurNo as 币种 ,                                           "                                                                                                                                                                                                                   
	+"claim_main.Payab_Amt_cnvt as [核赔给付额/应付赔款支出折合人民币] ,   "                                                                                                                                                                                                                   
	+"claim_main.Payab_Vou_Code as 应付赔款凭证号 ,                        "                                                                                                                                                                                                                   
	+"claim_main.Paid_Amt_cnvt as 实付赔款支出折合人民币 ,                 "                                                                                                                                                                                                                   
	+"claim_main.Paid_Vou_Code as 实付赔款凭证号 ,                         "                                                                                                                                                                                                                   
	+"claim_main.CollectPay_Code as 付款方式 ,                             "                                                                                                                                                                                                                   
	+"claim_main.Pay_account_code as 对方银行账号 ,                        "                                                                                                                                                                                                                   
	+"claim_main.Pay_check_no as 结算号 ,                                  "                                                                                                                                                                                                                   
	+"claim_main.Reje_amt as 拒赔金额 ,                                    "                                                                                                                                                                                                                   
	+"claim_main.Check_Fee as 查勘费用 ,                                   "                                                                                                                                                                                                                   
	+"claim_main.accommodate_cause as 通融赔付原因 ,                       "                                                                                                                                                                                                                   
	+"claim_main.N_Share_Sum as 摊回比例分保赔款 ,                         "                                                                                                                                                                                                                   
	+"claim_main.N_Share_Sum_n as 摊回非比例分保赔款                       "                                                                                                                                                                                                                   
	+" from %1$s claim_main where Gained_Date between '2012-01-01'and '2013-12-31' and Paid_Amt_cnvt is not null and Paid_Amt_cnvt != 0 and not exists( select 1  from Voucher_Info where CLAIM_MAIN.Paid_Vou_Code = VOUCHER_INFO.VOUCHER_CODE)";
	//JHWZ010	赔案信息表- 销售渠道为直销时，员工代码为空
	static String SQL_JHWZ010_c = "select "
		+"claim_main.CaseNo as 立案号 ,                                       "
		+"claim_main.PaySeq as 支付次数 ,                                     "
		+"claim_main.PolNo as 保单号 ,                                        "
		+"claim_main.CertNo as 分单号 ,                                       "
		+"claim_main.ListNo as 清单号 ,                                       "
		+"claim_main.Branch_Code as 分支机构代码 ,                            "
		+"claim_main.GP_Type as 个团标志 ,                                    "
		+"claim_main.Prpl_No as 投保单号码 ,                                  "
		+"claim_main.App_name as 投保人名称 ,                                 "
		+"claim_main.App_Idcard_Type as 投保人证件类型 ,                      "
		+"claim_main.App_Idcard_No as 投保人证件号码 ,                        "
		+"claim_main.Insurd_name as 被保险人名称 ,                            "
		+"claim_main.Ins_Idcard_Type as 被保险人证件类型 ,                    "
		+"claim_main.Ins_Idcard_No as 被保险人证件号码 ,                      "
		+"claim_main.BeneName as 受益人姓名 ,                                 "
		+"claim_main.App_Date as 投保日期 ,                                   "
		+"claim_main.Pol_Eff_Date as 保单生效日期 ,                           "
		+"claim_main.BrNo as 险种序号 ,                                       "
		+"claim_main.Plan_Code as 险种代码 ,                                  "
		+"claim_main.Busi_Src_Type as 销售渠道 ,                              "
		+"claim_main.Agt_Code as 中介机构代码 ,                               "
		+"claim_main.Salesman_No as 营销员代码 ,                              "
		+"claim_main.Speciman_No as 银保专管员代码 ,                          "
		+"claim_main.Staff_No as 员工代码 ,                                   "
		+"claim_main.c_rpt_no as 报案号 ,                                     "
		+"claim_main.Docu_Date as 报案日期 ,                                  "
		+"claim_main.Case_Date as 立案日期 ,                                  "
		+"claim_main.Reckon_Loss as 估损金额 ,                                "
		+"claim_main.Check_Date as 核赔通过日期 ,                             "
		+"claim_main.End_Date as 结案日期 ,                                   "
		+"claim_main.Paid_Name as 领取人姓名 ,                                "
		+"claim_main.Paid_Idcard_Type as 领取人证件类型 ,                     "
		+"claim_main.Paid_Idcard_No as 领取人证件号码 ,                       "
		+"claim_main.Amt_Type as 收付类型 ,                                   "
		+"claim_main.Gained_Date as [付讫日期（实付日期）] ,                  "
		+"claim_main.CurNo as 币种 ,                                          "
		+"claim_main.Payab_Amt_cnvt as [核赔给付额/应付赔款支出折合人民币] ,  "
		+"claim_main.Payab_Vou_Code as 应付赔款凭证号 ,                       "
		+"claim_main.Paid_Amt_cnvt as 实付赔款支出折合人民币 ,                "
		+"claim_main.Paid_Vou_Code as 实付赔款凭证号 ,                        "
		+"claim_main.CollectPay_Code as 付款方式 ,                            "
		+"claim_main.Pay_account_code as 对方银行账号 ,                       "
		+"claim_main.Pay_check_no as 结算号 ,                                 "
		+"claim_main.Reje_amt as 拒赔金额 ,                                   "
		+"claim_main.Check_Fee as 查勘费用 ,                                  "
		+"claim_main.accommodate_cause as 通融赔付原因 ,                      "
		+"claim_main.N_Share_Sum as 摊回比例分保赔款 ,                        "
		+"claim_main.N_Share_Sum_n as 摊回非比例分保赔款                      "
		+" from %1$s claim_main where (Staff_No is null or Staff_No='') and Busi_Src_Type like ('1%%')";
	//JHWZ010	赔案信息表- 销售渠道为银行邮政代理时，银保专管员代码为空
	static String SQL_JHWZ010_d = "select "
		+"claim_main.CaseNo as 立案号 ,                                         "
		+"claim_main.PaySeq as 支付次数 ,                                       "
		+"claim_main.PolNo as 保单号 ,                                          "
		+"claim_main.CertNo as 分单号 ,                                         "
		+"claim_main.ListNo as 清单号 ,                                         "
		+"claim_main.Branch_Code as 分支机构代码 ,                              "
		+"claim_main.GP_Type as 个团标志 ,                                      "
		+"claim_main.Prpl_No as 投保单号码 ,                                    "
		+"claim_main.App_name as 投保人名称 ,                                   "
		+"claim_main.App_Idcard_Type as 投保人证件类型 ,                        "
		+"claim_main.App_Idcard_No as 投保人证件号码 ,                          "
		+"claim_main.Insurd_name as 被保险人名称 ,                              "
		+"claim_main.Ins_Idcard_Type as 被保险人证件类型 ,                      "
		+"claim_main.Ins_Idcard_No as 被保险人证件号码 ,                        "
		+"claim_main.BeneName as 受益人姓名 ,                                   "
		+"claim_main.App_Date as 投保日期 ,                                     "
		+"claim_main.Pol_Eff_Date as 保单生效日期 ,                             "
		+"claim_main.BrNo as 险种序号 ,                                         "
		+"claim_main.Plan_Code as 险种代码 ,                                    "
		+"claim_main.Busi_Src_Type as 销售渠道 ,                                "
		+"claim_main.Agt_Code as 中介机构代码 ,                                 "
		+"claim_main.Salesman_No as 营销员代码 ,                                "
		+"claim_main.Speciman_No as 银保专管员代码 ,                            "
		+"claim_main.Staff_No as 员工代码 ,                                     "
		+"claim_main.c_rpt_no as 报案号 ,                                       "
		+"claim_main.Docu_Date as 报案日期 ,                                    "
		+"claim_main.Case_Date as 立案日期 ,                                    "
		+"claim_main.Reckon_Loss as 估损金额 ,                                  "
		+"claim_main.Check_Date as 核赔通过日期 ,                               "
		+"claim_main.End_Date as 结案日期 ,                                     "
		+"claim_main.Paid_Name as 领取人姓名 ,                                  "
		+"claim_main.Paid_Idcard_Type as 领取人证件类型 ,                       "
		+"claim_main.Paid_Idcard_No as 领取人证件号码 ,                         "
		+"claim_main.Amt_Type as 收付类型 ,                                     "
		+"claim_main.Gained_Date as [付讫日期（实付日期）] ,                    "
		+"claim_main.CurNo as 币种 ,                                            "
		+"claim_main.Payab_Amt_cnvt as [核赔给付额/应付赔款支出折合人民币] ,    "
		+"claim_main.Payab_Vou_Code as 应付赔款凭证号 ,                         "
		+"claim_main.Paid_Amt_cnvt as 实付赔款支出折合人民币 ,                  "
		+"claim_main.Paid_Vou_Code as 实付赔款凭证号 ,                          "
		+"claim_main.CollectPay_Code as 付款方式 ,                              "
		+"claim_main.Pay_account_code as 对方银行账号 ,                         "
		+"claim_main.Pay_check_no as 结算号 ,                                   "
		+"claim_main.Reje_amt as 拒赔金额 ,                                     "
		+"claim_main.Check_Fee as 查勘费用 ,                                    "
		+"claim_main.accommodate_cause as 通融赔付原因 ,                        "
		+"claim_main.N_Share_Sum as 摊回比例分保赔款 ,                          "
		+"claim_main.N_Share_Sum_n as 摊回非比例分保赔款                        "
		+" from %1$s claim_main where Gained_Date between '2012-01-01'and '2013-12-31' and Payab_Amt_cnvt is not null and Payab_Amt_cnvt != 0 and not exists( select 1 from Voucher_Info where CLAIM_MAIN.Payab_Vou_Code = VOUCHER_INFO.VOUCHER_CODE)";
	
	//JHWZ010	赔案信息表- 到账日期在取数区间内的应付赔款凭证号，在财务凭证表中不存在	6
	static String SQL_JHWZ010_e = "select "
		+"claim_main.CaseNo as 立案号 ,                                       "
		+"claim_main.PaySeq as 支付次数 ,                                     "
		+"claim_main.PolNo as 保单号 ,                                        "
		+"claim_main.CertNo as 分单号 ,                                       "
		+"claim_main.ListNo as 清单号 ,                                       "
		+"claim_main.Branch_Code as 分支机构代码 ,                            "
		+"claim_main.GP_Type as 个团标志 ,                                    "
		+"claim_main.Prpl_No as 投保单号码 ,                                  "
		+"claim_main.App_name as 投保人名称 ,                                 "
		+"claim_main.App_Idcard_Type as 投保人证件类型 ,                      "
		+"claim_main.App_Idcard_No as 投保人证件号码 ,                        "
		+"claim_main.Insurd_name as 被保险人名称 ,                            "
		+"claim_main.Ins_Idcard_Type as 被保险人证件类型 ,                    "
		+"claim_main.Ins_Idcard_No as 被保险人证件号码 ,                      "
		+"claim_main.BeneName as 受益人姓名 ,                                 "
		+"claim_main.App_Date as 投保日期 ,                                   "
		+"claim_main.Pol_Eff_Date as 保单生效日期 ,                           "
		+"claim_main.BrNo as 险种序号 ,                                       "
		+"claim_main.Plan_Code as 险种代码 ,                                  "
		+"claim_main.Busi_Src_Type as 销售渠道 ,                              "
		+"claim_main.Agt_Code as 中介机构代码 ,                               "
		+"claim_main.Salesman_No as 营销员代码 ,                              "
		+"claim_main.Speciman_No as 银保专管员代码 ,                          "
		+"claim_main.Staff_No as 员工代码 ,                                   "
		+"claim_main.c_rpt_no as 报案号 ,                                     "
		+"claim_main.Docu_Date as 报案日期 ,                                  "
		+"claim_main.Case_Date as 立案日期 ,                                  "
		+"claim_main.Reckon_Loss as 估损金额 ,                                "
		+"claim_main.Check_Date as 核赔通过日期 ,                             "
		+"claim_main.End_Date as 结案日期 ,                                   "
		+"claim_main.Paid_Name as 领取人姓名 ,                                "
		+"claim_main.Paid_Idcard_Type as 领取人证件类型 ,                     "
		+"claim_main.Paid_Idcard_No as 领取人证件号码 ,                       "
		+"claim_main.Amt_Type as 收付类型 ,                                   "
		+"claim_main.Gained_Date as [付讫日期（实付日期）] ,                  "
		+"claim_main.CurNo as 币种 ,                                          "
		+"claim_main.Payab_Amt_cnvt as [核赔给付额/应付赔款支出折合人民币] ,  "
		+"claim_main.Payab_Vou_Code as 应付赔款凭证号 ,                       "
		+"claim_main.Paid_Amt_cnvt as 实付赔款支出折合人民币 ,                "
		+"claim_main.Paid_Vou_Code as 实付赔款凭证号 ,                        "
		+"claim_main.CollectPay_Code as 付款方式 ,                            "
		+"claim_main.Pay_account_code as 对方银行账号 ,                       "
		+"claim_main.Pay_check_no as 结算号 ,                                 "
		+"claim_main.Reje_amt as 拒赔金额 ,                                   "
		+"claim_main.Check_Fee as 查勘费用 ,                                  "
		+"claim_main.accommodate_cause as 通融赔付原因 ,                      "
		+"claim_main.N_Share_Sum as 摊回比例分保赔款 ,                        "
		+"claim_main.N_Share_Sum_n as 摊回非比例分保赔款                      "
		+" from %1$s claim_main where (Speciman_No is null or Speciman_No='') and Busi_Src_Type = '221' ";
	//JHWZ010	赔案信息表- 非现金收款方式的对方银行账号为空
	static String SQL_JHWZ010_f = "select "
		+"claim_main.CaseNo as 立案号 ,                                         "
		+"claim_main.PaySeq as 支付次数 ,                                       "
		+"claim_main.PolNo as 保单号 ,                                          "
		+"claim_main.CertNo as 分单号 ,                                         "
		+"claim_main.ListNo as 清单号 ,                                         "
		+"claim_main.Branch_Code as 分支机构代码 ,                              "
		+"claim_main.GP_Type as 个团标志 ,                                      "
		+"claim_main.Prpl_No as 投保单号码 ,                                    "
		+"claim_main.App_name as 投保人名称 ,                                   "
		+"claim_main.App_Idcard_Type as 投保人证件类型 ,                        "
		+"claim_main.App_Idcard_No as 投保人证件号码 ,                          "
		+"claim_main.Insurd_name as 被保险人名称 ,                              "
		+"claim_main.Ins_Idcard_Type as 被保险人证件类型 ,                      "
		+"claim_main.Ins_Idcard_No as 被保险人证件号码 ,                        "
		+"claim_main.BeneName as 受益人姓名 ,                                   "
		+"claim_main.App_Date as 投保日期 ,                                     "
		+"claim_main.Pol_Eff_Date as 保单生效日期 ,                             "
		+"claim_main.BrNo as 险种序号 ,                                         "
		+"claim_main.Plan_Code as 险种代码 ,                                    "
		+"claim_main.Busi_Src_Type as 销售渠道 ,                                "
		+"claim_main.Agt_Code as 中介机构代码 ,                                 "
		+"claim_main.Salesman_No as 营销员代码 ,                                "
		+"claim_main.Speciman_No as 银保专管员代码 ,                            "
		+"claim_main.Staff_No as 员工代码 ,                                     "
		+"claim_main.c_rpt_no as 报案号 ,                                       "
		+"claim_main.Docu_Date as 报案日期 ,                                    "
		+"claim_main.Case_Date as 立案日期 ,                                    "
		+"claim_main.Reckon_Loss as 估损金额 ,                                  "
		+"claim_main.Check_Date as 核赔通过日期 ,                               "
		+"claim_main.End_Date as 结案日期 ,                                     "
		+"claim_main.Paid_Name as 领取人姓名 ,                                  "
		+"claim_main.Paid_Idcard_Type as 领取人证件类型 ,                       "
		+"claim_main.Paid_Idcard_No as 领取人证件号码 ,                         "
		+"claim_main.Amt_Type as 收付类型 ,                                     "
		+"claim_main.Gained_Date as [付讫日期（实付日期）] ,                    "
		+"claim_main.CurNo as 币种 ,                                            "
		+"claim_main.Payab_Amt_cnvt as [核赔给付额/应付赔款支出折合人民币] ,    "
		+"claim_main.Payab_Vou_Code as 应付赔款凭证号 ,                         "
		+"claim_main.Paid_Amt_cnvt as 实付赔款支出折合人民币 ,                  "
		+"claim_main.Paid_Vou_Code as 实付赔款凭证号 ,                          "
		+"claim_main.CollectPay_Code as 付款方式 ,                              "
		+"claim_main.Pay_account_code as 对方银行账号 ,                         "
		+"claim_main.Pay_check_no as 结算号 ,                                   "
		+"claim_main.Reje_amt as 拒赔金额 ,                                     "
		+"claim_main.Check_Fee as 查勘费用 ,                                    "
		+"claim_main.accommodate_cause as 通融赔付原因 ,                        "
		+"claim_main.N_Share_Sum as 摊回比例分保赔款 ,                          "
		+"claim_main.N_Share_Sum_n as 摊回非比例分保赔款                        "
		+" from %1$s claim_main where (Pay_account_code is null or Pay_account_code='') and CollectPay_Code != '1'";
	//static String SQL_JHWZ007_c = "select "
	//static String SQL_JHWZ007_c = "select "
	//static String SQL_JHWZ007_c = "select "
	//static String SQL_JHWZ007_c = "select "
	static DateFormat dateXlsFormart = new SimpleDateFormat("yyyy/MM/dd");
	static DateFormat dateDbFormart = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void main(String[] args) throws Exception{
		Connection cn = getCIRCConnAndInitDriver();
		try {
			generateReport(cn);
			
			//CGHB
			//importByEXL(cn,"C:\\doc\\CIRC\\数据源\\CGHB\\CGHB_Prem_Info.xls","CGHB_Prem_info", 52, 1, 183, new int[]{12,21,24});
			
			//财务
			//importByEXL(cn,"C:\\doc\\CIRC\\数据源\\基本表\\Branch_Info分支机构信息表(updated on 30 Aug).xls","Branch_Info", 11, 1, 14, null);
			//importByEXL(cn,"C:\\doc\\CIRC\\数据源\\Banc_Speci_Info银保专管员信息表（update 23 Aug）.xls","Banc_Speci_Info", 15, 1, 815, null);
			//importByEXL(cn,"C:\\doc\\CIRC\\数据源\\基本表\\Staff info员工信息表(4 Sep).xls","Staff_Info", 13, 10273, 13083, null);
			//importByEXL(cn,"C:\\doc\\CIRC\\数据源\\基本表\\agt_code中介机构信息表（updated on 27 Aug）.xls","agt_code", 10, 1, 520, null);
			
			//IMPI
			//importByEXL(cn,"C:\\doc\\CIRC\\数据源\\IMPI\\Claim_Main.xls","IPMI_Claim_Main", 49, 1, 2, null);
			//importByEXL(cn,"C:\\doc\\CIRC\\数据源\\IMPI\\Claim_report.xls","IPMI_Claim_Report", 17, 1, 2, null);
			//importByEXL(cn,"C:\\doc\\CIRC\\数据源\\IMPI\\Endo_Fee.xls","IPMI_Endo_Fee", 42, 1, 17, null);
			//importByEXL(cn,"C:\\doc\\CIRC\\数据源\\IMPI\\Pol_Main.xls","IPMI_Pol_Main", 40, 1, 18, null);
			//importByEXL(cn,"C:\\doc\\CIRC\\数据源\\IMPI\\Prem_Info.xls","ipmi_prem_info", 52, 1, 45, null);
			 
			//修改LG分配比例,只是针对LG的分配比例调整
			//generateLGSqlByEXL("C:\\doc\\LG\\20120910修改LG分配比例\\P2FOR62Z 招商信诺IT（特殊）运行申请表(20120910修改CLG分配比例).xls", "Active_CampaignCode_Logic", 5, 1, 121, null);
			
			//BFS
			//generateLGSqlByEXL("C:\\doc\\CIRC\\2013\\数据源\\BFS\\Bill_Code_Info.xls", "Bill_Code_Info", 4, 1, 10, null);
			//generateLGSqlByEXL("C:\\doc\\CIRC\\2013\\数据源\\BFS\\Bill_Info.xls", "Bill_Info", 11, 1, 1585, new int[]{7,8,9});
			
			//IPMI
			//generateLGSqlByEXL("C:\\doc\\CIRC\\2013\\数据源\\IMPI\\Claim_Main.xls", "IPMI_Claim_Main", 49, 1, 7, new int[]{15,16,25,26,29,30,35});
			//generateLGSqlByEXL("C:\\doc\\CIRC\\2013\\数据源\\IMPI\\Claim_report.xls", "IPMI_Claim_report", 18, 1, 7, new int[]{16});
			//generateLGSqlByEXL("C:\\doc\\CIRC\\2013\\数据源\\IMPI\\Endo_Fee.xls", "IPMI_Endo_Fee", 42, 1, 9, new int[]{28});
			//generateLGSqlByEXL("C:\\doc\\CIRC\\2013\\数据源\\IMPI\\Pol_Main.xls", "IPMI_Pol_Main", 41, 1, 14, new int[]{23});
			//generateLGSqlByEXL("C:\\doc\\CIRC\\2013\\数据源\\IMPI\\Prem_Info.xls", "IPMI_Prem_Info", 53, 1, 9, new int[]{16});
			
			//generateLGSqlByEXL("C:\\doc\\CIRC\\2013\\数据源\\Plan_infor险种代码表-20120730.xls", "Plan_info", 15, 1, 797, new int[]{9,10,11});
			//generateLGSqlByEXL("C:\\doc\\CIRC\\2013\\数据源\\Voucher_Info财务凭证信息表2013－报送.xls", "Voucher_Info", 26, 1, 25, new int[]{0,7,8});
			//generateLGSqlByEXL("C:\\doc\\CIRC\\2013\\数据源\\Sub_Account_Code财务明细科目代码表2013－报送.xls", "Sub_Account_Code", 10, 1, 1277, new int[]{7,8});
			//generateLGSqlByEXL("C:\\doc\\CIRC\\2013\\数据源\\Gener_Account_Code财务总帐科目代码表2013－报送.xls", "Gener_Account_Code", 8, 1, 135, new int[]{5,6});
			
			//generateLGSqlByEXL("C:\\doc\\CIRC\\2013\\数据源\\Staff info员工信息表 on 26 Nov－TM-Liv.xls", "Staff_Info", 11, 1, 4025, new int[]{6,7});
			
		} finally {
			cn.close();
		}
		
	}
	
	public static void generateLGSqlByEXL (String path, String table, int col_count, int from, int rownum, int[] dateNums) throws IOException, BiffException, SQLException{
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
			cols[i] = sheet.getCell(i, 0).getContents().trim().replaceAll("\\'", "");
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
				boolean flag = false;
				if (dateNums!=null) {
					for (int i = 0; i < dateNums.length; i++) {
						if (column_num == dateNums[i]) {
							flag = true;
							break;
						}
					}
				}
				
				if (flag) {
					if (temp.trim().equals("0") || temp.trim().equals("") || temp.trim().equalsIgnoreCase("NULL")) {
						value_list.append("NULL,");
					} else {
						value_list.append( "'" + temp.trim().replaceAll("\\/", "-") + "',");
					}
				} else {
					if ("getDate()".equals(temp.trim())) {
						value_list.append(temp.trim()).append(",");
					} else {
						value_list.append( "N'" + temp.trim().replaceAll("\\'", " ") + "',");
					}
				}
				column_num++;
			}
			temp = sql + " ( "+value_list.toString().substring(0, value_list.length()-1) + " ); \n";
			result.append(temp);
			System.out.println(row_num+" ==> "+temp);
			
			row_num++;
		}
		FileUtil.writeFile(result.toString(),"c:\\"+table+".sql");
		wb.close();
	}
	
	public static void importByEXL (Connection cn, String path, String table,int col_count, int from, int rownum, int[] dateNums) throws IOException, BiffException, SQLException{
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
			cols[i] = sheet.getCell(i, 0).getContents().replaceAll("\\'", "");
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
					boolean flag = false;
					if (dateNums!=null) {
						for (int i = 0; i < dateNums.length; i++) {
							if (column_num == dateNums[i]) {
								flag = true;
								break;
							}
						}
					}
					
					if (flag) {
						if (temp.trim().equals("0") || temp.trim().equals("")) {
							value_list.append("NULL,");
						} else {
							value_list.append( "'" + temp.trim().replaceAll("\\/", "-") + "',");
						}
					} else {
						value_list.append( "N'" + temp.trim().replaceAll("\\'", " ") + "',");
					}
					column_num++;
				}
				temp = sql + " ( "+value_list.toString().substring(0, value_list.length()-1) + " ) \n";
				result.append(temp);
				System.out.println(row_num+" ==> "+temp);
				cn.createStatement().execute(temp);
			
			row_num++;
		}
		//FileUtil.writeFile(result.toString(),"c:\\"+table+".sql");
		wb.close();
	}

	private static void generateReport_BAK(Connection cn)
	throws FileNotFoundException, IOException, SQLException,
	BiffException, RowsExceededException, WriteException {
		String filenameTemplate = "C:\\0828\\%1$s.xls";
		
		String[] allCategoryList = new String[]{"CGHB_","CICAP_","IPMI_",""};
		String[] categoryList = new String[]{"CICAP_","IPMI_",""};
		
		//1	JHKJ002	缴费信息表-应缴日期、到账日期和保单生效日期都在规定时间区间以前
		String t1TableName = "%1$sprem_info";
		String t1FileName = "T1_缴费信息表-应缴日期、到账日期和保单生效日期都在规定时间区间以前";
		//generateExcelReport(cn, SQL_JHKJ002, filenameTemplate, t1FileName, t1TableName, allCategoryList);
		System.out.println("T1 done");
		
		//2	JHWZ005	新保单信息表-销售渠道为代理或经纪业务时，中介机构代码在中介机构信息表中不存在或为空
		String t2TableName = "%1$spol_main";
		String t2FileName = "T1_新保单信息表-销售渠道为代理或经纪业务时，中介机构代码在中介机构信息表中不存在或为空";
		//generateExcelReport(cn, SQL_JHWZ005, filenameTemplate, t2FileName, t2TableName, allCategoryList);
		System.out.println("T1 done");
		
		//3	JHWZ005	新保单信息表-中介机构代码在中介机构信息表中不存在
		String t3TableName = "%1$spol_main";
		String t3FileName = "T2_新保单信息表-中介机构代码在中介机构信息表中不存在";
		//generateExcelReport(cn, SQL_JHWZ005_b, filenameTemplate, t3FileName, t3TableName, allCategoryList);
		System.out.println("T2 done");
		
		//4 JHWZ006	缴费信息表-被保险人证件类型在证件类型代码表中不存在
		//String t4TableName = "%1$sPrem_Info";
		//String t4FileName = "T4_缴费信息表-被保险人证件类型在证件类型代码表中不存在";
		//generateExcelReport(cn, SQL_JHWZ006, filenameTemplate, t4FileName, t4TableName, allCategoryList);
		//System.out.println("T4 done");
		
		//6 JHWZ006 缴费信息表- 收付款方式在收付款方式代码基表中不存在或为空
		String t6TableName = "%1$sPrem_Info";
		String t6_sql = "select * from %1$s where not exists( select P from CollectPay_Code where %1$s.CollectPay_Code = P)";
		String t6FileName = "T6_缴费信息表-收付款方式在收付款方式代码基表中不存在或为空";
		//generateExcelReport(cn, t6_sql, filenameTemplate, t6FileName, t6TableName, allCategoryList);
		System.out.println("T6 done");
		
		//8 JHWZ007 批单信息表- 银保专管员代码在银保专管员信息表中不存在
		String t8TableName = "%1$sEndo_Fee";
		String t8_sql = "select * from %1$s where Speciman_No is not null and Speciman_No != '' and not exists( select 1 from Banc_Speci_Info where %1$s.Speciman_No = BANC_SPECI_INFO.BANC_SPECI_NO)";
		String t8FileName = "T8_批单信息表-银保专管员代码在银保专管员信息表中不存在";
		//generateExcelReport(cn, t8_sql, filenameTemplate, t8FileName, t8TableName, categoryList);
		System.out.println("T8 done");
		
		//9 JHWZ007 批单信息表- 银保专管员代码在银保专管员信息表中不存在
		String t9TableName = "%1$sEndo_Fee";
		String t9_sql = "select * from Endo_Fee where (Speciman_No is null or Speciman_No='') and Busi_Src_Type = '221'";
		String t9FileName = "T9_批单信息表-销售渠道为银行邮政代理时，银保专管员代码为空";
		generateExcelReport(cn, t9_sql, filenameTemplate, t9FileName, t9TableName, categoryList);
		System.out.println("T9 done");
		
		
		//10 JHWZ007	批单信息表-投保日期、保单生效日期、发生日期（批改日期）、批单生效日期为空
		String t10TableName = "%1$sEndo_Fee";
		String t10FileName = "T8_批单信息表-投保日期、保单生效日期、发生日期（批改日期）、批单生效日期为空";
		//generateExcelReport(cn, SQL_JHWZ007, filenameTemplate, t10FileName, t10TableName, categoryList);
		System.out.println("T8 done");
		
		//13     JHWZ012 财务凭证信息表- 没有期初余额记录
		String t13TableName = "Voucher_Info";
		String t13FileName = "T11_财务凭证信息表-没有期初余额记录";
		//generateExcelReport(cn, SQL_JHWZ012, filenameTemplate, t13FileName, t13TableName, null);
		System.out.println("T11 done");
		
		//14     JHWZ012 险种代码表- 停止销售日期存在空值,或日期关系不正确 
		String t14TableName = "plan_info";
		String t14FileName = "T12_险种代码表-停止销售日期存在空值,或日期关系不正确";
		//generateExcelReport(cn, SQL_JHWZ012_a, filenameTemplate, t14FileName, t14TableName, null);
		System.out.println("T12 done");
		
		//15 JHWZ012	中介机构信息表- 获得许可证日期、许可证到期日期、签约日期、协议到期日存在空值,或日期关系不正确 
		String t15TableName = "AGT_CODE";
		String t15FileName = "T13_中介机构信息表-获得许可证日期、许可证到期日期、签约日期、协议到期日存在空值,或日期关系不正确 ";
		//generateExcelReport(cn, SQL_JHWZ012_b, filenameTemplate, t15FileName, t15TableName, null);
		System.out.println("T13 done");
		
		//16 JHWZ012	中介机构信息表- 中介机构类别在中介机构类别基表中不存在或为空
		String t16TableName = "AGT_CODE";
		String t16FileName = "T16_中介机构信息表-中介机构类别在中介机构类别基表中不存在或为空";
		//generateExcelReport(cn, SQL_JHWZ012_c, filenameTemplate, t16FileName, t16TableName, null);
		System.out.println("T16 done");
		
		//17	JHWZ012	员工信息表- 证件类型在证件类型代码表中不存在或为空 
		//String t17TableName = "Staff_Info";
		//String t17FileName = "T17_员工信息表-证件类型在证件类型代码表中不存在或为空 ";
		//generateExcelReport(cn, SQL_JHWZ012_d, filenameTemplate, t17FileName, t17TableName, null);
		//System.out.println("T17 done");
		
		//17 JHWZ012 分支机构信息表- 负责人代码在员工信息表中不存在或为空  
		String t17TableName = "Branch_Info";
		String t17_sql = "select * from %1$s  where not exists( select 1 from Staff_Info where %1$s.Leader_Code = STAFF_INFO.STAFF_NO)";
		String t17FileName = "T17_分支机构信息表-负责人代码在员工信息表中不存在或为空";
		//generateExcelReport(cn, t17_sql, filenameTemplate, t17FileName, t17TableName, null);
		System.out.println("T17 done");
		
		//18     JHWZ012 分支机构信息表- 经营区域代码不是GB/T2260规定的行政区划代码或为空
		String t21TableName = "Branch_Info";
		String t21FileName = "T14_分支机构信息表-经营区域代码不是GB T2260规定的行政区划代码或为空";
		//generateExcelReport(cn, SQL_JHWZ012_h, filenameTemplate, t21FileName, t21TableName, null);
		System.out.println("T14 done");
		
		//18	JHWZ012	员工信息表- 所属分支机构在分支机构信息表中不存在或为空
		//String t18TableName = "Staff_Info";
		//String t18FileName = "T18_员工信息表-所属分支机构在分支机构信息表中不存在或为空";
		//generateExcelReport(cn, SQL_JHWZ012_e, filenameTemplate, t18FileName, t18TableName, null);
		//System.out.println("T18 done");
		
		//19	JHWZ012	银保专管员信息表- 学历在学历代码基表中不存在或为空
		//String t19TableName = "Banc_Speci_Info";
		//String t19FileName = "T19_银保专管员信息表-学历在学历代码基表中不存在或为空";
		//generateExcelReport(cn, SQL_JHWZ012_f, filenameTemplate, t19FileName, t19TableName, null);
		//System.out.println("T19 done");
		
		//20	JHWZ012	银保专管员信息表- 所属分支机构在分支机构信息表中不存在或为空
		///String t20TableName = "Banc_Speci_Info";
		//String t20FileName = "T20_银保专管员信息表-所属分支机构在分支机构信息表中不存在或为空";
		//generateExcelReport(cn, SQL_JHWZ012_g, filenameTemplate, t20FileName, t20TableName, null);
		//System.out.println("T20 done");
	}
	
	private static void generateReport(Connection cn)
			throws FileNotFoundException, IOException, SQLException,
			BiffException, RowsExceededException, WriteException {
		String filenameTemplate = "C:\\1202\\%1$s.xls";
		
		String[] allCategoryList = new String[]{"CICAP_","IPMI_",""};
		String[] allCategoryList_a = new String[]{"CICAP_","IPMI_"};
		String[] allCategoryList_b = new String[]{"NCS_","IPMI_",""};
		String[] categoryList = new String[]{"NCS_","IPMI_",""};
		
//		//1	JHKJ006	赔案信息表-收付款方式为2、5时：对方银行帐号为空或不符合银行帐号一般特征（如存在数字、位数大于5等）
//		String t1TableName = "%1$sclaim_main";
//		String t1FileName = "T1_赔案信息表-收付款方式为2、5时：对方银行帐号为空或不符合银行帐号一般特征（如存在数字、位数大于5等）";
//		generateExcelReport(cn, SQL_JHKJ006, filenameTemplate, t1FileName, t1TableName, categoryList);
//		System.out.println("T1 done");
//		
		//2	JHKJ006  赔案信息表-付讫日期、实付赔款支出折合人民币不等于零时：实付赔款凭证号、领取人姓名、领取人证件类型、领取人证件号码存在为空
		String t2TableName = "%1$sclaim_main";
		String t2FileName = "T1_赔案信息表-付讫日期、实付赔款支出折合人民币不等于零时：实付赔款凭证号、领取人姓名、领取人证件类型、领取人证件号码存在为空";
		generateExcelReport(cn, SQL_JHKJ006_2, filenameTemplate, t2FileName, t2TableName, categoryList);
		System.out.println("T1 done");
		
//		//3	JHWZ005 新保单信息表- 被保险人证件类型在证件类型代码表中不存在
//		String t3TableName = "%1$spol_main";
//		String t3FileName = "T3_新保单信息表- 被保险人证件类型在证件类型代码表中不存在";
//		generateExcelReport(cn, SQL_JHWZ005_a, filenameTemplate, t3FileName, t3TableName, allCategoryList);
//		System.out.println("T3 done");
//		
//		//4 JHWZ005	新保单信息表- 投保人与被保险人关系在人员关系代码基表中不存在或为空
//		String t4TableName = "%1$spol_main";
//		String t4FileName = "T4_新保单信息表- 投保人与被保险人关系在人员关系代码基表中不存在或为空";
//		generateExcelReport(cn, SQL_JHWZ005_c, filenameTemplate, t4FileName, t4TableName, allCategoryList);
//		System.out.println("T4 done");
		
		//5 JHWZ005	新保单信息表- 个团标志为个险时投保人收入为空或为零
		String t5TableName = "%1$spol_main";
		String t5FileName = "T2_新保单信息表- 个团标志为个险时投保人收入为空或为零";
		generateExcelReport(cn, SQL_JHWZ005_d, filenameTemplate, t5FileName, t5TableName, allCategoryList);
		System.out.println("T5 done");
		
		//6 JHWZ005 新保单信息表- 员工代码在员工信息表中不存在
		String t6TableName = "%1$spol_main";
		//String t6_sql = "select * from %1$s where not exists( select P from CollectPay_Code where %1$s.CollectPay_Code = P)";
		String t6FileName = "T3_新保单信息表- 员工代码在员工信息表中不存在";
		generateExcelReport(cn, SQL_JHWZ005_e, filenameTemplate, t6FileName, t6TableName, allCategoryList);
		System.out.println("T6 done");
		
		//7 JHWZ005	新保单信息表- 投保人联系地址为空或不满足地址的一般性定义(字符串长度小于5)
		String t7TableName = "%1$spol_main";
		//String t6_sql = "select * from %1$s where not exists( select P from CollectPay_Code where %1$s.CollectPay_Code = P)";
		String t7FileName = "T4_新保单信息表- 投保人联系地址为空或不满足地址的一般性定义(字符串长度小于5)";
		generateExcelReport(cn, SQL_JHWZ005_f, filenameTemplate, t7FileName, t7TableName, allCategoryList);
		System.out.println("T7 done");
		
		//8 JHWZ005	新保单信息表- 个险新单(个团标志为P)的产品设计类型代码前三位为202(新型产品)或销售渠道代码为120(电销)时：新单首次回访日期为9999-01-01或回访方式、回访成功标志、签收保单回执日期、新单回访成功日期为空
		String t8TableName = "%1$spol_main";
		//String t8_sql = "select * from %1$s where Speciman_No is not null and Speciman_No != '' and not exists( select 1 from Banc_Speci_Info where %1$s.Speciman_No = BANC_SPECI_INFO.BANC_SPECI_NO)";
		String t8FileName = "T5_新保单信息表- 个险新单(个团标志为P)的产品设计类型代码前三位为202(新型产品)或销售渠道代码为120(电销)时：新单首次回访日期为9999-01-01或回访方式、回访成功标志、签收保单回执日期、新单回访成功日期为空";
		generateExcelReport_separate(cn, SQL_JHWZ005_g, filenameTemplate, t8FileName, t8TableName, allCategoryList);
		System.out.println("T8 done");
		
		//9 JHWZ005	新保单信息表- 回访成功标志为0(回访成功)时：签收保单回执日期、新单回访成功日期为9999-01-01
		String t9TableName = "%1$spol_main";
		//String t9_sql = "select * from Endo_Fee where (Speciman_No is null or Speciman_No='') and Busi_Src_Type = '221'";
		String t9FileName = "T6_新保单信息表- 回访成功标志为0(回访成功)时：签收保单回执日期、新单回访成功日期为9999-01-01";
		generateExcelReport(cn, SQL_JHWZ005_h, filenameTemplate, t9FileName, t9TableName, allCategoryList);
		System.out.println("T9 done");
		
		
		//10 JHWZ006	缴费信息表- 险种代码在险种代码表中不存在或为空
		String t10TableName = "%1$sprem_info";
		String t10FileName = "T7_缴费信息表- 险种代码在险种代码表中不存在或为空";
		generateExcelReport(cn, SQL_JHWZ006_a, filenameTemplate, t10FileName, t10TableName, allCategoryList);
		System.out.println("T10 done");
		
		//11 JHWZ006	缴费信息表- 员工代码若不为空：员工代码在员工信息表中不存在、应付员工绩效凭证号在财务凭证信息表中不存在或为空、员工直接绩效比例小于等于零 100355
		String t11TableName = "%1$sprem_info";
		String t11FileName = "T8_缴费信息表- 员工代码若不为空：员工代码在员工信息表中不存在、应付员工绩效凭证号在财务凭证信息表中不存在或为空、员工直接绩效比例小于等于零";
		generateExcelReport_separate(cn, SQL_JHWZ006_b, filenameTemplate, t11FileName, t11TableName, allCategoryList_a);
		System.out.println("T11 done");

		//12 JHWZ007	批单信息表- 险种代码在险种代码表中不存在或为空 3776
		String t12TableName = "%1$sendo_fee";
		String t12FileName = "T9_批单信息表- 险种代码在险种代码表中不存在或为空";
		generateExcelReport(cn, SQL_JHWZ007_a, filenameTemplate, t12FileName, t12TableName, allCategoryList);
		System.out.println("T12 done");
		
		//10  批单信息表- 批改类型代码在批改类型基表中不存在或为空	8
		String t13TableName_ = "%1$sendo_fee";
		String t13FileName_ = "T10_批单信息表- 批改类型代码在批改类型基表中不存在或为空";
		generateExcelReport(cn, SQL_ddfdfdfs, filenameTemplate, t13FileName_, t13TableName_, allCategoryList);
		System.out.println("T13 done");
		
		//13 JHWZ007 批单信息表- 员工代码在员工信息表中不存在 16249
		String t13TableName = "%1$sendo_fee";
		String t13FileName = "T11_批单信息表- 员工代码在员工信息表中不存在";
		generateExcelReport(cn, SQL_JHWZ007_b, filenameTemplate, t13FileName, t13TableName, allCategoryList);
		System.out.println("T13 done");
		
		//14 JHWZ007	批单信息表- 投保日期、保单生效日期、发生日期（批改日期）、批单生效日期为空 
		String t14TableName = "%1$sendo_fee";
		String t14FileName = "T12_批单信息表- 投保日期、保单生效日期、发生日期（批改日期）、批单生效日期为空 ";
		generateExcelReport(cn, SQL_JHWZ007_c, filenameTemplate, t14FileName, t14TableName, allCategoryList);
		System.out.println("T14 done");
		
		//15 JHWZ007	批单信息表- 批文、投保人证件号码、被保人名称为空	89316 
		String t15TableName = "%1$sendo_fee";
		String t15FileName = "T13_批单信息表- 批文、投保人证件号码、被保人名称为空";
		generateExcelReport_separate(cn, SQL_JHWZ007_d, filenameTemplate, t15FileName, t15TableName, allCategoryList);
		System.out.println("T15 done");
		
		//16 JHWZ007	批单信息表- 非现金收款方式的对方银行账号为空	89316	
		String t16TableName = "%1$sendo_fee";
		String t16FileName = "T14_批单信息表- 非现金收款方式的对方银行账号为空";
		generateExcelReport_separate(cn, SQL_JHWZ007_e, filenameTemplate, t16FileName, t16TableName, allCategoryList_a);
		System.out.println("T16 done");
//		
//		//17 JHWZ010 赔案信息表- 员工代码在员工信息表中不存在	6	
//		String t17TableName = "%1$sclaim_main";
//		String t17FileName = "T14_赔案信息表- 员工代码在员工信息表中不存在 ";
//		generateExcelReport(cn, SQL_JHWZ010_a, filenameTemplate, t17FileName, t17TableName, allCategoryList_b);
//		System.out.println("T17 done");
		
		//18 JHWZ010 赔案信息表- 到账日期在取数区间内的应付赔款凭证号，在财务凭证表中不存在	6	
		String t18TableName = "%1$sclaim_main";
		String t18FileName = "T15_赔案信息表- 到账日期在取数区间内的应付赔款凭证号，在财务凭证表中不存在";
		generateExcelReport(cn, SQL_JHWZ010_b, filenameTemplate, t18FileName, t18TableName, allCategoryList_b);
		System.out.println("T18 done");
		
		//19	JHWZ010	赔案信息表- 到账日期在取数区间内的实付赔款凭证号，在财务凭证表中不存在	6	
		String t19TableName = "%1$sclaim_main";
		String t19FileName = "T16_赔案信息表- 到账日期在取数区间内的实付赔款凭证号，在财务凭证表中不存在";
		generateExcelReport(cn, SQL_JHWZ010_c, filenameTemplate, t19FileName, t19TableName, allCategoryList_b);
		System.out.println("T19 done");
		
		//20	JHWZ010	赔案信息表- 销售渠道为直销时，员工代码为空	38
		String t20TableName = "%1$sclaim_main";
		String t20FileName = "T17_赔案信息表- 销售渠道为直销时，员工代码为空";
		generateExcelReport(cn, SQL_JHWZ010_e, filenameTemplate, t20FileName, t20TableName, allCategoryList_b);
		System.out.println("T20 done");
		
		//21	JHWZ010	赔案信息表- 销售渠道为银行邮政代理时，银保专管员代码为空	10
		String t21TableName = "%1$sclaim_main";
		String t21FileName = "T18_赔案信息表- 销售渠道为银行邮政代理时，银保专管员代码为空";
		generateExcelReport(cn, SQL_JHWZ010_d, filenameTemplate, t21FileName, t21TableName, allCategoryList_b);
		System.out.println("T21 done");
//		
//		//22	JHWZ010	赔案信息表- 非现金收款方式的对方银行账号为空	6
//		String t22TableName = "%1$sclaim_main";
//		String t22FileName = "T22_赔案信息表- 非现金收款方式的对方银行账号为空";
//		generateExcelReport(cn, SQL_JHWZ010_f, filenameTemplate, t22FileName, t22TableName, allCategoryList_b);
//		System.out.println("T22 done");
		
		//23	JHWZ012	财务凭证信息表- 没有期初余额记录	1	
		String t23TableName = "voucher_info";
		String t23SQL = "select "
			+"voucher_info.Voucher_Date as 记账日期 ,                     "
			+"voucher_info.Branch_Info as 分支机构 ,                      "
			+"voucher_info.Voucher_Code as 记账凭证号 ,                   "
			+"voucher_info.Entry_SN as 分录号 ,                           "
			+"voucher_info.Account_Code as 科目代码 ,                     "
			+"voucher_info.G_Account_Code as 总账科目代码 ,               "
			+"voucher_info.CurNo as 币种 ,                                "
			+"voucher_info.Debit_Sum as [借方金额（原币）] ,              "
			+"voucher_info.Debit_Sum_RMB as 借方金额折合人民币 ,          "
			+"voucher_info.Credit_Sum as [贷方金额（原币）] ,             "
			+"voucher_info.Credit_Sum_RMB as 贷方金额折合人民币 ,         "
			+"voucher_info.Brief as 摘要 ,                                "
			+"voucher_info.CollectPay_Code as 收付款方式 ,                "
			+"voucher_info.Bank_Acc as 银行账号 ,                         "
			+"voucher_info.Check_ID as 结算号 ,                           "
			+"voucher_info.Oppo_Bank_Acc as 对方银行帐号 ,                "
			+"voucher_info.Oppo_Check_ID as 对方结算号 ,                  "
			+"voucher_info.Proposal_Code as 投保单号 ,                    "
			+"voucher_info.PolNo as 保单号 ,                              "
			+"voucher_info.EntNo as 批单号 ,                              "
			+"voucher_info.CaseNo as 案件号 ,                             "
			+"voucher_info.Plan_Code as 险种代码 ,                        "
			+"voucher_info.Profit_center as [利润中心/渠道] ,             "
			+"voucher_info.Department as 部门 ,                           "
			+"voucher_info.Personal as 经办人代码 ,                       "
			+"voucher_info.Is_Original as 是否期初余额                    "
			+" from %1$s where Is_Original = 'Y'";
		String t23FileName = "T19_财务凭证信息表- 没有期初余额记录";
		generateExcelReport(cn, t23SQL, filenameTemplate, t23FileName, t23TableName, allCategoryList_b);
		System.out.println("T23 done");
		
//		//24	JHWZ012	险种代码表- 停止销售日期存在空值,或日期关系不正确 5
//		String t24TableName = "%1$sclaim_main";
//		String t24FileName = "T24_赔案信息表- 销售渠道为直销时，员工代码为空	";
//		generateExcelReport(cn, SQL_JHWZ010_d, filenameTemplate, t20FileName, t20TableName, allCategoryList_b);
//		System.out.println("T24 done");
	}

	private static void generateExcelReport(Connection cn,
			String sql, String filenameTemplate, String t1FileName, String t1TableName,
			String[] allCategoryList) throws FileNotFoundException,
			IOException, SQLException, BiffException, RowsExceededException,
			WriteException {
		File file = new File(String.format(filenameTemplate, t1FileName));
		FileOutputStream fos = new FileOutputStream(file);
		WritableWorkbook t1Book = Workbook.createWorkbook(fos); 
		if (allCategoryList!=null) {
			for (int i = 0; i < allCategoryList.length; i++) {
				String category = allCategoryList[i];
				WritableSheet t1CGHB = t1Book.createSheet("".equals(category)?"All":category, i);
				doSQL2EXL(cn, t1CGHB, String.format(sql, String.format(t1TableName, category)));
			}
		} else {
			String category = "t1TableName";
			WritableSheet sheet = t1Book.createSheet(category, 0);
			doSQL2EXL(cn, sheet, String.format(sql, t1TableName));
		}
		t1Book.write();
		t1Book.close();
	}
	
	private static void generateExcelReport_separate(Connection cn,
			String sql, String filenameTemplate, String t1FileName, String t1TableName,
			String[] allCategoryList) throws FileNotFoundException,
			IOException, SQLException, BiffException, RowsExceededException,
			WriteException {
		if (allCategoryList!=null) {
			for (int i = 0; i < allCategoryList.length; i++) {
				String category = allCategoryList[i];
				//WritableSheet t1CGHB = t1Book.createSheet("".equals(category)?"All":category, i);
				//doSQL2EXL(cn, t1CGHB, String.format(sql, String.format(t1TableName, category)));
				doSQL2EXL(cn, String.format(sql, String.format(t1TableName, category)), filenameTemplate, t1FileName, "".equals(category)?"All":category);
			}
		} else {
			String category = "t1TableName";
			//WritableSheet sheet = t1Book.createSheet(category, 0);
			//doSQL2EXL(cn, sheet, String.format(sql, t1TableName));
			doSQL2EXL(cn, String.format(sql, t1TableName), filenameTemplate, t1FileName, category);
		}
	}

	private static Connection getCIRCConnAndInitDriver()
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
//		Connection cn = DriverManager.getConnection (
//				 "jdbc:microsoft:sqlserver://CNIFDU02:1433;DatabaseName=CIRCAudit"
//				 , "uat_admin"
//				 , "uatadmin123"
//				);
		Connection cn = DriverManager.getConnection (
				 "jdbc:microsoft:sqlserver://CNITES02:1433;DatabaseName=CIRCAudit"
				 , "sa"
				 , "Aa123456"
				);
		return cn;
	}
	
	public static void doSQL2EXL(Connection cn, String sql, String filenameTemplate, String filename, String category) throws SQLException, BiffException, IOException, RowsExceededException, WriteException{
		File file = new File(String.format(filenameTemplate, filename + "_" + category));
		WritableWorkbook book = Workbook.createWorkbook(new FileOutputStream(file)); 
		int numberOfSheets = book.getNumberOfSheets();
		WritableSheet sheet = book.createSheet(category, numberOfSheets);
		
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		int cols_count = rs.getMetaData().getColumnCount();
		List<String> cols = new ArrayList<String>();
		for(int i=1; i<cols_count+1; i++){
			cols.add(rs.getMetaData().getColumnName(i));
		}
		
		int colindex = 0;
		for(int j=0; j<cols.size(); j++){
			sheet.addCell(new  jxl.write.Label(colindex++, 0, cols.get(j).toString()));
		}
		
		int i = 1;
		int split = 60000;
		int fileCount = 0;
		while(rs.next()){

			colindex = 0;
			for(int j=0; j<cols.size(); j++){
				try{
				sheet.addCell(new jxl.write.Label(colindex++, i, rs.getString(cols.get(j).toString())));
				}catch(java.sql.SQLException e){
					continue;
				}
			}
			i++;
			
			if (i%split == 0) {
				book.write();
				fileCount++;
				System.out.println("write "+ fileCount);
				book.close();
				
				file = new File(String.format(filenameTemplate, filename + "_" + category +"_"+fileCount));
				book = Workbook.createWorkbook(new FileOutputStream(file)); 
				numberOfSheets = book.getNumberOfSheets();
				sheet = book.createSheet(category, numberOfSheets);
				
				colindex=0;
				for(int j=0; j<cols.size(); j++){
					sheet.addCell(new  jxl.write.Label(colindex++, 0, cols.get(j).toString()));
				}
				
				i=1;
			}
		}
		
		book.write();
		book.close();
	}
	
	public static void doSQL2EXL(Connection cn, WritableSheet sheet, String sql) throws SQLException, BiffException, IOException, RowsExceededException, WriteException{
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		int cols_count = rs.getMetaData().getColumnCount();
		List<String> cols = new ArrayList<String>();
		for(int i=1; i<cols_count+1; i++){
			cols.add(rs.getMetaData().getColumnName(i));
		}
		
		int colindex = 0;
		for(int j=0; j<cols.size(); j++){
			sheet.addCell(new  jxl.write.Label(colindex++, 0, cols.get(j).toString()));
		}
		
		int i = 1;
		while(rs.next()){

			colindex = 0;
			for(int j=0; j<cols.size(); j++){
				try{
				sheet.addCell(new  jxl.write.Label(colindex++, i, rs.getString(cols.get(j).toString())));
				}catch(java.sql.SQLException e){
					continue;
				}
			}
			i++;
			
		}

	}
	
}
