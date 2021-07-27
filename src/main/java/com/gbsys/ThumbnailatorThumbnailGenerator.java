package com.gbsys;

import io.micronaut.core.annotation.NonNull;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Singleton
public class ThumbnailatorThumbnailGenerator implements ThumbnailGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(ThumbnailatorThumbnailGenerator.class);
    private final ThumbnailConfiguration thumbnailConfiguration;

    public ThumbnailatorThumbnailGenerator(ThumbnailConfiguration thumbnailConfiguration) {
        this.thumbnailConfiguration = thumbnailConfiguration;
    }

    @Override
    @NonNull
    public Optional<byte[]> thumbnail(@NonNull InputStream inputStream, @NonNull @NotBlank @Pattern(regexp = "jpg|png") String format) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Thumbnails
                    .of(inputStream)
                    .size(thumbnailConfiguration.getWidth(), thumbnailConfiguration.getHeight())
                    .outputFormat(format)
                    .toOutputStream(byteArrayOutputStream);
            return Optional.of(byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("IOException thrown while generating the thumbnail");
            }
        }
        return Optional.empty();
    }

}
