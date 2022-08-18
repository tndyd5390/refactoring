package me.whiteship.refactoring._06_mutable_data._20_remove_setting_method;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {

    @Test
    void person() {
        Person person = new Person("keesun", 10);
        assertEquals(10, person.getId());
        assertEquals("keesun", person.getName());
    }

}