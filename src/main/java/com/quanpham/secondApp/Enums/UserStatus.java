package com.quanpham.secondApp.Enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserStatus {
    @JsonProperty("active")
    ACTTIVE,

    @JsonProperty("inactive")
    INACTTIVE,

    @JsonProperty("none")
    NONE,

}
