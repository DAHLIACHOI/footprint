package com.study.footprint.config.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.study.footprint.common.exception.CommonBadRequestException;
import com.study.footprint.common.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    public S3Service(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile) throws IOException {

        // 파일이 존재할 때만 업로드
        if (ValidationUtil.hasFile(multipartFile)) {
            File uploadFile = convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

            return upload(uploadFile, multipartFile.getOriginalFilename());
        } else {
            throw new CommonBadRequestException("requiredFile");
        }
    }

    private String upload(File uploadFile, String originalName) {
        //파일명 중복방지를 위한 UUID
        String fileName = "footprint" +
                "/" + UUID.randomUUID() +
                "_" + originalName;

        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client
                .putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
//        File convertFile = new File("/" + UUID.randomUUID());
        File convertFile = new File(System.getProperty("user.home"), String.valueOf(UUID.randomUUID()));
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}