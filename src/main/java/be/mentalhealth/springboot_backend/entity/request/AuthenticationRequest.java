package be.mentalhealth.springboot_backend.entity.request;

public class AuthenticationRequest {
    public String username;
    public String password;
    public String otp;

    public AuthenticationRequest(String username, String password, String otp) {
        this.username = username;
        this.password = password;
        this.otp = otp;
    }

    public AuthenticationRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
