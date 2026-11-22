import java.awt.*;
import java.awt.image.BufferedImage;

class ImageOperations {

    /**
     * Takes out all the values representing the red color from the image data.
     *
     * @param img The image being modified.
     * @return A new image without any red values.
     */
    static BufferedImage zeroRed(BufferedImage img) {
        int width= img.getWidth();
        int height= img.getHeight();
        BufferedImage newImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB );
       for(int i=0; i<height; i++){
           for(int j=0;j<width;j++){
               Color base= new Color(img.getRGB(j,i));
               Color mod= new Color(0,base.getGreen(), base.getBlue());
               newImg.setRGB(j,i,mod.getRGB());
           }
       }
        return newImg;
    }

    /**
     * Converts the image to grayscale by modifying the color value data.
     *
     * @param img The image being modified.
     * @return Returns a new image in grayscale.
     */
    static BufferedImage grayscale(BufferedImage img) {
        int width= img.getWidth();
        int height= img.getHeight();
        BufferedImage newImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB );
        for(int i=0; i<width; i++){
            for(int j=0; j<height;j++){
                Color base= new Color(img.getRGB(j,i));
                int avg= (base.getRed()+base.getGreen()+base.getBlue())/3;
                Color gray= new Color (avg,avg,avg);
                newImg.setRGB(j,i,gray.getRGB());
            }
        }
        return newImg;
    }

    /**
     * Inverts the color value data for the image's pixels.
     * @param img The image being modified.
     * @return A color inverted version of the image.
     */
    static BufferedImage invert(BufferedImage img) {
        int width= img.getWidth();
        int height= img.getHeight();
        BufferedImage newImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB );
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                Color base=new Color(img.getRGB(j,i));
                Color inv= new Color(255-base.getRed(),255- base.getGreen(),255- base.getBlue());
                newImg.setRGB(j,i,inv.getRGB());
            }
        }
        return newImg;
    }

    /**
     * Flips the image on its axis depending on the direction given.
     *
     * @param img The image being flipped.
     * @param dir The direction of the flip.
     * @return Returns the mirrored image based on the direction given.
     */
    static BufferedImage mirror(BufferedImage img, MirrorMenuItem.MirrorDirection dir) {
        int width= img.getWidth();
        int height= img.getHeight();
        BufferedImage newImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB );
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                if(dir== MirrorMenuItem.MirrorDirection.VERTICAL){
                    newImg.setRGB(j,height-i-1,img.getRGB(j,i));
                }else{
                    newImg.setRGB(width-j-1,i,img.getRGB(j,i));
                }

            }
        }
        return newImg;
    }

    /**
     * Rotates the image depending on the direction given.
     *
     * @param img The image being rotated.
     * @param dir The direction being rotated towards.
     * @return A rotated version of the image.
     */
    static BufferedImage rotate(BufferedImage img, RotateMenuItem.RotateDirection dir) {
        int width= img.getWidth();
        int height= img.getHeight();
        BufferedImage newImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB );
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                if(dir==RotateMenuItem.RotateDirection.CLOCKWISE){
                    newImg.setRGB(height-i-1,j,img.getRGB(j,i));
                }else{
                    newImg.setRGB(i,width-j-1,img.getRGB(j,i));
                }
            }
        }
        return newImg;
    }

    /**
     * Repeats the given image n times in the direction given.
     *
     * @param img The given image.
     * @param n   The number of times repeated.
     * @param dir The direction being repeated in.
     * @return A new image made up of the multiple of the given image repeated in a given direction.
     */
    static BufferedImage repeat(BufferedImage img, int n, RepeatMenuItem.RepeatDirection dir) {
        int width= img.getWidth();
        int height= img.getHeight();
        BufferedImage newImg;
        if(dir== RepeatMenuItem.RepeatDirection.HORIZONTAL) {
            newImg = new BufferedImage(width * n, height, BufferedImage.TYPE_INT_RGB);
            for(int k=0;k<n;k++) {
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        newImg.setRGB(k*width+j,i,img.getRGB(j,i));
                    }
                }
            }
        }else {
            newImg = new BufferedImage(width,height*n,BufferedImage.TYPE_INT_RGB );
           for(int k=0;k<n;k++) {
               for (int i = 0; i < height; i++) {
                   for (int j = 0; j < width; j++) {
                       newImg.setRGB(j,k*height+i,img.getRGB(j,i));

                   }
               }
           }
        }
        return newImg;
    }

    /**
     * Zooms in on the image. The zoom factor increases in multiplicatives of 10% and
     * decreases in multiplicatives of 10%.
     *
     * @param img        the original image to zoom in on. The image cannot be already zoomed in
     *                   or out because then the image will be distorted.
     * @param zoomFactor The factor to zoom in by.
     * @return the zoomed in image.
     */
    static BufferedImage zoom(BufferedImage img, double zoomFactor) {
        int newImageWidth = (int) (img.getWidth() * zoomFactor);
        int newImageHeight = (int) (img.getHeight() * zoomFactor);
        BufferedImage newImg = new BufferedImage(newImageWidth, newImageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = newImg.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(img, 0, 0, newImageWidth, newImageHeight, null);
        g2d.dispose();
        return newImg;
    }
}
