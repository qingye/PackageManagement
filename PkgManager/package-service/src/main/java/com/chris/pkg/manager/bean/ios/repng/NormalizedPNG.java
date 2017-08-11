package com.chris.pkg.manager.bean.ios.repng;

import com.chris.pkg.manager.bean.io.Reader;
import com.chris.pkg.manager.utils.FileUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * Created by chris on 2017/8/10.
 */
public class NormalizedPNG {

    private static final String pngHeader = new String(new byte[]{-119, 80, 78, 71, 13, 10, 26, 10});
    private Reader reader = null;
    private File pngFile = null;

    public NormalizedPNG(File file) {
        try {
            this.reader = new Reader(new FileInputStream(file), true);
            ByteArrayOutputStream png = new ByteArrayOutputStream();
            if (checkPNG(png)) {
                decodePNG(png);
            }
            this.pngFile = FileUtil.write("normalized.png", png.toByteArray());
            png.close();
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.reader = null;
    }

    public File getPNGFile() {
        return this.pngFile;
    }

    private boolean checkPNG(ByteArrayOutputStream png) throws IOException {
        byte[] buffer = this.reader.readByteArray(8);
        String strHeader = new String(buffer);
        if (strHeader.equals(pngHeader)) {
            png.write(buffer, 0, 8);
            return true;
        }
        return false;
    }

    private void decodePNG(ByteArrayOutputStream png) throws Exception {
        int width = 0;
        int height = 0;
        ByteArrayOutputStream idat = new ByteArrayOutputStream();
        boolean breaks = false;
        for (; ; ) {
            boolean skip = false;
            IOSPngChunk chunk = getIOSPngChunk();
            switch (chunk.getType()) {
                case "IHDR":
                    width = this.reader.bytesToInt(chunk.getData(), 0, 4);
                    height = this.reader.bytesToInt(chunk.getData(), 4, 4);
                    break;
                case "IDAT":
                    idat.write(chunk.getData(), 0, chunk.getLength());
                    skip = true;
                    break;
                case "CgBI":
                    skip = true;
                    break;
                case "IEND":
                    breaks = true;
                    byte[] bytes = decompress(idat.toByteArray());
                    chunk = swapPixel(bytes, width, height);
            }
            if (!skip) {
                png.write(this.reader.intToByte(chunk.getLength()));
                png.write(chunk.getType().getBytes());
                if (chunk.getLength() > 0) {
                    png.write(chunk.getData());
                }
                png.write(this.reader.intToByte(chunk.getCrc()));
            }
            if (breaks) {
                break;
            }
        }
    }

    private IOSPngChunk getIOSPngChunk() {
        IOSPngChunk chunk = new IOSPngChunk();
        chunk.setLength(this.reader.readInt());
        chunk.setType(new String(this.reader.readByteArray(4)));
        chunk.setData(this.reader.readByteArray(chunk.getLength()));
        chunk.setCrc(this.reader.readInt());
        return chunk;
    }

    private IOSPngChunk swapPixel(byte[] data, int width, int height) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = 0;
        for (int h = 0; h < height; h++) {
            i = baos.size();
            baos.write(data[i]);
            for (int w = 0; w < width; w++) {
                i = baos.size();
                baos.write(data[(i + 2)]);
                baos.write(data[(i + 1)]);
                baos.write(data[(i + 0)]);
                baos.write(data[(i + 3)]);
            }
        }
        byte[] swap = compress(baos.toByteArray());
        IOSPngChunk chunk = new IOSPngChunk();
        chunk.setLength(swap.length);
        chunk.setType("IDAT");
        chunk.setData(swap);
        crc(chunk);

        return chunk;
    }

    private byte[] compress(byte[] data) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DeflaterOutputStream gzipOutputStream = new DeflaterOutputStream(baos);
        try {
            gzipOutputStream.write(data);
            gzipOutputStream.finish();
        } catch (IOException e) {
            throw e;
        } finally {
            gzipOutputStream.close();
            baos.close();
        }
        return baos.toByteArray();
    }

    private byte[] decompress(byte[] data) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream inflaterInputStream = new InflaterInputStream(new ByteArrayInputStream(data), new Inflater(true));

        int count = 0;
        byte[] buffer = new byte[1024];
        try {
            while ((count = inflaterInputStream.read(buffer, 0, 1024)) != -1) {
                baos.write(buffer, 0, count);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            inflaterInputStream.close();
            baos.close();
        }
        return baos.toByteArray();
    }

    private void crc(IOSPngChunk chunk) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(chunk.getType().getBytes());
        baos.write(chunk.getData());

        CRC32 crc32 = new CRC32();
        crc32.update(baos.toByteArray());
        chunk.setCrc((int) crc32.getValue());
    }
}
