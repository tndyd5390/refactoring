package me.whiteship.refactoring._04_long_parameter_list._15_remove_flag_argument;

import java.time.LocalDate;

/**
 * 플래그성 인수 제거하기
 * 함수 내부의 로직을 분리하는데 많이 사용되는 플래그 인수를 제거해보자
 * 플래그를 사용한 함수는 차이를 파악하기 어렵다.
 * bookConcert(customer, false), bookConcert(customer, true)
 * bookConcert(customer), premiumBookConcert(customer)
 * 조건문 분해하기를 사용하여 플래그 인수를 제거할 수 있다.
 */
public class Shipment {

    public static LocalDate regularDeliveryDate(Order order) {
        int deliveryTime = switch (order.getDeliveryState()) {
            case "WA", "CA" -> 2;
            case "OR", "TX", "NY" -> 3;
            default -> 4;
        };
        return order.getPlacedOn().plusDays(deliveryTime);
    }

    public static LocalDate rushDeliveryDate(Order order) {
        int deliveryTime = switch (order.getDeliveryState()) {
            case "WA", "CA", "OR" -> 1;
            case "TX", "NY", "FL" -> 2;
            default -> 3;
        };
        return order.getPlacedOn().plusDays(deliveryTime);
    }
}
