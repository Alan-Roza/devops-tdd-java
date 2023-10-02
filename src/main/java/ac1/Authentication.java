package ac1;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Authentication {
	private List<AuthUser> databaseUsers = new ArrayList<>();
	
	public Authentication() {
		List<AuthUser> users = Arrays.asList(
				new AuthUser("Admin", "adminPassword"),
				new AuthUser("Alan", "rozaworks"),
				new AuthUser("Matheus", "123456"),
				new AuthUser("Ana", "julia321")
		);
		
		getDatabaseUsers().addAll(users);
	}


	public ValidationResult validate(String user, String password) {
	    int limitTriesToBlockUser = 6;
	    
	    if (user == null || user.trim().isEmpty() || password == null || password.trim().isEmpty()) {
	        return new ValidationResult(false, "Usuário e Senha são obrigatórios");
	    }

	    for (AuthUser authUser : getDatabaseUsers()) {
	        if (authUser.getUser().equals(user.trim())) {
	            if (authUser.getFailTries() >= limitTriesToBlockUser) {
	                return new ValidationResult(false, "Credencial bloqueada temporariamente, tente novamente em 3 horas.");
	            }

	            if (authUser.getPassword().equals(password.trim())) {                                        
	                authUser.setFailTries(0);
	                return new ValidationResult(true, null);
	            }

	            authUser.setFailTries(authUser.getFailTries() + 1);
	        }
	    }

	    return new ValidationResult(false, "Usuário ou Senha inválidos!");
	}


	public List<AuthUser> getDatabaseUsers() {
		return databaseUsers;
	}

}
