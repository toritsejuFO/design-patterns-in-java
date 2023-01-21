package co.learn.designpatterns.patterns.decorator.four;


import co.learn.log.Log;

import java.util.stream.IntStream;

class MyStringBuilder {
    private StringBuilder sb;

    public MyStringBuilder() {
        this.sb = new StringBuilder();
    }

    public MyStringBuilder(String string) {
        this.sb = new StringBuilder(string);
    }

    public MyStringBuilder concat(String string) {
        return new MyStringBuilder(sb.toString().concat(string));
    }

    public MyStringBuilder appendLine(String string) {
        sb.append(string).append(System.lineSeparator());
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }

    // decorator
    public int compareTo(MyStringBuilder another) {
        return sb.compareTo(another.sb);
    }

    public MyStringBuilder append(Object obj) {
        sb.append(obj);
        return this;
    }

    public MyStringBuilder append(String str) {
        sb.append(str);
        return this;
    }

    public MyStringBuilder append(StringBuffer sb) {
        this.sb.append(sb);
        return this;
    }

    public MyStringBuilder append(CharSequence s) {
        sb.append(s);
        return this;
    }

    public MyStringBuilder append(CharSequence s, int start, int end) {
        sb.append(s, start, end);
        return this;
    }

    public MyStringBuilder append(char[] str) {
        sb.append(str);
        return this;
    }

    public MyStringBuilder append(char[] str, int offset, int len) {
        sb.append(str, offset, len);
        return this;
    }

    public MyStringBuilder append(boolean b) {
        sb.append(b);
        return this;
    }

    public MyStringBuilder append(char c) {
        sb.append(c);
        return this;
    }

    public MyStringBuilder append(int i) {
        sb.append(i);
        return this;
    }

    public MyStringBuilder append(long lng) {
        sb.append(lng);
        return this;
    }

    public MyStringBuilder append(float f) {
        sb.append(f);
        return this;
    }

    public MyStringBuilder append(double d) {
        sb.append(d);
        return this;
    }

    public MyStringBuilder appendCodePoint(int codePoint) {
        sb.appendCodePoint(codePoint);
        return this;
    }

    public MyStringBuilder delete(int start, int end) {
        sb.delete(start, end);
        return this;
    }

    public MyStringBuilder deleteCharAt(int index) {
        sb.deleteCharAt(index);
        return this;
    }

    public MyStringBuilder replace(int start, int end, String str) {
        sb.replace(start, end, str);
        return this;
    }

    public MyStringBuilder insert(int index, char[] str, int offset, int len) {
        sb.insert(index, str, offset, len);
        return this;
    }

    public MyStringBuilder insert(int offset, Object obj) {
        sb.insert(offset, obj);
        return this;
    }

    public MyStringBuilder insert(int offset, String str) {
        sb.insert(offset, str);
        return this;
    }

    public MyStringBuilder insert(int offset, char[] str) {
        sb.insert(offset, str);
        return this;
    }

    public MyStringBuilder insert(int dstOffset, CharSequence s) {
        sb.insert(dstOffset, s);
        return this;
    }

    public MyStringBuilder insert(int dstOffset, CharSequence s, int start, int end) {
        sb.insert(dstOffset, s, start, end);
        return this;
    }

    public MyStringBuilder insert(int offset, boolean b) {
        sb.insert(offset, b);
        return this;
    }

    public MyStringBuilder insert(int offset, char c) {
        sb.insert(offset, c);
        return this;
    }

    public MyStringBuilder insert(int offset, int i) {
        sb.insert(offset, i);
        return this;
    }

    public MyStringBuilder insert(int offset, long l) {
        sb.insert(offset, l);
        return this;
    }

    public MyStringBuilder insert(int offset, float f) {
        sb.insert(offset, f);
        return this;
    }

    public MyStringBuilder insert(int offset, double d) {
        sb.insert(offset, d);
        return this;
    }

    public int indexOf(String str) {
        return sb.indexOf(str);
    }

    public int indexOf(String str, int fromIndex) {
        return sb.indexOf(str, fromIndex);
    }

    public int lastIndexOf(String str) {
        return sb.lastIndexOf(str);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return sb.lastIndexOf(str, fromIndex);
    }

    public MyStringBuilder reverse() {
        sb.reverse();
        return this;
    }

    public int length() {
        return sb.length();
    }

    public int capacity() {
        return sb.capacity();
    }

    public void ensureCapacity(int minimumCapacity) {
        sb.ensureCapacity(minimumCapacity);
    }

    public void trimToSize() {
        sb.trimToSize();
    }

    public void setLength(int newLength) {
        sb.setLength(newLength);
    }

    public char charAt(int index) {
        return sb.charAt(index);
    }

    public int codePointAt(int index) {
        return sb.codePointAt(index);
    }

    public int codePointBefore(int index) {
        return sb.codePointBefore(index);
    }

    public int codePointCount(int beginIndex, int endIndex) {
        return sb.codePointCount(beginIndex, endIndex);
    }

    public int offsetByCodePoints(int index, int codePointOffset) {
        return sb.offsetByCodePoints(index, codePointOffset);
    }

    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        sb.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    public void setCharAt(int index, char ch) {
        sb.setCharAt(index, ch);
    }

    public String substring(int start) {
        return sb.substring(start);
    }

    public CharSequence subSequence(int start, int end) {
        return sb.subSequence(start, end);
    }

    public String substring(int start, int end) {
        return sb.substring(start, end);
    }

    public IntStream chars() {
        return sb.chars();
    }

    public IntStream codePoints() {
        return sb.codePoints();
    }

    public static int compare(CharSequence cs1, CharSequence cs2) {
        return CharSequence.compare(cs1, cs2);
    }
}

public class Demo {
    public static void main(String[] args) {
        MyStringBuilder msb = new MyStringBuilder();
        msb.append("Hello").appendLine(" world.");
        Log.log(msb.concat("Add this too.").toString());

    }
}
