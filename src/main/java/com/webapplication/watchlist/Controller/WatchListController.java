package com.webapplication.watchlist.Controller;

import com.webapplication.watchlist.Beans.WatchlistItem;
import com.webapplication.watchlist.Exception.DuplicateTitleException;
import com.webapplication.watchlist.Service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WatchListController {

    private WatchlistService watchlistService;

    @Autowired
    public WatchListController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    @GetMapping("/watchlist")
    public String getWatchList(Model model) {

        model.addAttribute("watchlistItems", watchlistService.getWatchlistItems());
        model.addAttribute("numberOfMovies", watchlistService.getWatchlistItemsSize());

        return "watchlist";
    }

    @GetMapping("/watchlistItemForm")
    public ModelAndView showWatchlistItemForm(@RequestParam(required = false) Integer id) {
        String viewName = "watchlistItemForm";
        Map<String, Object> model = new HashMap<String, Object>();

        WatchlistItem watchlistItem = watchlistService.findWatchlistItemById(id);

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

        try {
            watchlistService.addOrUpdateWatchlistItem(watchlistItem);
        } catch (DuplicateTitleException e) {
            bindingResult.rejectValue("title", "", "Title already existing");
            return new ModelAndView("watchlistItemForm");
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/watchlist");

        return new ModelAndView(redirectView);
    }
}
