package _10_immutable;

/**
 * 描述：     不可变的对象，演示其他类无法修改这个对象，public也不行
 */
public class _0_Person {

    final int age = 18;
    String alice = new String("Alice");
    final String name = alice;
    final _1_TestFinal testFinal = new _1_TestFinal();
    public static void main(String[] args) {
        _0_Person person = new _0_Person();
        person.alice = "44";
        System.out.println(person.name);
    }
}

