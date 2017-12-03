package Mining;

import java.util.Arrays;

public class DES {

    private static final int[] ORDER4 = new int[]{1, 3, 2, 0};
    private static final int[] ORDER8 = new int[]{1, 5, 2, 0, 3, 7, 4, 6};
    private static final int[] ORDER10 = new int[]{2, 4, 1, 6, 3, 9, 0, 8, 7, 5};
    private static final int[] ORDER4TO8 = new int[]{3, 0, 1, 2, 1, 2, 3, 0};
    private static final int[] ORDER8TO10 = new int[]{5, 2, 6, 3, 7, 4, 9, 8};

    private int[] keyOne;
    private int[] keyTwo;

    public String encrypt(String plaintext) {
        int[] plaintextArr = stringToIntArray(plaintext);
        plaintextArr = permutation(plaintextArr, ORDER8);
        plaintextArr = round(plaintextArr, keyOne);
        plaintextArr = switchArray(plaintextArr);
        plaintextArr = round(plaintextArr, keyTwo);
        plaintextArr = permutation(plaintextArr, reverseArray(ORDER8));
        return Arrays.toString(plaintextArr);
    }

    public void setKeys(String key) {
        int[] keyArr = stringToIntArray(key);
        keyArr = permutation(keyArr, ORDER10);
        int[] keyHead = shiftLeft(Arrays.copyOfRange(keyArr, 0, 5));
        int[] keyTail = shiftLeft(Arrays.copyOfRange(keyArr, 5, 10));
        keyOne = permutation(concat(keyHead, keyTail), ORDER8TO10);
        keyHead = shiftLeft(keyHead);
        keyTail = shiftLeft(keyTail);
        keyTwo = permutation(concat(keyHead, keyTail), ORDER8TO10);
    }

    private int[] round(int[] array, int[] key) {
        int[] head = Arrays.copyOfRange(array, 0, 4);
        int[] tail = Arrays.copyOfRange(array, 4, 8);
        head = xor(head, fFunction(tail, key));
        return concat(head, tail);
    }

    private int[] fFunction(int[] array, int[] key) {
        array = xor(expansion(array), key);
        int[] head = Arrays.copyOfRange(array, 0, 4);
        int[] tail = Arrays.copyOfRange(array, 4, 8);
        return permutation(sbox(head, tail), ORDER4);
    }

    private int[] sbox(int[] head, int[] tail) {
        int[] temp = new int[4];
        temp[0] = head[1];
        temp[1] = head[2];
        temp[2] = tail[0];
        temp[3] = tail[3];
        return temp;
    }

    private int[] reverseArray(int[] array) {
        for(int i = 0; i < array.length; i++) {
            int temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
        return array;
    }

    private int[] switchArray(int[] array) {
        int[] head = Arrays.copyOfRange(array, 0, 4);
        int[] tail = Arrays.copyOfRange(array, 4, 8);
        return concat(tail, head);
    }

    private int[] xor(int[] arrayOne, int[] arrayTwo) {
        int[] temp = new int[arrayOne.length];
        for(int i = 0; i < arrayOne.length; i++) {
            temp[i] = arrayOne[i] ^ arrayTwo[i];
        }
        return temp;
    }

    private int[] expansion(int[] array) {
        return permutation(concat(array, array), ORDER4TO8);
    }

    private int[] concat(int[] head, int[] tail) {
        int[] temp = new int[head.length + tail.length];
        System.arraycopy(head, 0, temp, 0, head.length);
        System.arraycopy(tail, 0, temp, head.length, tail.length);
        return temp;
    }

    private int[] shiftLeft(int[] arr) {
        int[] temp = new int[arr.length];
        for(int i = 0; i < arr.length; i++) {
            if (i + 1 == arr.length) {
                temp[i] = arr[0];
            } else {
                temp[i] = arr[i + 1];
            }
        }
        return temp;
    }

    private int[] permutation(int[] array, int[] order) {
        int[] temp = new int[array.length];
        for(int i = 0; i < order.length; i++) {
            temp[i] = array[order[i]];
        }
        return temp;
    }

    private int[] stringToIntArray(String string) {
        return string.chars()
                .mapToObj(i -> (char)i)
                .mapToInt(Character::getNumericValue)
                .toArray();
    }

    public static void main(String args[]) {
        DES des = new DES();
        des.setKeys("0123456789");
        String ciphertext = des.encrypt("01234567");
        System.out.println(ciphertext);
    }

}
