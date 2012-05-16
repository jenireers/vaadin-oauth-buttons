package org.vaadin.oauth;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FacebookApi;

import com.google.gson.annotations.SerializedName;

public class FacebookButton extends OAuthButton {

    private boolean emailScope = false;

    /**
     * Creates a "Log in with Facebook" button that will use the given API
     * key/secret to authenticate the user with Facebook, and then call the
     * given callback with {@link User} details.
     * 
     * @param apiKey
     *            API key from the service providing OAuth
     * @param apiSecret
     *            API secret from the service providing OAuth
     * @param authListener
     *            called once the user has been authenticated
     */
    public FacebookButton(String apiKey, String apiSecret,
            OAuthListener authListener) {
        super("Log in with Facebook", apiKey, apiSecret, authListener);
    }

    /**
     * Creates a button with the given caption that will use the given API
     * key/secret to authenticate the user with Facebook, and then call the
     * given callback with {@link User} details.
     * 
     * @param caption
     *            button caption
     * @param apiKey
     *            API key from the service providing OAuth
     * @param apiSecret
     *            API secret from the service providing OAuth
     * @param authListener
     *            called once the user has been authenticated
     */
    public FacebookButton(String caption, String apiKey, String apiSecret,
            OAuthListener authListener) {
        super(caption, apiKey, apiSecret, authListener);
    }

    /**
     * If enabled, Facebook will be asked to return the users email address,
     * which will also be reflected in the dialog presented to the user by
     * Facebook.
     * 
     * @param enabled
     */
    public void setEmailScope(boolean enabled) {
        emailScope = enabled;
    }

    /**
     * @see FacebookButton#setEmailScope(boolean)
     */
    public boolean isAskForEmail() {
        return emailScope;
    }

    @Override
    protected String getAuthUrl() {
        String url = getService().getAuthorizationUrl(null);
        if (emailScope) {
            url += "&scope=email";
        }
        return url;
    }

    @Override
    protected Class<? extends Api> getApi() {
        return FacebookApi.class;
    }

    @Override
    protected String getVerifierName() {
        return "code";
    }

    @Override
    protected String getJsonDataUrl() {
        return "https://graph.facebook.com/me";
    }

    @Override
    protected Class<? extends User> getUserClass() {
        return FacebookUser.class;
    }

    public static class FacebookUser implements User {
        @SerializedName("first_name")
        private String firstName;
        @SerializedName("last_name")
        private String lastName;
        private String username;
        private String id;
        private String link;
        private String email;
        private String token;
        private String tokenSecret;

        public String getToken() {
            return token;
        }

        public String getTokenSecret() {
            return tokenSecret;
        }

        public String getName() {
            return firstName + " " + lastName;
        }

        public String getScreenName() {
            return username;
        }

        public String getPictureUrl() {
            return "https://graph.facebook.com/" + id + "/picture";
        }

        public String getId() {
            return id;
        }

        public String getPublicProfileUrl() {
            return link;
        }

        public String getService() {
            return "facebook";
        }

        public String getEmail() {
            return email;
        }
    }
}