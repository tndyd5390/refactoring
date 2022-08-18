package me.whiteship.refactoring._06_mutable_data._21_replace_derived_variable_with_query;

import java.util.ArrayList;
import java.util.List;

/**
 * 변경할 수 있는 데이터를 최대한 줄이도록 노력해야 한다
 * 계산해서 알아낼수 있는 변수는 제거할 수 있다.
 * 계산에 필요한 데이터가 변하지 않는 값이라면 계산의 결과에 해당하는 데이터 역시 불변이다.
 */
public class ProductionPlan {

    private List<Double> adjustments = new ArrayList<>();

    public void applyAdjustment(double adjustment) {
        this.adjustments.add(adjustment);
    }

    public double getProduction() {
        return this.adjustments.stream().mapToDouble(Double::valueOf).sum();
    }
}
