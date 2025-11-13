package DTO;


import lombok.Data;

@Data
public class ShortLinkCreateReqDTO {
    private String originUrl;
    private String gid;
    private Integer createdType;
    private Integer validDateType;
    private String describe;
}

