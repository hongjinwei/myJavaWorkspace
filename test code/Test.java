class Animal{
	public  void bark(){
		System.out.println("Hello Animal");
	}
	
	public void run(){

	}
}

interface Mammal{
	public void attribute(String args);

}
class Dog extends Animal implements Mammal{
	public void attribute(String args){
		System.out.println("this is "+args);
	}
}

public class Test{
	public static void main(String[] args){
		Dog d = new Dog();
		d.bark();
		d.attribute("aa");
	}

}