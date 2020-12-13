package star.example.stargaze.callback;

import star.example.stargaze.models.response.ProfileUpdateResp;

public interface UpdateProfileListener {
    void onProfileUpdate(ProfileUpdateResp resp);
}
