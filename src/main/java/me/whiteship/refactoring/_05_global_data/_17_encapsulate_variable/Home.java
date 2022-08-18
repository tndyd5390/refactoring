package me.whiteship.refactoring._05_global_data._17_encapsulate_variable;

/**
 * 전역 데이터는 (static) 아무곳에서나 변경될수 있음
 * 어디서 바뀌었는지 확인하기 어렵다.
 * 클래스 변수 또한 비슷한 문제를 겪을 수 있다.
 * 변수 캡슐화하기로 접근을 제어하거나 어디서 사용하는지 파악할 수 있다.
 * <p>
 * 메소드는 점진적으로 변경할수 있으나 데이터는 한번에 모두 변경해야 한다.
 * 데이터 구조를 변경하는 작업을 메소드 구조 변경 작업으로 대체 해보자.
 * 불변 데이터의 경우에는 이런 리팩토링이 필요가 없다.
 */
public class Home {

    public static void main(String[] args) {
        System.out.println(Thermostats.getTargetTemperature());
        Thermostats.setTargetTemperature(68);
        Thermostats.setReadInFahrenheit(false);
    }
}
