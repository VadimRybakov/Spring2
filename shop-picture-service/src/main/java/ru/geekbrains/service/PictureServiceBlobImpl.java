package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import ru.geekbrains.model.Picture;
import ru.geekbrains.model.PictureData;
import ru.geekbrains.repo.PictureRepository;

public class PictureServiceBlobImpl implements PictureService {

  private final PictureRepository pictureRepository;

  @Autowired
  public PictureServiceBlobImpl(PictureRepository pictureRepository) {
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
        .filter(pic -> pic.getPictureData().getData() != null)
        .map(pic -> pic.getPictureData().getData());
  }

  @Override
  public PictureData createPictureData(byte[] picture) {
    return new PictureData(picture);
  }

  @Override
  public void deletePictureById(Integer pictureId) {
    pictureRepository.deleteById(pictureId);
  }

}
