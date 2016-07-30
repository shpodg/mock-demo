package demo.mockito.calculator.impl;

import demo.mockito.calculator.Suber;
import org.springframework.stereotype.Component;

/**
 * Created by oneday on 2016/7/24 0024.
 */
@Component
public class SuberImpl implements Suber {
    @Override
    public double sub(String n1, String n2) {
        return 0;
    }
}
