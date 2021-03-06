package com.cy.synchronizedTest;

/*
 * synchronized 关键字原理测试
 * 作用： 1.确保线程互斥的访问同步代码
 *       2.保证共享变量的修改能够及时可见
 *       3.有效解决重排序问题
 * 用法： 1.修饰普通方法(见程序SynchronizedTest1,线程2要等到线程1的method1执行完成后才开始执行method2方法)
 * 	 2.修饰静态方法(类)(见程序SynchronizedTest2)
 * 	      解释：静态方法的同步本质上是对类的同步(静态方法本质上是属于类的方法，而不是对象上的方法),所以即使test和test1属于不同的对象，但是它们同属于SynchronizedTest2类的实例,因此只能顺序执行method1和method2,不能并发执行。
 *       3.修饰代码块(见程序SynchronizedTest3)
 *            解释：虽然线程1和线程2都进入对应的方法开始执行，但是线程2在进入同步块之前，需要等待线程1中同步块执行完成。
 * 运行结果：method 1 start
	    method 1 execute
	    method 2 start
	    method 1 end
	    method 2 execute
	    method 2 end
 */
public class SynchronizedTest3 {
	
	public void method1() {
		
		System.out.println("method 1 start");
		try {
			//修饰代码块
			synchronized(this) {
				System.out.println("method 1 execute");
				Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("method 1 end");
		
	}
	
	public void method2() {
		
		System.out.println("method 2 start");
		try {
			//修饰代码块
			synchronized(this) {
				System.out.println("method 2 execute");
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("method 2 end");
	}

	public static void main(String[] args) {

		//创建实例对象
		final SynchronizedTest3 test = new SynchronizedTest3();
		new Thread(new Runnable() {
			@Override
			public void run() {
				test.method1();
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				test.method2();
			}
		}).start();
	}

}
