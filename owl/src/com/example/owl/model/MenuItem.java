package com.example.owl.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class MenuItem {

    private static int counter = 0;
    private int _id;
    private int _titleRes;
    private int _imageRes;
    @SuppressWarnings("rawtypes")
    private Class _class;
    private Fragment _fragment;
    private Bundle _args;

    public MenuItem() {
    }

    ;

    @SuppressWarnings("rawtypes")
    public MenuItem(int titleResource, int iconResource, Class c,
                    Bundle args) {
        this._id = counter++;
        this._titleRes = titleResource;
        this._imageRes = iconResource;
        this._class = c;
        this._args = args;
    }

    ;

    public int get_id() {
        return _id;
    }

    public int get_imageRes() {
        return _imageRes;
    }

    public int get_titleRes() {
        return _titleRes;
    }

    @SuppressWarnings("rawtypes")
    public Class get_class() {
        return _class;
    }

    public Fragment get_fragment() {
        return _fragment;
    }

    public Bundle get_args() {
        return _args;
    }

    public void set_titleRes(int _titleRes) {
        this._titleRes = _titleRes;
    }

    public void set_imageRes(int _imageRes) {
        this._imageRes = _imageRes;
    }

    @SuppressWarnings("rawtypes")
    public void set_class(Class _class) {
        this._class = _class;
    }

    public void set_fragment(Fragment _fragment) {
        this._fragment = _fragment;
    }

    public void set_args(Bundle _args) {
        this._args = _args;
    }

}
