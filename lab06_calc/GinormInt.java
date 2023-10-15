import java.util.Arrays;
/**
 * Filename: GinormInt.java
 * Description: very big integers!
 * Author: Will Judy
 * Date: April 13
 */

public class GinormInt implements Comparable<GinormInt> {
    // TODO: define your static and instance variables here
    public static final GinormInt ONE = new GinormInt("1");
    public static final GinormInt ZERO = new GinormInt("0");
    public static final GinormInt TEN = new GinormInt("10");
    int strLength;
    boolean negative;
    byte[] dgts;
    byte[] invDgts;
    String[] strValArray;
    boolean equals;
    int comparison = 0;
    byte[] sum;
    byte thisDgt;
    byte otherDgt;
    byte num;
    byte carry;

    /**
     * Constructor takes in a string, saves it, checks for a sign character,
     * checks to see if it's all valid digits, and reverses it for later use.
     *
     * @param  value  String value to make into a GinormInt
     */
    public GinormInt(String value) throws NumberFormatException, IllegalArgumentException {
        this.strLength = value.length();
        //System.out.println("Negative: " + value.contains("-"));
        this.negative = value.contains("-");
        //System.out.println("negative: " + negative);
        this.strValArray = value.split("");
        if (!negative) { //creates dgts for positive and negative cases
            this.dgts = new byte[strLength];
            for (int i = 0; i < strLength; i++) {
                dgts[i] = Byte.parseByte(strValArray[i]);
            }
        } else {
            this.dgts = new byte[strLength - 1];
            for (int i = 0; i < strLength - 1; i++) {
                dgts[i] = Byte.parseByte(strValArray[i+1]);
            }
        }
        System.out.println("dgts: " + Arrays.toString(dgts));
        if (!negative) { //createsinvDgts for positive and negative cases
            this.invDgts = new byte[strLength];
            int j = strLength - 1;
            for (int i = 0; i < strLength; i++) {
                invDgts[i] = dgts[j];
                j--;
            }
        } else {
            this.invDgts = new byte[strLength - 1];
            int j = strLength - 2;
            for (int i = 0; i < strLength - 1; i++) {
                invDgts[i] = dgts[j];
                j--;
            }
        } 
        System.out.println("invDgts: " + Arrays.toString(invDgts));
    }

    /**
     * Method to add a GinormInt value passed in as an argument to this GinormInt.
     *
     * @param  otherInt other GinormInt to add to this GinormInt
     * @return GinormInt that's the sum of this GinormInt and the one passed in
     */
    public GinormInt plus(GinormInt otherInt) { 
        int longLength = this.sumLength(otherInt);
        sum = new byte[longLength]; 
        System.out.println("Sum: " + Arrays.toString(sum));  
        if (this.dgts.length != longLength) {
            this.makeLonger(longLength);
        }
        if (otherInt.dgts.length != longLength) {
            otherInt.makeLonger(longLength);
        }    
        for (int i = 0; i < strLength; i++) {
            carry = 0;
            num = 0;
            thisDgt = this.invDgts[i];
            //System.out.println("thisDGT: " + thisDgt);
            otherDgt = otherInt.invDgts[i];
            //System.out.println("otherDgt: " + otherDgt);
            num = (byte) (thisDgt + otherDgt);
            if (num >= 10) {
                num -= 10;
                System.out.println("num: " + num + "   sum at i: " + sum[i]);
                sum[i] = num;
                System.out.println("num: " + num + "   sum at i: " + sum[i]);
                carry = 1;
                //System.out.println(" before carry " + String.valueOf(dgts))
                this.invDgts[i + 1] += carry;
            } else {
                sum[i] = num;
                System.out.println("num: " + num + "   sum at i: " + sum[i]);
            }
        }
        System.out.println("invSum: " + Arrays.toString(sum));
        // for (int i = 0; i < this.strLength; i++) {
        //   sum[i] = (byte) (this.invDgts[i] + otherInt.invDgts[i]);
        // }
        this.invDgts = sum;
        this.flipToReg(longLength);
        System.out.println("regular Sum: " + Arrays.toString(dgts));
        this.toString();
        return null;
    }

    /**
     * Method to subtract a GinormInt passed in as an argument to this GinormInt.
     *
     * @param  otherInt other GinormInt to subtract from this GinormInt
     * @return GinormInt that's the difference of this GinormInt and the one passed in
     */

    public GinormInt minus(GinormInt otherInt) {
        //this.plus(-otherInt);
        throw new UnsupportedOperationException("Sorry, that operation is not yet implemented.");
    }

    /**
     * Method to multiply a GinormInt passed in as an argument to this GinormInt.
     *
     * @param  otherInt other GinormInt to multiply by this GinormInt
     * @return GinormInt that's the product of this GinormInt and the one passed in
     */
    public GinormInt times(GinormInt otherInt) {
        // (Optional) your code here
        throw new UnsupportedOperationException("Sorry, that operation is not yet implemented.");
    }

    /**
     * Method to divide a GinormInt passed in as an argument from this GinormInt.
     *
     * @param  otherInt other GinormInt to divide into this GinormInt
     * @return GinormInt that's the (truncated integer) ratio of this GinormInt and the one passed in
     */
    public GinormInt div(GinormInt otherInt) {
        // (Optional) your code here
        throw new UnsupportedOperationException("Sorry, that operation is not yet implemented.");
    }

    /**
     * Method to find the remainder after dividing by a GinormInt passed in.
     *
     * @param  otherInt other GinormInt to divide into this GinormInt to compute the remainder
     * @return GinormInt that's the remainder after dividing the two BigInts
     */
    public GinormInt mod(GinormInt otherInt) {
        // (Optional) your code here
        throw new UnsupportedOperationException("Sorry, that operation is not yet implemented.");
    }

