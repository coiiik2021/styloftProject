package org.sale.project.controller.auth;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.sale.project.entity.FacebookAccount;

import java.io.IOException;

public class FacebookLogin {
    public static String getToken(String code) throws IOException {
        String response = Request.Post(FaceTant.FACEBOOK_LINK_GET_TOKEN)
                .bodyForm(
                        Form.form()
                                .add("client_id", FaceTant.FACEBOOK_CLIENT_ID)
                                .add("client_secret", FaceTant.FACEBOOK_CLIENT_SECRET)
                                .add("redirect_uri", FaceTant.FACEBOOK_REDIRECT_URI)
                                .add("code", code)
                                .build()
                )
                .execute().returnContent().asString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        return jobj.get("access_token").toString().replaceAll("\"", "");
    }
    public static FacebookAccount getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = FaceTant.FACEBOOK_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        return new Gson().fromJson(response, FacebookAccount .class);
    }
}
