package com.example.lab82.modules;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Component
public class DirectoryModule implements Module{

    private List<String> suitableExtension=new ArrayList<>(){{
        add("txt");
        add("tex");
        add("md");
    }};
    private List<String> option=new ArrayList<>(){{
        add("showFilesInDirectory");
        add("sumOfFilesSize");
        add("showDirectories");
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
        return extension==null;
    }

    @Override
    public String getName() {
        return "DirectoryModule";
    }

    public DirectoryModule() {}

    public static void showFilesInDirectory(File file){

        File[] files=file.listFiles();
        if(files!=null){
            for(File helpFile: files){
                if(helpFile.isFile()){
                    System.out.println(helpFile.getName());
                }
            }
        }

    }

    public static void sumOfFilesSize(File file){
        long sum=0;
        File[] files=file.listFiles();
        if(files!=null){
            for(File helpFile: files){
                if(helpFile.isFile()){
                    sum+=file.length();
                }
            }
        }
        System.out.println(sum);
    }

    public static void showDirectories(File file){
        File[] files=file.listFiles();
        if(files!=null){
            for(File helpFile: files){
                if(helpFile.isDirectory()){
                    System.out.println(helpFile.getName());
                }
            }
        }
    }

}
