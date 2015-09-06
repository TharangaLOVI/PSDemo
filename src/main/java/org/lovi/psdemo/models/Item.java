package org.lovi.psdemo.models;

public class Item {
	
	private int itemId;
	private String name;
	private ItemCategory itemCategory;
	private double price;
	private String comment;
	
	//this is item quantity in item plate
	private int plateItemQty;
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ItemCategory getItemCategory() {
		return itemCategory;
	}
	public void setItemCategory(ItemCategory itemCategory) {
		this.itemCategory = itemCategory;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getPlateItemQty() {
		return plateItemQty;
	}
	public void setPlateItemQty(int plateItemQty) {
		this.plateItemQty = plateItemQty;
	}
	
	
}
