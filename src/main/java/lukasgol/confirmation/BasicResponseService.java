package lukasgol.confirmation;

public class BasicResponseService implements ResponseService {
    @Override
    public void processResponse(Response response) {
        System.out.println(response.toString());
    }
}
