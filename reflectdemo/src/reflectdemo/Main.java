package reflectdemo;

/*
 * 理论说在前面：
 * 通俗的说，反射机制就是把一个类，类的成员（函数，属性），当成一个对象来操作；也就是说，类，类的成员，我们在运行的时候还可以动态地操作他们
 * */
 
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
//import java.lang.reflect.TypeVariable;
//import java.rmi.activation.ActivationInstantiator;

public class Main {
/*为了看清楚java反射部分的代码，所有的异常最后都抛给虚拟机处理
 	* @param args 
     * @throws ClassNotFoundException 
     * @throws InstantiationException 
     * @throws IllegalAccessException 
     * @throws InvocationTargetException  
     * @throws IllegalArgumentException  
     * @throws NoSuchFieldException  
     * @throws SecurityException  
     * @throws NoSuchMethodException  
 * */
	public static void main(String[] args) throws ClassNotFoundException,InstantiationException,IllegalAccessException,InvocationTargetException,IllegalArgumentException,NoSuchFieldException,SecurityException,NoSuchMethodException{
		//Demo1. 通过java反射机制得到类的包名和类名
		Demo1();
		System.out.println("=========================================");
		//Demo2. 验证所有的类都是Class类的实例对象
		Demo2();
		System.out.println("=========================================");
		//Demo3. 通过java反射机制，用Class创建类对象［这也就是反射存在的意义］，无参构造
		Demo3();
		System.out.println("=========================================");
		//Demo4. 通过java反射机制得到一个类的构造函数，并实现构造带参实例对象
		Demo4();
		System.out.println("=========================================");
		//Demo5. 通过java反射机制操作成员变量，set和get
		Demo5();
		System.out.println("=========================================");
		//Demo6. 通过java反射机制得到类的一些属性：继承的接口，父类，函数信息，成员信息，类型等
		Demo6();
		System.out.println("=========================================");
		//Demo7. 通过java反射机制调用类中的方法
		Demo7();
		System.out.println("=========================================");
		//Demo8. 通过java反射机制获得类加载器
		Demo8();
		System.out.println("=========================================");
	}
	
	/*
	 * Demo1:通过java反射机制得到类的类名和包名
	 * */
	public static void Demo1(){
		Person person = new Person();
		System.out.println("Demo1: 包名："+ person.getClass().getPackage().getName()+","+"完整类名："+person.getClass().getName());
	}
	
	/*
	 * Demo2:验证所有的类都是Class类的实例对象
	 * @throws ClassNotFoundException
	 * */
	public static void Demo2() throws ClassNotFoundException{ 
		//定义两个类型都未知的Class，设置初值为null，看看如何给他们赋值成Person类
		Class<?> class1 = null;
		Class<?> class2 = null;
		//写法1，可能抛出ClassNotFoundException［多用这个写法］
		class1 = Class.forName("reflectdemo.Person");//返回与给定的字符串名称相关联类或接口的Class对象
		System.out.println("Demo2:（写法1）包名："+class1.getPackage().getName()+","+"完整类名："+class1.getName());
		
		//写法2
		class2 = Person.class;
		System.out.println("Demo2:（写法2）包名："+class2.getPackage().getName()+","+"完整类名："+class2.getName());
	}
	
	/*
	 * Demo3:通过java反射机制，利用Class创建类对象［这也是反射的意义所在］
	 * @throws ClassNotFoundException  
     * @throws IllegalAccessException  
     * @throws InstantiationException  
	 * */
	public static void Demo3() throws ClassNotFoundException,InstantiationException,IllegalAccessException{
		Class<?> class1 = null;
		class1 = Class.forName("reflectdemo.Person");
		//由于这里不能带参数，所以你要实例化的这个类Person，一定要有无参构造函数哈～
		Person person = (Person)class1.newInstance();
		person.setAge(20);
		person.setName("LeeFeng");
		System.out.println("Demo3:"+person.getName()+" : "+person.getAge());
	}
	
	/*
	 * Demo4:通过java反射机制得到一个类的构造函数，并实现创建带参实例对象
	 * @throws ClassNotFoundException  
     * @throws InvocationTargetException  
     * @throws IllegalAccessException  
     * @throws InstantiationException  
     * @throws IllegalArgumentException  
	 * */
	public static void Demo4() throws ClassNotFoundException,IllegalArgumentException,InstantiationException,IllegalAccessException,InvocationTargetException{
		Class<?> class1 = null;
		Person person1 = null;
		Person person2 = null;
		
		class1 = Class.forName("reflectdemo.Person");
		//得到一系列构造函数集合
		Constructor<?>[] constructors = class1.getConstructors();
		
		person1 = (Person) constructors[0].newInstance();
		person1.setAge(30);
		person1.setName("leeFeng");
		
		person2 = (Person)constructors[1].newInstance(20,"leeFeng");
		System.out.println("Demo4:"+person1.getName()+" : "+person1.getAge()+" , "+person2.getName()+" : "+person2.getAge());
	}
	
