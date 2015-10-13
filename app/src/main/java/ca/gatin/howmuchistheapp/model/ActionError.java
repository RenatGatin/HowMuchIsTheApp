package ca.gatin.howmuchistheapp.model;

/**
 * @author RGatin
 * @since 12-Oct-2015
 */
public class ActionError {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ActionError(String code, String message){
        this.code = code;
        this.message = message;
    }
}
