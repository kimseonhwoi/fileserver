package sh.love.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sh.love.jpa.domain.FileList;
import sh.love.jpa.repository.FileListRepository;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class MainService {

    @Value("${spring.servlet.multipart.location}")
    private String filePath;

    @Autowired
    private FileListRepository fileListRepository;

    public List<FileList> findFileList(){
        return fileListRepository.findByUseYn("Y");
    }

    public void save(MultipartFile multipartFile, String content) throws Exception {
        if(!multipartFile.isEmpty()){
            FileList fileList = new FileList(multipartFile.getOriginalFilename(), content);
            multipartFile.transferTo(new File(filePath+fileList.getFileUuidName()));
            fileListRepository.save(fileList);
        } else {
            throw new Exception("파일 업로드에 실패하였습니다.");
        }
    }

    public void delete(Long sn) throws Exception {
        try {
            FileList fileList = fileListRepository.findById(sn).get();
            fileList.setUseYn("N");
        } catch (Exception e) {
            throw new Exception("삭제할 파일이 없습니다.");
        }
    }

    public FileList findFileListId(Long sn) {
        return fileListRepository.findById(sn).get();
    }
}
