package cafeboard.UserInfo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserInfoRestController {
    private UserInfoService userInfoService;

    public UserInfoRestController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/users/join")
    public UserInfoMyProfileResponseDTO createUserAccount(@RequestBody UserInfoCreateRequestDTO request) {

        return userInfoService.create(request);
    }

    @GetMapping("/users")
    public UserInfoMyProfileResponseDTO readUserProfile(@RequestParam String userId) {

        return userInfoService.read(userId);
    }

    @PutMapping("/users")
    public UserInfoMyProfileResponseDTO updateUserInfo(
            @RequestBody UserInfoUpdateRequestDTO request,
            @RequestParam String userId) {

        return userInfoService.update(request, userId);
    }

    @DeleteMapping("/users")
    public void deleteUser(@RequestParam String userId) {

        userInfoService.delete(userId);
    }
}
