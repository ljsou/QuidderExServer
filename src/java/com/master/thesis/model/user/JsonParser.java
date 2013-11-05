/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.master.thesis.model.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Javier
 */
public class JsonParser {

    TwitterUser twitterUser;
    FacebookUser facebookuser;

    public JsonParser() {
        this.twitterUser = new TwitterUser();
        this.facebookuser = new FacebookUser();
    }

    public JsonParser(String jsonUserProfile, String provider) {
        System.out.println("JSON: " + jsonUserProfile);
        Gson gson = new Gson();

        if (provider != null && provider.equalsIgnoreCase("facebook")) {
            System.out.println("entró Gson FromJson Facebook!");
            this.facebookuser = gson.fromJson(jsonUserProfile, FacebookUser.class);
        } else if (provider != null && provider.equalsIgnoreCase("twitter")) {
            System.out.println("entró Gson FromJson Twitter!");
            this.twitterUser = gson.fromJson(jsonUserProfile, TwitterUser.class);
        } else {
            System.out.println("otra cosa pasó aquí");
        }
        //System.out.println("entró al JsonParser");
//        System.out.println("ID: " + user.getID());
//        System.out.println("Fulname: " + user.getFullName());
//        System.out.println("Profile: " + user.getProfileName());
    }

    public TwitterUser getTwitterUser() {
        return this.twitterUser;
    }

    public void setTwitterUser(TwitterUser twitterUser) {
        this.twitterUser = twitterUser;
    }

    public FacebookUser getFacebookuser() {
        return facebookuser;
    }

    public void setFacebookuser(FacebookUser facebookuser) {
        this.facebookuser = facebookuser;
    }
}
