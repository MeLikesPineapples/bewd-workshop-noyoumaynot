package han.aim.se.noyoumaynot.movie.service;

import han.aim.se.noyoumaynot.movie.repository.UserToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService {
  ArrayList<UserToken> userTokens = new ArrayList<>();

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
      if (userToken.getToken().equals(token)) {
        return true;
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
