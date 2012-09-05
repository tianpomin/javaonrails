package me.jor.classloader;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ClassPathClassLoader extends AbstractJORClassLoader {

	private File classpath;
	public ClassPathClassLoader(File classpath, ClassLoader parent, String startClassName, boolean startClassInCustomPath) {
		super(parent, startClassName, startClassInCustomPath);
		this.classpath=classpath;
	}

	@Override
	protected InputStream getBytecodeInputStream(String name) throws FileNotFoundException {
		return new FileInputStream(new File(classpath,super.convertPackagePath(name)));
	}
	
	@Override
	protected URL findJORResource(String name) {
		File res=new File(classpath,super.convertPackagePath(name));
		if(res.exists()){
			try {
				return res.toURI().toURL();
			} catch (MalformedURLException e) {
				return null;
			}
		}else{
			return null;
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		ClassLoader cl=new ClassPathClassLoader(new File("E:\\class"),ClassPathClassLoader.class.getClassLoader(),"test2",true);
		System.out.println(ClassPathClassLoader.class.getClassLoader());
//		System.out.println(cl.loadClass("test2"));
//		System.out.println(cl.loadClass("test2").getClassLoader());
		System.out.println(cl.loadClass("test1").getClassLoader());
	}
}
