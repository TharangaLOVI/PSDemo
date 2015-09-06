package org.lovi.psdemo.models;

public class PlateLog {
	
	private int plateLogId;
	private String date;
	private String startTime;
	private String endTime;
	private String comment;
	private String relatedSupervicerId;
	private String relatedManagerId;
	private String relatedDriverId;
	private ItemsPlate itemsPlate;
	
	
	public int getPlateLogId() {
		return plateLogId;
	}
	public void setPlateLogId(int plateLogId) {
		this.plateLogId = plateLogId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getRelatedSupervicerId() {
		return relatedSupervicerId;
	}
	public void setRelatedSupervicerId(String relatedSupervicerId) {
		this.relatedSupervicerId = relatedSupervicerId;
	}
	public String getRelatedManagerId() {
		return relatedManagerId;
	}
	public void setRelatedManagerId(String relatedManagerId) {
		this.relatedManagerId = relatedManagerId;
	}
	public String getRelatedDriverId() {
		return relatedDriverId;
	}
	public void setRelatedDriverId(String relatedDriverId) {
		this.relatedDriverId = relatedDriverId;
	}
	public ItemsPlate getItemsPlate() {
		return itemsPlate;
	}
	public void setItemsPlate(ItemsPlate itemsPlate) {
		this.itemsPlate = itemsPlate;
	}
	
	
}
