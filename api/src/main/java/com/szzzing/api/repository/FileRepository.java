package com.szzzing.api.repository;

import com.szzzing.api.dto.FileDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FileRepository {

    private final SqlSessionTemplate sqlSessionTemplate;
    private final String NAME_SPACE = "fileMapper.";

    public void insertOne(FileDto fileDto) {
        sqlSessionTemplate.insert(NAME_SPACE+"insertOne", fileDto);
    }

    public void deleteOne(String renameName) {
        sqlSessionTemplate.delete(NAME_SPACE+"deleteOne", renameName);
    }
}
