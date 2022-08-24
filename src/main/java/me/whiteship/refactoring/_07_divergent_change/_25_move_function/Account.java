package me.whiteship.refactoring._07_divergent_change._25_move_function;


/**
 * 함수 옮기기
 * 모듈화가 잘된 소프트웨어는 최소한의 지식으로 프로그램을 변경할 수 있다.
 * 관련있는 함수나 필드가 모여있어야 더 쉽게 찾고 이해할 수 있다.
 * 함수를 옮겨야 하는경우
 * - 해당 함수가 다른 문맥(클래스)에 있는 데이터(필드)를 더 많이 참조하는 경우
 * - 해당 함수를 다른 클라이언트(클래스) 에서도 필요로 하는 경우
 */
public class Account {

    private int daysOverdrawn;

    private AccountType type;

    public Account(int daysOverdrawn, AccountType type) {
        this.daysOverdrawn = daysOverdrawn;
        this.type = type;
    }

    public double getBankCharge() {
        double result = 4.5;
        if (this.daysOverdrawn() > 0) {
            result += this.type.overdraftCharge(this.daysOverdrawn);
        }
        return result;
    }

    private int daysOverdrawn() {
        return this.daysOverdrawn;
    }

}
