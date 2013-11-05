/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.master.thesis.model.user;

/**
 *
 * @author Javier
 */
class Email {

    private String Type;
    private String Value;

    public Email() {
        System.out.println("email!!!");
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }
}
