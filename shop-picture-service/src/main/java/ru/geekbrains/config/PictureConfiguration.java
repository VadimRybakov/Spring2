package ru.geekbrains.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.geekbrains.repo.PictureRepository;
import ru.geekbrains.service.PictureService;
import ru.geekbrains.service.PictureServiceBlobImpl;
import ru.geekbrains.service.PictureServiceFileImpl;

@Configuration
public class PictureConfiguration {

  @Bean
  @ConditionalOnProperty(name = "picture.storage.type", havingValue = "database")
  public PictureService pictureServiceBlobImpl(PictureRepository pictureRepository) {
    return new PictureServiceBlobImpl(pictureRepository);
  }

  @Bean
  @ConditionalOnProperty(name = "picture.storage.type", havingValue = "files")
  public PictureService pictureServiceFileImpl(PictureRepository pictureRepository) {
    return new PictureServiceFileImpl(pictureRepository);
  }
}
