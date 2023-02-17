package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.User;

public class HomeController {
    private static User loggedInUser = null;

    public void initUser(User user) {
        if (loggedInUser == null)
            loggedInUser = user;
    }

    public void logOutUser() {
        loggedInUser = null;
    }
}
