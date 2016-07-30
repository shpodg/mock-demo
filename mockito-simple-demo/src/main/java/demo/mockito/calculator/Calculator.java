package demo.mockito.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by oneday on 2016/7/24 0024.
 */
@Component("calculator")
public class Calculator {

    @Autowired
    private Adder adder;
    @Autowired
    private Suber suber;

    public double val(String e){
        String[] a = e.split("\\+");
        String[] b = a[1].split("-");
        double v = suber.sub(b[0],b[1]);
        return adder.add(a[0],String.valueOf(v));
    }

    public void setAdder(Adder adder) {
        this.adder = adder;
    }

    public void setSuber(Suber suber) {
        this.suber = suber;
    }
}
