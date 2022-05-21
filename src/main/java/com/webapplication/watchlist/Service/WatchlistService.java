package com.webapplication.watchlist.Service;

import com.webapplication.watchlist.Beans.WatchlistItem;
import com.webapplication.watchlist.Exception.DuplicateTitleException;
import com.webapplication.watchlist.Repository.WatchlistRepository;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

public class WatchlistService {
    WatchlistRepository watchlistRepository = new WatchlistRepository();
    MovieRatingWithApiService movieRatingWithApiService = new MovieRatingWithApiService();

    public List<WatchlistItem> getWatchlistItems(){
        List<WatchlistItem> watchlistItems = watchlistRepository.getWatchlistItems();
        for(WatchlistItem watchlistItem : watchlistItems){
            String rating = movieRatingWithApiService.getMovieRating(watchlistItem.getTitle());
            if(rating!=null){
                watchlistItem.setRating(rating);
            }
            else{
                //TODO if rating from api is null, must display rating from the form
                return watchlistRepository.getWatchlistItems();
            }
        }
        return watchlistItems;
    }

    public int getWatchlistItemsSize(){
        return watchlistRepository.getWatchlistItems().size();
    }

    public WatchlistItem findWatchlistItemById(Integer id){
        return watchlistRepository.findWatchlistItemByID(id);
    }

    public void addOrUpdateWatchlistItem(WatchlistItem watchlistItem) throws DuplicateTitleException {
        WatchlistItem existingItem = findWatchlistItemById(watchlistItem.getId());

        //TODO if title already exist, then update the existing one instead of throwing an error
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
