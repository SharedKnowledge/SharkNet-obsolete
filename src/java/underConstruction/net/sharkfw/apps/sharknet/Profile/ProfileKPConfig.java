package Profile;

/**
 * Created by s0539720 on 29.04.2015.
 */
public interface ProfileKPConfig {
    /**
     * This configuration determines whether unsigned messages are accepted.
     * @param acceptWithoutVerification
     */
    public void setAcceptWithoutVerification(boolean acceptWithoutVerification);
    public boolean getAcceptWithoutVerification();

    /**
     * Configuration to determine whether profiles should be sent to an unknown peer.
     * @param sendProfiles2UnknownPeer
     */
    public void setSendProfiles2UnknownPeer(boolean sendProfiles2UnknownPeer);
    public boolean getSendProfiles2UnknownPeer();

    /**
     * Configuration to determine whether the users own profile is sent automatically when a profile is received.
     * @param sendMyProfileAutomatically
     */
    public void setSendMyProfileAutomatically(boolean sendMyProfileAutomatically);
    public boolean getSendMyProfileAutomatically();

    /**
     * Configuration to determine whether the peer that requests a profile from the user is asked for all of
     * his/her profiles automatically.
     * @param ask4ProfilesAutomatically
     */
    public void setAsk4ProfilesAutomatically(boolean ask4ProfilesAutomatically);
    public boolean getAsk4ProfilesAutomatically();

    /**
     * This configuration determines whether a profile should be accepted if the owner is someone else than the
     * person that the profile is of.
     * @param acceptProfileWithDifferentOwner
     */
    public void setAcceptProfileWithDifferentOwner(boolean acceptProfileWithDifferentOwner);
    public boolean getAcceptProfileWithDifferentOwner();

    /**
     * Configuration to determine whether peers with whom a WiFi-Direct connection is established are asked for their
     * profiles automatically.
     * @param ask4ProfilesAutomaticallyOnWiFiDirectConnection
     */
    public void setAsk4ProfilesAutomaticallyOnWiFiDirectConnection(boolean ask4ProfilesAutomaticallyOnWiFiDirectConnection);
    public boolean getAsk4ProfilesAutomaticallyOnWiFiDirectConnection();

    /**
     * This configuration determines whether the users profile is sent automatically when a WiFi-Direct connection is
     * established.
     * @param sendProfileAutomaticallyOnWiFiDirectConnection
     */
    public void setSendProfileAutomaticallyOnWiFiDirectConnection(boolean sendProfileAutomaticallyOnWiFiDirectConnection);
    public boolean getSendProfileAutomaticallyOnWiFiDirectConnection();
}
