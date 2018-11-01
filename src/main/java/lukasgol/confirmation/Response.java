package lukasgol.confirmation;

public enum Response {

    NOT_ENOUGH_BANKNOTES("There is not enough banknotes"),
    NOT_ENOUGH_ON_ACCOUNT("There is not enough money on account"),
    OK("Done"), BAD_AMOUNT("Wrong amount"), TOO_MANY_BANKNOTES_TO_WITHDRAWAL("too many banknotes in one operation"), TO0_BIG_AMOUNT_TO_WITHDRAWAL("too big amount to withdrawal");

    private final String message;


    Response(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "response message='" + message + '\'' +
                '}';
    }
}
