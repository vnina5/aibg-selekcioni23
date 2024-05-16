package aibg.selekcioni23.logic;


import aibg.selekcioni23.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LogicClassTest {
    private final LogicClass logicClass = new LogicClass();

    @Test
    public void calculateTrueResultTest() {
        User user = new User("tim1", "tim1");
        user.setAssignment(new Assignment(64, 5, 25,20,8));

        logicClass.calculateTrueResult(user);
        assertEquals(user.getTrueResult(), 763);
    }

}
