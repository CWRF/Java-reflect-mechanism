package reflectdemo;

/*
 * ����˵��ǰ�棺
 * ͨ�׵�˵��������ƾ��ǰ�һ���࣬��ĳ�Ա�����������ԣ�������һ��������������Ҳ����˵���࣬��ĳ�Ա�����������е�ʱ�򻹿��Զ�̬�ز�������
 * */
 
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
//import java.lang.reflect.TypeVariable;
//import java.rmi.activation.ActivationInstantiator;

public class Main {
/*Ϊ�˿����java���䲿�ֵĴ��룬���е��쳣����׸����������
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
		//Demo1. ͨ��java������Ƶõ���İ���������
		Demo1();
		System.out.println("=========================================");
		//Demo2. ��֤���е��඼��Class���ʵ������
		Demo2();
		System.out.println("=========================================");
		//Demo3. ͨ��java������ƣ���Class������������Ҳ���Ƿ�����ڵ�����ݣ��޲ι���
		Demo3();
		System.out.println("=========================================");
		//Demo4. ͨ��java������Ƶõ�һ����Ĺ��캯������ʵ�ֹ������ʵ������
		Demo4();
		System.out.println("=========================================");
		//Demo5. ͨ��java������Ʋ�����Ա������set��get
		Demo5();
		System.out.println("=========================================");
		//Demo6. ͨ��java������Ƶõ����һЩ���ԣ��̳еĽӿڣ����࣬������Ϣ����Ա��Ϣ�����͵�
		Demo6();
		System.out.println("=========================================");
		//Demo7. ͨ��java������Ƶ������еķ���
		Demo7();
		System.out.println("=========================================");
		//Demo8. ͨ��java������ƻ���������
		Demo8();
		System.out.println("=========================================");
	}
	
	/*
	 * Demo1:ͨ��java������Ƶõ���������Ͱ���
	 * */
	public static void Demo1(){
		Person person = new Person();
		System.out.println("Demo1: ������"+ person.getClass().getPackage().getName()+","+"����������"+person.getClass().getName());
	}
	
	/*
	 * Demo2:��֤���е��඼��Class���ʵ������
	 * @throws ClassNotFoundException
	 * */
	public static void Demo2() throws ClassNotFoundException{ 
		//�����������Ͷ�δ֪��Class�����ó�ֵΪnull��������θ����Ǹ�ֵ��Person��
		Class<?> class1 = null;
		Class<?> class2 = null;
		//д��1�������׳�ClassNotFoundException�۶������д����
		class1 = Class.forName("reflectdemo.Person");//������������ַ���������������ӿڵ�Class����
		System.out.println("Demo2:��д��1��������"+class1.getPackage().getName()+","+"����������"+class1.getName());
		
		//д��2
		class2 = Person.class;
		System.out.println("Demo2:��д��2��������"+class2.getPackage().getName()+","+"����������"+class2.getName());
	}
	
	/*
	 * Demo3:ͨ��java������ƣ�����Class������������Ҳ�Ƿ�����������ڣ�
	 * @throws ClassNotFoundException  
     * @throws IllegalAccessException  
     * @throws InstantiationException  
	 * */
	public static void Demo3() throws ClassNotFoundException,InstantiationException,IllegalAccessException{
		Class<?> class1 = null;
		class1 = Class.forName("reflectdemo.Person");
		//�������ﲻ�ܴ�������������Ҫʵ�����������Person��һ��Ҫ���޲ι��캯������
		Person person = (Person)class1.newInstance();
		person.setAge(20);
		person.setName("LeeFeng");
		System.out.println("Demo3:"+person.getName()+" : "+person.getAge());
	}
	
