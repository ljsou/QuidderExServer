/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.master.thesis.extension;

import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
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
    private String url = "//";
    private int responseType = 401;
    private String userID = null;

    public void gParameters() throws IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//        String url = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("url");
//        System.out.println("3. URL: " + url);       


        switch (this.responseType) {
            case 401:
                System.out.println("401");
                response.setContentType("text/html");
                response.addHeader("nombre", "empty");
                response.setStatus(response.SC_UNAUTHORIZED);
                break;
            case 200:
                System.out.println("200 OK");
                response.setContentType("text/html");
                response.addHeader("nombre", "javier");
                response.setStatus(response.SC_OK);
                this.responseType = 401;
                break;
            case 202:
                System.out.println("202 Acepted");
                response.setContentType("text/html");
                response.setStatus(response.SC_ACCEPTED);
                this.responseType = 401;
                break;
        }

//        System.out.println("getAuthType: " + request.getAuthType()
//                + "\n getContextPath: " + request.getContextPath()
//                + "\n getLocalAddr: " + request.getLocalAddr()
//                + "\n getLocalName: " + request.getLocalName()
//                + "\n getMethod: " + request.getMethod()
//                + "\n getPathInfo: " + request.getPathInfo()
//                + "\n getPathTranslated: " + request.getPathTranslated()
//                + "\n getQueryString: " + request.getQueryString()
//                + "\n getRemoteAddr: " + request.getRemoteAddr()
//                + "\n getRemoteHost: " + request.getRemoteHost()
//                + "\n getRemoteUser: " + request.getRemoteUser()
//                + "\n getServerName: " + request.getServerName()
//                + "\n getRequestURL: " + request.getRequestURL().toString());

        Enumeration parameterList = request.getParameterNames();
        while (parameterList.hasMoreElements()) {
            String sName = parameterList.nextElement().toString();
            String[] sMultiple = request.getParameterValues(sName);
//            System.out.println("sName: " + sName);
            if ((1 >= sMultiple.length)) // parameter has a single value. print it.
            {
                if ((this.userID == null) && (validateUser(request.getParameter("pin")))) {
                    this.userID = request.getParameter("pin");
                    System.out.println("User " + this.userID + " is logged!");
                    System.out.println(sName + " = " + request.getParameter(sName));
                    this.responseType = 202;
                }if (this.userID != null){
                    System.out.println(sName + " = " + request.getParameter(sName));
                    this.responseType = 200;
                }

//                System.out.println(sName + " = " + request.getParameter(sName));
            } else {
                for (int i = 0; i < sMultiple.length; i++) // if a paramater contains multiple values, print all of them
                {
                    System.out.println(sName + "[" + i + "] = " + sMultiple[i]);
                }
            }
        }

    }

    public String getUrl() {
        return this.url;
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
