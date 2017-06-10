package BeansFolder;

/**
 * Created by hp on 5/5/2017.
 */

public class Request {

    private String name;
    private int memberId;
    private int applicationFriendAssociationID;

    public int getApplicationFriendAssociationID() {
        return applicationFriendAssociationID;
    }

    public void setApplicationFriendAssociationID(int applicationFriendAssociationID) {
        this.applicationFriendAssociationID = applicationFriendAssociationID;
    }

    public Request(int applicationFriendAssociationID) {

        this.applicationFriendAssociationID = applicationFriendAssociationID;
    }

    public Request(String name, int memberId, int applicationFriendAssociationID) {
        this.name = name;
        this.memberId = memberId;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
