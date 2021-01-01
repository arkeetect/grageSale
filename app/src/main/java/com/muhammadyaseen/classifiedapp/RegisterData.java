package com.muhammadyaseen.classifiedapp;

public class RegisterData {
    public String register_Name;
    public String register_Email;
//Empty constructor compulsory
    public RegisterData() {
    }
//  constructor
    public RegisterData(String register_Name, String register_Email) {
        this.register_Name = register_Name;
        this.register_Email = register_Email;
    }
//............Setter and Getter
    public String getRegister_Name() {
        return register_Name;
    }

    public void setRegister_Name(String register_Name) {
        this.register_Name = register_Name;
    }

    public String getRegister_Email() {
        return register_Email;
    }

    public void setRegister_Email(String register_Email) {
        this.register_Email = register_Email;
    }
    //.................................End Setter and Getter ...........//

}
