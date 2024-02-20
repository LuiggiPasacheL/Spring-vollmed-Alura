package med.voll.api.util;

@lombok.Getter
@lombok.Setter
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
