package com.isuite.rjil.iagent.jiomoney.common;

import java.io.Serializable;

/**
 * 
 * @author Novelvox
 * 
 */
public class VerificationQuestion implements Serializable {

	private static final long serialVersionUID = 5022137477353243274L;

	private String srNo;
	private String questionId;
	private String question;
	private boolean selectValue = false;
	private String custAnswer;
	private Boolean isSelected = false;
	public VerificationQuestion() 
	{	
	}
	public String getCustAnswer() {
		return custAnswer;
	}

	public void setCustAnswer(String custAnswer) {
		this.custAnswer = custAnswer;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getSrNo() {
		return srNo;
	}

	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean getSelectValue() {
		return selectValue;
	}

	public void setSelectValue(boolean selectValue) {
		this.selectValue = selectValue;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((custAnswer == null) ? 0 : custAnswer.hashCode());
		result = prime * result
				+ ((isSelected == null) ? 0 : isSelected.hashCode());
		result = prime * result
				+ ((question == null) ? 0 : question.hashCode());
		result = prime * result
				+ ((questionId == null) ? 0 : questionId.hashCode());
		result = prime * result + ((srNo == null) ? 0 : srNo.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VerificationQuestion other = (VerificationQuestion) obj;
		if (custAnswer == null) {
			if (other.custAnswer != null)
				return false;
		} else if (!custAnswer.equals(other.custAnswer))
			return false;
		if (isSelected == null) {
			if (other.isSelected != null)
				return false;
		} else if (!isSelected.equals(other.isSelected))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (questionId == null) {
			if (other.questionId != null)
				return false;
		} else if (!questionId.equals(other.questionId))
			return false;
		if (srNo == null) {
			if (other.srNo != null)
				return false;
		} else if (!srNo.equals(other.srNo))
			return false;
		return true;
	}

}
