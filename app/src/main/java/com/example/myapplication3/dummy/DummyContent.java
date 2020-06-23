package com.example.myapplication3.dummy;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myapplication3.DBFiller;
import com.example.myapplication3.FeedReaderDbHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent extends AsyncTask {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static FeedReaderDbHelper dbhelp = null;
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    public DummyContent(FeedReaderDbHelper db){
        dbhelp = db;
        ITEM_MAP = new HashMap<String, DummyItem>();
        ITEMS = new ArrayList<DummyItem>();
        /**
         * to reset and set new database; use only once
         */
        //dbhelp.clearAll();
        //DBFiller.fill(dbhelp);
        Cursor res = dbhelp.getAllData();
        if(res.getCount() == 0 ) {
            DBFiller.fill(dbhelp);
        }
        while(res.moveToNext()) {
            addItem(createDummyItem(res.getString(1),res.getString(2),res.getString(3)));
        }
    }

    public static void update(FeedReaderDbHelper db){
        ITEM_MAP = new HashMap<String, DummyItem>();
        ITEMS = new ArrayList<DummyItem>();
        Cursor res = db.getAllData();
        while(res.moveToNext()) {
            addItem(createDummyItem(res.getString(1),res.getString(2),res.getString(3)));
        }
    }


    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    private static DummyItem createDummyItem(String id, String content, String details) {
        return new DummyItem(id, content, details);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    /**
     * A dummy item representing a piece of content. id = tyuł; content=rating; details = opis
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
