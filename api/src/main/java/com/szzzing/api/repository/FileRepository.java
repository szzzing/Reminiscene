package com.szzzing.api.repository;

import com.szzzing.api.dto.common.FileDto;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@RequiredArgsConstructor
public class FileRepository {

    private final SqlSessionTemplate sqlSessionTemplate;
    private final String NAME_SPACE = "fileMapper.";

    public void insertFile(FileDto fileDto) {
        sqlSessionTemplate.insert(NAME_SPACE+"insertOneFile", fileDto);
    }
    public void insertFile(ArrayList<FileDto> list) {
        sqlSessionTemplate.insert(NAME_SPACE+"insertFileList", list);
    }

    public void deleteFile(String renameName) {
        sqlSessionTemplate.delete(NAME_SPACE+"deleteOneFile", renameName);
    }
}
