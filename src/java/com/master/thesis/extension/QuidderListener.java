/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.master.thesis.extension;

import com.master.thesis.control.MongoDB;
import com.mongodb.BasicDBObject;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Luis Javier Surez Meza <luisjavier.suarezmeza at gmail.com>
 */
@ManagedBean
@SessionScoped
public class QuidderListener implements Serializable {

    private static final long serialVersionUID = 8799656478674716638L;
    private int responseType;
    private MongoDB mongoDB;

    public QuidderListener() {
        this.responseType = 401;
        this.mongoDB = new MongoDB();
    }

    public void gParameters() throws IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        Map<String, String> parameters = new HashMap<String, String>();

        switch (this.responseType) {
            case 401:
                System.out.println("401 Unauthorized");
                response.setContentType("text/html");
                response.addHeader("Warning", "Incorrect user information!");
                response.setStatus(response.SC_UNAUTHORIZED);
                break;
            case 200:
                System.out.println("200 OK");
                response.setContentType("text/html");
                response.addHeader("Info:", "Saved!");
                response.setStatus(response.SC_OK);
                this.responseType = 401;
                break;
            case 202:
                System.out.println("202 Acepted");
                response.setContentType("text/html");
                response.addHeader("Acepted:", "Welcome!");
                response.setStatus(response.SC_ACCEPTED);
                this.responseType = 401;
                break;
        }

        Enumeration parameterList = request.getParameterNames();
        while (parameterList.hasMoreElements()) {
            String sName = parameterList.nextElement().toString();
            String value = request.getParameter(sName);
            parameters.put(sName, value);
            //System.out.println(sName + ": " + value);

        }
        if (searchInMongoByID(parameters.get("pin"))) {
            System.out.println(parameters.get("pin") + ", " + parameters.get("url") + ", " + parameters.get("summary"));
            if (parameters.get("url") == null && parameters.get("summary") == null) {
                System.out.println("-----202-----");
                this.responseType = 202;
            }
            if (parameters.get("url") != null && parameters.get("summary") != null) {
                System.out.println("-----200-----");
                this.responseType = 200;
            }
        }
    }

    public boolean searchInMongoByID(String id) {
        boolean result = false;
        BasicDBObject dBObject = new BasicDBObject("ID", id);
        String userJson = this.mongoDB.getJsonFromDB("quidderDB", "users", dBObject);
        System.out.println("User Status: " + userJson);
        if (!userJson.equalsIgnoreCase("null")) {
            System.out.println("This user exists in the system (Twitter).");
            result = true;
        }
        return result;
    }
}
