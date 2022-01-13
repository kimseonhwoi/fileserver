package sh.love.jpa.domain;

import lombok.*;
import org.apache.commons.io.FilenameUtils;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "file_list")
public class FileList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sn;

    @Column
    private String fileName;

    @Column
    private String fileUuidName;

    @Column
    private String content;

    @Column
    private String useYn;

    public FileList(String fileName, String content) {
        String ext = FilenameUtils.getExtension(fileName);
        ext = !ext.equals("") ? "."+ext : "";
        this.fileName = fileName;
        this.content = content;
        this.useYn = "Y";
        this.fileUuidName = UUID.randomUUID().toString() + ext;
    }
}
