package com.example.lab82.modules;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.*;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImageModule implements Module {

    private List<String> suitableExtension=new ArrayList<>(){{
        add("jpg");
        add("jpeg");
        add("jfif");
        add("png");
        add("gif");
        add("avif");
    }};

    private List<String> option=new ArrayList<>(){{
        add("printImageSizeData");
        add("printCenterData");
        add("printImageEXIF");
    }};

    @Override
    public List<String> getPermissibleExtension() {
        return suitableExtension;
    }

    @Override
    public List<String> getOption() {
        return option;
    }

    public boolean isExtensionSuitable(String extension) {
        return suitableExtension.contains(extension);
    }

    @Override
    public String getName() {
        return "ImageModule";
    }
    public ImageModule(){}

    public static void printImageSizeData(File file){
        try(InputStream fileReader = Files.newInputStream(file.toPath())){
            BufferedImage image = ImageIO.read(fileReader);
            int height = image.getHeight();
            int width = image.getWidth();

            System.out.println( "Height= "+ height+ "\nWidth= "+ width);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printCenterData(File file){
        try(InputStream fileReader = Files.newInputStream(file.toPath())) {
            BufferedImage image = ImageIO.read(fileReader);
            System.out.println("x= "+ image.getWidth()/2+ "\ny= "+ image.getHeight()/2);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static void printImageEXIF(File file){
        try(InputStream fileReader = Files.newInputStream(file.toPath())){
            Metadata metadata = ImageMetadataReader.readMetadata(fileReader);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    System.out.format("[%s] - %s = %s\n",
                            directory.getName(), tag.getTagName(), tag.getDescription());
                }
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.format("ERROR: %s", error);
                    }
                }

            }
        }catch (ImageProcessingException | IOException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
