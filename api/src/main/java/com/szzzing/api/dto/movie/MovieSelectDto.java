package com.szzzing.api.dto.movie;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MovieSelectDto {
    private Integer page;
    private final int limit = 7;
    private int offset;
    private String sort;
    private String loginUser;    // 로그인시 코멘트 좋아요 여부 체크
    private String query;
    private int id;
    private String genre;
    private ArrayList<String> genreList;

    public void setOffset() {
        offset = limit * (page-1);
    }
}