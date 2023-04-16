package com.example.lab82;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.lab82.modules.Module;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

@SpringBootApplication
public class Application {

	private static final ApplicationContext cntx = new AnnotationConfigApplicationContext(Application.class);

	public static ApplicationContext getContext() {
		return cntx;
	}

	public static ArrayList<Module> modules;

	public ApplicationContext getCntx() {
		return cntx;
	}

	public static void main(String[] args) {

		System.out.println("Files\nC:\\Users\\PC\\Desktop\\testData\\gr.txt\n" +
				"C:\\Users\\PC\\Desktop\\testData\\korol-i-shut-prygnu-so-skaly.mp3\n" +
				"C:\\Users\\PC\\Desktop\\testData\\ExamTickets.jpg");

		File file = getFileFromUser();
		String extension = getExtension(file);
		modules=getModules();
		ArrayList<Function> list= new ArrayList<>();
		Module moduleToWork=null;
		ArrayList<String> options = new ArrayList<>();
		for (Module module : modules) {
			if (module.isExtensionSuitable(extension)) {
				moduleToWork=module;
				for (String option : module.getOption()) {
					options.add(option);
				}
			}
		}
		if(moduleToWork==null) return;
		System.out.println("Modules to your file");
		for(String option: options) {
			System.out.println(option);
		}
		int number=getNumberFromUser(options.size());

		try {
			Method m=moduleToWork.getClass().getMethod(options.get(number-1),File.class);

			m.invoke(null,file);
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		Scanner in=new Scanner(System.in);
		in.nextLine();
	}


	public static int getNumberFromUser(Integer limit){
		Scanner in = new Scanner(System.in);
		System.out.println("Введите число от 1 до "+ limit.toString());
		int number;
		while (true){
			String str=in.next();
			try {
				number=Integer.parseInt(str);
				if(number>=0&&number<=limit) break;
				else System.out.println("Число вне ограничений");
			}catch (Exception ignoreException){
				System.out.println("Вы ввели не число попробуйте ещё раз");
			}
		}
		return number;
	}
	private static ArrayList<Module> getModules(){

		ArrayList<Module> modules = new ArrayList<Module>();
		for (String e : cntx.getBeanDefinitionNames()){
			Object bean = cntx.getBean(e);

			Module module;
			try {
				module = (Module) bean;
				modules.add(module);
			} catch (Exception ex) {
				continue;
			}
		}
		return modules;
	}

	public static File getFileFromUser() {
		Scanner in = new Scanner(System.in);
		File file;
		boolean isFileExists;
		do {
			System.out.println("Input filepath:");
			String filename = in.nextLine();

			file = new File(filename);
			isFileExists = file.exists();
			if (!isFileExists) System.out.println("File with path " + filename + " does not exist");
		} while (!isFileExists);
		return file;
	}

	public static String getExtension(File file) {
		String[] array = file.getName().split("\\.");
		if(array.length>1) return array[array.length - 1];
		else return null;

	}
}