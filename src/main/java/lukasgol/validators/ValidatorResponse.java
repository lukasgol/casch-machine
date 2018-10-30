package lukasgol.validators;

import lukasgol.confirmation.Response;

import java.util.Objects;

public class ValidatorResponse {
    private final Response response;
    private final boolean isOk;

    public ValidatorResponse(Response response, boolean isOk) {
        this.response = response;
        this.isOk = isOk;
    }

    public Response getResponse() {
        return response;
    }

    public boolean isOk() {
        return isOk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidatorResponse that = (ValidatorResponse) o;
        return isOk == that.isOk &&
                response == that.response;
    }

    @Override
    public int hashCode() {

        return Objects.hash(response, isOk);
    }
}
