package com.groomthon.univ.api.hello.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HelloControllerTest {

    @Test
    void helloWorld() {

        HelloController controller = new HelloController();

        String res = controller.helloWorld();

        assertThat(res).isEqualTo("Hello World");
    }
}