package com.quanpham.secondApp.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PermissionEnum {
    @JsonProperty("fullAccess")
    FULLACCESS,

    @JsonProperty("view")
    VIEW,

    @JsonProperty("add")
    ADD,

    @JsonProperty("update")
    UPDATE,

    @JsonProperty("delete")
    DELETE,

    @JsonProperty("upload")
    UPLOAD,

    @JsonProperty("export")
    EXPORT,

    @JsonProperty("import")
    IMPORT,

    @JsonProperty("send")
    SEND,

    @JsonProperty("share")
    SHARE,
}
