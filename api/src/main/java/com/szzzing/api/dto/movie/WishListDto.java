package com.szzzing.api.dto.movie;

import lombok.Data;

import java.util.ArrayList;

@Data
public class WishListDto {
    private ArrayList<WishDto> list;
    private int page;
}