package com.example.lab82.modules;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class TextModule implements Module {


    private List<String> suitableExtension=new ArrayList<>(){{
        add("txt");
        add("tex");
        add("md");
    }};

    private List<String> option=new ArrayList<>(){{
        add("deleteFirstRow");
        add("getNumberOfLines");
        add("showAmountOfEachSymbol");
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
        return "TextModule";
    }

    public TextModule(){
        specialSymbolsArray=new ArrayList<>();
        specialSymbolsArray.add(' ');
        specialSymbolsArray.add('\n');
        specialSymbolsArray.add('\r');
        specialSymbolsArray.add('\t');
    }


    public static void getNumberOfLines(File file){
        var lines = 0L;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.readLine() != null) {
                lines++;
            }
            System.out.println( lines);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void deleteFirstRow(File file) {
        String name = file.getName();
        String path = file.getPath();
        String[] array = name.split("\\.");
        String line;
        File helpFile = new File(path + array[0] + "help." + array[1]);
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(helpFile))) {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        file.delete();
        helpFile.renameTo(file);
        System.out.println("OK");
    }


    private static ArrayList<Character> specialSymbolsArray;

    public static void showAmountOfEachSymbol(File file){
        HashMap<Character,Integer> map=new HashMap<>();

        try (FileReader reader =new FileReader(file)){
            int c;
            while((c=reader.read())!=-1){

                Character character=(char) c;
                if (specialSymbolsArray.contains(character)) break;
                if(!map.containsKey(character)){
                    map.put(character,1);
                }
                else {
                    map.put(character, map.get(character)+1);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        writeHashMap(map);
    }

    public static void writeHashMap(HashMap<Character,Integer> map){
        for (Character key:map.keySet()){
            System.out.print(key);
            System.out.print(" – ");
            System.out.println(map.get(key));
        }
    }



}
