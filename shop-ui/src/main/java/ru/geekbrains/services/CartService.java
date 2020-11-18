package ru.geekbrains.services;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.SessionData;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService {

  private final Map<SessionData, Integer> sessions;
  private static final long serialVersionUID = -9025621122549454991L;

  @JsonCreator
  public CartService(@JsonProperty("sessionDatas") List<SessionData> sessions) {
    this.sessions = sessions.stream().collect(Collectors.toMap(li -> li, SessionData::getQty));
  }

  public CartService() {
    this.sessions = new HashMap<>();
  }

  public List<SessionData> getSessionDatas() {
    sessions.forEach(SessionData::setQty);
    return new ArrayList<>(sessions.keySet());
  }

  public void updateCart(SessionData sessionData) {
    sessions.put(sessionData, sessionData.getQty());
  }

  public void removeProduct(SessionData sessionData) {
    sessions.remove(sessionData);
  }
}
