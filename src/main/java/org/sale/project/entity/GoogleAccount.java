package org.sale.project.entity;

import lombok.Data;

@Data

public class GoogleAccount {
    private String  id, email, name, first_name, given_name, family_name, picture;

    private boolean verified_email;
}
