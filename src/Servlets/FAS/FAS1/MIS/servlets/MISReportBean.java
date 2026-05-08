package Servlets.FAS.FAS1.MIS.servlets;

import java.io.Serializable;

public class MISReportBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2203661406322368063L;
	private int officeId;
	private String officeName;
	private String officeLevelId;
	private int regionOfficeId;
	private int unitId;
	private int districtCode;
	private String districtName;
	private String regionName;
	private long arwsp;
	/**
	 * @return the arwsp
	 */
	public long getArwsp() {
		return arwsp;
	}
	/**
	 * @param arwsp the arwsp to set
	 */
	public void setArwsp(long arwsp) {
		this.arwsp = arwsp;
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
	 * @return the officeName
	 */
	public String getOfficeName() {
		return officeName;
	}
	/**
	 * @param officeName the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	/**
	 * @return the officeLevelId
	 */
	public String getOfficeLevelId() {
		return officeLevelId;
	}
	/**
	 * @param officeLevelId the officeLevelId to set
	 */
	public void setOfficeLevelId(String officeLevelId) {
		this.officeLevelId = officeLevelId;
	}
	/**
	 * @return the regionOfficeId
	 */
	public int getRegionOfficeId() {
		return regionOfficeId;
	}
	/**
	 * @param regionOfficeId the regionOfficeId to set
	 */
	public void setRegionOfficeId(int regionOfficeId) {
		this.regionOfficeId = regionOfficeId;
	}
	/**
	 * @return the unitId
	 */
	public int getUnitId() {
		return unitId;
	}
	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	/**
	 * @return the districtCode
	 */
	public int getDistrictCode() {
		return districtCode;
	}
	/**
	 * @param districtCode the districtCode to set
	 */
	public void setDistrictCode(int districtCode) {
		this.districtCode = districtCode;
	}
	/**
	 * @return the districtName
	 */
	public String getDistrictName() {
		return districtName;
	}
	/**
	 * @param districtName the districtName to set
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}
	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

}
