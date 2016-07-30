package demo.mockito.calculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by oneday on 2016/7/24 0024.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:demo/mockito/calculator/app.xml")
public class CalculatorTest {


    @InjectMocks
    @Autowired
    Calculator calculator;

    @Mock
    Adder adder;
    @Mock
    Suber suber;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testVal(){

        when(adder.add("2",String.valueOf(2.0))).thenReturn(4.0);
        when(suber.sub("3","1")).thenReturn(2.0);

        double v = calculator.val("2+3-1");

        verify(adder).add("2",String.valueOf(2.0));
        verify(suber).sub("3","1");
        System.out.println(v);
    }
}
