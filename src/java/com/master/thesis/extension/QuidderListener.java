/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.master.thesis.extension;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
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
    private int responseType = 401;    

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
        if (parameters.get("pin") != null) {
            System.out.println(parameters.get("pin") + ", " + parameters.get("url") + ", " + parameters.get("summary"));
            if (parameters.get("pin") != null && parameters.get("url") == null && parameters.get("summary") == null) {
                if (validateUser(parameters.get("pin"))) {
                    this.responseType = 202;
                }
            }
            if (validateUser(parameters.get("pin")) && parameters.get("url") != null && parameters.get("summary") != null) {
                this.responseType = 200;
            }
        }
    }

    public boolean validateUser(String userID) {
        String uID = "1234";
        boolean result = false;
        if (userID.equalsIgnoreCase(uID)) {
            result = true;
        }
        return result;
    }
}
