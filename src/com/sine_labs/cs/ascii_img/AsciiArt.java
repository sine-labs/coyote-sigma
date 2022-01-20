package com.sine_labs.cs.ascii_img;

import java.util.Arrays;

public class AsciiArt {
    // https://www.geeksforgeeks.org/converting-image-ascii-image-python/
    private static final String G_SCALE = (
            "$@B%8&WM#*oahkbdpqwmZO0QLCJUYX"
                    + "zcvunxrjft/\\|()1{}[]?-_+~i!l"
                    + "I;:,\\\"^`\". "
    );
    private char[][] ascii;

    public AsciiArt(Image img, int scale) {
        double avg = 0;
        int[][] gScale = new int[img.getHeight()][img.getWidth()];
        for (int r = 0; r < gScale.length; r++) {
            for (int c = 0; c < gScale[0].length; c++) {
                gScale[r][c] = Image.gScale(img.getPixel(r, c));
                avg += gScale[r][c];
            }
        }

        // clamp it between 0 & 100
        int newHeight = img.getHeight() / scale;
        int newWidth = img.getWidth() / scale;
        ascii = new char[newHeight][newWidth];
        for (int r = 0; r < newHeight; r++) {
            for (int c = 0; c < newWidth; c++) {
                int total = 0;
                for (int nr = r * scale; nr < (r + 1) * scale; nr++) {
                    for (int nc = c * scale; nc < (c + 1) * scale; nc++) {
                        total += gScale[nr][nc];
                    }
                }
                int brightness = total / (scale * scale);
                ascii[r][c] = G_SCALE.charAt(brightness * G_SCALE.length() / 256);
            }
        }
    }

    public char getPixel(int r, int c) {
        return ascii[r][c];
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (char[] r : ascii) {
            for (char c : r) {
                ret.append(c);
            }
            ret.append('\n');
        }
        return ret.toString();
    }
}
