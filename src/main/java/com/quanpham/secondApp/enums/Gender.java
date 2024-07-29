package com.quanpham.secondApp.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Gender {
    @JsonProperty("male")
    MALE,

    @JsonProperty("female")
    FEMALE,

    @JsonProperty("none")
    NONE,
}
