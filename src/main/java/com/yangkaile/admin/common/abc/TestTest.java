package com.yangkaile.admin.common.abc;

/**
 * @author yangkaile
 * @date 2019-02-12 19:50:33
 * 算法学习
 */
public class TestTest {


    /**
     * 阶乘的递归运算
     * 缺点：每次递归都要创建新的栈帧，内存空间浪费严重
     * @param n
     * @return
     */
    private int factorial(int n){
        if(n <= 1){
            return 1;
        }else {
            return  n * factorial(n - 1);
        }
    }

    /**
     * 阶乘运算优化后的算法：尾递归
     * 好处是复用一个栈帧
     * @param n
     * @param result
     * @return
     */
    private int factorial(int n ,int result){
        if(n <= 1){
            return result;
        }else {
            return factorial(n-1 ,n * result);
        }
    }


    public static void main(String[] args){
        TestTest test = new TestTest();
        print("factorial(4)",test.factorial(4));
        print("factorial(4,1)",test.factorial(4,1));
    }

    public static void print(String description , Object object){
        System.out.println("===" + description + "===:" + object);
    }
}
