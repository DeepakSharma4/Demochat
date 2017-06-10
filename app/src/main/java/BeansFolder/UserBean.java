package BeansFolder;

/**
 * Created by hp on 4/20/2017.
 */

public class UserBean {



    private String name, emailId;
    private int memberID;


    public UserBean(String name, String emailId, int memberID) {
        this.name = name;
        this.emailId = emailId;
        this.memberID = memberID;

    }

    public String getName() {
        return name;
    }

    public String getEmailId() {
        return emailId;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }
}
