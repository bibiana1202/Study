import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JUnitQuiz {

    @Test
    public void junittTest(){
        String name1 = "홍길동";
        String name2 = "홍길동";
        String name3 = "홍길동";
        // 1. 모든 변수가 null 이 아닌지 확인
        assertThat(name1).isNotNull();
        assertThat(name1).isNotNull();
        assertThat(name1).isNotNull();
        // 2. name1 과 name2 가 같은지 확인
        assertThat(name1).isEqualTo(name2);
        // 3. name1 과 name3 가 같은지 확인
        assertThat(name1).isEqualTo(name3);
    }

    @Test
    public void junitTest2(){
        int number1 = 15;
        int number2 = 0;
        int number3 = -5;

        // 1. number1은 양수인지 확인
        assertThat(number1).isPositive();
        // 2. number2은 0인지 확인
        assertThat(number2).isZero();
        // 3. number3은 음수인지 확인
        assertThat(number3).isNegative();
        // 4. number1은 number2보다 큰지 확인
        assertThat(number1).isGreaterThan(number2);
        // 5. number3은 number2 보다 작은지 확인
        assertThat(number3).isLessThan(number2);
    }
}
