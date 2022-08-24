package me.whiteship.refactoring._06_mutable_data._23_change_reference_to_value;

/**
 * 어떤 객체의 변경 내역을 다른 곳으로 전파하고 싶다면 레퍼런스 객체를 사용!!
 * 아니면 값 객체를 사용!!!
 */
public class Person {

    private TelephoneNumber officeTelephoneNumber;

    public String officeAreaCode() {
        return this.officeTelephoneNumber.areaCode();
    }

    public void officeAreaCode(String areaCode) {
        this.officeTelephoneNumber = new TelephoneNumber(areaCode, this.officeNumber());
    }

    public String officeNumber() {
        return this.officeTelephoneNumber.number();
    }

    public void officeNumber(String number) {
        this.officeTelephoneNumber = new TelephoneNumber(this.officeAreaCode(), number);
    }

}
