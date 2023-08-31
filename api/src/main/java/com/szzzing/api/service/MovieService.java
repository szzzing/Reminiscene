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

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;
    private final FileRepository fileRepository;

    public boolean addRate(RateDto rateDto) {
        return movieRepository.insertOneRate(rateDto) > 0;
    }

    public boolean updateRate(RateDto rateDto) {
        return movieRepository.updateOneRate(rateDto) > 0;
    }

    public boolean deleteRate(RateDto rateDto) {
        return movieRepository.deleteOneRate(rateDto) > 0;
    }

    public StatusDto getStatus(HashMap<String, String> map) {
        return movieRepository.selectOneStatus(map);
    }

    public boolean addWish(WishDto wishDto) {
        return movieRepository.insertOneWish(wishDto) > 0;
    }

    public boolean deleteWish(WishDto wishDto) {
        return movieRepository.deleteOneWish(wishDto) > 0;
    }

    public boolean addWatching(WatchingDto watchingDto) {
        return movieRepository.insertOneWatching(watchingDto) > 0;
    }

    public boolean deleteWatching(WatchingDto watchingDto) {
        return movieRepository.deleteOneWatching(watchingDto) > 0;
    }

    public boolean addComment(CommentDto commentDto) {
        return movieRepository.insertOneComment(commentDto) > 0;
    }

    public CommentDto getComment(CommentDto commentDto) {
        return movieRepository.selectOneComment(commentDto);
    }

    public boolean modifyComment(CommentDto commentDto) {
        return movieRepository.updateOneComment(commentDto) > 0;
    }

    public MovieListDto getWishList(CommonSelectDto commonSelectDto) {
        if(commonSelectDto.getPage()==null) commonSelectDto.setPage(1);
        commonSelectDto.setOffset();

        MovieListDto movieListDto = new MovieListDto();
        movieListDto.setList(movieRepository.selectWishList(commonSelectDto));
        movieListDto.setPage(commonSelectDto.getPage());

        return movieListDto;
    }

    public MovieListDto getWatchingList(CommonSelectDto commonSelectDto) {
        if(commonSelectDto.getPage()==null) commonSelectDto.setPage(1);
        commonSelectDto.setOffset();

        MovieListDto movieListDto = new MovieListDto();
        movieListDto.setList(movieRepository.selectWatchingList(commonSelectDto));
        movieListDto.setPage(commonSelectDto.getPage());

        return movieListDto;
    }

    @Transactional
    public int addMovie(MovieInsertDto movieInsertDto) {
        FileDto posterDto = FileUtil.getFileDto(movieInsertDto.getPosterFile(), "poster");
        FileDto backdropDto = FileUtil.getFileDto(movieInsertDto.getBackdropFile(), "backdrop");
        ArrayList<FileDto> fileDtoList = new ArrayList<>();
        fileDtoList.add(posterDto);
        fileDtoList.add(backdropDto);
        
        // 파일 업로드
        FileUtil.uploadFile(movieInsertDto.getPosterFile(), posterDto, "poster");
        FileUtil.uploadFile(movieInsertDto.getBackdropFile(), backdropDto, "backdrop");
        
        // 파일 DB에 저장
        movieInsertDto.setPosterPath(posterDto.getRenameName());
        movieInsertDto.setBackdropPath(backdropDto.getRenameName());
        fileRepository.insertFile(fileDtoList);
        
        // 영화 DB에 저장
        return movieRepository.insertOneMovie(movieInsertDto);
    }

    public MovieListDto getMovieList(MovieSelectDto movieSelectDto) {
        if(movieSelectDto.getPage()==null) movieSelectDto.setPage(1);
        movieSelectDto.setOffset();

        MovieListDto movieListDto = new MovieListDto();
        movieListDto.setList(movieRepository.selectMovieList(movieSelectDto));
        movieListDto.setPage(movieSelectDto.getPage());

        return movieListDto;
    }

    public MovieDto getMovie(String id) {
        return movieRepository.selectOneMovie(id);
    }

    public MovieRankListDto getMovieRenkList() {
        MovieRankListDto movieRankListDto = new MovieRankListDto();
        movieRankListDto.setMonthBestList(movieRepository.selectMonthBestMovieList());
        movieRankListDto.setBestRateList(movieRepository.selectBestRateMovieList());
        return movieRankListDto;
    }
}
