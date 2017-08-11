package com.chris.pkg.manager.bean.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    private InputStream stream = null;
    private int position = 0;
    private int lastPosition = 0;
    private boolean bigEndian = false;

    public Reader(InputStream stream, boolean bigEndian) {
        this.stream = stream;
        this.bigEndian = bigEndian;
        this.position = 0;
    }

    public void mark() {
        this.lastPosition = this.position;
    }

    public int totalConsume() {
        int total = this.position - this.lastPosition;
        this.lastPosition = -1;
        return total;
    }

    public int getPosition() {
        return this.position;
    }

    public int readByte() {
        return readInt(1);
    }

    public int readShort() {
        return readInt(2);
    }

    public int readInt() {
        return readInt(4);
    }

    public byte[] readByteArray(int length) {
        return readByte(length);
    }

    public char[] readCharArray(int length) {
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = ((char) readShort());
        }
        return chars;
    }

    public int[] readIntArray(int length) {
        int[] intValue = new int[length];
        for (int i = 0; i < length; i++) {
            intValue[i] = readInt();
        }
        return intValue;
    }

    public String[] readUtf8StringArray(int[] offsets) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < offsets.length - 1; i++) {
            readShort();
            int size = offsets[(i + 1)] - offsets[i] - 2;
            if (size > 127) {
                int b = readByte();
                if (b >> 4 == 8) {
                    readByte();
                    size--;
                }
                size--;
            }
            try {
                stringList.add(toAscii(new String(readByteArray(size), "utf-8")));
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return (String[]) stringList.toArray(new String[0]);
    }

    public String[] readUtf16StringArray(int length) {
        List<String> stringList = new ArrayList<>();
        while (stringList.size() < length) {
            int size = (readShort() + 1) * 2;
            stringList.add(toAscii(new String(readByteArray(size))));
        }
        return (String[]) stringList.toArray(new String[0]);
    }

    private byte[] readByte(int length) {
        byte[] bytes = new byte[length];
        try {
            if (this.stream.read(bytes, 0, length) != length) {
                bytes = null;
            } else {
                this.position += length;
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return bytes;
    }

    private int readInt(int length) {
        int value = 0;
        if ((length > 0) && (length <= 4)) {
            byte[] bytes = new byte[length];
            try {
                int consume = this.stream.read(bytes, 0, length);
                if (consume > 0) {
                    if (consume == length) {
                        value = bytesToInt(bytes);
                    }
                    this.position += consume;
                } else if (consume == -1) {
                    value = -1;
                }
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
        return value;
    }

    private int bytesToInt(byte[] bytes) {
        int value = 0;
        int length = bytes.length;
        if (this.bigEndian) {
            for (int i = 0; i < length; i++) {
                value |= (bytes[i] & 0xFF) << (length - i - 1) * 8;
            }
        } else {
            for (int i = 0; i < length; i++) {
                value |= (bytes[i] & 0xFF) << i * 8;
            }
        }
        return value;
    }

    public byte[] intToByte(int value) {
        byte[] bit = new byte[4];
        if (this.bigEndian) {
            for (int i = 0; i < 4; i++) {
                bit[i] = ((byte) (value >> (4 - i - 1) * 8));
            }
        } else {
            for (int i = 0; i < 4; i++) {
                bit[i] = ((byte) (value >> i * 8));
            }
        }
        return bit;
    }

    public String toAscii(String utfString) {
        if (utfString == null) {
            return null;
        }
        byte[] src = utfString.getBytes();
        ArrayList<Byte> list = new ArrayList<>();
        for (byte b : src) {
            if (b != 0) {
                list.add(b);
            }
        }
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i);
        }
        return new String(bytes);
    }

    public int bytesToInt(byte[] src, int start, int length) {
        int value = 0;
        if (start + length < src.length) {
            byte[] bytes = getBytes(src, start, length);
            value = bytesToInt(bytes);
        }
        return value;
    }

    public byte[] getBytes(byte[] src, int start, int length) {
        byte[] bytes = new byte[length];
        if (start + length < src.length) {
            System.arraycopy(src, start, bytes, 0, length);
        }
        return bytes;
    }
}
