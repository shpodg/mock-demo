package demo.mockito.calculator.impl;

import demo.mockito.calculator.Adder;
import org.springframework.stereotype.Component;

/**
 * Created by oneday on 2016/7/24 0024.
 */
@Component
public class AdderImpl implements Adder {
    @Override
    public double add(String n1, String n2) {
        return 0;
    }
}