    /**
     * Method to compare this GinormInt to another GinormInt passed in.
     *
     * @param  otherInt other GinormInt to compare to
     * @return 1 if this GinormInt is larger, 0 if equal, -1 if it's smaller
     */
    @Override
    public int compareTo(GinormInt otherInt) {
        System.out.println("compare: " + Arrays.toString(this.dgts) + " to: " + Arrays.toString(otherInt.dgts));
        if (this.equals(otherInt)) {
            comparison = 0;
            // System.out.println(comparison);
            // return comparison;
        } else if (!this.negative && !otherInt.negative) {
            if (this.strLength > otherInt.strLength) {
                // System.out.println(comparison);
                comparison = 1;
            } else if (this.strLength < otherInt.strLength) {
                // System.out.println(comparison);
                comparison = -1;
            } else {
                while (comparison == 0) {
                    for (int i = 0; i < strLength; i++) {
                        if (this.dgts[i] > otherInt.dgts[i]) {
                            comparison = 1;
                            // System.out.println(comparison);
                            // return comparison;
                        } if (this.dgts[i] < otherInt.dgts[i]) {
                            comparison = -1;
                            // System.out.println(comparison);
                            // return comparison;
                        }
                    }
                }
            }
        } else if (!this.negative && otherInt.negative) {
            comparison = 1;
            // System.out.println(comparison);
            // return comparison;
        } else if (this.negative && !otherInt.negative) {
            comparison = -1;
            // System.out.println(comparison);
            // return comparison;
        } else {
            if (this.strLength < otherInt.strLength) {
                comparison = 1;
                // System.out.println(comparison);
                // return comparison;
            } else if (this.strLength > otherInt.strLength) {
                comparison = -1;
                // System.out.println(comparison);
                // return comparison;
            } else {
                while (comparison == 0) {
                    for (int i = 0; i < strLength; i++) {
                        if (this.dgts[i] < otherInt.dgts[i]) {
                            comparison = 1;
                            // System.out.println(comparison);
                            // return comparison;
                        } else if (this.dgts[i] > otherInt.dgts[i]) {
                            comparison = -1;
                            // System.out.println(comparison);
                            // return comparison;
                        }
                    }
                }
            }
        }
        System.out.println("comparison: " + comparison);
        return comparison;
    }

    //Helper function returns length of longer array size
    public int sumLength(GinormInt otherInt) {
        int dgtLength;
        if (this.strLength > otherInt.strLength) {
            if (!this.negative) {
                dgtLength = this.strLength;
            } else {
                dgtLength = this.strLength - 1;
            } 
        } else {
            if (!otherInt.negative) {
                dgtLength = otherInt.strLength;
            } else {
                dgtLength = otherInt.strLength - 1;
            }
        }
        System.out.println("longer num digit length: " + dgtLength);
        return dgtLength;
    }

    public void makeLonger(int length) {
        System.out.println("LENGTH: " + length);
        byte[] extendo = new byte[length];
        int difference = length - this.dgts.length;
        int j = 0;
        for (int i = 0; i < difference; i++) {
            extendo[i] = 0;
        }
        for (int i = difference; i < length; i++) {
            extendo[i] = this.dgts[j];
        }
        this.dgts = extendo;
        System.out.println("longer version of dgts: " + Arrays.toString(this.dgts));
        this.flipToInv(length);
        System.out.println("longer version of invDgts: " + Arrays.toString(this.invDgts));
    }

    /**
     * Method to check if this GinormInt equals another GinormInt passed in.
     *
     * @param  otherInt other GinormInt to compare to
     * @return true if they're equal, false otherwise
     */
    public boolean equals(GinormInt otherInt) {
        if (this.negative == otherInt.negative) {
            if (Arrays.equals(this.dgts, otherInt.dgts)) {
                equals = true;
                System.out.println(equals);
            } else {
                equals = false;
                System.out.println(equals);
            }
        } else {
            equals = false;
            System.out.println(equals);
        }
    return equals;
    }

    // helper functions to flip digit order :

    public void flipToInv(int length) {
        System.out.println("string length: " + length);
        int j = 0;
        j = length - 1;
        invDgts = new byte[length];
        System.out.println("digits: " + Arrays.toString(dgts));
        System.out.println("digits: " + Arrays.toString(invDgts));
        System.out.println("string length: " + length);
        for (int i = 0; i < length; i++) {
            invDgts[i] = dgts[j];
            System.out.println("j: " + j  + "i: " + i);
            j--;
        }
        // } else {
        //     int j = strLength - 2;
        //     for (int i = 0; i < strLength - 1; i++) {
        //         invDgts[i] = dgts[j];
        //         j--;
        //     }
        // }
        System.out.println("to inv: " + Arrays.toString(invDgts));
    }
    public void flipToReg(int length) {
        if (!negative) {
            int j = length - 1;
            for (int i = 0; i < length; i++) {
                dgts[i] = invDgts[j];
                j--;
            }
        } else {
            int j = length - 2;
            for (int i = 0; i < length - 1; i++) {
                dgts[i] = invDgts[j];
                j--;
            }
        }
        System.out.println("back to reg: " + Arrays.toString(dgts));
    }


    /**
     * Method to return the string representation of this GinormInt.
     *
     * @return String representation
     */
    @Override
    public String toString() {
        String output = "";
        if (negative) {
            output = "-";
        } else {
            output = "";
        }
        for (int i = 0; i < dgts.length; i++) {
            output += String.valueOf(dgts[i]);
        }
        System.out.println("output: " + output);
        return output;
    }
    public static void main(String[] args) {
        //don't need the try catch here
        try {
        GinormInt x1 = new GinormInt("125");
        x1.plus(new GinormInt("6"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        //catch (IllegalArgumentException e) {
        //}
    }


}