package cafeboard.UserInfo;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    private UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfoMyProfileResponseDTO create(UserInfoCreateRequestDTO request) {

        UserInfo userInfo = userInfoRepository.save(new UserInfo(request.userId(), request.nickname(), request.password()));
        return new UserInfoMyProfileResponseDTO(userInfo.getUserId(), userInfo.getName());
    }

    public UserInfoMyProfileResponseDTO read(String userId) {

        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        return new UserInfoMyProfileResponseDTO(userInfo.getUserId(), userInfo.getName());
    }

    @Transactional
    public UserInfoMyProfileResponseDTO update(UserInfoUpdateRequestDTO request, String userId) {

        // todo - 지금 방식은 항상 수정 시 닉네임과 비밀번호를 사용자가 입력해야된다.
        // 닉네임이나 비밀번호 중 하나만 변경하고 싶은 경우도 처리할 수 있도록 수정하기
        // 이 상태에서 닉네임이나 비번중 하나만 수정할 경우 나머지 하나에 NULL값이 들어가는 문제가 발생할 수 있음
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        userInfo.setNickname(request.nickname());
        userInfo.setPassword(request.password());

        return new UserInfoMyProfileResponseDTO(userInfo.getUserId(), userInfo.getName());
    }

    public Long delete(String userId) {

        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        userInfoRepository.deleteById(userInfo.getId());

        return userInfo.getId();
    }
}
