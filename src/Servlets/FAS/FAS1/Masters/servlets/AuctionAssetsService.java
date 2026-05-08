package Servlets.FAS.FAS1.Masters.servlets;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

public interface AuctionAssetsService {
	
	public String getAuctionOffice()throws SQLException;
	
	public String surveyReportNo(int unitCode, int officeCode) throws SQLException;
	
	public Set<AuctionAssetBean> surveyReportNo(int unitCode, int officeCode, String check) throws SQLException;
	
	public String assetCode(int unitCode, int officeCode, int surveyCode) throws SQLException;
	
	public String assetCode(int unitCode, int officeCode, int surveyCode, String check) throws SQLException;
	
	public String viewAuctionedDetails(int unitCode, int officeCode, int surveyCode, int assetCode) throws SQLException;
	
	public String addAssetAuction(int accountUnitId, int accountOfficeId, String financialYear, int surveyNo, Date surveyDate, int auctionOffice, int assetCode, String userid) throws SQLException;

	public Map<String,Integer> addAuctionDoneMaster(int accountUnitId, int accountOfficeId,
			int surveyNo, Date auctionDate, int auctionOffice,
			String[] assetAuction, String[] referenceNo,
			String[] referenceDate, String[] auctioneer,
			String[] auctionAmount, String[] remarks, String userid) throws SQLException;

	public String viewDetails(int accountUnitId, int accountOfficeId, String surveyNo, String assetAuctioin, String category) throws SQLException;
	
	public void sendMessage(HttpServletResponse response,String msg,String bType);

	public String getAuctionDetails(int unitCode, int officeCode, int surveyNo,
			int assetcode) throws SQLException;

	public String getCashReceipt(int unitCode, int officeCode, int year,
			int month, String check) throws SQLException;

	public String addAssetAuctionVoucher(int accountUnitId,
			int accountOfficeId, String financialYear, int surveyNo,
			int assetAuction, Date auctionDate, int auctionNo,
			int auctionOffice, int auctionAmount, int recoverYear, int month,
			int receiptNo, Date receiptDate, int jvrYear, int jvrmonth,
			int journalNo, Date journalDate, String userid) throws SQLException;
	
	public String viewDetails(int accountUnitId, int accountOfficeId) throws SQLException;

}
