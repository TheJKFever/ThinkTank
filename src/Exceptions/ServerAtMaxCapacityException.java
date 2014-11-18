package Exceptions;

public class ServerAtMaxCapacityException extends Exception {
	public ServerAtMaxCapacityException(String msg) {
		super(msg);
	}
}
