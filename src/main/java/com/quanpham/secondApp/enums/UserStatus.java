package com.quanpham.secondApp.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserStatus {
    @JsonProperty("active")
    ACTIVE,

    @JsonProperty("inactive")
    INACTTIVE,

    @JsonProperty("none")
    NONE,

}
