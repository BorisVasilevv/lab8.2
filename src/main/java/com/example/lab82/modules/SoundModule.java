package com.example.lab82.modules;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class SoundModule implements Module {

    private List<String> suitableExtension=new ArrayList<>(){{
        add("mp3");
        add("wav");
        add("aiff");
        add("aac");

    }};

    private List<String> option=new ArrayList<>(){{
        add("getSoundName");
        add("getFileSize");
        add("getDurationOfTrack");
    }};

    @Override
    public List<String> getPermissibleExtension() {
        return suitableExtension;
    }

    @Override
    public List<String> getOption() {
        return option;
    }
    @Override
    public boolean isExtensionSuitable(String extension) {
        return suitableExtension.contains(extension);
    }

    @Override
    public String getName() {
        return "SoundModule";
    }
    public SoundModule() { }

    public static void getSoundName(File file)   {
        System.out.println( workWithMusic(file, "title"));
    }

    public static void getFileSize(File file)   {
        System.out.println(  workWithMusic(file, "size"));
    }
    public static void getDurationOfTrack(File file)   {
        System.out.println( workWithMusic(file, "duration"));
    }

    public static String workWithMusic(File file, String stringParam){
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "/c", "ffprobe -v error -of flat -show_format " + "\"" + file.getAbsolutePath() + "\"")
                .directory(new File("C:\\ffmpeg\\bin"));

        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                if (line.contains(stringParam)) {
                    return line.split("=")[1].replace("\"", "");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

}
