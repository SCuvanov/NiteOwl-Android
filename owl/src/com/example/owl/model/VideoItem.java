package com.example.owl.model;

public class VideoItem {
    private int _id;
    private int _price;
    private String _title;
    private String _image;
    private String _desc;
    private String _time;
    private Boolean _selected;

    public VideoItem() {
    }

    ;

    public VideoItem(int id, int price, String title, String image, String desc, String time, Boolean selected) {
        this._id = id;
        this._price = price;
        this._title = title;
        this._image = image;
        this._desc = desc;
        this._time = time;
        this._selected = selected;
    }

    ;

    public int get_id() {
        return _id;
    }

    public int get_price() {
        return _price;
    }

    public String get_title() {
        return _title;
    }

    public String get_image() {
        return _image;
    }

    public String get_desc() {
        return _desc;
    }

    public String get_time() {
        return _time;
    }

    public Boolean get_selected() {
        return _selected;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_price(int _price) {
        this._price = _price;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public void set_image(String _image) {
        this._image = _image;
    }

    public void set_desc(String _desc) {
        this._desc = _desc;
    }

    public void set_selected(Boolean _selected) {
        this._selected = _selected;
    }

}
