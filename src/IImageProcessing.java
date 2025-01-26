/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author leo
 */
public interface IImageProcessing {
    /**
     * Generate the intensity histogram from the given image pixels. 
     * Image pixels is a 3D array, with the following format: [width][height][R, G, B].
     * Returning 2 dimensional array of the intensity histogram, [R, G, B][0...255].
     */
    public int[][] generateIntensityHistogram(int[][][] imagePixels);
}
