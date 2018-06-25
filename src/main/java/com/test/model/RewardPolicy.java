package com.test.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RewardPolicy implements Serializable {

	/**
	 * 
	 */
	@Id
	@GeneratedValue
	private Long id;
	private static final long serialVersionUID = 1L;

	private int pointsAwarded;

	private Date lastDateOfChallenge;

	
	@Column
	@ElementCollection(targetClass=String.class)
	private List<String> itemsForOffer;

	private Date startDateOfChallenge;

	private String challengeType;

	public int getPointsAwarded() {
		return pointsAwarded;
	}

	public void setPointsAwarded(int pointsAwarded) {
		this.pointsAwarded = pointsAwarded;
	}

	public Date getLastDateOfChallenge() {
		return lastDateOfChallenge;
	}

	public void setLastDateOfChallenge(Date lastDateOfChallenge) {
		this.lastDateOfChallenge = lastDateOfChallenge;
	}

	public List<String> getItemsForOffer() {
		return itemsForOffer;
	}

	public void setItemsForOffer(List<String> itemsForOffer) {
		this.itemsForOffer = itemsForOffer;
	}

	public Date getStartDateOfChallenge() {
		return startDateOfChallenge;
	}

	public void setStartDateOfChallenge(Date startDateOfChallenge) {
		this.startDateOfChallenge = startDateOfChallenge;
	}

	public String getChallengeType() {
		return challengeType;
	}

	public void setChallengeType(String challengeType) {
		this.challengeType = challengeType;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[pointsAwarded :- " + pointsAwarded + " lastDateOfReward :- " + lastDateOfChallenge + " itemsForOffer"
				+ itemsForOffer.toString();
	}
}
