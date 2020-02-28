package _2_threadlocal;

/**
 * 描述：（重要栗子）  演示ThreadLocal用法2：避免传递参数的麻烦
 *                  哇我写了一个ThreadLocalTest类把两种场景结合起来啦耶耶耶
 */
public class _7cj2_ThreadLocalNormalUsage {

    public static void main(String[] args) {
        ThreadLocalTest.begin();
        new Service1().process();
        System.out.println(ThreadLocalTest.end());
    }
}

class User {
    String name;
    public User(String name) {
        this.name = name;
    }
}

class UserContextHolder {  //看这类的成员好像工具类啊
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}

//生成user对象，类似于filter的功能
class Service1 {
    public void process() {
        User user = new User("超哥");
        UserContextHolder.holder.set(user);//所有经过我的线程，你们的threadlocalmap属性中都被我放上这个属性了，键就是UserContextHodlerThreadLocal
        new Service2().process();     //看看看我没有往下传user参数啊
    }
}

//处理欢迎用户业务
class Service2 {
    public void process() {
        User user = UserContextHolder.holder.get();
//        ThreadSafeFormatter.dateFormatThreadLocal.get();
        System.out.println("Service2拿到用户名：" + user.name);
        new Service3().process();
    }
}

//处理抽奖业务
class Service3 {
    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service3拿到用户名：" + user.name);
        UserContextHolder.holder.remove();
    }
}

