package it.uniroma3.siw.tornei.exception;

public class UsernameGiaRegistratoException extends RuntimeException {

	public UsernameGiaRegistratoException() {
		super("Username già registrato");
	}
}