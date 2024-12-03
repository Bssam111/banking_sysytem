import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String encryptedPassword;
    private String email;
    private String phoneNumber;
    private String fullName;
    private String birthDate;
    private String nationalId;
    private String address;
    private Account account;

    public User(String username, String encryptedPassword, String email, String phoneNumber,
                String fullName, String birthDate, String nationalId, String address) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.nationalId = nationalId;
        this.address = address;
        this.account = new Account(new IBAN().generateRandomIBAN());
    }

  
    public String getUsername() {
        return username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Account getAccount() {
        return account;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }


    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public void setAccount(Account account) {
        this.account = account;
    }


    // Add getters and setters for the new fields
    public String getFullName() {
        return fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getAddress() {
        return address;
    }
}
