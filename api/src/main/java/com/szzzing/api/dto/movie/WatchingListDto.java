package com.szzzing.api.dto.movie;

import lombok.Data;

import java.util.ArrayList;

@Data
public class WatchingListDto {
    private ArrayList<WatchingDto> list;
    private int page;
}