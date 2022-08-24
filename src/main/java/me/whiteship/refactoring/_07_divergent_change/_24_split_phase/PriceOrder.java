package me.whiteship.refactoring._07_divergent_change._24_split_phase;

/**
 * 단계 쪼개기
 * 서로 다른 일을 하는 코드를 다른 모듈로 분리한다.
 * 여러 일을 하는 함수의 처리 과정을 각기 다른 단계로 구분할 수 있다.
 * ex) 전처리 -> 주요작업 -> 후처리
 * ex) 텍스트 읽어오기 -> 실행 가능한 형태로 변경
 * 서로 다은 데이터를 사용한다면 단계를 나누는데 있어 중요한 단서가 될수 있음
 * 중간 데이터(intermediate date)를 만들어 단계를 구분하고 매개변수를 줄이는데 활용할수 있
 */
public class PriceOrder {

    public double priceOrder(Product product, int quantity, ShippingMethod shippingMethod) {
        final PriceData priceData = calculatePriceData(product, quantity);
        return applyShipping(priceData, shippingMethod);
    }
    
    private static PriceData calculatePriceData(Product product, int quantity) {
        final double basePrice = product.basePrice() * quantity;
        final double discount = Math.max(quantity - product.discountThreshold(), 0)
            * product.basePrice() * product.discountRate();
        return new PriceData(basePrice, discount, quantity);
    }

    private double applyShipping(PriceData priceData, ShippingMethod shippingMethod) {
        final double shippingPerCase = (priceData.basePrice() > shippingMethod.discountThreshold()) ?
            shippingMethod.discountedFee() : shippingMethod.feePerCase();
        final double shippingCost = priceData.quantity() * shippingPerCase;
        return priceData.basePrice() - priceData.discount() + shippingCost;
    }
}
