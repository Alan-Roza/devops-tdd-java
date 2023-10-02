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
	    if (areCredentialsMissing(user, password)) {
	        return new ValidationResult(false, "Usuário e Senha são obrigatórios");
	    }

	    AuthUser authUser = findUserInDatabase(user.trim());

	    if (authUser == null) {
	        return new ValidationResult(false, "Usuário ou Senha inválidos!");
	    }

	    if (isUserBlocked(authUser)) {
	        return new ValidationResult(false, "Credencial bloqueada temporariamente, tente novamente em 3 horas.");
	    }

	    if (isPasswordValid(authUser, password.trim())) {
	        authUser.setFailTries(0);
	        return new ValidationResult(true, null);
	    }

	    authUser.setFailTries(authUser.getFailTries() + 1);
	    return new ValidationResult(false, "Usuário ou Senha inválidos!");
	}

	private boolean areCredentialsMissing(String user, String password) {
	    return user == null || user.trim().isEmpty() || password == null || password.trim().isEmpty();
	}

	private AuthUser findUserInDatabase(String username) {
	    for (AuthUser authUser : getDatabaseUsers()) {
	        if (authUser.getUser().equals(username)) {
	            return authUser;
	        }
	    }
	    return null;
	}

	private boolean isUserBlocked(AuthUser authUser) {
	    int limitTriesToBlockUser = 6;
	    return authUser.getFailTries() >= limitTriesToBlockUser;
	}

	private boolean isPasswordValid(AuthUser authUser, String password) {
	    return authUser.getPassword().equals(password);
	}


	public List<AuthUser> getDatabaseUsers() {
		return databaseUsers;
	}

}
