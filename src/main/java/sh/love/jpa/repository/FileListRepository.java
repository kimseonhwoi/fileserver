package sh.love.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sh.love.jpa.domain.FileList;

import java.util.List;

@Repository
public interface FileListRepository extends JpaRepository<FileList, Long> {

    List<FileList> findByUseYn(String useYn);
}
