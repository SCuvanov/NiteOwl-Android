package com.example.owl.model;

public class PictureItem {

	private int _id;
	private String _desc;

	public PictureItem(int _id, String _desc) {
		this._id = _id;
		this._desc = _desc;
	}

	public int getImageId() {
		return _id;
	}

	public void setImageId(int _id) {
		this._id = _id;
	}

	public String getDesc() {
		return _desc;
	}

	public void setDesc(String _desc) {
		this._desc = _desc;
	}

	@Override
	public String toString() {
		return _desc;
	}

}
