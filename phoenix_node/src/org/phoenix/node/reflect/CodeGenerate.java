package org.phoenix.node.reflect;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

public class CodeGenerate {
	 static String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("%20", " ");
	 
	 static String WEB_INF = classPath.substring(0, classPath.length()-1);
	 static String WEB_INF_Path = WEB_INF.substring(0, WEB_INF.lastIndexOf("/"))+"/";
	 
	 static String libPath = WEB_INF_Path+"lib/";
	
	public String runByJavaCode(String codeContent,String classPackagePath){
		 JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); 
		 // 通过 JavaCompiler 取得标准 StandardJavaFileManager 对象，StandardJavaFileManager 对象主要负责
		 // 编译文件对象的创建，编译的参数等等，我们只对它做些基本设置比如编译 CLASSPATH 等。
		 StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null); 
		 Iterable<? extends File> jarFiles = Arrays.asList(new File(libPath).listFiles());
		 try {
			fileManager.setLocation(StandardLocation.CLASS_PATH, jarFiles);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		 // 因为是从内存中读取 Java 源文件，所以需要创建我们的自己的 JavaFileObject，即 InMemoryJavaFileObject 
		 JavaFileObject fileObject = new InMemoryJavaFileObject(classPackagePath, codeContent); 
		 Iterable<? extends JavaFileObject> files = Arrays.asList(fileObject); 
		 
		 // 从文件读取编译源代码
		  //Files[] javafiles = ... ; // input for first compilation task
		  //Iterable<? extends JavaFileObject> files = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(javafiles));
		 // 编译结果信息的记录
		 StringWriter sw = new StringWriter(); 
		 Iterable<String> options = Arrays.asList("-d", classPath); 

		 JavaCompiler.CompilationTask task = 
		 compiler.getTask(sw, fileManager, null, options, null, files); 
		 try {
			fileManager.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		 if (!task.call()) { 
			 String failedMsg = sw.toString(); 
			 return "compile fail " + failedMsg; 
		 } else {
			 try {
			    ClassLoader classLoader = this.getClass().getClassLoader();
				Class<?> clazz = classLoader.loadClass(classPackagePath);
				Method method = clazz.getDeclaredMethod("run");
				method.invoke(clazz.newInstance());
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "execute fail ";
			} 
		 }
	}
	 // 自定义 JavaFileObject 实现了 SimpleJavaFileObject，指定 string 为 java 源代码，这样就不用将源代码
	 // 存在内存中，直接从变量中读入即可。
	static class InMemoryJavaFileObject extends SimpleJavaFileObject{  
        private String content = null;  
        protected InMemoryJavaFileObject(String name, String content) {  
            super(URI.create("string:///"+name.replace('.', '/')+Kind.SOURCE.extension),Kind.SOURCE);  
            this.content = content;  
        }  
        public CharSequence getCharContent(boolean ignoreEncodingErrors)throws IOException {  
            return content;  
        }  
    }
}
