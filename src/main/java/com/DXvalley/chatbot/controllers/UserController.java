package com.DXvalley.chatbot.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import com.DXvalley.chatbot.DTO.PasswordChangeDTO;
import com.DXvalley.chatbot.DTO.UserProfileDTO;
import com.DXvalley.chatbot.service.EmailService;
import com.DXvalley.chatbot.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;
import com.DXvalley.chatbot.models.Users;
import com.DXvalley.chatbot.repository.RoleRepository;
import com.DXvalley.chatbot.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manageAdmins")
public class UserController {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final FileUploadService fileUploadService;
    @Autowired
    EmailService emailService;

    private boolean isSysAdmin() {
        AtomicBoolean hasSysAdmin = new AtomicBoolean(false);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getAuthorities().forEach(grantedAuthority -> {
            if (grantedAuthority.getAuthority().equals("sysAdmin")) {
                hasSysAdmin.set(true);
            }
        });
        return hasSysAdmin.get();
    }

    private boolean isOwnAccount(String userName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername((String) auth.getPrincipal()).getUsername().equals(userName);
    }

    @GetMapping("/getUsers")
    List<Users> getUsers() {
        if (isSysAdmin()) {
            return this.userRepository.findAll(Sort.by("username"));
        }
        var users = this.userRepository.findAll(Sort.by("username"));
        users.removeIf(user -> {
            var containsAdmin = false;
            for (var role : user.getRoles()) {
                containsAdmin = containsAdmin || role.getRoleName().equals("Admin");
            }
            return containsAdmin;
        });
        return users;
    }
    @GetMapping("/getUserByUsernameOrEmail/{usernameOrEmail}")
    public ResponseEntity<?> getUserByUsernameOrEmail(@PathVariable String usernameOrEmail) {
        var user = userRepository.findByEmailOrUsername(usernameOrEmail, usernameOrEmail);
        if (user == null) {
            createUserResponse response = new createUserResponse("error", "Cannot find this user!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable Long userId) {
        var user = userRepository.findByUserId(userId);
        if (user == null) {
            createUserResponse response = new createUserResponse("error", "Cannot find this user!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/createUser")
    public ResponseEntity<createUserResponse> accept(@RequestBody Users tempUser) {
        var user = userRepository.findByUsername(tempUser.getUsername());
        String password;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (user != null) {
            createUserResponse response = new createUserResponse("error", "user already exists");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        tempUser.setRoles(tempUser.getRoles().stream().map(x -> this.roleRepo.findByRoleName(x.getRoleName())).collect(Collectors.toList()));
        password = tempUser.getPassword();
        tempUser.setPassword(passwordEncoder.encode(tempUser.getPassword()));
        tempUser.setFullName(tempUser.getFullName());
        tempUser.setCreatedAt(LocalDateTime.now().format(dateTimeFormatter));
        tempUser.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgBhcplevwUKGRs1P-Ps8Mwf2wOwnW_R_JIA&usqp=CAU");
        tempUser.setCoverImgUrl("http://res.cloudinary.com/do394twgw/image/upload/v1680341073/zpvhxhpk0gpuiuoxvnwe.png");
        userRepository.save(tempUser);
        emailService.sendEmail(tempUser.getEmail(), "user created!",tempUser.getFullName(),"USER_CREATED",tempUser.getUsername(),password);
        createUserResponse response = new createUserResponse("success", "user created successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/uploadCoverImg/{userNameOrEmail}")
    public ResponseEntity<?> uploadCoverImg(@RequestParam MultipartFile coverImg, @PathVariable String userNameOrEmail) {
        String coverImgUrl = null;
        createUserResponse response;
        Users user = userRepository.findByEmailOrUsername(userNameOrEmail, userNameOrEmail);
        if (user == null) {
            response = new createUserResponse("error", "user does not exist");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            try {
                coverImgUrl = fileUploadService.uploadFile(coverImg);
            } catch (Exception e) {
                response = new createUserResponse("error", "Bad file size or format!");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            user.setCoverImgUrl(coverImgUrl);
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }
    @PutMapping("/uploadProfileImg/{userNameOrEmail}")
    public ResponseEntity<?> uploadProfileImg(@RequestParam MultipartFile profileImg, @PathVariable String userNameOrEmail) {
        String profileImgUrl = null;
        createUserResponse response;
        Users user = userRepository.findByEmailOrUsername(userNameOrEmail, userNameOrEmail);
        if (user == null) {
            response = new createUserResponse("error", "user does not exist");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            try {
                profileImgUrl = fileUploadService.uploadFile(profileImg);
            } catch (Exception e) {
                response = new createUserResponse("error", "Bad file size or format!");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            user.setImageUrl(profileImgUrl);
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @GetMapping("/getUserProfile/{userNameOrEmail}")
    public ResponseEntity<?> getUserProfile(@PathVariable String userNameOrEmail) {

        Users user = userRepository.findByEmailOrUsername(userNameOrEmail, userNameOrEmail);
        UserProfileDTO userProfileDTO = new UserProfileDTO();


        if (user == null) {
            ResponseMessage response;
            response = new ResponseMessage("Unable to get user");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            userProfileDTO.setEmail(user.getEmail());
            userProfileDTO.setFullName(user.getFullName());
            userProfileDTO.setRoles(user.getRoles());
            userProfileDTO.setCoverImgUrl(user.getCoverImgUrl());
            userProfileDTO.setImageUrl(user.getImageUrl());
            return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
        }

    }

    @PutMapping("/changePassword/{userNameOrEmail}")
    public ResponseEntity<pinchangeResponse> changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO, @PathVariable String userNameOrEmail) {
        Users user = userRepository.findByEmailOrUsername(userNameOrEmail, userNameOrEmail);
        pinchangeResponse response;
        if ((user == null)) {
            response = new pinchangeResponse("User does not exist");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (!passwordEncoder.matches(passwordChangeDTO.getOldPassword(), user.getPassword())) {
            response = new pinchangeResponse("Wrong Password");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else if (passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getOldPassword())) {
            response = new pinchangeResponse("Choose another password");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            user.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));

            emailService.sendEmail(user.getEmail(), "password changed!",user.getFullName(),"PASSWORD_CHANGED",null,null);
            userRepository.save(user);
            response = new pinchangeResponse("Password Change successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
    @PutMapping("/changePin/{phoneNumber}")
    public ResponseEntity<pinchangeResponse> pinChange(@RequestBody Users tempUser,
                                                       @PathVariable String phoneNumber) {
        Users user = userRepository.findByUsername(phoneNumber);
        // edit(tempUser,user);
        // user.setUsername(tempUser.getUsername());

        user.setPassword(passwordEncoder.encode(tempUser.getPassword()));
        userRepository.save(user);
        // .setPassword(null);
        pinchangeResponse response = new pinchangeResponse("success");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @PutMapping("/manageAccount/{userName}/{usernameChange}")
    public Users manageAccount(@RequestBody UsernamePassword temp,
                               @PathVariable String userName,
                               @PathVariable Boolean usernameChange) throws AccessDeniedException {
        if (isOwnAccount(userName)) {
            Users user = userRepository.findByUsername(userName);
            if (passwordEncoder.matches(temp.getOldPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(temp.getNewPassword()));
            }
            if (usernameChange) {
                user.setUsername(temp.getNewUsername());
            }
            Users response = userRepository.save(user);
            response.setPassword(null);
            return response;
        } else
            throw new AccessDeniedException("403 Forbidden");
    }

    @DeleteMapping("/delete/user/{userId}")
    void deleteUser(@PathVariable Long userId) {
        this.userRepository.deleteById(userId);
    }

}

@Getter
@Setter
class UsernamePassword {
    private String newUsername;
    private String newPassword;
    private String oldPassword;
}

@Getter
@Setter
@AllArgsConstructor
class createUserResponse {
    String status;
    String description;
}

@Getter
@Setter
@AllArgsConstructor
class pinchangeResponse {
    String status;
}

@Getter
@Setter
@AllArgsConstructor
class ResponseMessage {
    String status;
}
