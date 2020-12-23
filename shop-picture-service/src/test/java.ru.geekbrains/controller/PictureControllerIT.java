package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.config.PictureConfiguration;
import ru.geekbrains.controller.PictureController;
import ru.geekbrains.model.Picture;
import ru.geekbrains.model.PictureData;
import ru.geekbrains.repo.PictureRepository;
import ru.geekbrains.service.PictureService;
import ru.geekbrains.service.PictureServiceFileImpl;

@SpringBootTest(classes = {PictureService.class, PictureController.class,
    PictureConfiguration.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
class PictureControllerIT {

  private Picture picture;
  private byte[] data;
  private PictureService pictureService;

  private static final String FILE_NAME = "banan.jpg";

  @Value("${picture.storage.path}")
  private String storagePath;

  @Autowired
  private MockMvc mvc;

  @MockBean
  private PictureRepository pictureRepository;

  @BeforeEach
  void init() {
    pictureService = new PictureServiceFileImpl(pictureRepository);

    Path path = Paths.get(storagePath + "\\" + FILE_NAME);
    try {
      data = Files.readAllBytes(path);
      PictureData pictureData = new PictureData(data);
      pictureData.setFileName("banan.jpg");
      picture = new Picture();
      picture = new Picture(FILE_NAME, "image/jpg", pictureData);
    } catch (IOException e) {
      e.printStackTrace();
    }
    picture.setId(1);

    when(pictureRepository.findById(eq(1)))
        .thenReturn(Optional.of(picture));
  }

  @Test
  void downloadProductPictureTest() throws Exception {
    mvc.perform(get("/picture/" + picture.getId()))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType("image/jpg"))
        .andExpect(content().bytes(picture.getPictureData().getData()));
  }

}
