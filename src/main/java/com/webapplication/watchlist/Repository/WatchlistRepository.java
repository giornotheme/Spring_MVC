package com.webapplication.watchlist.Repository;

import com.webapplication.watchlist.Beans.WatchlistItem;

import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository {
    private List<WatchlistItem> watchlistItems = new ArrayList<WatchlistItem>();
    private static int index = 1;

    public List<WatchlistItem> getWatchlistItems() {
        return watchlistItems;
    }

    public void addItem(WatchlistItem watchlistItem){
        watchlistItem.setId(index++);
        watchlistItems.add(watchlistItem);
    }

    public WatchlistItem findWatchlistItemByID(Integer id) {
        for (WatchlistItem watchlistItem : watchlistItems) {
            if (watchlistItem.getId().equals(id)) {
                return watchlistItem;
            }
        }
        return null;
    }

    public WatchlistItem findWatchlistItemByTitle(String title) {
        for (WatchlistItem watchlistItem : watchlistItems) {
            if (watchlistItem.getTitle().equals(title)) {
                return watchlistItem;
            }
        }
        return null;
    }


}
