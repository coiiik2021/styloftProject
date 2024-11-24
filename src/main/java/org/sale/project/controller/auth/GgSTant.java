package org.sale.project.controller.auth;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GgSTant {


    public static final String GOOGLE_CLIENT_ID = "264565889451-li40qm8elg7569blr27vkuvb23qbmqe1.apps.googleusercontent.com";

    public static final String GOOGLE_CLIENT_SECRET = "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";

    public static final String GOOGLE_REDIRECT_URI =  "google";

    public static final String GOOGLE_GRANT_TYPE = "authorization_code";

    public static final String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";

    public static final String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
}
