package han.aim.se.noyoumaynot.movie.service;

import han.aim.se.noyoumaynot.movie.domain.Role;
import han.aim.se.noyoumaynot.movie.domain.User;
import han.aim.se.noyoumaynot.movie.repository.UserToken;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.ArrayList;

@Service
public class AuthenticationService {
  ArrayList<UserToken> userTokens = new ArrayList<>();
  ArrayList<User> users = new ArrayList<>();
  Role user = new Role("user", false);
  Role admin = new Role("admin", true);

  public AuthenticationService() {
    users.add(new User("Bob","Build", user));
    users.add(new User("John","Doe", admin));
  }

<<<<<<< Updated upstream
  public UserToken login(String username, String password) {
    if (password.equals("admin")) {
      UserToken userToken = new UserToken(username);
      userTokens.add(userToken);
      return userToken;
=======
  public String login(String username, String password) {
    for (User user : users) {
      if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
        UserToken userToken = new UserToken(username);
        userTokens.add(userToken);
        return userToken.getToken();
      }
>>>>>>> Stashed changes
    }
    return "Login failed, wrong username or password";
  }

  public boolean isValidToken(String token) {
    for (UserToken userToken : userTokens) {
      if (userToken.getToken().equals(token) && !userToken.isExpired()) {
        return true;
      }
    }
    return false;
  }

  public boolean isAdmin(String token, User user) throws AuthenticationException {
    for (UserToken userToken : userTokens) {
      if (user.getRole().getAdmin() && userToken.getToken().equals(token)) {
        return true;
      }
    }
    throw new AuthenticationException("Unauthorized role detected");
  }

  public String getUsername(String token) {
    for (UserToken userToken : userTokens) {
      if (userToken.getToken().equals(token)) {
        return userToken.getUsername();
      }
    }
    return null;
  }
}
