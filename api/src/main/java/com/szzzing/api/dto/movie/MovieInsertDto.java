package com.szzzing.api.dto.movie;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
public class MovieInsertDto {
    private int id;
    private String title;
    private String originalTitle;
    private String tagline;
    private String overview;
    private Date releaseDate;
    private MultipartFile backdropFile;
    private MultipartFile posterFile;
    private String backdropPath;
    private String posterPath;
    private String genre;
    private int runtime;
}
