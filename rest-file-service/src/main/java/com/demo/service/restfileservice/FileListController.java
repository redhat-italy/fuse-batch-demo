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

    @RequestMapping(
            value = "/messagelist",
            method = RequestMethod.GET,
            headers = "Accept=application/json"
    )
    public String getMessageList() throws Exception {
        List<String> fileList = getFileList();
        StringBuffer sb = new StringBuffer();
        String response = getListAsJson(fileList, sb);
        return response;
    }

    private String getListAsJson(List<String> fileList, StringBuffer sb) {
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


    @RequestMapping(
            value = "/filecontent/{filename}",
            method = RequestMethod.GET,
            headers = "Accept=application/json"
    )
    public String readFile(@PathVariable String filename) {
        String destinationPath = System.getenv(DESTINATION_PATH);

        String content = getFileContent(destinationPath + File.separator + filename).trim();


        return "{\"content\":\"" + content + "\"}";

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

    public List<String> getFileList() {
        String destinationPath = System.getenv(DESTINATION_PATH);
        System.out.println(">>> Reading from " + destinationPath);
        List<String> results = new ArrayList<String>();

        File[] files = new File(destinationPath).listFiles();

        for (File file : files) {
            System.out.println(">>> " + file.getName());
            //if (file.isFile()) {
            //if (file.getName().startsWith("MSG")) {
            results.add(file.getName());
            //}
            //}
        }
        return results;
    }
}