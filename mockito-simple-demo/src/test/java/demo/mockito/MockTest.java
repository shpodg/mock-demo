package demo.mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by oneday on 2016/7/22 0022.
 */

public class MockTest {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Mock List mockedList;

    /**
     * 使用注解方式
     * @Mock 用来设定mock对象
     * MockitoAnnotations.initMocks(this); 处理本类的注解
     */
    @Test
    public void test0(){
        mockedList.add(1);
        verify(mockedList).add(1);
    }



    /**
     * 基本示例
     */
    @Test
    public void test1() {
        //mock creation
        List mockedList = mock(List.class);
        // using mock object
        mockedList.add("one");
        mockedList.clear();
        mockedList.add("3"); // no verify? OK

        // verification
        verify(mockedList).add("one");

        verify(mockedList).clear();

        verify(mockedList).add("2"); // this will throw an exception
    }

    /**
     * mock对象没有设置返回值的
     * 基本类型  int 0 boolean false
     * 对象类型 返回 null
     */
    @Test
    public void test2() {
        // You can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        // following prints "first"
        System.out.println(mockedList.get(0));
        // following throws runtime exception
        try {
            System.out.println(mockedList.get(1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));

        // Although it is possible to verify a stubbed invocation, usually it's just redundant
        // See http://monkeyisland.pl/2008/04/26/asking-and-telling
        verify(mockedList, atLeast(2)).get(0);
    }

    /**
     * 参数模糊匹配
     */
    @Test
    public void test3() {
        List mockedList = mock(List.class);

        // stubbing using built-in anyInt() argument matcher
        when(mockedList.get(anyInt())).thenReturn("element");

        // following prints "element"
        System.out.println(mockedList.get(999));

        // you can also verify using an argument matcher
        verify(mockedList).get(anyInt());

    }

    /**
     * 验证方法被调用次数
     */
    @Test
    public void test4() {
        List mockedList = mock(List.class);
        //using mock
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        //following two verifications work exactly the same - times(1) is used by default
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        //exact number of invocations verification
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        //verification using never(). never() is an alias to times(0)
        verify(mockedList, never()).add("never happened");

        //verification using atLeast()/atMost()
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("five times");
        verify(mockedList, atMost(5)).add("three times");

    }

    /**
     * 函数调用顺序验证
     */
    @Test
    public void test5() {
        // A. Single mock whose methods must be invoked in a particular order
        List singleMock = mock(List.class);

        //using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");
        //create an inOrder verifier for a single mock
        InOrder inOrder = inOrder(singleMock);

        //following will make sure that add is first called with "was added first, then with "was added second"
        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");

        // B. Multiple mocks that must be used in a particular order
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        //using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

        //create inOrder object passing any mocks that need to be verified in order
        inOrder = inOrder(firstMock, secondMock);

        //following will make sure that firstMock was called before secondMock
        inOrder.verify(firstMock).add("was called first");
        inOrder.verify(secondMock).add("was called second");

        // Oh, and A + B can be mixed together at will
    }

    /**
     * 连续调用返回不同的值
     */
    @Test
    public void test6(){
        List mockedList = mock(List.class);

        when(mockedList.add("some arg"))
                .thenThrow(new RuntimeException())
                .thenReturn(true);

        //First call: throws runtime exception:
        try {
            mockedList.add("some arg");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //Second call: prints "foo"
        System.out.println(mockedList.add("some arg"));

        //Any consecutive call: prints "foo" as well (last stubbing wins).
        System.out.println(mockedList.add("some arg"));
    }

    /**
     * 制定返回值策略
     */
    @Test
    public void test7(){
        class A{
            public String call(String... s){
                return null;
            }
        }
        A mock = mock(A.class);

        when(mock.call(anyString(),anyString())).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Object mock = invocation.getMock();
                return "called with arguments: " + Arrays.toString(args);
            }
        });

        //Following prints "called with arguments: foo"
        System.out.println(mock.call("foo","foo2"));
    }

    /**
     * 在真实对象上进行部分 mock
     * spy
     */
    @Test
    public void test8(){
        List list = new LinkedList();
        List spy = spy(list);

        //optionally, you can stub out some methods:
        when(spy.size()).thenReturn(100);

        //using the spy calls <b>real</b> methods
        spy.add("one");
        spy.add("two");

        //prints "one" - the first element of a list
        System.out.println(spy.get(0));

        //size() method was stubbed - 100 is printed
        System.out.println(spy.size());

        //optionally, you can verify
        verify(spy).add("one");
        verify(spy).add("two");
    }

    /**
     * 处理参数类型匹配
     * isA()
     */
    @Test
    public void test9(){
        List mockList = mock(List.class);
        when(mockList.add(isA(Integer.class))).thenReturn(true);

        boolean v = mockList.add(new Float(1));
        System.out.println(v);
        verify(mockList).add(isA(Integer.class));
    }
}
