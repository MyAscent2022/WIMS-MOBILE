package com.example.wims_new.model;

public class ResBody {

    private String message;
    private boolean save;
    private boolean to_confirm;
    boolean error;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public boolean isTo_confirm() {
        return to_confirm;
    }

    public void setTo_confirm(boolean to_confirm) {
        this.to_confirm = to_confirm;
    }
}
