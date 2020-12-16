package ru.geekbrains.service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import ru.geekbrains.model.Picture;
import ru.geekbrains.model.PictureData;
import ru.geekbrains.repo.PictureRepository;

@Slf4j
public class PictureServiceFileImpl implements PictureService {

  @Value("${picture.storage.path}")
  private String storagePath;

  private final PictureRepository pictureRepository;

  public PictureServiceFileImpl(PictureRepository pictureRepository) {
    this.pictureRepository = pictureRepository;
  }

  @Override
  public Optional<String> getPictureContentTypeById(int id) {
    return pictureRepository.findById(id)
        .map(Picture::getContentType);
  }

  @Override
  public Optional<byte[]> getPictureDataById(int id) {
    return pictureRepository.findById(id)
        .filter(pic -> pic.getPictureData().getFileName() != null)
        .map(pic -> Paths.get(storagePath, pic.getPictureData().getFileName()))
        .filter(Files::exists)
        .map(path -> {
          try {
            return Files.readAllBytes(path);
          } catch (IOException ex) {
            log.error("Can't read picture file ", ex);
            throw new RuntimeException(ex);
          }
        });
  }

  @Override
  public PictureData createPictureData(byte[] picture, String fileName) {
    try (OutputStream outputStream = Files.newOutputStream(Path.of(storagePath, fileName))) {
      outputStream.write(picture);
    } catch (IOException ex) {
      log.error("Can't create picture file ", ex);
      throw new RuntimeException(ex);
    }

    return new PictureData(fileName);
  }

  @Override
  public void deletePictureById(Integer pictureId) {
    pictureRepository.deleteById(pictureId);
  }
}
