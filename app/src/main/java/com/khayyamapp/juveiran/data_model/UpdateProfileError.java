package com.khayyamapp.juveiran.data_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProfileError {

@SerializedName("message")
@Expose
private String message;
@SerializedName("errors")
@Expose
private Errors errors;

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public Errors getErrors() {
return errors;
}

public void setErrors(Errors errors) {
this.errors = errors;
}

}