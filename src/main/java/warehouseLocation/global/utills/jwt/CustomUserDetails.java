package warehouseLocation.global.utills.jwt;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class CustomUserDetails implements UserDetails {

  private String username;
  private Long userId;


  public CustomUserDetails(String username, Long userId) {
    this.username = username;
    this.userId = userId;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean isAccountNonLocked() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean isCredentialsNonExpired() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean isEnabled() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
