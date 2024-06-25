
package login;


import org.junit.Test;
import static org.junit.Assert.*;


public class LoginTest {
  @Test
  public void testCheckUserNameInvalid()
  {
      Login user = new Login("user1","Password1!");
      assertFalse(user.checkUserName());
  }
    @Test
    public void testCheckPasswordComplexityValid()
    {
         Login user = new Login("user&1", "Ch&&sec@ke99!");
      assertTrue(user.checkPasswordComplexity());
    }   
}
