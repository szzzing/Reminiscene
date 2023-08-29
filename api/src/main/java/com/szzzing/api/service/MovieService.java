package com.szzzing.api.service;

import com.szzzing.api.dto.comment.CommentDto;
import com.szzzing.api.dto.common.CommonSelectDto;
import com.szzzing.api.dto.common.FileDto;
import com.szzzing.api.dto.movie.*;
import com.szzzing.api.repository.FileRepository;
import com.szzzing.api.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
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
    public int addMovie(MovieDto movieDto) {
        ArrayList<FileDto> fileDtoList = new ArrayList<>();

        try {
            URL imgURL = new URL(movieDto.getBackdropPath());
            String extension = movieDto.getBackdropPath().substring(movieDto.getBackdropPath().lastIndexOf(".")+1); // 확장자
            String fileName = UUID.randomUUID().toString(); // 이미지 이름
            movieDto.setBackdropPath(fileName+"."+extension);

            BufferedImage image = ImageIO.read(imgURL);
            File file = new File("../../Reminiscene/upload/backdrop/" + fileName + "." + extension);
            if(!file.exists()) { // 해당 경로의 폴더가 존재하지 않을 경우
                file.mkdirs(); // 해당 경로의 폴더 생성
            }

            ImageIO.write(image, extension, file); // image를 file로 업로드
            System.out.println("이미지 업로드 완료!");

            FileDto fileDto = new FileDto();
            fileDto.setOriginalName(movieDto.getTitle().replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]", "_")+"_backdrop."+extension);
            fileDto.setRenameName(movieDto.getBackdropPath());
            fileDto.setDirectory("/upload/backdrop/");
            fileDtoList.add(fileDto);
            fileRepository.insertOne(fileDto);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            URL imgURL = new URL(movieDto.getPosterPath());
            String extension = movieDto.getPosterPath().substring(movieDto.getPosterPath().lastIndexOf(".")+1); // 확장자
            String fileName = UUID.randomUUID().toString(); // 이미지 이름
            movieDto.setPosterPath(fileName+"."+extension);

            BufferedImage image = ImageIO.read(imgURL);
            File file = new File("../../Reminiscene/upload/poster/" + fileName + "." + extension);
            if(!file.exists()) { // 해당 경로의 폴더가 존재하지 않을 경우
                file.mkdirs(); // 해당 경로의 폴더 생성
            }

            ImageIO.write(image, extension, file); // image를 file로 업로드
            System.out.println("이미지 업로드 완료!");

            FileDto fileDto = new FileDto();
            fileDto.setOriginalName(movieDto.getTitle().replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]", "_")+"_poster."+extension);
            fileDto.setRenameName(movieDto.getPosterPath());
            fileDto.setDirectory("/upload/poster/");
            fileDtoList.add(fileDto);
            fileRepository.insertOne(fileDto);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieRepository.insertOneMovie(movieDto);
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
