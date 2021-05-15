package com.example.testproject;

import android.content.Context;
import android.content.res.TypedArray;

import com.example.testproject.models.UtilityPayments;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ResourceType")
public class DataGenerator {

    public static List<UtilityPayments> getUtilityData(Context ctx) {
        List<UtilityPayments> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.images);
        String[] name_arr = ctx.getResources().getStringArray(R.array.names);

        for (int i = 0; i < drw_arr.length(); i++) {
            UtilityPayments obj = new UtilityPayments();
            obj.image = drw_arr.getResourceId(i, 0);
            obj.name = name_arr[i];
            items.add(obj);
        }
        return items;
    }


    public static List<UtilityPayments> getWaterData(Context context) {
        List<UtilityPayments> water_items = new ArrayList<>();
        TypedArray water_arr = context.getResources().obtainTypedArray(R.array.water_images);
        String[] water_name_ = context.getResources().getStringArray(R.array.water_names);

        for (int j = 0; j < water_arr.length(); j++) {
            UtilityPayments object = new UtilityPayments();
            object.image = water_arr.getResourceId(j, 0);
            object.name = water_name_[j];
            water_items.add(object);
        }
        return water_items;
    }

    public static List<UtilityPayments> getMobileData(Context context) {
        List<UtilityPayments> mobile_items = new ArrayList<>();
        TypedArray mobile_arr = context.getResources().obtainTypedArray(R.array.mobile_images);
        String[] mobile_name_ = context.getResources().getStringArray(R.array.mobile_names);

        for (int k = 0; k < mobile_arr.length(); k++) {
            UtilityPayments object = new UtilityPayments();
            object.image = mobile_arr.getResourceId(k, 0);
            object.name = mobile_name_[k];
            mobile_items.add(object);
        }
        return mobile_items;
    }

    public static List<UtilityPayments> getPostPayData(Context ctx) {
        List<UtilityPayments> post_items = new ArrayList<>();
        TypedArray post_arr = ctx.getResources().obtainTypedArray(R.array.landline_images);
        String[] post_name_ = ctx.getResources().getStringArray(R.array.landline_names);

        for (int l = 0; l < post_arr.length();l++ ) {
            UtilityPayments object = new UtilityPayments();
            object.image = post_arr.getResourceId(l, 0);
            object.name = post_name_[l];
            post_items.add(object);
        }
        return post_items;
    }
    public static List<UtilityPayments> getCabletvData(Context ctx) {
        List<UtilityPayments> post_items = new ArrayList<>();
        TypedArray post_arr = ctx.getResources().obtainTypedArray(R.array.cabletv_images);
        String[] post_name_ = ctx.getResources().getStringArray(R.array.cabletv_names);

        for (int l = 0; l < post_arr.length();l++ ) {
            UtilityPayments object = new UtilityPayments();
            object.image = post_arr.getResourceId(l, 0);
            object.name = post_name_[l];
            post_items.add(object);
        }
        return post_items;
    }    public static List<UtilityPayments> getInternetData(Context ctx) {
        List<UtilityPayments> post_items = new ArrayList<>();
        TypedArray post_arr = ctx.getResources().obtainTypedArray(R.array.internet_images);
        String[] post_name_ = ctx.getResources().getStringArray(R.array.internet_names);

        for (int l = 0; l < post_arr.length();l++ ) {
            UtilityPayments object = new UtilityPayments();
            object.image = post_arr.getResourceId(l, 0);
            object.name = post_name_[l];
            post_items.add(object);
        }
        return post_items;
    }
}
