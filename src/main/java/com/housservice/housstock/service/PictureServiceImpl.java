package com.housservice.housstock.service;


import com.housservice.housstock.repository.PictureRepository;
import org.springframework.stereotype.Service;

@Service

public class PictureServiceImpl implements PictureService {
final
PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public void deleteImg(String imgId) {
    pictureRepository.deleteById(imgId);
    }
}
