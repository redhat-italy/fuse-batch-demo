package com.demo.service.restfileservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

@RestController
public class FileListController {

    private static final String DESTINATION_PATH = "DESTINATION_PATH";

    @CrossOrigin
    @RequestMapping(
            value = "/messagelist",
            method = RequestMethod.GET,
            headers = "Accept=application/json"
    )
    public String getMessageList() throws Exception {
        String response = getListAsJson(getFileList("export"));
        return response;
    }

    @CrossOrigin
    @RequestMapping(
            value = "/errorlist",
            method = RequestMethod.GET,
            headers = "Accept=application/json"
    )
    public String getErrorList() throws Exception {
        String response = getListAsJson(getFileList("error"));
        return response;
    }

    private String getListAsJson(List<String> fileList) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"workedfile\":[");
        if (fileList.size() > 0) {

            for (Iterator<String> names = fileList.iterator(); names.hasNext(); ) {
                String aFileName = names.next();
                sb.append("{\"name\": \"" + aFileName + "\"}");
                if (names.hasNext()) {
                    sb.append(",");
                }
            }
            sb.append("]}");
            return sb.toString();
        }
        sb.append("]}");
        return sb.toString();
    }


    @CrossOrigin
    @RequestMapping(
            value = "/filecontent/{filename}",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = "plain/text"
    )
    public String readFile(@PathVariable String filename) {
        String destinationPath = System.getenv(DESTINATION_PATH);
        String content = getFileContent(destinationPath + File.separator + filename).trim();

        //return "{\"content\":\"" + content + "\"}";
        return content;
    }

    private List<String> getFileList() {
        return this.getFileList("");
    }

    private List<String> getFileList(String namePattern) {
        String destinationPath = System.getenv(DESTINATION_PATH);
        System.out.println(">>> Reading from " + destinationPath);
        List<String> results = new ArrayList<String>();

        File[] files = new File(destinationPath).listFiles();

        for (File file : files) {
            System.out.println(">>> " + file.getName());


            //if (file.isFile()) {
            //if (file.getName().startsWith("MSG")) {
            int namePatternIndex = file.getName().toLowerCase().indexOf(namePattern);

            int patternLength = namePattern.trim().length();

            if (patternLength > 0 && namePatternIndex >= 0) {
                results.add(file.getName());
            } 
            //}
            //}
        }
        return results;
    }

    private static String getFileContent(String filePath) {
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            content = "Error reading file ";
        }

        return content;
    }
}