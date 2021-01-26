package com.paum.bangbang;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.LinearLayout;

// represents class that manages layouts at the top of the game activity
public class TopLayoutsManagement {
    private Context context;
    // layouts id
    private int[] layoutsId;
    // number of layouts to be managed
    private final int numOfLayouts = 9;
    private final int numOfDisplayed = 3;

    public TopLayoutsManagement(Context context){
        this.context = context;

        this.layoutsId = new int[numOfLayouts];
        this.layoutsId[0] = R.id.layout_top0;
        this.layoutsId[1] = R.id.layout_top1;
        this.layoutsId[2] = R.id.layout_top2;
        this.layoutsId[3] = R.id.layout_top3;
        this.layoutsId[4] = R.id.layout_top4;
        this.layoutsId[5] = R.id.layout_top5;
        this.layoutsId[6] = R.id.layout_top6;
        this.layoutsId[7] = R.id.layout_top7;
        this.layoutsId[8] = R.id.layout_top8;
    }

    // initialize layouts
    public void initialize(){
        for(int id:this.layoutsId){
            LinearLayout topLayout = ((Activity)this.context).findViewById(id);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(Color.RED);
            topLayout.setBackground(drawable);
        }
    }

    // returns array of layouts id
    public int[] getLayoutsId(){
        return this.layoutsId;
    }

    // sets a border of the layouts with the specified index
    public void setBorders(int index){
        for(int i = 0; i < this.layoutsId.length; i++){
            LinearLayout topLayout = ((Activity)this.context).findViewById(this.layoutsId[i]);
            GradientDrawable border = (GradientDrawable) topLayout.getBackground();

            // if layout is in the specified range
            if(i >= index && i < index + numOfDisplayed){
                // draw borders
                border.setStroke(5, Color.BLACK);
            }
            else{
                // remove borders
                border.setStroke(0, Color.BLACK);
            }
            topLayout.setBackground(border);
        }
    }

    public void setAccepted(int id){
        // display the accepted state on the top layout
        LinearLayout topLayout = (LinearLayout)((Activity)this.context).findViewById(id);
        GradientDrawable border = new GradientDrawable();
        border.setColor(Color.BLUE);
        border.setStroke(5, Color.BLACK);
        topLayout.setBackground(border);
    }
}
