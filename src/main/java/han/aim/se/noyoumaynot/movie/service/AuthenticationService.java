package han.aim.se.noyoumaynot.movie.service;

import han.aim.se.noyoumaynot.movie.domain.Role;
import han.aim.se.noyoumaynot.movie.domain.User;
import han.aim.se.noyoumaynot.movie.repository.UserToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService {
  ArrayList<UserToken> userTokens = new ArrayList<>();
  ArrayList<User> users = new ArrayList<>();
  Role user = new Role("user", false);
  Role admin = new Role("admin", true);

  public AuthenticationService() {
    users.add(new User("Bob","Build"));
    users.add(new User("John","Doe"));
  }

  public UserToken login(String username, String password) {
    if (password == "admin") {
      UserToken userToken = new UserToken(username);
      userTokens.add(userToken);
      return userToken;
    }
    return null;
  }

  public boolean isValidToken(String token) {
    for (UserToken userToken : userTokens) {
      if (userToken.getToken().equals(token) && !userToken.isExpired()) {
        return true;
      }
    }
    return false;
  }

  public boolean isAdmin(String token, String username) {
    for (UserToken userToken : userTokens) {
      if (userToken.getUsername().equals(admin.getName()) && userToken.getToken().equals(token)) {

      }
    }
    return false;
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
