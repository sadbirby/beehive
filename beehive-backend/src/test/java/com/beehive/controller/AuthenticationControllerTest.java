package com.beehive.controller;

import com.beehive.security.JwtService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtService jwtService;
    @InjectMocks
    AuthenticationController authenticationController;

  /*@Test
  void testAuthenticate() {
    when(authenticationManager.authenticate(any(Authentication.class)))
        .thenReturn(
            new Authentication() {
              @Override
              public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
              }

              @Override
              public Object getCredentials() {
                return null;
              }

              @Override
              public Object getDetails() {
                return null;
              }

              @Override
              public Object getPrincipal() {
                return null;
              }

              @Override
              public boolean isAuthenticated() {
                return true;
              }

              @Override
              public void setAuthenticated(boolean isAuthenticated)
                  throws IllegalArgumentException {}

              @Override
              public String getName() {
                return "";
              }
            });
    when(jwtService.generateToken(anyString())).thenReturn("generatedTokenResponse");

    AuthenticationResponse result =
        authenticationController.authenticate(new AuthenticationRequest("username", "password"));
    Assertions.assertEquals("generatedTokenResponse", result.getToken());
    Assertions.assertEquals("SUCCESS", result.getStatusMessage());
  }*/
}
