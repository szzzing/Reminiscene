package com.szzzing.api.service;

import com.szzzing.api.dto.comment.CommentDto;
import com.szzzing.api.dto.common.CommonSelectDto;
import com.szzzing.api.dto.common.FileDto;
import com.szzzing.api.dto.movie.*;
import com.szzzing.api.repository.FileRepository;
import com.szzzing.api.repository.MovieRepository;
import com.szzzing.api.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public interface MovieService {

    public boolean addRate(RateDto rateDto);

    public boolean updateRate(RateDto rateDto);

    public boolean deleteRate(RateDto rateDto);

    public StatusDto getStatus(HashMap<String, String> map);

    public boolean addWish(WishDto wishDto);

    public boolean deleteWish(WishDto wishDto);

    public boolean addWatching(WatchingDto watchingDto);
    public boolean deleteWatching(WatchingDto watchingDto);

    public MovieListDto getWishList(CommonSelectDto commonSelectDto);

    public MovieListDto getWatchingList(CommonSelectDto commonSelectDto);

    public MovieListDto getMovieList(MovieSelectDto movieSelectDto);

    public MovieDto getMovie(String id);

    public MovieRankListDto getMovieRankList();
}
