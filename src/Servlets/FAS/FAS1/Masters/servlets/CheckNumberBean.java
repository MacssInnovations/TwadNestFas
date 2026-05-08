package Servlets.FAS.FAS1.Masters.servlets;


public class CheckNumberBean implements Comparable<CheckNumberBean>{
	
	private Integer chequeNo;
	private String paymentDate;
	
	public boolean equals(Object obj) {		
		if(obj == null || !(obj instanceof CheckNumberBean)){
			return false;
		}
		CheckNumberBean bean = (CheckNumberBean) obj;
		if((bean.getChequeNo().equals(chequeNo))){
			return true;
		}
		return false;
	}
	public int hashCode(){
		return chequeNo.hashCode();
	}
	public int compareTo(CheckNumberBean o) {
		return chequeNo.compareTo(o.getChequeNo());
	}

	/**
	 * @return the chequeNo
	 */
	public Integer getChequeNo() {
		return chequeNo;
	}

	/**
	 * @param chequeNo the chequeNo to set
	 */
	public void setChequeNo(Integer chequeNo) {
		this.chequeNo = chequeNo;
	}	
	/**
	 * @return the paymentDate
	 */
	public String getPaymentDate() {
		return paymentDate;
	}
	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String toString(){
		return Integer.toString(chequeNo);
	}

}
