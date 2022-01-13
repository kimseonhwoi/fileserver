package sh.love.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sh.love.jpa.domain.FileList;
import sh.love.service.MainService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class MainController {

    @Value("${spring.servlet.multipart.location}")
    private String filePath;

    @Autowired
    private MainService mainService;

    @RequestMapping(value = "/")
    public String main(Model model){
        model.addAttribute("fileList", mainService.findFileList());
        return "/main";
    }

    @RequestMapping(value = "/upload")
    public String upload(Model model){
        model.addAttribute("fileList", mainService.findFileList());
        return "/upload";
    }

    @RequestMapping(value = "/fileUpload", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST)
    @ResponseBody
    public Object fileUpload(Model model, @RequestParam(value = "file", required = false) MultipartFile multipartFile,
                             @RequestParam(value = "content" ,required = false) String content){
        try{
            mainService.save(multipartFile, content);
        }catch (Exception e) {
            e.printStackTrace();
            return 500;
        }
        return 200;
    }

    @RequestMapping(value = "/fileDelete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(Model model, @RequestParam(value = "sn", required = true) String sn){
        try {
            mainService.delete(Long.parseLong(sn));
        } catch (Exception e) {
            return 500;
        }
        return 200;
    }

    @RequestMapping(value = "/fileDownload")
    @ResponseBody
    public void fileDownload(HttpServletResponse response, Model model, @RequestParam(value = "sn", required = true) String sn){
        try {
            FileList fileList = mainService.findFileListId(Long.parseLong(sn));
            byte[] fileByte = FileUtils.readFileToByteArray(new File(filePath+fileList.getFileUuidName()));

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileList.getFileName(), "UTF-8")+"\";");
            response.setHeader("Content-Transfer-Encoding", "binary");

            response.getOutputStream().write(fileByte);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
        }
    }

}
