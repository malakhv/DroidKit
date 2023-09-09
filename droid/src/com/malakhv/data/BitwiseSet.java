/* *
 * Copyright (C) 1996-2013 Mikhail Malakhov <malakhv@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 * See the License for the specific language governing permissions
 * and limitations under the License.
 * */

package com.malakhv.data;

/**
 * This class implements abstract bitwise set. Each element is a bit flag and may take the value
 * {@code true} (turned on) or {@code false} (turned off).
 *
 * <p>Individual bits can be examined or set (turned on/off) by using appropriate bit mask. One
 * {@code BitwiseSet} may be used to modify the contents of another {@code BitwiseSet} through
 * logical AND, logical inclusive OR, and logical exclusive OR operations.</p>
 *
 * <p>For store a bit flags, the {@code BitwiseSet} uses one {@code int} field. By default, all
 * bits in the set initially have the value {@code false}.</p>
 *
 * @author <a href="https://github.com/malakhv">Mikhail.Malakhov</a>
 * */
@SuppressWarnings("unused")
public class BitwiseSet {

    /** The string tag for debug. */
    private static final String TAG = BitwiseSet.class.getSimpleName();

    /** The value means all flags are turned off (disabled). This value used by default. */
    public static final int BITWISE_VALUE_ALL_OFF = 0;

    /** The value means all flags are turned on (enabled). */
    public static final int BITWISE_VALUE_ALL_ON = -1;

    /** The digits for represent bit value as string. */
    public static final char[] DIGITS = {'0', '1'};

    /** The inner data of this object (bitwise value). */
    private int mValue = BITWISE_VALUE_ALL_OFF;

    /**
     * Constructs a new {@link BitwiseSet} instance with default values. All bits are
     * initially {@code false}.
     * */
    public BitwiseSet() { this(BITWISE_VALUE_ALL_OFF); }

    /**
     * Constructs a new {@link BitwiseSet} instance. All bits are initially accordingly with
     * {@code value}.
     * @param value The initial value for this {@link BitwiseSet}.
     * */
    public BitwiseSet(int value) { mValue = value; }

    /**
     * Constructs a new {@link BitwiseSet} instance with the same bits as {@code toCopy}.
     * */
    public BitwiseSet(BitwiseSet toCopy) {
        if (toCopy != null) {
            this.setValue(toCopy.getValue());
        } else {
            throw new IllegalArgumentException("The toCopy must be not null.");
        }
    }

    /** Returns the current value this {@code BitwiseSet}. */
    public int getValue() { return mValue; }

    /** Sets a new value for this {@code BitwiseSet}. */
    public void setValue(int value) { mValue = value; }

    /**
     * Checks the status (true or false, turned on or turned of) of the bits specified by mask.
     * @param mask The bit mask, a flag or combination of flags.
     * */
    public boolean is(int mask) { return Bitwise.is(mValue, mask); }

    /**
     * Set the new state of the bits (true or false, turned on or turned of) specified by mask.
     * @param mask The bit mask, a flag or combination of flags.
     * @return The new value of this {@link BitwiseSet}.
     * */
    public int set(int mask, boolean on) { return mValue = Bitwise.set(mValue, mask, on); }

    /**
     * Set the new state of the bit (true or false, turned on or turned of) by index.
     * @param index The bit index.
     * @param on The new state for bit.
     * @return The new value of this {@link BitwiseSet}.
     * */
    public int setBit(int index, boolean on) {
        return on ? turnOn(index) : turnOff(index);
    }

    /**
     * Turns on the bits into {@code value} by specified bit index.
     * @param index The bit index.
     * @return The new value of this {@link BitwiseSet}.
     * */
    public int turnOn(int index) {
        final int mask = Bitwise.getMask(index);
        return mValue = Bitwise.turnOn(mValue, mask);
    }

    /**
     * Turns off the bits into {@code value} by specified bit index.
     * @param index The bit index.
     * @return The new value of this {@link BitwiseSet}.
     * */
    public int turnOff(int index) {
        final int mask = Bitwise.getMask(index);
        return mValue = Bitwise.turnOff(mValue, mask);
    }

    /**
     * Inverts the new state of the bits specified by mask.
     * @param mask The bit mask, a flag or combination of flags.
     * @return The new value of this {@link BitwiseSet}.
     * */
    public int invert(int mask) { return mValue = Bitwise.invert(mValue, mask); }

    /**
     * Sets all bits {@code false} (turns all bits off).
     * */
    public void clear() { clear(false); }

    /**
     * Sets all bits to specified state.
     * @param on The new state for all bits.
     * */
    public void clear(boolean on) {
        setValue(on ? BITWISE_VALUE_ALL_ON : BITWISE_VALUE_ALL_OFF);
    }

