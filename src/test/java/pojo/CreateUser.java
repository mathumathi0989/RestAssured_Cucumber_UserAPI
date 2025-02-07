package pojo;

public class CreateUser {
	private String user_first_name;
    private String user_last_name;
    private String user_contact_number;
    private String user_email_id;
    private UserAddress userAddress;

    // Getters and Setters
    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_last_name() {
        return user_last_name;
    }

    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getUser_contact_number() {
        return user_contact_number;
    }

    public void setUser_contact_number(String user_contact_number) {
        this.user_contact_number = user_contact_number;
    }

    public String getUser_email_id() {
        return user_email_id;
    }

    public void setUser_email_id(String user_email_id) {
        this.user_email_id = user_email_id;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    // Inner POJO class for UserAddress
    public static class UserAddress {
        private String plotNumber;
        private String street;
        private String state;
        private String country;
        private String zipCode;

        // Getters and Setters
        public String getPlotNumber() {
            return plotNumber;
        }

        public void setPlotNumber(String plotNumber) {
            this.plotNumber = plotNumber;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }
    
}
