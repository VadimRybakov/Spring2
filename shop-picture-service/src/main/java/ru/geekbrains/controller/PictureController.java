package ru.geekbrains.controller;

import java.io.IOException;
import java.util.Optional;
import javassist.NotFoundException;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.service.PictureService;

@Slf4j
@Controller
@RequestMapping("/picture")
public class PictureController {

  private final PictureService pictureService;

  @Autowired
  public PictureController(PictureService pictureService) {
    this.pictureService = pictureService;
  }

  @GetMapping("/{pictureId}")
  public void downloadProductPicture(@PathVariable("pictureId") Integer pictureId,
      HttpServletResponse response)
      throws IOException, NotFoundException {
    log.info("Downloading picture {}", pictureId);

    Optional<String> optional = pictureService.getPictureContentTypeById(pictureId);
    if (optional.isPresent()) {
      response.setContentType(optional.get());
      response.getOutputStream().write(pictureService.getPictureDataById(pictureId).get());
    } else {
      throw new NotFoundException("picture not found");
    }
  }

  @GetMapping("/delete/{pictureId}")
  public String deletePicture(@PathVariable("pictureId") Integer pictureId) {
    pictureService.deletePictureById(pictureId);
    return "redirect:/products";
  }

}
