package com.tenutz.storemngsim.utils;

public class PaymentUtils {

    /**
     *
     * 1) 마지막 검증코드숫자를 제외하고 그 앞 숫자부터 거꾸로 가면서 2를 곱하고, 그 다음은 1을 곱하고, 다시 2와 1 곱하기를
     * 번갈아 나열해서 곱한다.
     * 2) 곱셈한 결과가 10을 넘을 경우 다시 숫자끼리 더한다.
     * 3) 모든 숫자의 계산 결과를 모두 더한다.
     * 4) 합계 숫자의 끝자리 수를 10에서 빼면 검증코드 숫자이다.  끝자리 수가 0인 경우 그냥 0이다.
     *
     * @param code 코드
     * @return 검증숫자
     */
    public static char getCheckDigit(String code) {
        char[] chars = code.toCharArray();
        int factor = 2;
        int sum = 0;

        for(int i = chars.length-1; i >= 0; i--) {
            int result = (chars[i] - '0') * ((factor++ - 1) % 2 + 1);
            if(result > 10) {
                result = (result % 10) + 1;
            }
            sum += result;
        }

        return Character.forDigit((sum % 10) == 0 ? 0 : 10 - (sum % 10), 10);
    }

    /**
     *
     * 인접한 두 문자를 조합하여 원래 코드의 절반 길이의 새로운 코드를 만든다.
     *
     * @param stringDigit 숫자와 영소문자 조합의 8자리 코드
     * @return 숫자와 영대문자 조합의 4자리 코드
     */
    public static String generateUniqueTransactionCode(String stringDigit) {

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < stringDigit.length(); i += 2) {
            char c1 = stringDigit.charAt(i);
            char c2 = stringDigit.charAt(i + 1);
            if(c1 >= 'a' && c1 <= 'z') {
                c1 -= 'a';
                c1 += 10;
            }
            if(c1 >= '0' && c1 <= '9') {
                c1 -= '0';
            }
            if(c2 >= 'a' && c2 <= 'z') {
                c2 -= 'a';
                c2 += 10;
            }
            if(c2 >= '0' && c2 <= '9') {
                c2 -= '0';
            }
            char newCode = (char)((c1 + c2) % ('z' - ('a' - 1) + '9' - ('0' - 1)));
            if(newCode >= 10) {
                sb.append((char) ((newCode - 10) + 'A'));
            } else {
                sb.append(newCode + '0');
            }
        }

        return sb.toString();
    }
}
