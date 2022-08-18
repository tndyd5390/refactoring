package me.whiteship.refactoring._06_mutable_data._20_remove_setting_method;

/**
 * 세터 제거하기
 * 세터는 없애는 것이 좋다.
 * 객체지향 체조원칙에도 나와있다.
 */
public class Person {

    private final String name;

    private final int id;

    public Person(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public int getId() {
        return id;
    }

}
