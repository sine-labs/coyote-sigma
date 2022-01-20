package com.sine_labs.cs.ascii_img;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class Image {
    Color[][] img;

    public Image(int h, int w) {
        img = new Color[h][w];
        for (int r = 0; r < h; r++) {
            Arrays.fill(img[r], Color.WHITE);
        }
    }

    public Image(File file) throws IOException {
        BufferedImage read = ImageIO.read(file);
        img = new Color[read.getHeight()][read.getWidth()];
        for (int r = 0; r < img.length; r++) {
            for (int c = 0; c < img[r].length; c++) {
                img[r][c] = new Color(read.getRGB(r, c));
            }
        }
    }

    public Color getPixel(int r, int c) { return img[r][c]; }

    public int getHeight() { return img.length; }

    public int getWidth() { return img[0].length; }

    public void setPixel(int r, int c, Color col) { img[r][c] = col; }

    public void setPixel(int r, int c, int darkness) {
        img[r][c] = new Color(darkness, darkness, darkness);
    }

    public static int gScale(Color col) {
        return (col.getRed() + col.getGreen() + col.getBlue()) / 3;
    }
}
