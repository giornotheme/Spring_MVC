package com.webapplication.watchlist.Service;

import com.webapplication.watchlist.Beans.WatchlistItem;
import com.webapplication.watchlist.Exception.DuplicateTitleException;
import com.webapplication.watchlist.Repository.WatchlistRepository;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

public class WatchlistService {
    WatchlistRepository watchlistRepository = new WatchlistRepository();

    public List<WatchlistItem> getWatchlistItems(){
        return watchlistRepository.getWatchlistItems();
    }

    public int getWatchlistItemsSize(){
        return watchlistRepository.getWatchlistItems().size();
    }

    public WatchlistItem findWatchlistItemById(Integer id){
        return watchlistRepository.findWatchlistItemByID(id);
    }

    public void addOrUpdateWatchlistItem(WatchlistItem watchlistItem) throws DuplicateTitleException {
        WatchlistItem existingItem = findWatchlistItemById(watchlistItem.getId());

        if (existingItem == null) {
            if(watchlistRepository.findWatchlistItemByTitle(watchlistItem.getTitle()) != null){
                throw new DuplicateTitleException();
            }
            watchlistRepository.addItem(watchlistItem);
        }
        else{
            existingItem.setComment(watchlistItem.getComment());
            existingItem.setPriority(watchlistItem.getPriority());
            existingItem.setRating(watchlistItem.getRating());
            existingItem.setTitle(watchlistItem.getTitle());
        }
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/watchlist");
    }
}
