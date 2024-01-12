package com.lelisay.CooPayroll10.coremodule.user.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/autheticate")
public class JWTController {
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

//    @PostMapping
//    public String getTokenForAuthenticatedUser(
//            @RequestBody JWTAuthenticationRequest authRequest
//    ){
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        authRequest.getUserName(),
//                        authRequest.getPassword()
//                )
//        );
//        if(authentication.isAuthenticated()){
//            return jwtService.getGeneratedToken(authRequest.getUserName());
//        }else {
//            throw new UsernameNotFoundException("Invalid user credentials");
//        }
//
//
//    }

    @PostMapping
    public String getTokenForAuthenticatedUser(@RequestBody JWTAuthenticationRequest authRequest) {
        System.out.println("Received authentication request: " + authRequest);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUserName(),
                        authRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            System.out.println("User authenticated: " + authRequest.getUserName());
            return jwtService.getGeneratedToken(authRequest.getUserName());
        } else {
            throw new UsernameNotFoundException("Invalid user credentials");
        }
    }

}
