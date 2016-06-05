package tossapon.responses;

/**
 * Created by Tossapon Nuanchuay on 6/5/2559.
 */
public class Response {
    private boolean status;
    private String reason;

    public Response(boolean status) {
        this.status = status;
    }

    public Response(boolean status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }
}
