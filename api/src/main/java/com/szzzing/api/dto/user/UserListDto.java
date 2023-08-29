package com.szzzing.api.dto.user;

import lombok.Data;

import java.util.ArrayList;

@Data
public class UserListDto {
    private ArrayList<UserDto> list;
    private int page;
    private int listCount;
}
