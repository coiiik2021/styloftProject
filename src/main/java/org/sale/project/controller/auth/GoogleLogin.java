package org.sale.project.controller.auth;

 import com.google.gson.Gson;
 import com.google.gson.JsonObject;
//import com.nimbusds.jose.shaded.gson.Gson;
//import com.nimbusds.jose.shaded.gson.JsonObject;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
// import org.apache.http.client.ClientProtocolException;
 import org.apache.http.client.fluent.Form;
 import org.apache.http.client.fluent.Request;
import org.sale.project.entity.GoogleAccount;

//import org.apache.hc.core5.http.ClientProtocolException;

import java.io.IOException;

public class GoogleLogin {
    public String getToken(String code) throws IOException {

        String response = Request.Post(GgSTant.GOOGLE_LINK_GET_TOKEN)

                .bodyForm(

                        Form.form()

                                .add("client_id", GgSTant.GOOGLE_CLIENT_ID)

                                .add("client_secret", GgSTant.GOOGLE_CLIENT_SECRET)

                                .add("redirect_uri", GgSTant.GOOGLE_REDIRECT_URI)

                                .add("code", code)

                                .add("grant_type", GgSTant.GOOGLE_GRANT_TYPE)

                                .build()

                )

                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);

        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");

        return accessToken;

    }

    public GoogleAccount getUserInfo(String accessToken) throws Exception {

        String link = GgSTant.GOOGLE_LINK_GET_USER_INFO + accessToken;

        String response = Request.Get(link).execute().returnContent().asString();

        return new Gson().fromJson(response, GoogleAccount.class);

    }
}
