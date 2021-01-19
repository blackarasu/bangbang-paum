package com.paum.bangbang;

import android.app.Activity;
import android.content.Context;

public class PlayerLivesObserver implements IObserver{

    Context context;

    public PlayerLivesObserver(Context context){
        this.context = context;
    }

    @Override
    public void update() {
        ((Activity)this.context).finish();
    }
}
