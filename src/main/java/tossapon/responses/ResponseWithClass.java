package tossapon.responses;

/**
 * Created by Tossapon Nuanchuay on 6/5/2559.
 */
public class ResponseWithClass<T> extends Response {

    private T data;

    public ResponseWithClass(boolean status, T t) {
        super(status);
        this.data = t;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
