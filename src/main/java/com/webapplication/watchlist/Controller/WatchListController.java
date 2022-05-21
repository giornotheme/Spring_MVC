package com.webapplication.watchlist.Controller;

import com.webapplication.watchlist.Beans.WatchlistItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WatchListController {

    private List<WatchlistItem> watchlistItems = new ArrayList<WatchlistItem>();
    private static int index = 1;

    @GetMapping("/watchlist")
    public String getWatchList(Model model) {

        //watchlistItems.clear();
        watchlistItems.add(new WatchlistItem("Clannad", "10", "high", "super", index++));
        watchlistItems.add(new WatchlistItem("mes couilles", "10", "high", "sur ton front", index++));
        watchlistItems.add(new WatchlistItem("mes couilles au bord de l'eau", "10", "high", "ça fait un radeau", index++));
        watchlistItems.add(new WatchlistItem("SNK", "10", "low", "les titans sont dans les murs", index++));

        model.addAttribute("watchlistItems", watchlistItems);
        model.addAttribute("numberOfMovies", watchlistItems.size());

        return "watchlist";
    }

    @GetMapping("/watchlistItemForm")
    public ModelAndView showWatchlistItemForm(@RequestParam(required = false) Integer id) {
        String viewName = "watchlistItemForm";
        Map<String, Object> model = new HashMap<String, Object>();

        WatchlistItem watchlistItem = findWatchlistItemByID(id);

        if (watchlistItem == null) {
            model.put("watchlistItem", new WatchlistItem());
        } else {
            model.put("watchlistItem", watchlistItem);
        }
        model.put("watchlistItem", new WatchlistItem());
        return new ModelAndView(viewName, model);
    }

    @PostMapping("/watchlistItemForm")
    public ModelAndView submitWatchlistItemForm(@Valid WatchlistItem watchlistItem, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return new ModelAndView("watchlistItemForm");
        }
        WatchlistItem existingItem = findWatchlistItemByID(watchlistItem.getId());

        if (existingItem == null) {
            watchlistItem.setId(index++);
            watchlistItems.add(watchlistItem);
        }
        else{
            existingItem.setComment(watchlistItem.getComment());
            existingItem.setPriority(watchlistItem.getPriority());
            existingItem.setRating(watchlistItem.getRating());
            existingItem.setTitle(watchlistItem.getTitle());
        }
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/watchlist");

        return new ModelAndView(redirectView);
    }

    //Methodes pour les vues
    private WatchlistItem findWatchlistItemByID(Integer id) {
        for (WatchlistItem watchlistItem : watchlistItems) {
            if (watchlistItem.getId().equals(id)) {
                return watchlistItem;
            }
        }
        return null;
    }

}