	/*
	 * Demo4:ͨ��java������Ƶõ�һ����Ĺ��캯������ʵ�ִ�������ʵ������
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
		//�õ�һϵ�й��캯������
		Constructor<?>[] constructors = class1.getConstructors();
		
		person1 = (Person) constructors[0].newInstance();
		person1.setAge(30);
		person1.setName("leeFeng");
		
		person2 = (Person)constructors[1].newInstance(20,"leeFeng");
		System.out.println("Demo4:"+person1.getName()+" : "+person1.getAge()+" , "+person2.getName()+" : "+person2.getAge());
	}
	
	/*
	 * Demo5:ͨ��java������Ʋ�����Ա������set��get
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
		personNameField.set(obj, "���");
		
		System.out.println("Demo5:�޸�����֮��õ����Ա�����ֵ��"+personNameField.get(obj));
	}
	
	/*
	 * Demo6��ͨ��java������Ƶõ����һЩ���ԣ��̳еĽӿڣ����࣬������Ϣ����Ա��Ϣ�����͵�
	 * @throws ClassNotFoundException
	 * */
	public static void Demo6() throws ClassNotFoundException{
		Class<?> class1 = null;
		class1 = Class.forName("reflectdemo.SuperMan");
		
		//ȡ�ø�������
		Class<?> superClass = class1.getSuperclass();
		System.out.println("Demo6�� SuperMan��ĸ�������"+superClass.getName());
		
		System.out.println("=====================================");
		
		Field[] fields = class1.getDeclaredFields();
		for(int i = 0;i < fields.length;i++){
			System.out.println("���еĳ�Ա��"+fields[i]);
		}
		System.out.println("=====================================");
		
		//ȡ���෽��
		Method[] methods = class1.getDeclaredMethods();
		for(int i = 0;i < methods.length;i++){
			System.out.println("Demo6,ȡ��SuperMan��ķ�����");
			System.out.println("��������"+methods[i].getName());
			System.out.println("�����������ͣ�"+methods[i].getReturnType());
			System.out.println("�����������η���"+Modifier.toString(methods[i].getModifiers()));
			System.out.println("��������д����"+methods[i]);
		}
		System.out.println("=====================================");
		
		//ȡ����ʵ�ֵĽӿڣ���Ϊ�ӿ���Ҳ����Class�����Եõ��ӿ��еķ���Ҳ��һ���ķ����õ���
		Class<?> interfaces[] = class1.getInterfaces();
		for(int i = 0;i < interfaces.length;i++){
			System.out.println("ʵ�ֵĽӿ�������"+interfaces[i].getName());
		}
	}
	
	/*
	 * Demo7:ͨ��java������Ƶ����෽��
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
		
		System.out.println("Demo7:\n�����޲η���fly������");
		 Method method = class1.getMethod("fly");
		 method.invoke(class1.newInstance());
		 
		 System.out.println("�����в�������walk(int m): ");
		 method = class1.getMethod("walk",int.class );
		 method.invoke(class1.newInstance(), 100);
	}
	
	/*
	 * Demo8:ͨ��java������Ƶõ���������Ϣ
	 * ��java�������������������[����������Ͻ�ȡ] 
 
        1��Bootstrap ClassLoader �˼���������c++��д��һ�㿪���к��ټ��� 
 
        2��Extension ClassLoader ����������չ��ļ��أ�һ���Ӧ����jre\lib\extĿ¼�е��� 
 
        3��AppClassLoader ����classpathָ�����࣬����õļ�������ͬʱҲ��java��Ĭ�ϵļ������� 
     *  
     * @throws ClassNotFoundException  
	 * */
	public static void Demo8() throws ClassNotFoundException{
		Class<?> class1 = null;
		class1 = Class.forName("reflectdemo.SuperMan");
		String nameString = class1.getClassLoader().getClass().getName();
		
		System.out.println("Demo8:�������������"+nameString);
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
		System.out.println("���˻��Ү����");
	}
	
	public boolean isBlueBriefs(){
		return BlueBriefs;
	}
	
	public void setBlueBriefs(boolean blueBriefs){
		BlueBriefs = blueBriefs;
	}
	
	@Override
	public void walk(int m){
		System.out.println("���˻���Ү��������"+m+"�׾��߲����ˣ�");
	}
}

interface ActionInterface{
	public void walk(int m);
}

