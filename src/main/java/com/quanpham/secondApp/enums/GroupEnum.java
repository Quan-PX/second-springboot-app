package com.quanpham.secondApp.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum GroupEnum {
    @JsonProperty("top")
    TOP,

    @JsonProperty("mid")
    MIDDLE,

    @JsonProperty("bot")
    BOT,
}
