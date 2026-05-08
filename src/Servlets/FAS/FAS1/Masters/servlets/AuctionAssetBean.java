package Servlets.FAS.FAS1.Masters.servlets;

import java.util.Date;

public class AuctionAssetBean implements Comparable<AuctionAssetBean>{
	
	private int accountId;
	private int officeId;	
	private String surveyNo;
	private int designation;
	private int assetCode;
	private int refNo;
	private Date refDate;
	private String status;
	private int posting;
	private String remarks;
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj == null || !(obj instanceof AuctionAssetBean)){
			return false;
		}
		AuctionAssetBean bean = (AuctionAssetBean)obj;
		return getSurveyNo().equals(bean.getSurveyNo());
	}


	
	public int hashCode() {
		// TODO Auto-generated method stub
		return surveyNo.hashCode();
	}


	
	public int compareTo(AuctionAssetBean o) {
		// TODO Auto-generated method stub		
		return surveyNo.compareTo(o.getSurveyNo());
	}


	/**
	 * @return the accountId
	 */
	public int getAccountId() {
		return accountId;
	}


	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}


	/**
	 * @return the officeId
	 */
	public int getOfficeId() {
		return officeId;
	}


	/**
	 * @param officeId the officeId to set
	 */
	public void setOfficeId(int officeId) {
		this.officeId = officeId;
	}


	/**
	 * @return the designation
	 */
	public int getDesignation() {
		return designation;
	}


	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(int designation) {
		this.designation = designation;
	}


	/**
	 * @return the assetCode
	 */
	public int getAssetCode() {
		return assetCode;
	}


	/**
	 * @param assetCode the assetCode to set
	 */
	public void setAssetCode(int assetCode) {
		this.assetCode = assetCode;
	}


	/**
	 * @return the refNo
	 */
	public int getRefNo() {
		return refNo;
	}


	/**
	 * @param refNo the refNo to set
	 */
	public void setRefNo(int refNo) {
		this.refNo = refNo;
	}


	/**
	 * @return the refDate
	 */
	public Date getRefDate() {
		return refDate;
	}


	/**
	 * @param refDate the refDate to set
	 */
	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the posting
	 */
	public int getPosting() {
		return posting;
	}


	/**
	 * @param posting the posting to set
	 */
	public void setPosting(int posting) {
		this.posting = posting;
	}


	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}


	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	/**
	 * @return the surveyNo
	 */
	public String getSurveyNo() {
		return surveyNo;
	}


	/**
	 * @param surveyNo the surveyNo to set
	 */
	public void setSurveyNo(String surveyNo) {
		this.surveyNo = surveyNo;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	public String toString() {
		// TODO Auto-generated method stub
		return surveyNo;
	}
	
	

}