	/*
	 * Demo5:通过java反射机制操作成员变量，set和get
	 * @throws IllegalAccessException  
     * @throws IllegalArgumentException  
     * @throws NoSuchFieldException  
     * @throws SecurityException  
     * @throws InstantiationException  
     * @throws ClassNotFoundException  
	 * */
	public static void Demo5() throws IllegalAccessException,IllegalArgumentException,NoSuchFieldException,SecurityException,InstantiationException,ClassNotFoundException{
		Class<?> class1 = null;
		class1 = Class.forName("reflectdemo.Person");
		Object obj = class1.newInstance();
		
		Field personNameField = class1.getDeclaredField("name");
		personNameField.setAccessible(true);
		personNameField.set(obj, "五九");
		
		System.out.println("Demo5:修改属性之后得到属性变量的值："+personNameField.get(obj));
	}
	
	/*
	 * Demo6：通过java反射机制得到类的一些属性：继承的接口，父类，函数信息，成员信息，类型等
	 * @throws ClassNotFoundException
	 * */
	public static void Demo6() throws ClassNotFoundException{
		Class<?> class1 = null;
		class1 = Class.forName("reflectdemo.SuperMan");
		
		//取得父类名称
		Class<?> superClass = class1.getSuperclass();
		System.out.println("Demo6： SuperMan类的父类名："+superClass.getName());
		
		System.out.println("=====================================");
		
		Field[] fields = class1.getDeclaredFields();
		for(int i = 0;i < fields.length;i++){
			System.out.println("类中的成员："+fields[i]);
		}
		System.out.println("=====================================");
		
		//取得类方法
		Method[] methods = class1.getDeclaredMethods();
		for(int i = 0;i < methods.length;i++){
			System.out.println("Demo6,取得SuperMan类的方法：");
			System.out.println("函数名："+methods[i].getName());
			System.out.println("函数返回类型："+methods[i].getReturnType());
			System.out.println("函数访问修饰符："+Modifier.toString(methods[i].getModifiers()));
			System.out.println("函数代码写法："+methods[i]);
		}
		System.out.println("=====================================");
		
		//取得类实现的接口，因为接口类也属于Class，所以得到接口中的方法也是一样的方法得到哈
		Class<?> interfaces[] = class1.getInterfaces();
		for(int i = 0;i < interfaces.length;i++){
			System.out.println("实现的接口类名："+interfaces[i].getName());
		}
	}
	
	/*
	 * Demo7:通过java反射机制调用类方法
	 * @throws ClassNotFoundException  
     * @throws NoSuchMethodException  
     * @throws SecurityException  
     * @throws InvocationTargetException  
     * @throws IllegalAccessException  
     * @throws IllegalArgumentException  
     * @throws InstantiationException  
	 * */
	public static void Demo7() throws ClassNotFoundException,NoSuchMethodException,SecurityException,InvocationTargetException,IllegalAccessException,IllegalArgumentException,InstantiationException{
		Class<?> class1 = null;
		class1 = Class.forName("reflectdemo.SuperMan");
		
		System.out.println("Demo7:\n调用无参方法fly（）：");
		 Method method = class1.getMethod("fly");
		 method.invoke(class1.newInstance());
		 
		 System.out.println("调用有参数方法walk(int m): ");
		 method = class1.getMethod("walk",int.class );
		 method.invoke(class1.newInstance(), 100);
	}
	
	/*
	 * Demo8:通过java反射机制得到加载器信息
	 * 在java中有三种类类加载器。[这段资料网上截取] 
 
        1）Bootstrap ClassLoader 此加载器采用c++编写，一般开发中很少见。 
 
        2）Extension ClassLoader 用来进行扩展类的加载，一般对应的是jre\lib\ext目录中的类 
 
        3）AppClassLoader 加载classpath指定的类，是最常用的加载器。同时也是java中默认的加载器。 
     *  
     * @throws ClassNotFoundException  
	 * */
	public static void Demo8() throws ClassNotFoundException{
		Class<?> class1 = null;
		class1 = Class.forName("reflectdemo.SuperMan");
		String nameString = class1.getClassLoader().getClass().getName();
		
		System.out.println("Demo8:类加载器类名："+nameString);
	}
	
}

class Person{
	private int age;
	private String name;
	
	public Person(){
		
	}
	
	public Person(int age,String name){
		this.age = age;
		this.name = name;
	}
	
	public int getAge(){
		return age;
	}
	
	public void setAge(int age){
		this.age = age;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}

class SuperMan extends Person implements ActionInterface{
	private boolean BlueBriefs;
	
	public void fly(){
		System.out.println("超人会飞耶～～");
	}
	
	public boolean isBlueBriefs(){
		return BlueBriefs;
	}
	
	public void setBlueBriefs(boolean blueBriefs){
		BlueBriefs = blueBriefs;
	}
	
	@Override
	public void walk(int m){
		System.out.println("超人会走耶～～走了"+m+"米就走不动了！");
	}
}

interface ActionInterface{
	public void walk(int m);
}

