package io.depa.controller;

import io.depa.exception.AppException;
import io.depa.model.Role;
import io.depa.model.User;
import io.depa.payload.request.SignInRequest;
import io.depa.payload.request.SignUpRequest;
import io.depa.payload.response.ApiResponse;
import io.depa.payload.response.JwtAuthResponse;
import io.depa.repository.RoleRepository;
import io.depa.repository.UserRepository;
import io.depa.security.JwtTokenProvider;
import io.depa.service.MailService;
import io.depa.util.Constants;
import io.depa.util.Helpers;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          MailService mailService,
                          JwtTokenProvider jwtTokenProvider,
                          PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository,
                          UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.mailService = mailService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestParam(value = "token") String token) {
        // Getting claims
        Claims claims = Jwts.parser()
                .setSigningKey(Constants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        // Creating user
        User user = new User(
                claims.get("email").toString(),
                claims.get("username").toString(),
                claims.get("password").toString());
        // Creating user's roles
        Role userRole = roleRepository.findByName(
                Role.Name.ROLE_USER).orElseThrow(() -> new AppException("User role not set"));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(userRole));
        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsernameOrEmail(), signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthResponse(jwt));
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest,
                                         HttpServletRequest request) {
        // Check availability
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(
                    false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(
                    false, "Email address already in use!"), HttpStatus.BAD_REQUEST);
        }
        // Storing user's claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", signUpRequest.getEmail());
        claims.put("username", signUpRequest.getUsername());
        claims.put("password", signUpRequest.getPassword());
        // Building JWT token
        String confirmationToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(Helpers.getExpiration())
                .signWith(SignatureAlgorithm.HS512, Constants.JWT_SECRET)
                .compact();
        // Building mail message
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(signUpRequest.getEmail());
        mailMessage.setSubject("Registration Confirmation");
        mailMessage.setText("To confirm your e-mail address, please click the link below:\n"
                + request.getHeader("referer")
                + "/api/auth/confirm?token=" + confirmationToken);
        mailMessage.setFrom("artemletter@gmail.com");
        // Sending mail message
        mailService.sendMail(mailMessage);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/signUp")
                .build().toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, "Confirmation email has been sent"));
    }
}
