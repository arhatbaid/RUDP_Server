package server;

import model.ImageChunksMetaData;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;


@Controller
public class ImageController {

    private ImageChunksMetaData[] arrImagesChunkData = null;
    private byte[] arrImage = null;
    private File imageFile = null;

    @GetMapping(value = "/")
    private String getImage(ModelMap model) throws IOException {
        arrImagesChunkData = ServerImpl.getArrImagesChunkData();
        if(arrImagesChunkData == null || arrImagesChunkData.length == 0) return "No images found";
        for (int i = 0, arrSize = arrImagesChunkData.length; i < arrSize; i++) {
            imageFile = new File(arrImagesChunkData[i].getImageName());
            if (imageFile != null && imageFile.exists()) {
                try (FileInputStream imageInFile = new FileInputStream(imageFile)) {
                    byte imageData[] = new byte[(int) imageFile.length()];
                    imageInFile.read(imageData);
                    model.put(arrImagesChunkData[i].getImageName().replace(".jpeg", ""), Base64.getEncoder().encodeToString(imageData));
                }
            }
        }

/*
        ClassPathResource imgFile = new ClassPathResource("C:/Capstone/RUDP_Server/screen_1.jpeg");
        ClassPathResource imgFile2 = new ClassPathResource("C:/Capstone/RUDP_Server/screen_2.jpeg");
        ClassPathResource imgFile3 = new ClassPathResource("C:/Capstone/RUDP_Server/screen_3.jpeg");
        ClassPathResource imgFile4 = new ClassPathResource("C:/Capstone/RUDP_Server/screen_4.jpeg");

        byte[] bytes1 = StreamUtils.copyToByteArray(((ClassPathResource) imgFile).getInputStream());
        byte[] bytes2 = StreamUtils.copyToByteArray(((ClassPathResource) imgFile2).getInputStream());
        byte[] bytes3 = StreamUtils.copyToByteArray(((ClassPathResource) imgFile3).getInputStream());
        byte[] bytes4 = StreamUtils.copyToByteArray(((ClassPathResource) imgFile4).getInputStream());

        byte[] encoded = Base64.encodeBase64(bytes1);
        byte[] encoded1= Base64.encodeBase64(bytes2);
        byte[] encoded2= Base64.encodeBase64(bytes3);
        byte[] encoded3= Base64.encodeBase64(bytes4);

        String encodedString = new String(encoded);
        String encodedString1 = new String(encoded1);
        String encodedString2 = new String(encoded2);
        String encodedString3 = new String(encoded3);

        model.put("screen_1", encodedString);
        model.put("screen_2", encodedString1);
        model.put("screen_3", encodedString2);*/
//        model.put("screen_1", base64Image);

        return "welcome";
    }


}
