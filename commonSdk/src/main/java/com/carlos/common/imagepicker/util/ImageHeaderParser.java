/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.media.ExifInterface
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.carlos.common.imagepicker.util;

import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Log;
import com.kook.librelease.StringFog;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class ImageHeaderParser {
    private static final String TAG = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAgIP2gzNApiDiAwKAguDG4gRQNrDgpF"));
    public static final int UNKNOWN_ORIENTATION = -1;
    private static final int EXIF_MAGIC_NUMBER = 65496;
    private static final int MOTOROLA_TIFF_MAGIC_NUMBER = 19789;
    private static final int INTEL_TIFF_MAGIC_NUMBER = 18761;
    private static final String JPEG_EXIF_SEGMENT_PREAMBLE = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JQdfCWglIxI="));
    private static final byte[] JPEG_EXIF_SEGMENT_PREAMBLE_BYTES = com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JQdfCWglIxI=")).getBytes(Charset.forName(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQUqW3pTRVo="))));
    private static final int SEGMENT_SOS = 218;
    private static final int MARKER_EOI = 217;
    private static final int SEGMENT_START_ID = 255;
    private static final int EXIF_SEGMENT_TYPE = 225;
    private static final int ORIENTATION_TAG_TYPE = 274;
    private static final int[] BYTES_PER_FORMAT = new int[]{0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8};
    private final Reader reader;

    public ImageHeaderParser(InputStream is) {
        this.reader = new StreamReader(is);
    }

    public int getOrientation() throws IOException {
        int magicNumber = this.reader.getUInt16();
        if (!ImageHeaderParser.handles(magicNumber)) {
            if (Log.isLoggable((String)TAG, (int)3)) {
                Log.d((String)TAG, (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ihg+Km8zNARLHgo1KAgqDnkKAShqETgbLggEJ0saQSRuJBosOD4qCWUFFj9vIFA3")) + magicNumber));
            }
            return -1;
        }
        int exifSegmentLength = this.moveToExifSegmentAndGetLength();
        if (exifSegmentLength == -1) {
            if (Log.isLoggable((String)TAG, (int)3)) {
                Log.d((String)TAG, (String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JT4+CWoFNCxLEQo1PxgmOWoaAit4ESgzIxghJGEgLCJsDgodIzpXKGsFBjFsAVg7DRcYOXkaFi5sJwU3Ki4uPWoVNCZmVyQ2Ki41OmkVNAVlNyxF")));
            }
            return -1;
        }
        byte[] exifData = new byte[exifSegmentLength];
        return this.parseExifSegment(exifData, exifSegmentLength);
    }

    private int parseExifSegment(byte[] tempArray, int exifSegmentLength) throws IOException {
        int read = this.reader.read(tempArray, exifSegmentLength);
        if (read != exifSegmentLength) {
            if (Log.isLoggable((String)TAG, (int)3)) {
                Log.d((String)TAG, (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQgcP2sjHitLEQo1PxguPW4jAShrDlkaLio6D2IKJChuDh49OD4cO28VJwJ7AQI0Jj0+M29SHTY=")) + exifSegmentLength + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("M186P2swMAV9DlEoLQMmKGkjQSx+MzxF")) + read));
            }
            return -1;
        }
        boolean hasJpegExifPreamble = this.hasJpegExifPreamble(tempArray, exifSegmentLength);
        if (hasJpegExifPreamble) {
            return ImageHeaderParser.parseExifSegment(new RandomAccessReader(tempArray, exifSegmentLength));
        }
        if (Log.isLoggable((String)TAG, (int)3)) {
            Log.d((String)TAG, (String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwgYKW8zAiZiICQyIxcMM34zGjBqAScrKQcMJ30KQSVsHgpF")));
        }
        return -1;
    }

    private boolean hasJpegExifPreamble(byte[] exifData, int exifSegmentLength) {
        boolean result;
        boolean bl = result = exifData != null && exifSegmentLength > JPEG_EXIF_SEGMENT_PREAMBLE_BYTES.length;
        if (result) {
            for (int i = 0; i < JPEG_EXIF_SEGMENT_PREAMBLE_BYTES.length; ++i) {
                if (exifData[i] == JPEG_EXIF_SEGMENT_PREAMBLE_BYTES[i]) continue;
                result = false;
                break;
            }
        }
        return result;
    }

    private int moveToExifSegmentAndGetLength() throws IOException {
        int segmentLength;
        block7: {
            short segmentType;
            long skipped;
            do {
                short segmentId;
                if ((segmentId = this.reader.getUInt8()) != 255) {
                    if (Log.isLoggable((String)TAG, (int)3)) {
                        Log.d((String)TAG, (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQgcMWojGj1gMCQpKAc6D2kjMAZiAS8o")) + segmentId));
                    }
                    return -1;
                }
                segmentType = this.reader.getUInt8();
                if (segmentType == 218) {
                    return -1;
                }
                if (segmentType == 217) {
                    if (Log.isLoggable((String)TAG, (int)3)) {
                        Log.d((String)TAG, (String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JT4AI2ojMyhoDCAAISsMAmMLGg9iDTwaLCo6J2caGiF5ESwuLwgmJ2UwMFo=")));
                    }
                    return -1;
                }
                segmentLength = this.reader.getUInt16() - 2;
                if (segmentType == 225) break block7;
            } while ((skipped = this.reader.skip(segmentLength)) == (long)segmentLength);
            if (Log.isLoggable((String)TAG, (int)3)) {
                Log.d((String)TAG, (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQgcP2sjHitLEQo1PxgqCWwgTChrARocKhgmLEsaFiRqHicbOD0cM2wVNy57AVRF")) + segmentType + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("M186LWsVBgZiDg08LBdeOmoFJC9sV1Ar")) + segmentLength + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("M186OmwaMyh9DiggLAciCG8wLyhsJ10aKQc6J2JSGSM=")) + skipped));
            }
            return -1;
        }
        return segmentLength;
    }

    private static int parseExifSegment(RandomAccessReader segmentData) {
        ByteOrder byteOrder;
        int headerOffsetSize = JPEG_EXIF_SEGMENT_PREAMBLE.length();
        short byteOrderIdentifier = segmentData.getInt16(headerOffsetSize);
        if (byteOrderIdentifier == 19789) {
            byteOrder = ByteOrder.BIG_ENDIAN;
        } else if (byteOrderIdentifier == 18761) {
            byteOrder = ByteOrder.LITTLE_ENDIAN;
        } else {
            if (Log.isLoggable((String)TAG, (int)3)) {
                Log.d((String)TAG, (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IQgcMWojGj1gMCQ/Kj02MW4jMCZrDjA6PQMHJA==")) + byteOrderIdentifier));
            }
            byteOrder = ByteOrder.BIG_ENDIAN;
        }
        segmentData.order(byteOrder);
        int firstIfdOffset = segmentData.getInt32(headerOffsetSize + 4) + headerOffsetSize;
        int tagCount = segmentData.getInt16(firstIfdOffset);
        for (int i = 0; i < tagCount; ++i) {
            int byteCount;
            int tagOffset = ImageHeaderParser.calcTagOffset(firstIfdOffset, i);
            short tagType = segmentData.getInt16(tagOffset);
            if (tagType != 274) continue;
            short formatCode = segmentData.getInt16(tagOffset + 2);
            if (formatCode < 1 || formatCode > 12) {
                if (!Log.isLoggable((String)TAG, (int)3)) continue;
                Log.d((String)TAG, (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JS4ALHsFAiZmNCAoKQc1OmkVNARlATg/PQg2KWIaLyN4CiBF")) + formatCode));
                continue;
            }
            int componentCount = segmentData.getInt32(tagOffset + 4);
            if (componentCount < 0) {
                if (!Log.isLoggable((String)TAG, (int)3)) continue;
                Log.d((String)TAG, (String)com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Oz4uPWsaMC9mNDM8LBccPGlSTSllJw47LD4cJ2AzESNpJFk+KRccVg==")));
                continue;
            }
            if (Log.isLoggable((String)TAG, (int)3)) {
                Log.d((String)TAG, (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JS4ALHsKMDdiIgY2KBcMInsjSFo=")) + i + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhcqP2g2MD9hHjMd")) + tagType + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhgiD28jEjdmHCg1KBcLJQ==")) + formatCode + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Phg2D2oaICVgNDA2LBUqDWUjMAZ5AVRF")) + componentCount));
            }
            if ((byteCount = componentCount + BYTES_PER_FORMAT[formatCode]) > 4) {
                if (!Log.isLoggable((String)TAG, (int)3)) continue;
                Log.d((String)TAG, (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JS4ALHsFFj9mHjM8Ly1fLW8aASh5Mz8/PAQ6KmAjESNsJygiLy4qCmgKMAVqNx07DRcuJmwzLD1vHgYwIz4lDnsFOCVhNF07LBUqDWkzBTM=")) + formatCode));
                continue;
            }
            int tagValueOffset = tagOffset + 8;
            if (tagValueOffset < 0 || tagValueOffset > segmentData.length()) {
                if (!Log.isLoggable((String)TAG, (int)3)) continue;
                Log.d((String)TAG, (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAgEDmgVPDdgVyQgLwc6Bm4jOAVrDwYtLi02J2ZSQVo=")) + tagValueOffset + com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("PhcqP2g2MD9hHjMd")) + tagType));
                continue;
            }
            if (byteCount < 0 || tagValueOffset + byteCount > segmentData.length()) {
                if (!Log.isLoggable((String)TAG, (int)3)) continue;
                Log.d((String)TAG, (String)(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAgEDmgVPDdgVyQ2LAdXOGkgRChlJycrLS0YCmIFNyNuNFk7ODscBXgaMDNoMzwzOwgMKHkVLDVpDBogKhgtOw==")) + tagType));
                continue;
            }
            return segmentData.getInt16(tagValueOffset);
        }
        return -1;
    }

    private static int calcTagOffset(int ifdOffset, int tagIndex) {
        return ifdOffset + 2 + 12 * tagIndex;
    }

    private static boolean handles(int imageMagicNumber) {
        return (imageMagicNumber & 0xFFD8) == 65496 || imageMagicNumber == 19789 || imageMagicNumber == 18761;
    }

    public static void copyExif(ExifInterface originalExif, int width, int height, String imageOutputPath) {
        String[] attributes = new String[]{com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JTwcI2oVFithN1RF")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRg+LGgYMC9gDjBF")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JRg+LGgYMC9gDjAWKQc6MWUzLDJrASxF")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JQdfKGowLAVhNDBLKQdXPQ==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JT4EP28zRVo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JT4AOWsVHg5iDlk9LBcAVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JSs6A2MVHgZjAQovKBcMVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JSs6A2MVHgZjAQovKBcMAmkjHlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JSs6A2AFJAZiDyggLwdXKg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JSs6A2IFJAZjAQovKBcMVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JSs6A2IFJAZjAQovKBcMAmkjHlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JSs6A2IFGiZiJAYgLAc2PQ==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JSs6A2IFGiZiJAYgLAc2PWIVGi4=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JSs6A2cKFiV9JDApIy0cDmkLPCtvEVkcLghSVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JSs6A2QFAiNiDyggLwdXKg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAU2UmcwICtiDgoALwg2MW8VEgM=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Owg+MWgVSFo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OwgAPGgVHlo=")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ii0uOmczNCluHgY3KAhSVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ii0uOmczNCluHgY3KAU2MWkFLAZqDlEgLghSVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Ii0uOmczNCluHgY3KAVfKGwjEi9lNzgd")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("IS5fCWwFNBR9DlE7Kj0qPQ=="))};
        try {
            ExifInterface newExif = new ExifInterface(imageOutputPath);
            for (String attribute : attributes) {
                String value = originalExif.getAttribute(attribute);
                if (TextUtils.isEmpty((CharSequence)value)) continue;
                newExif.setAttribute(attribute, value);
            }
            newExif.setAttribute(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAgIP2gzNFJjDgogKRhSVg==")), String.valueOf(width));
            newExif.setAttribute(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("JAgIP2gzNA5iDlk9LBcAVg==")), String.valueOf(height));
            newExif.setAttribute(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Oy0MCWgVBgZ9AQozKi0YVg==")), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("OhhSVg==")));
            newExif.saveAttributes();
        }
        catch (IOException e) {
            Log.d((String)TAG, (String)e.getMessage());
        }
    }

    private static class StreamReader
    implements Reader {
        private final InputStream is;

        public StreamReader(InputStream is) {
            this.is = is;
        }

        @Override
        public int getUInt16() throws IOException {
            return this.is.read() << 8 & 0xFF00 | this.is.read() & 0xFF;
        }

        @Override
        public short getUInt8() throws IOException {
            return (short)(this.is.read() & 0xFF);
        }

        @Override
        public long skip(long total) throws IOException {
            if (total < 0L) {
                return 0L;
            }
            long toSkip = total;
            while (toSkip > 0L) {
                long skipped = this.is.skip(toSkip);
                if (skipped > 0L) {
                    toSkip -= skipped;
                    continue;
                }
                int testEofByte = this.is.read();
                if (testEofByte == -1) break;
                --toSkip;
            }
            return total - toSkip;
        }

        @Override
        public int read(byte[] buffer, int byteCount) throws IOException {
            int toRead;
            int read;
            for (toRead = byteCount; toRead > 0 && (read = this.is.read(buffer, byteCount - toRead, toRead)) != -1; toRead -= read) {
            }
            return byteCount - toRead;
        }
    }

    private static interface Reader {
        public int getUInt16() throws IOException;

        public short getUInt8() throws IOException;

        public long skip(long var1) throws IOException;

        public int read(byte[] var1, int var2) throws IOException;
    }

    private static class RandomAccessReader {
        private final ByteBuffer data;

        public RandomAccessReader(byte[] data, int length) {
            this.data = (ByteBuffer)ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN).limit(length);
        }

        public void order(ByteOrder byteOrder) {
            this.data.order(byteOrder);
        }

        public int length() {
            return this.data.remaining();
        }

        public int getInt32(int offset) {
            return this.data.getInt(offset);
        }

        public short getInt16(int offset) {
            return this.data.getShort(offset);
        }
    }
}