    /**
     * Bitwise AND operation with another specified {@link BitwiseSet} object.
     * @return The new value of this {@link BitwiseSet} object.
     * */
    public int and(BitwiseSet bitwiseSet) {
        if (bitwiseSet == null)
            throw new IllegalArgumentException(TAG + ": and(): bitwiseSet is null");
        return and(bitwiseSet.getValue());
    }

    /**
     * Bitwise AND operation with another specified {@code value}.
     * @return The new value of this {@link BitwiseSet} object.
     * */
    public int and(int value) { return mValue = Bitwise.and(mValue, value); }

    /**
     * Bitwise OR operation with another specified {@link BitwiseSet} object.
     * @return The new value of this {@link BitwiseSet} object.
     * */
    public int or(BitwiseSet bitwiseSet) {
        if (bitwiseSet == null)
            throw new IllegalArgumentException(TAG + ": or(): bitwiseSet is null");
        return or(bitwiseSet.getValue());
    }

    /**
     * Bitwise OR operation with another specified {@code value}.
     * @return The new value of this {@link BitwiseSet} object.
     * */
    public int or(int value) { return mValue = Bitwise.or(mValue, value); }

    /**
     * Bitwise XOR operation with another specified {@link BitwiseSet} object.
     * @return The new value of this {@link BitwiseSet} object.
     * */
    public int xor(BitwiseSet bitwiseSet) {
        if (bitwiseSet == null)
            throw new IllegalArgumentException(TAG + ": xor(): bitwiseSet is null");
        return xor(bitwiseSet.getValue());
    }

    /**
     * Bitwise XOR operation with another specified {@code value}.
     * @return The new value of this {@link BitwiseSet} object.
     * */
    public int xor(int value) { return mValue = Bitwise.xor(mValue, value); }

    /**
     * Bitwise NOT operation, inverts all bits in this {@link BitwiseSet} object.
     * */
    public int not() { return mValue = Bitwise.not(mValue); }

    /**
     * Bitwise signed left shift operation.
     * @return Result of logical signed left shift operation for {@code value}.
     * */
    public int shl() { return shl(1); }

    /**
     * Bitwise signed left shift operation.
     * @param shift The number of positions to shift.
     * @return Result of logical signed left shift operation for {@code value}.
     * */
    public int shl(int shift) { return mValue = Bitwise.shl(mValue, shift); }

    /**
     * Bitwise signed right shift operation.
     * @return Result of logical signed right shift operation for {@code value}.
     * */
    public int shr() { return shr(1); }

    /**
     * Bitwise signed right shift operation.
     * @param shift The number of positions to shift.
     * @return Result of logical signed right shift operation for {@code value}.
     * */
    public int shr(int shift) { return mValue = Bitwise.shr(mValue, shift); }

    /**
     * Bitwise unsigned right shift operation.
     * @return Result of logical signed right shift operation for {@code value}.
     * */
    public int sur() { return sur(1); }

    /**
     * Bitwise unsigned right shift operation.
     * @param shift The number of positions to shift.
     * @return Result of logical signed right shift operation for {@code value}.
     * */
    public int sur(int shift) { return mValue = Bitwise.sur(mValue, shift); }

    /**
     * Convert the bits to bytes array.
     * */
    public byte[] toBytes() { return Bitwise.toBytes(mValue); }

    /**
     * Returns a string containing a concise, human-readable description of this object. In this
     * case, it is value of this {@link BitwiseSet} object in decimal format.
     * */
    @Override
    public String toString() { return String.valueOf(getValue()); }

    /**
     * Returns a string containing a concise, human-readable description of this object. In this
     * case, it is binary string representation of the value of this {@link BitwiseSet} object. The
     * returned string is a concatenation of '0' and '1' characters.
     * */
    public String toBinaryString() {
        int value = getValue();
        char[] buf = new char[Integer.SIZE];
        int cursor = buf.length;
        do {
            buf[--cursor] = DIGITS[value & 1]; value >>>= 1;
        } while (cursor > 0);
        return new String(buf);
    }

    /**
     * Returns a string containing a concise, human-readable description of this object. In this
     * case, it is hexadecimal string representation of the value of this {@link BitwiseSet}
     * object.
     * */
    public String toHexString() {
        // TODO Need to implement this
        return null;
    }

