package me.whiteship.refactoring._04_long_parameter_list._14_replace_parameter_with_query;

/**
 * - 함수의 매개변수 목록은 짧을수록 이해하기 좋다
 * - 한 매개변수를 다른 매개변수를 통해 알아낼 수 있다면 중복 매개변수이다.
 * - 매개변수의 값을 전달하는것은 "함수를 호출하는 쪽"의 책임이다. 가능하면 함수를 호출하는 쪽의 책임을 줄이고 함수 내부에서 책임지도록 한다.
 */
public class Order {

    private int quantity;

    private double itemPrice;

    public Order(int quantity, double itemPrice) {
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    public double finalPrice() {
        double basePrice = this.quantity * this.itemPrice;
        return this.discountedPrice(basePrice);
    }

    private int discountLevel() {
        return this.quantity > 100 ? 2 : 1;
    }

    private double discountedPrice(double basePrice) {
        return discountLevel() == 2 ? basePrice * 0.90 : basePrice * 0.95;
    }
}
