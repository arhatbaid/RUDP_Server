package server;

import model.ImageChunksMetaData;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;


@Controller
public class HelloWorld {

    private ImageChunksMetaData[] arrImagesChunkData = null;
    private byte[] arrImage = null;
    private File imageFile = null;

    @GetMapping(value = "/image")
    private String getImage(ModelMap model) throws IOException {
        arrImagesChunkData = ServerImpl.getArrImagesChunkData();
        if(arrImagesChunkData == null || arrImagesChunkData.length == 0) return "No images found";
        for (int i = 0, arrSize = arrImagesChunkData.length; i < arrSize; i++) {
            imageFile = new File(arrImagesChunkData[i].getImageName());
            if (imageFile != null && imageFile.exists()) {
                arrImage = Base64.encodeBase64(FileUtils.readFileToByteArray(imageFile));
                model.put(arrImagesChunkData[i].getImageName().replace(".jpeg", ""), new String(Base64.encodeBase64(arrImage)));
            }
        }
        return "welcome";
    }


}