    /**
     * Compares this instance with the specified object and indicates if they are equal.
     * @param o The object to compare this instance with.
     * */
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (this.getClass() != o.getClass()) return false;
        final BitwiseSet obj = (BitwiseSet) o;
        return this.getValue() == obj.getValue();
    }

    /**
     * Returns an integer hash code for this object. Int this implementation it just equals value
     * of this {@link BitwiseSet} object.
     * */
    @Override
    public int hashCode() { return getValue(); }

    /**
     * Class that contains methods for working with bitwise operations.
     * @author Mikhail Malakhov, 2015
     * */
    public static class Bitwise {

        /** This class has only static data. Not needed to create instance. */
        private Bitwise() {}

        /**
         * Turns on the bits into {@code value} by specified {@code mask}.
         * @param value The original value.
         * @param mask The bit mask.
         * @return The modified {@code value}.
         * */
        public static int turnOn(int value, int mask) { return value | mask; }

        /**
         * Turns off the bits into {@code value} by specified {@code mask}.
         * @param value The original value.
         * @param mask The bit mask.
         * @return The modified {@code value}.
         * */
        public static int turnOff(int value, int mask) {
            return value & (~mask);
        }

        /**
         * Checks the bits are turned on by specified {@code mask}.
         * @param value The original value.
         * @param mask The bit mask.
         * @return True, if all bits specified in {@code mask} turned on into {@code value}.
         * Otherwise {@code false}.
         * */
        public static boolean is(int value, int mask) { return (value & mask) == mask; }

        /**
         * Turns on/off the bits into {@code value} by specified {@code mask}.
         * @param value The original value.
         * @param mask The bit mask.
         * @param on If {@code true}, turns on the specified bits, otherwise turns off.
         * @return The modified {@code value}.
         * */
        public static int set(int value, int mask, boolean on) {
            if (on) return turnOn(value, mask);
            else return turnOff(value, mask);
        }

        /**
         * Turns on/off the bits into {@code value} by specified {@code mask}.
         * @param value The original value.
         * @param mask The bit mask.
         * @return The modified {@code value}.
         * */
        public static int invert(int value, int mask) { return value ^ mask; }

        /**
         * Bitwise AND operation.
         * @param value1 The operand one.
         * @param value2 The operand two.
         * @return Result of logical AND operation between operand one and operand two.
         * */
        public static int and(int value1, int value2) { return value1 & value2; }

        /**
         * Bitwise OR operation.
         * @param value1 The operand one.
         * @param value2 The operand two.
         * @return Result of logical OR operation between operand one and operand two.
         * */
        public static int or(int value1, int value2) { return value1 | value2; }

        /**
         * Bitwise XOR operation.
         * @param value1 The operand one.
         * @param value2 The operand two.
         * @return Result of logical XOR operation between operand one and operand two.
         * */
        public static int xor(int value1, int value2) { return value1 ^ value2; }

        /**
         * Bitwise NOT operation, inverts all bits in {@code value}.
         * @param value The original value.
         * @return Result of logical NOT operation for {@code value}.
         * */
        public static int not(int value) { return ~value; }

        /**
         * Bitwise signed left shift operation.
         * @param value The original value.
         * @return Result of logical signed left shift operation for {@code value}.
         * */
        public static int shl(int value) { return Bitwise.shl(value ,1); }

        /**
         * Bitwise signed left shift operation.
         * @param value The original value.
         * @param shift The number of positions to shift.
         * @return Result of logical signed left shift operation for {@code value}.
         * */
        public static int shl(int value, int shift) { return value << shift; }

        /**
         * Bitwise signed right shift operation.
         * @param value The original value.
         * @return Result of logical signed right shift operation for {@code value}.
         * */
        public static int shr(int value) { return Bitwise.shr(value ,1); }

        /**
         * Bitwise signed right shift operation.
         * @param value The original value.
         * @param shift The number of positions to shift.
         * @return Result of logical signed right shift operation for {@code value}.
         * */
        public static int shr(int value, int shift) { return value >> shift; }

        /**
         * Bitwise unsigned right shift operation.
         * @param value The original value.
         * @return Result of logical signed right shift operation for {@code value}.
         * */
        public static int sur(int value) { return Bitwise.shr(value ,1); }

        /**
         * Bitwise unsigned right shift operation.
         * @param value The original value.
         * @param shift The number of positions to shift.
         * @return Result of logical signed right shift operation for {@code value}.
         * */
        public static int sur(int value, int shift) { return value >>> shift; }

        /**
         * Returns the bit mask for bit by specified bit index.
         * @param index the index of bit
         * */
        public static int getMask(int index) {
            if (index < 0 || index > (Integer.SIZE - 1)) return 0;
            return  1 << (index);
        }

        /**
         * Convert specified integer value to byte array with {@code 0} and {@code 1} bits values.
         * */
        public static byte[] toBytes(int value) {
            final byte[] bytes = new byte[Integer.SIZE];
            int mask = 1 << bytes.length;
            for (int i = 0; i < bytes.length; i++, mask >>>= 1 ) {
                bytes[i] = (byte) (Bitwise.is(value, mask) ? 1 : 0);
            }
            return bytes;
        }
    }
}
