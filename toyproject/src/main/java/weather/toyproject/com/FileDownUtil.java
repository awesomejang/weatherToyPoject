package weather.toyproject.com;

import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FileDownUtil {

	@ResponseBody
	@GetMapping("/images/{filename}")
	public Resource showImage(@PathVariable String filename) throws MalformedURLException {
		log.info("filenname= {}", filename);
	 	//return new UrlResource("file:" + file.getFullPath(filename));
		return new UrlResource("file:" + "C:/testimg/game_111.jpg");
	 }
	
}
