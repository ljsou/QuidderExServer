/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.master.thesis.control;

import com.google.gson.Gson;
import com.master.thesis.control.MongoDB;
import com.master.thesis.model.user.FacebookUser;
import com.master.thesis.model.user.TwitterUser;
import com.mongodb.BasicDBObject;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Javier
 */
@ManagedBean
@SessionScoped
public class UserManagedBean {

    private MongoDB mongoDB;
    private String provider;
    private TwitterUser currentTwitterUser;
    private FacebookUser currentFacebookUser;
    private String jsonUserDataProfile;

    /**
     * Creates a new instance of UserManagedBean
     */
    public UserManagedBean() {
        this.mongoDB = new MongoDB();
        this.currentTwitterUser = new TwitterUser();
        this.currentFacebookUser = new FacebookUser();
        this.provider = null;
        this.jsonUserDataProfile = null;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public TwitterUser getCurrentTwitterUser() {
        return currentTwitterUser;
    }

    public void setCurrentTwitterUser(TwitterUser currentTwitterUser) {
        this.currentTwitterUser = currentTwitterUser;
    }

    public FacebookUser getCurrentFacebookUser() {
        return currentFacebookUser;
    }

    public void setCurrentFacebookUser(FacebookUser currentFacebookUser) {
        this.currentFacebookUser = currentFacebookUser;
    }

    public String getJsonUserDataProfile() {
        return jsonUserDataProfile;
    }

    public void setJsonUserDataProfile(String jsonUserDataProfile) {
        this.jsonUserDataProfile = jsonUserDataProfile;
    }

    public void login() {

        if (this.provider != null) {
            if (this.provider.equalsIgnoreCase("twitter")) {

                Gson gson = new Gson();
                TwitterUser twitterUser = gson.fromJson(this.jsonUserDataProfile, TwitterUser.class);
                System.out.println("TwitterUser: " + twitterUser.getFirstName() + " " + twitterUser.getLastName());
                System.out.println("TwitterUserID: " + twitterUser.getID());

                BasicDBObject dBObject = new BasicDBObject("ID", twitterUser.getID());
                String twitterUserJson = this.mongoDB.getJsonFromDB("quidderDB", "users", dBObject);
                System.out.println("Twitter User Status: " + twitterUserJson);
                if (twitterUserJson.equalsIgnoreCase("null")) {
                    this.mongoDB.insertDocumentIntoDB("quidderDB", "users", this.jsonUserDataProfile);
                    this.currentTwitterUser = twitterUser;
                } else {
                    System.out.println("This twitter user already exists in the system");
                    this.currentTwitterUser = twitterUser;
                }
            } else if (this.provider.equalsIgnoreCase("facebook")) {

                Gson gson = new Gson();
                FacebookUser facebookUser = gson.fromJson(this.jsonUserDataProfile, FacebookUser.class);
                System.out.println("Facebook User: " + facebookUser.getFirstName() + " " + facebookUser.getLastName());
                System.out.println("FacebookUserID: " + facebookUser.getID());

                BasicDBObject dBObject = new BasicDBObject("ID", facebookUser.getID());
                String facebookUserJson = this.mongoDB.getJsonFromDB("quidderDB", "users", dBObject);
                System.out.println("Facebook User Status: " + facebookUserJson);
                if (facebookUserJson.equalsIgnoreCase("null")) {
                    this.mongoDB.insertDocumentIntoDB("quidderDB", "users", this.jsonUserDataProfile);
                    this.currentFacebookUser = facebookUser;
                } else {
                    System.out.println("This facebook user already exists in the system");
                    this.currentFacebookUser = facebookUser;
                }
            }
        }
    }        

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(UserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("session ends");
        return "logout";
    }

    public boolean isLoggedIn() {
        System.out.println("enter to isLoggedIn");
        if (this.currentTwitterUser != null) {
            System.out.println("is not Null");
            return true;
        } else if (this.currentFacebookUser != null) {
            System.out.println("is not Null");
            return true;
        } else if (this.currentTwitterUser == null || this.currentFacebookUser == null) {
            System.out.println("is Null");
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(UserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        } else {
            System.out.println("something different happened");
            return false;
        }
    }
}
