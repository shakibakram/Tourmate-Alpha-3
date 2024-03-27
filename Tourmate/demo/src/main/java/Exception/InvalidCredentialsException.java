package Exception;

public class InvalidCredentialsException extends Throwable{
    public InvalidCredentialsException(){
        super("Invalid username and/or password.");
    }
}