package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import ru.geekbrains.model.Picture;
import ru.geekbrains.model.PictureData;
import ru.geekbrains.repo.PictureRepository;
import ru.geekbrains.service.PictureService;
import ru.geekbrains.service.PictureServiceBlobImpl;

@TestInstance(Lifecycle.PER_CLASS)
class PictureServiceTest {

  private PictureService pictureServiceBlob;
  private PictureRepository pictureRepository;
  private PictureData pictureData;
  private Picture picture;

  @BeforeAll
  public void init() {
    pictureRepository = mock(PictureRepository.class);
    pictureServiceBlob = new PictureServiceBlobImpl(pictureRepository);

    pictureData = new PictureData();
    pictureData.setId(1);
    pictureData.setFileName("Banana");
    String data = "banana";
    pictureData.setData(data.getBytes());

    picture = new Picture();
    picture.setId(1);
    picture.setName("Banana");
    picture.setContentType("image/jpeg");
    picture.setPictureData(pictureData);
  }

  @BeforeEach
  void findById() {
    when(pictureRepository.findById(eq(1)))
        .thenReturn(Optional.of(picture));
  }

  @Test
  void getPictureContentTypeByIdTest() {
    Optional<String> optBlob = pictureServiceBlob.getPictureContentTypeById(1);

    assertTrue(optBlob.isPresent());
    assertEquals(picture.getContentType(), optBlob.get());
  }

  @Test
  void getPictureDataByIdTest() {
    Optional<byte[]> data = pictureServiceBlob.getPictureDataById(1);
    assertEquals(pictureData.getData(), data.get());
  }

  @Test
  void createPictureDataTest() {
    PictureData picData = pictureServiceBlob
        .createPictureData(pictureData.getData(), picture.getName());
    assertEquals(pictureData.getData(), picData.getData());
  }

  @Test
  void deletePictureByIdTest() {
    pictureServiceBlob.deletePictureById(1);
    verify(pictureRepository, times(1)).deleteById(1);
  }

}
