package ru.geekbrains.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.geekbrains.model.PictureData;

@Service
public interface PictureService {

  Optional<String> getPictureContentTypeById(int id);

  Optional<byte[]> getPictureDataById(int id);

  PictureData createPictureData(byte[] picture, String fileName);

  void deletePictureById(Integer pictureId);
}
